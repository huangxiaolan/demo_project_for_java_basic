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
        server.addEventListener("byte_send", byte[].class, new DataListener<byte []>() {
            
            public void onData(SocketIOClient client, byte [] bs, AckRequest ackRequest) {
                try {

                    Response response=Response.newBuilder().setCode(0).setMsg("byte_send byte[]").build();
                    if(ackRequest.isAckRequested()){
                        ackRequest.sendAckData(response.toByteArray());

                    }
                    //解析字节为对象
                    User user=User.parseFrom(bs);
                    System.out.println("receive user : "+user);

                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException("byte_send 数据处理异常",e);
                }
                
                
            }
        });

        //添加监听事件
        server.addEventListener("byte_send_without_ack", byte[].class, new DataListener<byte []>() {

            public void onData(SocketIOClient client, byte [] bs, AckRequest ackRequest) {
                try {

                    Response response=Response.newBuilder().setCode(0).setMsg("byte_send byte[]").build();

                    //解析字节为对象
                    User user=User.parseFrom(bs);
                    System.out.println("receive user : "+user);
                    //构造返回结果并且发送回前端

                    client.sendEvent("byte_send_without_ack_res",response.toByteArray());
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException("byte_send_without_ack 数据处理异常",e);
                }


            }
        });

        //添加监听事件
        server.addEventListener("string_send", String.class, new DataListener<String>() {

            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {

                if(ackRequest.isAckRequested()){
                    ackRequest.sendAckData("string_send_ack data");
                }
                //解析字节为对象

                System.out.println("receive data : "+data);
                //构造返回结果并且发送回前端

            }
        });

        //添加监听事件
        server.addEventListener("string_send_without_ack", String.class, new DataListener<String>() {

            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {

                //解析字节为对象

                System.out.println("receive data : "+data);
                //构造返回结果并且发送回前端

                client.sendEvent("string_send_without_ack_res",data);
            }
        });
        
        //启动socket服务
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
        
        
        
        
    }
}
