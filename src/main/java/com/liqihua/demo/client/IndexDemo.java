package com.liqihua.demo.client;

import net.sf.json.JSONObject;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class IndexDemo {


    /**
     * 创建索引
     * @param indexName
     */
    public static void create(String indexName){
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加索引同时设置mapping
     * @param indexName
     */
    public static void createWithMapping(String indexName){
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("numeric_detection",true);
        map.put("date_detection",true);
        map.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
        request.mapping("product",map);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            jsonPrint(response);
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
