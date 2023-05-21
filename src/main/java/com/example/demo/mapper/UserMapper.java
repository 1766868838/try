package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.User;

/**
 *
 * @ClassName: UserMapper
 * @Description:
 * @author: zyp
 */

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
