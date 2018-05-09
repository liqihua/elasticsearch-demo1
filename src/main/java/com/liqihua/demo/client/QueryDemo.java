package com.liqihua.demo.client;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liqihua
 * @since 2018/5/9
 */
public class QueryDemo {


    @Test
    public void matchAllQuery(){
        SearchRequest request = new SearchRequest();
        //request.indices("");//指定index
        //request.types("");//指定type
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void termQuery(){
        String field = "product_sku";
        String value = "151160010137";

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(field, value));
        //sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //sourceBuilder.from(0);
        //sourceBuilder.size(5);
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            System.out.println(JSONArray.fromObject(response.getHits().getHits()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void match(){
        String field = "product_name";
        String keyword = "新款系带气垫缓震运动鞋";

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field,keyword));
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            System.out.println(JSONArray.fromObject(response.getHits().getHits()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }











    /**
     * 打印对象
     * @param obj
     */
    public static void jsonPrint(Object obj){
        System.out.println(JSONObject.fromObject(obj).toString());
    }

}
