package com.example.demo.init;

import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;

import org.mybatis.logging.Logger;

@Component
public class StartAllJobInit {
    protected Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    @Resource
	UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @PostConstruct
    public void init(){
        System.out.println("容器完成后执行");
        startJob();
    }

    public void startJob(){

        redisTemplate.delete("userInf"); 

        List<User> list = userMapper.selectList(null);   
        //System.out.println(list.get(0));
        //redisTemplate.opsForList().rightPushAll("userInf", list);
        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
        
        for (User user : list) {
            Map<String,Object>map = mapper.toHash(user);
            redisTemplate.opsForHash().putAll(user.getUsername(), map);
        }
        //Global.list = redisTemplate.opsForList().range("userInf", 0,-1);
        //redisTemplate.opsForHash().
    }
}
