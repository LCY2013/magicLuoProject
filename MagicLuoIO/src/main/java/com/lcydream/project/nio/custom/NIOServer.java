package com.lcydream.project.nio.custom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * NIOServer
 * 网络多客户端聊天室
 * 1.客户端通过java NIO连接服务器，支持客户端的连接
 * 2.客户端初次登陆，服务端提示输入昵称，昵称需要唯一，不唯一提示重新输入，每次发送的内容都带上昵称
 * 3.某一个客户端登陆后，需要将设置好的欢迎信息总人数信息给这个客户端，并且通知其他客户端
 * 4.服务端收到某一个客户端的消息后，转发给其他客户端
 * @author Luo Chun Yun
 * @date 2018/10/18 11:24
 */
public class NIOServer {

    /**
     * 端口号
     */
    private int port = 8080;

    /**
     * 设置字符集编码
     */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * 服务端记录在线人数
     */
    private static HashSet<String> users = new HashSet<>();

    /**
     * 系统提示语
     */
    private static String SYS_HINT = "系统提示：该昵称已经存在，请换一个昵称。";

    /**
     * 系统定义的消息协议
     */
    private static String SYS_USER_CONTENT_SPILIT = "#";

    /**
     * 连接选择器
     */
    private Selector selector=null;

    /**
     * 初始化服务器数据
     * @param port 端口号
     * @throws Exception 异常处理
     */
    public NIOServer(Integer port)throws IOException {
        //不为空就赋值新的端口号
        if(port != null){
            this.port = port;
        }

        //打开nio的通道
        ServerSocketChannel server = ServerSocketChannel.open();
        //绑定端口号
        server.bind(new InetSocketAddress(this.port));
        //设置异步操作
        server.configureBlocking(false);

        //选择器的获取,用于排队
        selector = Selector.open();

        //注册选择器到通道
        server.register(selector, SelectionKey.OP_ACCEPT);

        //启动
        System.out.println("NIO服务端启动。端口号是："+this.port);
    }

    /**
     * 启动监听器
     */
    public void listener() throws Exception{
        //启动一个死循环去监听选择器的事件
        //CPU的频率就可控，这里是一个可控的固定值
        while (true){
            //获取选择器中的总事件数
            int waitNum = selector.select();
            //如果没有事件，就暂停3秒,直到有事件来处理
            if(waitNum == 0){
                TimeUnit.SECONDS.sleep(3);
                continue;
            }
            //获取所有的客户端的事件ID，通过这个事件ID获取可用通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历所有可用通道进行事件处理
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取其中的某一个可用通道
                SelectionKey key = iterator.next();

                //移除已经处理过的事件
                iterator.remove();
                //开始处理这个事件
                process(key);
            }
        }
    }

    /**
     * 具体的某个业务处理方法
     * @param key 通道标识
     */
    private void process(SelectionKey key) throws IOException{
        //首先判断客户端是否已经就位,如果就位了就提示处理
        if (key.isAcceptable()){
            //获取事件标识的通道
            ServerSocketChannel channel = (ServerSocketChannel)key.channel();
            //获取某一个客户端在服务器的套接字通道
            SocketChannel client = channel.accept();
            //非阻塞模式
            client.configureBlocking(false);
            //注册选择器，并设置为读取模式，收到一个连接请求，就启动一个SocketChannel，注册到selector上，以后都由这个SocketChannel去处理
            client.register(selector,SelectionKey.OP_READ);
            //将此对应的channel设置为准备接受其他客户端的请求
            key.interestOps(SelectionKey.OP_ACCEPT);
            //处理提示
            System.out.println("客户端来的真实IP是："+client.getRemoteAddress());
            client.write(charset.encode("请输入你的昵称："));
        }
        //处理客户端的读请求
        if(key.isReadable()){
            //返回SelectionKey对应的channel，其中有数据需要读取
            SocketChannel channel = (SocketChannel)key.channel();

            //申请一个大小为1024字节的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //拼接获取客户端的数据
            StringBuilder content = new StringBuilder();

            //具体读取的实现逻辑
            try {
                //判断是否存在可读的数据
                while (channel.read(byteBuffer) > 0){
                    //固定可操作的范围
                    byteBuffer.flip();
                    //将内容解码
                    content.append(charset.decode(byteBuffer));
                }
                System.out.println("客户端来的真实IP是："+channel.getRemoteAddress());
                //将这次的channel重新注册可读事件，等待下一次的读取
                key.interestOps(SelectionKey.OP_READ);
            }catch (Exception e){
                //如果出现了异常就关闭这次的事件和通道
                key.cancel();
                if(key.channel() != null){
                    key.channel().close();
                }
            }

            //获取通信内容
            if (content.length() > 0){
                String[] arrayStrContent = content.toString().split(SYS_USER_CONTENT_SPILIT);
                //注册用户
                if(arrayStrContent.length == 1){
                    //获取昵称
                    String nickName = arrayStrContent[0];
                    if(users.contains(nickName)){
                        //系统提示
                        channel.write(charset.encode(SYS_HINT));
                    }else {
                        //加入聊天室的列表
                        users.add(nickName);
                        //计算在线人数
                        int count = onlineCount();
                        //返回消息
                        String message = "欢迎 " + nickName + " 进入聊天室！当前在线人数：" + count;
                        //广播路由
                        broadCast(null,message);
                    }
                }else if(arrayStrContent.length > 1){
                    String nickName = arrayStrContent[0];
                    String message = content.substring(nickName.length()+SYS_USER_CONTENT_SPILIT.length());
                    message = nickName + " 说 " + message;
                    if(users.contains(nickName)){
                        //广播路由，不回发这个消息给该客户端
                        broadCast(channel,message);
                    }
                }
            }
        }
    }

    /**
     * 多路广播
     * @param channel 通道
     * @param message 消息
     */
    private void broadCast(SocketChannel channel, String message) throws IOException{
        //广播数据到所有的SocketChannel
        //广播数据到除了自己以外的所有客户端
        for(SelectionKey key : selector.keys()){
            //目标Channel
            Channel channels = key.channel();
            //如果client不为空，不回发此内容的客户端
            if(channels instanceof SocketChannel && channels != channel){
                SocketChannel targetChannels = (SocketChannel) channels;
                targetChannels.write(charset.encode(message));
            }
        }
    }

    /**
     * 检查下线
     * @return count
     */
    private int onlineCount() {
        int res = 0;
        //检查所有的在线用户
        for (SelectionKey key : selector.keys()){
            Channel channel = key.channel();

            if(channel instanceof SocketChannel){
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            new NIOServer(8080).listener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
