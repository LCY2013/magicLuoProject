package com.lcydream.project.chat.protocal.coder;

import com.lcydream.project.chat.protocal.command.IMP;
import com.lcydream.project.chat.protocal.content.IMMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IMDeCoder
 * 消息解码器
 * @author Luo Chun Yun
 * @date 2018/10/26 15:42
 */
public class IMDeCoder extends ByteToMessageDecoder {

    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try{
            //读取可用字节
            final int length = in.readableBytes();
            final byte[] arrayBytes = new byte[length];
            //消息内容
            String content = new String(arrayBytes, in.readerIndex(), length);

            if(!"".equals(content.trim())){
                if(!IMP.isIMP(content)){
                    ctx.channel().pipeline().remove(this);
                    return;
                }
            }

            in.getBytes(in.readerIndex(),arrayBytes,0,length);
            out.add(new MessagePack().read(arrayBytes, IMMessage.class));
            in.clear();
        }catch (Exception e){
            ctx.channel().pipeline().remove(this);
        }
    }

    /**
     * 解析成自定义协议
     * @param msg 消息
     * @return 自定义对象
     */
    public IMMessage deCoder(String msg){
        if(null == msg || "".equals(msg.trim())){
            return null;
        }
        try {
            //匹配消息
            Matcher matcher = pattern.matcher(msg);
            //取出自定义消息头信息，命令信息等
            String header = "";
            //取出发送的消息内容
            String content = "";
            if(matcher.matches()){
                header = matcher.group(1);
                content = matcher.group(3);
            }
            String[] headers = header.split("\\]\\[");
            long time=0;
            try {
                time = Long.parseLong(headers[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
            //获取昵称
            String nickName = headers[2];
            //昵称最多10个字
            nickName = nickName.length() < 10 ? nickName : nickName.substring(0,9);

            if(msg.startsWith("[" + IMP.LOGIN.getName() + "]")){
                return new IMMessage(headers[0],time,nickName);
            }else if(msg.startsWith("[" + IMP.CHAT.getName() + "]")){
                return new IMMessage(headers[0],time,nickName,content);
            }else if(msg.startsWith("[" + IMP.FLOWER.getName() + "]")){
                return new IMMessage(headers[0],time,nickName);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
