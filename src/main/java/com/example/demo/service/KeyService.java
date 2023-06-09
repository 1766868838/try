package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.utils.PasswordToKey;

import jakarta.annotation.Resource;


/**
 * 用户名密码业务类
 */
@Service
public class KeyService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
	UserMapper userMapper;

    /**
     * 登录业务
     * @param username
     * @param password
     * @return 用户名密码是否匹配
     * @throws UnsupportedEncodingException
     * @throws DataAccessException
     * @1766868838
     */
    public Boolean loginKey(String username,String password) throws UnsupportedEncodingException{
        try{
            Boolean result;//函数返回的结果
            //按username返回需要的redis hash表存入objectMap
            Map<Object, Object> objectMap = redisTemplate.opsForHash().entries(username.toString());
            //System.out.println(objectMap.get("password"));

            if(objectMap.isEmpty()){
                result = false;
            }
            else{
                //与注册时类似的密码加密过程
                byte[] salt = new byte[16];
                salt = PasswordToKey.hexStringToBytes(objectMap.get("password").toString().substring(0, 32));
                String newKey = PasswordToKey.decrypt(password,salt);
                String oldKey = objectMap.get("password").toString();
                if(newKey.equals(oldKey)){
                    //密码正确，登录界面
                    result = true;
                }
                else{
                    //密码错误，提示错误信息
                    result = false;
                }
            }

            return result;

        }
        catch(DataAccessException exception){
            return false;
        }
    }

    /**
     * 应当在前端先与域名融合进行一次哈希运算，再在服务端将这次哈希运算的结果与随机产生的盐值再进行一次哈希运算存入数据库。
     * @param username
     * @param password
     */
    public Boolean registKey(String username,String password,String email){
        //哈希运算得到密钥
        password = PasswordToKey.main(password);

        //写入数据库
        User user = new User(username,password,email);
        try{
            //插入数据库
            userMapper.insert(user);
            //更新缓存
            Jackson2HashMapper mapper = new Jackson2HashMapper(true);
            Map<String,Object>map = mapper.toHash(user);
            redisTemplate.opsForHash().putAll(user.getUsername(), map);
            return true;
        }
        catch(DataAccessException exception){
            return false;
        }
    }
}
