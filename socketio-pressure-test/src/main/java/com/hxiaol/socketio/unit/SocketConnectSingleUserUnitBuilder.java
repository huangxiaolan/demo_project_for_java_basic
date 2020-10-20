package com.hxiaol.socketio.unit;

import com.hxiaol.socketio.config.ConnectConfig;
import com.hxiaol.socketio.metrics.Metric;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 一房间单用户连接构建器
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocketConnectSingleUserUnitBuilder implements UnitBuilder {

    ConnectConfig connectConfig;

    ScheduledExecutorService timer;

    Metric metric;

    ConnectionPool connectionPool;

    Dispatcher dispatcher ;

    OkHttpClient okHttpClient;

    @Override
    public Unit build() {
        String roomId = UUID.randomUUID().toString();
        long uid = ThreadLocalRandom.current().nextLong(9000000000000L, 999000000000000L);
        return new SocketConnectUnit(roomId, connectConfig.getGameid(), String.valueOf(uid), connectConfig.getOpt(),
                                     connectConfig.getUrl(), timer, metric, connectionPool,dispatcher,okHttpClient);
    }
}