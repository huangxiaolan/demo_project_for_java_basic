package com.hxiaol.socket.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hxiaol.socket.server.pb.Response;
import com.hxiaol.socket.server.pb.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9099);
        
        //构造服务器
        final SocketIOServer server = new SocketIOServer(config);
        //添加监听事件
        server.addEventListener("user_req", byte[].class, new DataListener<byte []>() {
            
            public void onData(SocketIOClient client, byte [] bs, AckRequest ackRequest) {
                
                try {
                    
                    //解析字节为对象
                    User user=User.parseFrom(bs);
                    System.out.println("receive user : "+user);
                    //构造返回结果并且发送回前端
                    Response response=Response.newBuilder().setCode(0).setMsg("user_res event").build();
                    client.sendEvent("user_res",response.toByteArray());
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException("user_req 数据处理异常",e);
                }
                
                
            }
        });
        
        //启动socket服务
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
        
        
        
        
    }
}
