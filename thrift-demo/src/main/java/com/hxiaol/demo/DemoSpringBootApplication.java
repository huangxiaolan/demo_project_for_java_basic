package com.hxiaol.demo;

import com.hxiaol.demo.service.AntiCheatServerImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ysec.anti_cheat_hw.AntiCheatService;

@SpringBootApplication
public class DemoSpringBootApplication {

	public static void main(String[] args) {

		try {
			System.out.println("服务端开启....");
			TProcessor tprocessor = new AntiCheatService.Processor<AntiCheatService.Iface>(new AntiCheatServerImpl());
			TServerSocket serverTransport = new TServerSocket(50005);
			TServer.Args tArgs = new TServer.Args(serverTransport);
			tArgs.processor(tprocessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			TServer server = new TSimpleServer(tArgs);
			server.serve();
		}catch (TTransportException e) {
			e.printStackTrace();
		}

	}
}
