package com.hxiaol.nettyserver.demo.client;

import com.hxiaol.nettyserver.demo.handler.NettyClientFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
/**
 */
public class NettyClient {



    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public static void main(String[] args) throws InterruptedException, IOException {
        String host = "127.0.0.1";  //ip地址
        int port = 8083;          //端口
        int sendCount = 10000000; //发送次数用于测试内存泄露问题
        /// 通过nio方式来接收连接和处理连接
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();


        b.group(group);
        b.channel(NioSocketChannel.class);
        b.handler(new NettyClientFilter());
        // 连接服务端
        Channel ch = b.connect(host, port).sync().channel();
        System.out.println("客户端成功启动...");
        start(ch,sendCount);
    }

    public static void start(Channel ch,int sendCount) throws IOException{
        String str="Hello Netty";
        for(int i=0;i<sendCount;i++){
            ch.writeAndFlush(str+ "\r\n");
        }

        System.out.println("客户端发送数据:"+sendCount);
    }

}
