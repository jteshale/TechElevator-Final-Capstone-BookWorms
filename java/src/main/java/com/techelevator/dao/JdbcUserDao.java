package com.techelevator.dao;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.model.Book;
import com.techelevator.model.UserNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			throw new UserNotFoundException();
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password, String role) {
        String insertUserSql = "insert into users (username,password_hash,role) values (?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole) == 1;
    }

    @Override
    public void createFamilyAccount(String familyName, int userId) {
        String sql = "INSERT INTO family_account (family_name) values (?) RETURNING family_id";
        Integer familyId = 0;
        try {
            familyId = jdbcTemplate.queryForObject(sql, Integer.class, familyName);
        } catch (DataAccessException e) {
//            return;
        }

        sql = "INSERT INTO family_user (family_id, user_id) values (?, ?)";
        try {
            jdbcTemplate.update(sql, familyId, userId);
        } catch (DataAccessException e) {
            return;
        }

        return;
    }


    @Override
    public String getFamilyNameForUsername(String username) {
        String sql = "SELECT family_name from users u\n" +
                "JOIN family_user ON family_user.user_id = u.user_id\n" +
                "JOIN family_account fa ON fa.family_id = family_user.family_id \n" +
                "where u.username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, username);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }
    @Override
    public void addFamilyMember(int curId, int addedId) {
        String sql = "SELECT family_id FROM family_user WHERE user_id = ?";
        Integer familyId = jdbcTemplate.queryForObject(sql, Integer.class, curId);

        sql = "INSERT INTO family_user (family_id, user_id) values (?, ?)";
        jdbcTemplate.update(sql, familyId, addedId);
    }

    @Override
    public List<User> familyMembers(int userId){
        String sql = "SELECT family_id FROM family_user WHERE user_id = ?";
        SqlRowSet family = jdbcTemplate.queryForRowSet(sql, userId);
        Integer familyId = 0;
        if (family.next()) {
           familyId = family.getInt("family_id");
        }

        sql = "SELECT * FROM users u JOIN family_user fu ON u.user_id = fu.user_id WHERE fu.family_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, familyId);
        List<User> users = new ArrayList<>();
        while(rowSet.next()){
            users.add(mapRowToUser(rowSet));
        }
        return users;
    }

    @Override
    public void updateBookStatus(int userId, int bookId, String status) {
        String sql = "UPDATE book_user SET status = ? WHERE user_id = ? AND book_id = ?";
        jdbcTemplate.update(sql, status, userId, bookId);
    }

    @Override
    public List<Book> currentlyReading(int userId){
        String sql = "SELECT * FROM book b JOIN book_user bu ON b.book_id = bu.book_id WHERE user_id = ? AND status = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, "READING");
        List<Book> books = new ArrayList<>();
        while (rows.next()){
            books.add(mapRowToBook(rows));
        }
        return books;
    }

    @Override
    public List<Book> finishedReading(int userId){
        String sql = "SELECT * FROM book b JOIN book_user bu ON b.book_id = bu.book_id WHERE user_id = ? AND status = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, "READ");
        List<Book> books = new ArrayList<>();
        while (rows.next()){
            books.add(mapRowToBook(rows));
        }
        return books;
    }


    @Override
    public int getBooksByStatus(int userId,String status){
        String sql = "SELECT COUNT(*) FROM book_user where status = ? and user_id = ?";
        Integer bookCount = jdbcTemplate.queryForObject(sql, Integer.class, status, userId);
        return bookCount;
    }

    @Override
    public  int getMinutesRead(int userId){
        String sql = "SELECT sum(minutes_read) as totalMinutes from reading_details where user_id = ?";
        Integer totalMinutes = jdbcTemplate.queryForObject(sql, Integer.class,  userId);
        if(totalMinutes == null){
            totalMinutes = 0;
        }
        return totalMinutes;
}
    @Override
    public int minutesReadFromISBN(int userId, String ISBN){
        String sql = "SELECT sum(minutes_read) as totalMinutes FROM reading_details WHERE isbn = ? AND user_id = ?";
        Integer totalMinutes = jdbcTemplate.queryForObject(sql, Integer.class, ISBN, userId);
        return totalMinutes;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }

    private Book mapRowToBook(SqlRowSet row){
        Book book = new Book();
        book.setDescription(row.getString("description"));
        book.setAuthor(row.getString("author"));
        book.setNumberofpages(row.getInt("numberofpages"));
        book.setBook_id(row.getInt("book_id"));
        book.setIsbn(row.getString("isbn"));
        book.setGenre(row.getString("genre"));
        book.setRating(row.getString("rating"));
        book.setFormat(row.getString("format"));
        book.setBook_name(row.getString("book_name"));
        return book;
    }
}
