package com.hu.video.mapper;

import com.hu.video.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);


//    @Insert("insert  into user(name,password,birth,sex) values(#{name},#{password},#{birth},#{sex})")
    @Insert("insert  into user(name,password,birth) values(#{name},#{password},#{birth})")
    boolean setUser(User user);
}
