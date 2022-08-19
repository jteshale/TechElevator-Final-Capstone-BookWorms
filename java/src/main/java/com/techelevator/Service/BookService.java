package com.techelevator.Service;

import com.techelevator.dao.BookDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.AuthorAPI;
import com.techelevator.model.Book;
import com.techelevator.model.BookAPI;
import com.techelevator.model.User.WorksAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BookService {
    private BookDao bookDao;
    private UserDao userDao;

    public BookService(BookDao bookDao, UserDao userDao){
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    public void createBook(Book createdBook, String username){
        bookDao.createBook(createdBook, userDao.findIdByUsername(username));
    }

//    public Book createBook(String Isbn, String curUsername){
//        String url = "https://openlibrary.org/isbn/" + Isbn + ".json";
//        RestTemplate restTemplate = new RestTemplate(); // Create a new client
//
//        Book newBook = new Book();
//
//        BookAPI bookAPI = restTemplate.getForObject(
//                url,
//                BookAPI.class);
//
//        if(bookAPI.getWorks() != null){
//            String worksUrl = "https://openlibrary.org" + bookAPI.getWorks().get(0).get("key") + ".json";
//            WorksAPI worksAPI = restTemplate.getForObject(worksUrl, WorksAPI.class);
//            newBook.setDescription(worksAPI.getDescription());
//        }
//
//        if(bookAPI.getAuthors() != null){
//            String authorUrl = "https://openlibrary.org" + bookAPI.getAuthors().get(0).get("key") + ".json";
//            AuthorAPI authorAPI = restTemplate.getForObject(authorUrl, AuthorAPI.class);
//            newBook.setAuthor(authorAPI.getName());
//        }
//
//
//
//        newBook.setNumberofpages(bookAPI.getNumber_of_pages());
//        newBook.setBook_name(bookAPI.getTitle());
//        newBook.setIsbn(Isbn);
//
//        bookDao.createBook(newBook, userDao.findIdByUsername(curUsername));
//        return newBook;
//    }

    public List<Book> userReadingList(int userId){
        return bookDao.userReadingList(userId);
    }

    public int getIdByIsbn(String Isbn){
        return bookDao.getIdByIsbn(Isbn);
    }
}
