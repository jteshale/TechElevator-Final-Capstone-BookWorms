package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookAPI {
    private int number_of_pages;
    private String title;
    @JsonProperty("works")
    private ArrayList<LinkedHashMap<String, String>> works;
    @JsonProperty("authors")
    private ArrayList<LinkedHashMap<String, String>> authors;

    public int getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(int number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<LinkedHashMap<String, String>> getWorks() {
        return works;
    }

    public void setWorks(ArrayList<LinkedHashMap<String, String>> works) {
        this.works = works;
    }

    public ArrayList<LinkedHashMap<String, String>> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<LinkedHashMap<String, String>> authors) {
        this.authors = authors;
    }
}
