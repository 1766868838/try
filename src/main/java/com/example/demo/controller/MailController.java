package com.example.demo.controller;

import com.example.demo.service.MailService;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件处理
 * @author zyp
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MailService mailService;

    /**
     * 发送文本邮件,前端地址格式应为http://localhost:8081/mail/sendTextMail
     * @param to
     * @throws MessagingException
     */
    @RequestMapping("/sendTextMail")
    public void sendTextMail(String to , HttpSession httpSession) throws MessagingException{
        mailService.sendTextMailMessage(to,httpSession);
    }

    /**
     * 判断验证码是否正确,前端地址格式应为http://localhost:8081/mail/checkTextMail
     * @param to
     * @param httpSession
     * @param code
     */
    @RequestMapping("/checkTextMail")
    public String checkTextMail(String to,String code, HttpSession httpSession){
        boolean result = mailService.checkTextMail(to ,code ,httpSession);

        //在实际使用中不应该是返回一个字符串，直接跳转可能更好。
        if(result){
            return "right";
        }
        else{
            return "false";
        }
    }
}
