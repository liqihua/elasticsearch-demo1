package com.liqihua.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liqihua
 * @since 2018/5/7
 */
@Configuration
public class ESConfig {
    //host地址
    public static final String IP = "47.106.100.193";
    //端口
    public static final int PORT = 9555;
    //请求url
    public static final String URL = "http://"+IP+":"+PORT;


    /**
     * springboot注入配置
     */
    @Bean
    public RestHighLevelClient ESClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost(ESConfig.IP, ESConfig.PORT, "http")));
    }
}
