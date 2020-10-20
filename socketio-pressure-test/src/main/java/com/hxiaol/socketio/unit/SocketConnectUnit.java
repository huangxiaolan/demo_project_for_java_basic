package com.hxiaol.socketio.unit;

import com.hxiaol.socketio.event.Event;
import com.hxiaol.socketio.metrics.Metric;
import com.hxiaol.socketio.utils.MessageConstants;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.AnimalExchangeRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.GameOverRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.HeartbeatReq;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.HeartbeatRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.LoadProgressReq;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.LoadProgressRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.LoginRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.ReadyGoRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.ReadyReq;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.ReadyRes;
import com.yy.wolfkill.h5game.hechengfangzhi.pb.UserLoginRes;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
public class SocketConnectUnit implements Unit {

    @NonNull
    String roomId;
    @NonNull
    String gameid;
    @NonNull
    String uid;
    @NonNull
    String opt;
    @NonNull
    String url;
    @NonNull
    ScheduledExecutorService timer;
    @NonNull
    Metric metric;
    @NonNull
    ConnectionPool cp ;
    @NonNull
    Dispatcher dispatcher;
    @NonNull
    OkHttpClient okHttpClient;

    AtomicBoolean started = new AtomicBoolean(false);

    Socket socket;

    AtomicBoolean stopped = new AtomicBoolean(false);



    volatile long loadProgressReq;
    volatile long heartStartTime;
    volatile long exchangeStartTime;

    public void addEvent(Event event) {

    }

