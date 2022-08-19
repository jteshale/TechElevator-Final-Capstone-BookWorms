package com.techelevator.Service;

import com.techelevator.dao.ReadingDao;
import com.techelevator.model.Reading;
import org.springframework.stereotype.Component;

@Component
public class ReadingService {
    private ReadingDao readingDao;

    public ReadingService(ReadingDao readingDao){
        this.readingDao = readingDao;

    }

   public void logReading(Reading reading){
        readingDao.logReading(reading);
   }



}
