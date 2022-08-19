package com.techelevator.Service;

import com.techelevator.dao.PrizeDao;
import com.techelevator.model.Prize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrizeService {
    private PrizeDao prizeDao;
    public PrizeService(PrizeDao prizeDao) {
        this.prizeDao = prizeDao;
    }

    public List<Prize> findAll() {
        return prizeDao.findAll();
    }
}

