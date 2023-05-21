package com.example.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 邮件业务类
 * @author qzz
 */
@Service
public class MailService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注入邮件工具类
     */
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 检测邮件信息类
     * @param to
     */
    private void checkMail(String to){
        if(StringUtils.isEmpty(to)){
            throw new RuntimeException("邮件收信人不能为空");
        }
    }

    /**
     * 发送纯文本邮件
     * @param to
     * @param subject
     * @param text
     * @throws jakarta.mail.MessagingException
     */
    public void sendTextMailMessage(String to,HttpSession session) throws jakarta.mail.MessagingException{
        
        checkMail(to);
        String code = randomCode();

        //true 代表支持复杂的类型
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
        //邮件发信人
        mimeMessageHelper.setFrom(sendMailer);
        //邮件收信人  1或多个
        mimeMessageHelper.setTo(to.split(","));
        //邮件主题
        mimeMessageHelper.setSubject("测试邮件");
        //邮件内容
        mimeMessageHelper.setText("您收到的验证码是："+code);
        //邮件发送时间
        mimeMessageHelper.setSentDate(new Date());

        //发送邮件
        javaMailSender.send(mimeMessageHelper.getMimeMessage());
        System.out.println("发送邮件成功："+sendMailer+"->"+to);
    
        //将发送的邮件地址与code对应，直接存在redis内部
        stringRedisTemplate.opsForValue().set(to.toString(),code.toString(),60*10,TimeUnit.SECONDS);
    }


    /**
     * 检查验证码是否正确
     * @param to
     * @param code
     * @param session
     * @return 正确或错误
     */
    public boolean checkTextMail(String to, String code,HttpSession session) {
        String key = stringRedisTemplate.opsForValue().get(to.toString());
        boolean result;
        if(key.equals(code)){
            result = true;
        }
        else result = false;

        return result;
    }

    /**
     * @return 6位随机数
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i<6 ;i++){
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}