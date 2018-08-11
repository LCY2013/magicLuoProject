package com.lcydream.project.start;

import com.alibaba.fastjson.JSON;
import com.lcydream.project.config.MagicConfiguration;
import com.lcydream.project.executor.ExecutorFactory;
import com.lcydream.project.session.MagicSqlSession;
import com.lcydream.project.start.entity.Member;
import com.lcydream.project.start.mapper.MemberMapper;

import java.util.List;
import java.util.Map;

/**
 * TestMain
 *
 * @author Luo Chun Yun
 * @date 2018/8/6 23:06
 */
public class TestMain {

    public static void main(String[] args) throws Exception{
        MagicConfiguration configuration = new MagicConfiguration();
        configuration.scanPath("com.lcydream.project.start.mapper");
        configuration.build();
        MagicSqlSession sqlSession = new MagicSqlSession(configuration,
                ExecutorFactory.get(ExecutorFactory.ExecutorType.CACHING,configuration));
        MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
        //System.out.println(mapper.insertMember(3,"yyr"));
        //System.out.println(mapper.insertMember(4,"yyr3"));
        //System.out.println(mapper.updateMember("yyr4",4));
        /*Map<String, Object> stringObjectMap = mapper.selectAll();
        System.out.println(JSON.toJSONString(stringObjectMap));*/
        //List<Map<String, Object>> member = mapper.selectMember();
        List<Map<String, Object>> member = mapper.selectMember();
        System.out.println(JSON.toJSONString(member));
        System.out.println(mapper.deleteMemberById(3));
        List<Map<String, Object>> member1 = mapper.selectMember();
        //Member member = mapper.selectOneMember(1);
        //Member member = mapper.selectOneMemberByName("magic");
        //Member member = mapper.selectOneMemberByIdAndName(1,"magic");
        System.out.println(JSON.toJSONString(member1));
        List<Map<String, Object>> member2 = mapper.selectMember();
        System.out.println(JSON.toJSONString(member2));
        //System.out.println(String.format("%d , %sj",new Object[]{1,"k"}));
    }
}
