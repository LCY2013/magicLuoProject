package com.lcydream.project.start.mapper;

import com.lcydream.project.annotation.Sql;
import com.lcydream.project.start.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * MemberMapper
 *
 * @author Luo Chun Yun
 * @date 2018/8/6 23:06
 */
public interface MemberMapper {

    @Sql(sql = "select * from member")
    Map<String,Object> selectAll();
    @Sql(sql = "select * from member")
    List<Map<String,Object>> selectMember();
    @Sql(sql = "select * from member where id = #{id}")
    List<Map<String,Object>> selectMemberList(int id);
    @Sql(sql = "select * from member where id = #{id} ")
    Member selectOneMember(int id);
    @Sql(sql = "select * from member where name = #{magic} ")
    Member selectOneMemberByName(String magic);
    @Sql(sql = "select * from member where id = #{id} and name = #{name} ")
    Member selectOneMemberByIdAndName(int id,String name);
    @Sql(sql = "insert into member(id,name) values(#{id},#{name})")
    int insertMember(int id,String name);
    @Sql(sql = "update member set name=#{name} where id =#{id}")
    int updateMember(String name,int id);
    @Sql(sql = "delete from member where id =#{id}")
    int deleteMemberById(int id);
}
