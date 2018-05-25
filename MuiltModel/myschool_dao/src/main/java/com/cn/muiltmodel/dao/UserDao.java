package com.cn.muiltmodel.dao;

import com.cn.muiltmodel.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    //获取全部信息
    public List<User> getAll();
    //添加用户
    public Integer addUser(User user);
    //删除用户
    public Integer delUser(Integer id);
    //修改用户
    public Integer updUser(User user);
    //按条件查询
    public User findByParam(Map<String,Object> map);
    //根据条件查询用户集合
    public List<User> getByMap(Map<String,Object> map);
}
