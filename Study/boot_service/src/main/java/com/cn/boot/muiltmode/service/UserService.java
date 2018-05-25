package com.cn.boot.muiltmode.service;



import com.cn.boot.muiltmode.entity.User;
import io.swagger.models.auth.In;

import java.util.List;

public interface UserService {
    List<User> getAll();
    Boolean addUser(User user);
    Boolean delUser(Integer id);
    Boolean updUser(User user);
    User findByCode(Integer id);
}