package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.service.KeyService;

import java.util.List;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/key")
public class KeyController {
    
    @Resource
    KeyService keyService;

    /**
     * 登录,前端地址格式应为http://localhost:8081/key/login?username=xxxx&password=xxxxx
     * @param username
     * @param password
     * @return 登录成功返回用户的用户名，登录失败返回错误
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/login")
    public String login(String username,String password) throws UnsupportedEncodingException{
        if(keyService.loginKey(username,password)){
            //登录成功
            return username;
        }
        else {
            return "error";
        }
    }

    /**
     * 注册，用户必须在这里输入三个量。
     * @param username
     * @param password
     * @param email
     */
    @RequestMapping("/regist")
    public Boolean regist(String username,String password,String email){
        if(keyService.registKey(username, password, email)){
            return true;
        }
        else {
            return false;
        }
    }
    @RequestMapping("/findAll")
    public List<User> findAll(){
        return keyService.findAll();
    }
}
