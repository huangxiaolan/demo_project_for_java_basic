package com.hxiaol.demo.service;

import org.apache.thrift.TException;
import ysec.anti_cheat_hw.AntiCheatService;
import ysec.anti_cheat_hw.Request;
import ysec.anti_cheat_hw.RequestEx;
import ysec.anti_cheat_hw.Response;
import ysec.anti_cheat_hw.ResponseEx;

import java.util.ArrayList;
import java.util.HashMap;

public class AntiCheatServerImpl implements AntiCheatService.Iface {
    @Override
    public Response riskLevelQueryHw(Request request) throws TException {
        return new Response(1,new HashMap<>(),"",new ArrayList<>());

    }

    @Override
    public ResponseEx riskLevelQueryHwEx(RequestEx request) throws TException {
        return new ResponseEx();

    }
}