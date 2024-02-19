package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;


@Mapper
public interface UserMapper extends BaseMapper<User> {
    /*@Results(id="UserMapper",value = {
            @Result(column="created_at",property="created_at",jdbcType= JdbcType.TIMESTAMP),
            @Result(column="updated_at",property="updated_at",jdbcType= JdbcType.TIMESTAMP),
            @Result(column="deleted_at",property="deleted_at",jdbcType= JdbcType.TIMESTAMP),
            @Result(column="avatar_url",property="avatar_url",jdbcType= JdbcType.VARCHAR)
    })*/

    @Select("select * from user where username=#{username};")
    User selectByName(@Param("username") String username);

}
