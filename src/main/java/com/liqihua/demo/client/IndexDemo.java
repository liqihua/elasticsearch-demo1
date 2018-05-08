package com.liqihua.demo.client;

import net.sf.json.JSONObject;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
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
     * @param index
     */
    public static void create(String index){
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加索引同时设置mapping
     * @param index
     */
    public static void createWithMapping(String index,String type){
        CreateIndexRequest request = new CreateIndexRequest(index);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("numeric_detection",true);
        map.put("date_detection",true);
        map.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
        request.mapping(type,map);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建同时设置别名
     * @param index
     * @param alias
     */
    public static void createWithAlias(String index,String alias){
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.alias(new Alias(alias));
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除索引
     * @param index
     */
    public static void delete(String index){
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            DeleteIndexResponse response = ESClient.client.indices().delete(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开索引
     * @param index
     */
    public static void open(String index){
        OpenIndexRequest request = new OpenIndexRequest(index);
        try {
            OpenIndexResponse response = ESClient.client.indices().open(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭索引
     * @param index
     */
    public static void close(String index){
        CloseIndexRequest request = new CloseIndexRequest(index);
        try {
            CloseIndexResponse response = ESClient.client.indices().close(request);
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
