package com.liqihua.demo.client;

import com.liqihua.config.ESConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class ESClient {
    /**
     * 初始化
     */
    public static RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(ESConfig.IP, ESConfig.PORT, "http")));

    /**
     * 重新初始化
     */
    public static RestHighLevelClient init(){
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(ESConfig.IP, ESConfig.PORT, "http")));
        return client;
    }

    /**
     * 关闭连接
     */
    public static void close(){
        if(client != null) {
            try {
                client.close();
                client = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}
