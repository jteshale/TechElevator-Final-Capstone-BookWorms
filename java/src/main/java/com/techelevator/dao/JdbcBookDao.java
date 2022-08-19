package com.techelevator.dao;

import com.techelevator.model.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBookDao implements BookDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createBook(Book book, int userId){
//        Original code - 8/16/22
//        String sql = "INSERT INTO book (book_name, isbn, description, author, numberofpages, rating, genre) values (?, ?, ?, ?, ?, ?,?) RETURNING book_id";
//
//        Integer bookId;
//        try {
//            bookId = jdbcTemplate.queryForObject(sql, Integer.class, book.getBook_name(), book.getIsbn(), book.getDescription(), book.getAuthor(), book.getNumberofpages(), book.getRating(), book.getGenre());
//        } catch (DataAccessException e) {
//            return;
//        }
//
//        sql = "INSERT INTO book_user (book_id, user_id) values (?, ?)";
//        try {
//            jdbcTemplate.update(sql, bookId, userId);
//        } catch (DataAccessException e) {
//            return;
//        }

        String bookCountSQL = "Select book_id from book where isbn = ?";
        String recordCountSQL = ("Select count(*) from book_user where book_id = ? and user_id = ?");
        Integer curBookId = 0;
        Integer bookUserExists = 0;
        String sql;

        try {
            curBookId = jdbcTemplate.queryForObject(bookCountSQL, Integer.class, book.getIsbn());
//            System.out.println("loop0  BookId : " + curBookId + " -- UserId : " + userId);
        }catch (DataAccessException e) {
//            return;
        }

        try {
            bookUserExists = jdbcTemplate.queryForObject(recordCountSQL, Integer.class, book.getBook_id(), userId);
//            System.out.println("loopA  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);

        }catch (DataAccessException e) {
            e.getMessage();
//            return;
        }


        if(curBookId == 0){
//            System.out.println("loop1  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);
            Integer bookId;
            sql = "INSERT INTO book (book_name, isbn, description, author, numberofpages, rating, genre) values (?, ?, ?, ?, ?, ?,?) RETURNING book_id";

            try {
                bookId = jdbcTemplate.queryForObject(sql, Integer.class, book.getBook_name(), book.getIsbn(), book.getDescription(), book.getAuthor(), book.getNumberofpages(), book.getRating(), book.getGenre());
            } catch (DataAccessException e) {
                return;
            }

            sql = "INSERT INTO book_user (book_id, user_id) values (?, ?)";
            try {
                jdbcTemplate.update(sql, bookId, userId);
            } catch (DataAccessException e) {
                return;
            }

        }else if (curBookId != 0 && bookUserExists == 0) {
//            System.out.println("loop2  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);
            sql = "INSERT INTO book_user (book_id, user_id) values (?, ?)";
            try {
                jdbcTemplate.update(sql, curBookId, userId);
            } catch (DataAccessException e) {
                return;
            }

        }
    }

    public int getIdByIsbn(String Isbn){
        String sql = "SELECT book_id FROM book WHERE isbn = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, Isbn);
    }

    public ArrayList<Book> userReadingList(int userId){
        String sql = "SELECT b.* FROM book_user bu JOIN book b ON bu.book_id = b.book_id WHERE bu.user_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);

        ArrayList<Book> books = new ArrayList<>();
        while(rows.next()){
            books.add(mapRowToBook(rows));
        }
        return books;
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
