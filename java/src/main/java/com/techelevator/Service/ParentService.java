package com.techelevator.Service;

import com.techelevator.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class ParentService {
    private UserDao userDao;
    public ParentService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createChildAccount(String username, String password) {
        userDao.create(username, password, "ROLE_USER");
    }

    public void createFamilyAccount(String familyName, String curUsername) {
        int curId = userDao.findIdByUsername(curUsername);
        userDao.createFamilyAccount(familyName, curId);
    }

    public String getFamilyForUser(String curUsername) {
        return userDao.getFamilyNameForUsername(curUsername);
    }

    public void addFamilyMember(String username, String curUsername){
        int curId = userDao.findIdByUsername(curUsername);
        int addedId = userDao.findIdByUsername(username);
        userDao.addFamilyMember(curId, addedId);
    }
}
