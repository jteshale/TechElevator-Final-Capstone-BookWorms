package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    void createBook(Book book, int userId);

    List<Book> userReadingList(int userId);

    int getIdByIsbn(String Isbn);

}
