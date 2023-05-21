package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
	UserMapper userMapper;
    /**
     * 登录业务
     * @param username
     * @param password
     * @return 用户名密码是否匹配
     * @throws UnsupportedEncodingException
     */
    public Boolean loginKey(String username,String password) throws UnsupportedEncodingException{
        
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> list = userMapper.selectList(queryWrapper);
		User user = list.get(0);
        byte[] salt = new byte[16];
        salt = PasswordToKey.hexStringToBytes(user.getPassword().substring(0, 32));
        byte[] key = PasswordToKey.main(password,salt);
        byte[] innerkey = PasswordToKey.hexStringToBytes(user.getPassword().substring(32, 64));
        Boolean result;
        if(Arrays.equals(innerkey,key)){
            //密码正确，登录界面
            result = true;
        }
        else{
            //密码错误，提示错误信息
            result = false;
        }
        return result;
    }

    /**
     * 应当在前端先与域名融合进行一次哈希运算，再在服务端将这次哈希运算的结果与随机产生的盐值再进行一次哈希运算存入数据库。
     * @param username
     * @param password
     */
    public Boolean registKey(String username,String password,String email){
        //哈希运算得到密钥
        Security.addProvider(new BouncyCastleProvider());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] key = PasswordToKey.main(password,salt);
        byte[] keyByte = new byte[salt.length + key.length];
        System.arraycopy(salt, 0, keyByte, 0, salt.length);
        System.arraycopy(key, 0, keyByte, salt.length, key.length);
        password = new BigInteger(1, keyByte).toString(16);;
        //写入数据库
        User user = new User(username,password,email);
        int i = userMapper.insert(user);
        if(i>0){
            return true;
        }
        else{
            return false;
        }
    }

    public List<User> findAll(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",1766868838);
        List<User> list = userMapper.selectList(queryWrapper);
        return list;
    }
}
