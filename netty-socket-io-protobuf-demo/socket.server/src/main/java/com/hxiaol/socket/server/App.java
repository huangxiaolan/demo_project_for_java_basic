package com.hxiaol.socket.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

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
        

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", byte[].class, new DataListener<byte []>() {
            
            public void onData(SocketIOClient client, byte [] bs, AckRequest ackRequest) {
                
                server.getBroadcastOperations().sendEvent("chatevent");
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}
