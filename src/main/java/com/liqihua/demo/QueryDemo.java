package com.liqihua.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import sun.applet.Main;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liqihua
 * @since 2018/4/28
 */
public class QueryDemo {
    public static final String HOST_NAME = "47.106.100.193";
    public static final int PORT = 9555;

    public static void main(String[] args) {
        queryDemo1();
    }

    public static void queryDemo1(){
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());//查出所有
        request.source(sourceBuilder);
        RestHighLevelClient client = getClient();
        try {
            SearchResponse response = client.search(request);
            System.out.println("---"+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }

    public static void queryDemo2(){
        SearchRequest searchRequest = new SearchRequest("index-1");//指定在索引名为index-1的索引找
        searchRequest.types("user");//找类型是user的
        RestHighLevelClient client = getClient();
        try {
            SearchResponse response = client.search(searchRequest);
            System.out.println("---"+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }

    public static void queryDemo3(){
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("name", "liqihua"));//找出name=liqihua的
        sourceBuilder.from(0);//其实下标
        sourceBuilder.size(3);//返回条数
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));//设置查询超时
        request.source(sourceBuilder);
        RestHighLevelClient client = getClient();
        try {
            SearchResponse response = client.search(request);
            System.out.println("---"+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }



    /**
     * 打开连接
     * @return
     */
    public static RestHighLevelClient getClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST_NAME, PORT, "http")));
        return client;
    }

    /**
     * 关闭连接
     * @param client
     */
    public static void closeClient(RestHighLevelClient client){
        if(client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
