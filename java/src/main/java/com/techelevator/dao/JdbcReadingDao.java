package com.techelevator.dao;

import com.techelevator.model.Reading;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcReadingDao implements ReadingDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReadingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void logReading(Reading reading) {
        String sql = "INSERT INTO reading_details (user_id, book_id, isbn,  minutes_read) values(?, ?, ?,?) ";

            jdbcTemplate.update(sql,  reading.getUser_id(), reading.getBook_id(), reading.getIsbn(), reading.getMinutes_read());

    }
}
