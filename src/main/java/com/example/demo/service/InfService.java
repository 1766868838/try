package com.example.demo.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.utils.PasswordToKey;

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

    public Boolean delete(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        int i = userMapper.delete(queryWrapper);
        if(i>0) return true;
        else return false;
    }

    public Boolean update(String oldUsername,String newUsername,String password,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", oldUsername);
        User user = userMapper.selectList(queryWrapper).get(0);
        //哈希获得密钥
        password = PasswordToKey.main(password);

        if(newUsername.isEmpty()){
            newUsername = oldUsername;
        }
        if(password.isEmpty()){
            password = user.getPassword();
        }
        if(email.isEmpty()){
            email = user.getEmail();
        }

        user = new User(newUsername, password ,email);
        int i = userMapper.update(user, queryWrapper);
        if(i>0) return true;
        else return false;
    }
}
