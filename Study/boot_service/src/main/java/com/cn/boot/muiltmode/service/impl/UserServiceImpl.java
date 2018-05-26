package com.cn.boot.muiltmode.service.impl;

import com.cn.boot.muiltmode.dao.UserDao;
import com.cn.boot.muiltmode.entity.User;
import com.cn.boot.muiltmode.service.UserService;
import com.cn.boot.muiltmode.service.redis.UserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRedis userRedis;

    @Override
    public List<User> getAll() {
        List<User> list=userDao.getAll();
        return list;
    }

    //前缀
    private String prefix="boot:mysql:guafu";

    @Override
    public Boolean addUser(User user) {
        if(userDao.addUser(user)>0){
            userRedis.add(prefix+user.getId(),30L,user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delUser(Integer id) {
        userRedis.delete(prefix+id);
        if(userDao.delUser(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updUser(User user) {
        if (userDao.updUser(user)>0){
//            删除之前的
            userRedis.delete(prefix+user.getId());
//            存储当前值
            userRedis.add(prefix+user.getId(),30L,user);
            return true;
        }
        return false;
    }

    @Override
    public User findByCode(Integer integer) {
//        先去redis查询 如不存在
        User user=userRedis.get(prefix+integer);
        if(user==null){
//          取数据库查
            user=userDao.findByCode(integer);
            if(user!=null){
//                查到结果存入redis
                userRedis.add(prefix+user.getId(),30L,user);
            }
        }
        return user;
    }
}
