package com.lcydream.project.chat.protocal.coder;

import com.lcydream.project.chat.protocal.command.IMP;
import com.lcydream.project.chat.protocal.content.IMMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * IMEnCoder
 * 消息编码器
 * @author Luo Chun Yun
 * @date 2018/10/26 15:43
 */
public class IMEnCoder extends MessageToByteEncoder<IMMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IMMessage msg, ByteBuf out) throws Exception{
        out.writeBytes(new MessagePack().write(msg));
    }

    /**
     * 对自定义消息进行编码
     * @param imMessage 自定义消息
     * @return 字符
     */
    public String encode(IMMessage imMessage){
        if(imMessage == null){
            return "";
        }

        String cmdPrex = "["+imMessage.getCmd()+"]"+"["+imMessage.getTime()+"]";

        if(IMP.LOGIN.getName().equals(imMessage.getCmd())
            || IMP.FLOWER.getName().equals(imMessage.getCmd())
            || IMP.CHAT.getName().equals(imMessage.getCmd())){
            cmdPrex += ("["+imMessage.getSender()+"]");
        }else if(IMP.SYSTEM.getName().equals(imMessage.getCmd())){
            cmdPrex += ("["+imMessage.getOnline()+"]");
        }

        if(!(null == imMessage.getContent() || "".equals(imMessage.getContent()))){
            cmdPrex += (" - " + imMessage.getContent());
        }

        return cmdPrex;
    }
}
