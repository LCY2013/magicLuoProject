package com.lcydream.project.chat.protocal.processor;

import com.alibaba.fastjson.JSONObject;
import com.lcydream.project.chat.protocal.coder.IMDeCoder;
import com.lcydream.project.chat.protocal.coder.IMEnCoder;
import com.lcydream.project.chat.protocal.command.IMP;
import com.lcydream.project.chat.protocal.content.IMMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * MsgProcessor
 *  自定义消息协议的封装逻辑实现
 * @author Luo Chun Yun
 * @date 2018/10/26 14:47
 */
public class MsgProcessor {

    /**
     * 记录在线用户
     */
    private final static ChannelGroup onlineUsers =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 定义一些扩展属性
     */
    private final AttributeKey<String> NICK_NAME = AttributeKey.valueOf("nickName");
    private final AttributeKey<String> IP_ADDR = AttributeKey.valueOf("ipAddr");
    private final AttributeKey<JSONObject> ATTRS = AttributeKey.valueOf("attrs");

    /**
     * 自定义编码器
     */
    private IMEnCoder imEnCoder = new IMEnCoder();

    /**
     * 自定义解码器
     */
    private IMDeCoder imDeCoder = new IMDeCoder();

    /**
     * 获取用户昵称
     * @param client 客户端
     * @return nickName
     */
    public String getNickName(Channel client){
        return client.attr(NICK_NAME).get();
    }

    /**
     * 获取用户远程IP地址
     * @param client 客户端
     * @return address
     */
    public String getAddress(Channel client){
        return client.remoteAddress().toString().replaceFirst("/","");
    }

    /**
     * 获取扩展属性
     * @param client 客户端
     * @return attrs
     */
    public JSONObject getAttrs(Channel client){
        try {
            return client.attr(ATTRS).get();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 设置扩展属性
     * @param client 客户端连接信息
     * @param key 扩展字段的键
     * @param value 扩展字段的值
     */
    public void setAttrs(Channel client,String key,Object value){
        try {
            JSONObject jsonObject = client.attr(ATTRS).get();
            jsonObject.put(key, value);
            client.attr(ATTRS).set(jsonObject);
        }catch (Exception e){
            JSONObject json = new JSONObject();
            json.put(key,value);
            client.attr(ATTRS).set(json);
        }
    }

    /**
     * 登出信息通知
     * @param client 客户端连接信息
     */
    public void logout(Channel client){
        //如果nickName为null，没有遵从聊天协议的连接，表示非法登录
        if(getNickName(client) == null){
            return;
        }
        for (Channel channel : onlineUsers){
            IMMessage content = new IMMessage(IMP.SYSTEM.getName(),systemTime(),onlineUsers.size(),getNickName(client)+" 离开");
            String encodeMessage = imEnCoder.encode(content);
            channel.writeAndFlush(new TextWebSocketFrame(encodeMessage));
        }
        onlineUsers.remove(client);
    }

    /**
     * 发送消息
     * @param client 客户端连接信息
     * @param msg 消息内容
     */
    public void sendMsg(Channel client,IMMessage msg){
        sendMsg(client,imEnCoder.encode(msg));
    }

    /**
     * 发送消息
     * @param client 客户端连接信息
     * @param msg 消息内容
     */
    public void sendMsg(Channel client,String msg){
        IMMessage request = imDeCoder.deCoder(msg);
        if(null == request){
            return;
        }

        //获取客户端连接的IP地址
        String address = getAddress(client);

        //处理登录请求
        if(request.getCmd().equals(IMP.LOGIN.getName())){
            client.attr(NICK_NAME).getAndSet(request.getSender());
            client.attr(IP_ADDR).getAndSet(request.getAddr());
            onlineUsers.add(client);

            for (Channel channel : onlineUsers){
                if(channel != client){
                    request = new IMMessage(IMP.SYSTEM.getName(),systemTime(),onlineUsers.size(),getNickName(client)+" 加入");
                }else {
                    request = new IMMessage(IMP.SYSTEM.getName(),systemTime(),onlineUsers.size(),"已经与服务器建立了连接!");
                }
                String content = imEnCoder.encode(request);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
            //处理聊天请求
        }else if(request.getCmd().equals(IMP.CHAT.getName())){
            for (Channel channel : onlineUsers){
                if(channel != client){
                    request.setSender(getNickName(client));
                }else {
                    request.setSender("I");
                }
                request.setTime(systemTime());
                String content = imEnCoder.encode(request);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
            //处理送花请求
        }else if(request.getCmd().equals(IMP.FLOWER.getName())){
            JSONObject attr = getAttrs(client);
            long currTime = systemTime();
            //刷花的时间过短
            if(null != attr){
                Long lastFlowerTime = attr.getLong("lastFlowerTime");
                //10秒内不允许重复刷鲜花
                int secend = 10;
                //计算两个时间的时间差
                long sub = currTime - lastFlowerTime;
                //处理高频送花的逻辑
                if(sub < 1000 * secend){
                    request.setSender("I");
                    request.setCmd(IMP.SYSTEM.getName());
                    request.setContent("您送鲜花太频繁，"+(secend - Math.round(sub / 1000)) + "秒后再试。");
                    String content = imEnCoder.encode(request);
                    client.writeAndFlush(content);
                    return;
                }

                //正常送花
                for (Channel channel : onlineUsers){
                    if(channel == client){
                        request.setSender("I");
                        request.setContent("你送给大家一波鲜花");
                        setAttrs(client,"lastFlowerTime",currTime);
                    }else {
                        request.setSender(getNickName(client));
                        request.setContent(getNickName(client) + " 送来一波鲜花雨");
                    }
                    request.setTime(systemTime());
                    String content = imEnCoder.encode(request);
                    channel.writeAndFlush(content);
                }
            }
        }
    }

    /**
     * 获取当前系统时间
     * @return 返回系统时间的时间戳
     */
    private long systemTime(){
        return System.currentTimeMillis();
    }
}
