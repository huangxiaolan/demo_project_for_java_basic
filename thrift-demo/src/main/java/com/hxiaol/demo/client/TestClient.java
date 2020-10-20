package com.hxiaol.demo.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import ysec.anti_cheat_hw.AntiCheatService;
import ysec.anti_cheat_hw.AuthorizeMsg;
import ysec.anti_cheat_hw.Dimension;
import ysec.anti_cheat_hw.RequestEx;
import ysec.anti_cheat_hw.ResponseEx;
import ysec.anti_cheat_hw.TermType;

public class TestClient {
    public static void main(String[] args) {
        System.out.println("客户端启动....");
        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket("127.0.0.1", 12500));
            TProtocol protocol = new TBinaryProtocol(transport);
            AntiCheatService.Client client = new AntiCheatService.Client(protocol);
            transport.open();
            RequestEx request = new RequestEx();
            request.setAppId("app");
            request.setToken("token");
            request.setUid("101001427");
            request.setTermType(TermType.WEB.getValue());
            request.setDimension(Dimension.ALL.getValue());

            AuthorizeMsg msg = new AuthorizeMsg();
            msg.setAuthKey("4ad32cefee2b40baa1c3b3a69464c584");
            msg.setAuthUser("1430540133");
            msg.putToKeyvalue("auth-type","4");
            request.setAuthMsg(msg);

            ResponseEx result = client.riskLevelQueryHwEx(request);
            System.out.println(result);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            result = client.riskLevelQueryHwEx(request);
            System.out.println(result);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            result = client.riskLevelQueryHwEx(request);
            System.out.println(result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

}