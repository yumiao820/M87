package com.cn.muiltmodel.service;

import com.cn.muiltmodel.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getAll();
    public Boolean addUser(User user);
    public Boolean delUser(Integer id);
    public Boolean updUser(User user);
    //按条件查询
    public User findByParam(Map<String,Object> map);
    //根据条件查询用户集合
    public List<User> getByMap(Map<String,Object> map);
}
