package com.techelevator.dao;

import com.techelevator.model.Prize;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPrizeDao implements PrizeDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcPrizeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prize> findAll() {
        List<Prize> prizes = new ArrayList<>();
        String sql = "select * from prize";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Prize prize= mapRowToPrize(results);
            prizes.add(prize);
        }

        return prizes;
    }


    private Prize mapRowToPrize(SqlRowSet row){
        Prize prize = new Prize();
        prize.setPrize_id(row.getInt("prize_id"));
        prize.setName(row.getString("name"));
        prize.setDescription(row.getString("description"));
        prize.setMileStone(row.getInt("mileStone"));
        prize.setMaxPrizes(row.getInt("maxPrizes"));
        prize.setStartDate(row.getDate("startDate"));
        prize.setEndDate(row.getDate("endDate"));
        return prize;
    }

}
