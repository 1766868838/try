package com.example.demo.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;

import jakarta.annotation.Resource;

/**
 * 管理业务类
 */
@Service
public class InfService {
    
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    UserMapper userMapper;

    /**
     * 返回所有数据
     * @return List<User>
     */
    public List<User> findAll(){
        List<User> list = userMapper.selectList(null);
        return list;
    }
}
