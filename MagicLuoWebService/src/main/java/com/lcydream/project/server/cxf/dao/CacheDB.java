package com.lcydream.project.server.cxf.dao;

import com.lcydream.project.server.cxf.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CacheDB
 * 缓存数据
 * @author Luo Chun Yun
 * @date 2018/8/25 20:42
 */
@Component
public class CacheDB {

    private Map<Integer, User> userMap = new HashMap<>();

    public CacheDB(){
        for(int i=0;i<10;i++){
            userMap.put(i,new User(i,"magic" + i));
        }
    }

    public List<User> getUserList(){
        List<User> list = new ArrayList<>();
        for (int i = 0 ; i<userMap.size();i++){
            list.add(userMap.get(i));
        }
        return list;
    }

    public User getUserById(int id){
        return userMap.get(id);
    }

    public int deleteUserById(int id){
        if(userMap.get(id)==null){
            return 0;
        }
        userMap.remove(id);
        return 1;
    }

    public int addUser(User user){
        if(userMap.get(user.getId())!=null || user==null){
            return 0;
        }
        userMap.put(user.getId(),user);
        return 1;
    }

    public int updateUser(User user){
        if(userMap.get(user.getId())==null || user==null){
            return 0;
        }
        userMap.put(user.getId(),user);
        return 1;
    }
}
