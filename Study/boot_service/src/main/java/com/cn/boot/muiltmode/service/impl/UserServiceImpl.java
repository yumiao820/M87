package com.cn.boot.muiltmode.service.impl;

import com.cn.boot.muiltmode.dao.UserDao;
import com.cn.boot.muiltmode.entity.User;
import com.cn.boot.muiltmode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Boolean addUser(User user) {
        if(userDao.addUser(user)>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean delUser(Integer id) {
        if(userDao.delUser(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updUser(User user) {
        if(userDao.updUser(user)>0){
            return true;
        }
        return false;
    }

    @Override
    public User findByCode(Integer integer) {
        return userDao.findByCode(integer);
    }
}
