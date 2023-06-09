package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.service.InfService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("inf")
public class InfController {
    
    @Resource
    InfService infService;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return infService.findAll();
    }

    @RequestMapping("/delete")
    public Boolean delete(String username){
        return infService.delete(username);
    }
    @RequestMapping("/update")
    public Boolean update(String newUsername,String oldUsername,String password,String email){

        return infService.update(oldUsername,newUsername,password,email);
    }
}
