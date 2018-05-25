package com.cn.muiltmodel.service.impl;

import com.cn.muiltmodel.dao.UserDao;
import com.cn.muiltmodel.entity.User;
import com.cn.muiltmodel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public List<User> getAll() {
        return userDao.getAll();
    }

    public Boolean addUser(User user) {
        if(userDao.addUser(user)>0){
            return true;
        }
        return false;
    }

    public Boolean delUser(Integer id) {
        if(userDao.delUser(id)>0){
            return true;
        }
        return false;
    }

    public Boolean updUser(User user) {
        if(userDao.updUser(user)>0){
            return true;
        }
        return false;
    }

    public User findByParam(Map<String, Object> map) {
        return userDao.findByParam(map);
    }

    public List<User> getByMap(Map<String, Object> map) {
        return userDao.getByMap(map);
    }
}
