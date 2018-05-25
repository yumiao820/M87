package com.cn.boot.muiltmode.dao;


import com.cn.boot.muiltmode.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserDao {

    List<User> getAll();
    int addUser(User user);
    int delUser(Integer id);
    int updUser(User user);
     User findByCode(Integer id);
}