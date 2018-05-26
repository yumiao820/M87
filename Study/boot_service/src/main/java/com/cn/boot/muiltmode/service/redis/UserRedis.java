package com.cn.boot.muiltmode.service.redis;

import com.cn.boot.muiltmode.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public User get(String key) {
        User user = null;
        Gson gson = new Gson();
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            user = gson.fromJson(json, User.class);
        }
        return user;
    }

    public List<User> getList(String key) {
        List<User> list = null;
        Gson gson = new Gson();
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            list = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        }
        return list;
    }

    public void add(String key, Long time, User user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, Long time, List<User> user) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}