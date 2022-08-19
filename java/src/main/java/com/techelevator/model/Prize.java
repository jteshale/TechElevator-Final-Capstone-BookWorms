package com.techelevator.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prize {
    private int prize_id;
    private String name;
    private String description;
    private int mileStone;
    private int maxPrizes;
    private Date startDate;
    private Date endDate;

    public Prize( int prize_id, String name, String description, int mileStone, int maxPrizes, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.mileStone  = mileStone;
        this.maxPrizes = maxPrizes;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Prize() {

    }

    public int getPrize_id() {
        return prize_id;
    }

    public void setPrize_id(int prize_id) {
        this.prize_id = prize_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMileStone() {
        return mileStone;
    }

    public void setMileStone(int mileStone) {
        this.mileStone = mileStone;
    }

    public int getMaxPrizes() {
        return maxPrizes;
    }

    public void setMaxPrizes(int maxPrizes) {
        this.maxPrizes = maxPrizes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