    public void start() {

        if (!started.compareAndSet(false, true)) {
            log.warn("not start twice");
            return;
        }

        String postData = "{\"roomid\":\"" + roomId + "\",\"channelid\":\"guangzhou1\",\"gameid\":\"" + gameid
                + "\",\"player\":{\"uid\":\"" +
                uid + "\",\"name\":\"test2\",\"avatarurl\":\"http://s1.yy.com/guild/header/10001.jpg\",\"opt\":\"" +
                opt + "\"}}";
        String timestamp = "1509710152";
        String nonstr = UUID.randomUUID().toString();
        String appKey = "";
        String sign = timestamp + nonstr + postData + appKey;

        IO.Options opts = new IO.Options();

        opts.webSocketFactory = okHttpClient;
        opts.callFactory = okHttpClient;
        opts.forceNew = true;
        opts.path = "/" + gameid;
        opts.query = MessageFormat.format("openid={0}&timestamp={1}&sign={2}", roomId, timestamp, sign);
        opts.transports = new String[] { "websocket" };

        Socket socketEmpty = null;

        try {
            socketEmpty = IO.socket(url, opts);
        } catch (URISyntaxException e) {
            throw new RuntimeException("start fail", e);

        }
        //System.out.println("connect url:"+url);
        this.socket = socketEmpty;
        Socket socket = socketEmpty;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            public void call(Object... args) {
                for (Object object : args) {
                    //System.out.println(Socket.EVENT_CONNECT + "：" + object);
                }
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            public void call(Object... args) {
                for (Object object : args) {
                    //System.out.println(Socket.EVENT_DISCONNECT + "：" + object);
                }
            }

        }).on(Socket.EVENT_ERROR, new Emitter.Listener() {

            public void call(Object... args) {
                for (Object object : args) {
                    //System.out.println(Socket.EVENT_ERROR + "：" + object);
                }
            }

        }).on(MessageConstants.LOGIN_RES, args -> {
            for (Object object : args) {
                try {
                    LoginRes loginRes = LoginRes.parseFrom((byte[]) object);
                    //System.out.println(MessageConstants.LOGIN_RES + "：" + loginRes);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            loadProgressReq = System.currentTimeMillis();
            String sn = MessageConstants.LOAD_PROGRESS_REQ + "|" + System.currentTimeMillis();
            LoadProgressReq loadProgressReq = LoadProgressReq.newBuilder().setProgress(100).build();

            socket.emit(MessageConstants.LOAD_PROGRESS_REQ, loadProgressReq.toByteArray());
            metric.send(MessageConstants.LOAD_PROGRESS_REQ);

        }).on(MessageConstants.USER_LOGIN_RES, args -> {
            for (Object object : args) {
                try {
                    UserLoginRes userLoginRes = UserLoginRes.parseFrom((byte[]) object);
                    //System.out.println(MessageConstants.USER_LOGIN_RES + "：" + userLoginRes);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).on(MessageConstants.LOAD_PROGRESS_RES, args -> {
            for (Object object : args) {
                try {
                    LoadProgressRes loadProgressRes = LoadProgressRes.parseFrom((byte[]) object);
                    //System.out.println(MessageConstants.LOAD_PROGRESS_RES + "：" + loadProgressRes);

                    if (loadProgressRes.getDataCount() == 2) {
                        ReadyReq readyReq = ReadyReq.newBuilder().setReady(true).build();
                        socket.emit(MessageConstants.READY_REQ, readyReq.toByteArray());
                    }

                    metric.recv(MessageConstants.LOAD_PROGRESS_RES);
                    metric.add(MessageConstants.LOAD_PROGRESS_RES, System.currentTimeMillis() - loadProgressReq, true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).on(MessageConstants.READY_RES, args -> {

            for (Object object : args) {
                try {
                    ReadyRes querySnReq = ReadyRes.parseFrom((byte[]) object);
                    //System.out.println("ready_res：" + querySnReq);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on(MessageConstants.READY_GO_RES, args -> {

            for (Object object : args) {
                try {
                    ReadyGoRes querySnReq = ReadyGoRes.parseFrom((byte[]) object);
                    //System.out.println("ready_go：" + querySnReq);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).on(MessageConstants.GAME_OVER_RES, args -> {
            for (Object object : args) {
                try {

                    GameOverRes querySnReq = GameOverRes.parseFrom((byte[]) object);
                    //System.out.println("game_over_res：" + querySnReq);

                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).on(MessageConstants.HEART_RES, args -> {
            for (Object object : args) {
                try {

                    HeartbeatRes querySnReq = HeartbeatRes.parseFrom((byte[]) object);
                    //System.out.println("HEART_RES：" + querySnReq);
                    metric.recv(MessageConstants.HEART_RES);
                    metric.add(MessageConstants.HEART_RES, System.currentTimeMillis() - querySnReq.getCTime(), true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).on(MessageConstants.ANIMAL_EXCHANGE_RES, args -> {
            for (Object object : args) {
                try {

                    AnimalExchangeRes querySnReq = AnimalExchangeRes.parseFrom((byte[]) object);
                    //System.out.println(MessageConstants.ANIMAL_EXCHANGE_RES + "：" + querySnReq);
                    metric.recv(MessageConstants.ANIMAL_EXCHANGE_RES);
                    metric.add(MessageConstants.ANIMAL_EXCHANGE_RES, System.currentTimeMillis() - querySnReq.getCTime(),
                               true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        socket.connect();

        timer.scheduleAtFixedRate(() -> {
            metric.send(MessageConstants.HEART_REQ);
            heartStartTime = System.currentTimeMillis();
            socket.emit(MessageConstants.HEART_REQ, HeartbeatReq.newBuilder().setCTime(System.currentTimeMillis()).build().toByteArray());

        }, 2000, 3000, TimeUnit.MILLISECONDS);

//        timer.scheduleAtFixedRate(() -> {
//            metric.send(MessageConstants.ANIMAL_EXCHANGE_REQ);
//            exchangeStartTime = System.currentTimeMillis();
//            socket.emit(MessageConstants.ANIMAL_EXCHANGE_REQ,
//                        AnimalExchangeReq.newBuilder().setPid2(10).setPid1(9).setCTime(System.currentTimeMillis()).build().toByteArray());
//
//        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        socket.disconnect();
        socket.close();

        stopped.set(true);

    }

    @Override
    public boolean isStop() {
        return stopped.get();
    }
}