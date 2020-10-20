package com.hxiaol.socketio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "data.connect")
public class ConnectConfig {

    String gameid ;

    String opt ;

    String url ;
}