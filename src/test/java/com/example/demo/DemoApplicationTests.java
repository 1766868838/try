package com.example.demo;

import static org.mockito.Mockito.reset;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.demo.pojo.User;

import jakarta.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.UserMapper;

@SpringBootTest()
class DemoApplicationTests {

	@Resource
	UserMapper userMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

	@Resource
	DataSource dataSource;
	@Test
	void jkl() {
		byte[] sdf = {0x1A,0x73,(byte) 0xA5,(byte)0x8C,(byte)0xE9,(byte)0xBB,
		(byte)0xC6,0x3B,(byte)0x8E,0x3D,0x29,0x21,(byte)0xCC,(byte)0xB3,(byte)0xA3,(byte)0xF7};
		System.out.println(sdf.toString());
	}
	
	@Test
	void list(){
		//redisTemplate.opsForHash().delete("1766868838", "");
		redisTemplate.delete("1766868838");
	}
}
