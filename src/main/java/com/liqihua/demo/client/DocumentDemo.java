package com.liqihua.demo.client;

import net.sf.json.JSONObject;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class DocumentDemo {


    /**
     * 添加记录
     * @param index
     * @param type
     * @param id
     * @param doc
     */
    public static void add(String index, String type, String id, Map<String,Object> doc){
        IndexRequest request = new IndexRequest(index, type, id);
        request.source(doc);
        try {
            IndexResponse response = ESClient.client.index(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取记录
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static GetResponse get(String index,String type,String id){
        GetRequest request = new GetRequest(index,type,id);
        try {
            GetResponse response = ESClient.client.get(request);
            jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除记录
     * @param index
     * @param type
     * @param id
     */
    public static void delete(String index,String type,String id){
        DeleteRequest request = new DeleteRequest(index,type,id);
        try {
            DeleteResponse response = ESClient.client.delete(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新记录
     * @param index
     * @param type
     * @param id
     * @param doc
     */
    public static void update(String index,String type,String id,Map<String,Object> doc){
        UpdateRequest request = new UpdateRequest(index,type,id);
        request.doc(doc);
        try {
            UpdateResponse response = ESClient.client.update(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量添加
     * @param index
     * @param type
     * @param IdStart
     * @param IdEnd
     */
    public static void bulkAdd(String index,String type,int IdStart,int IdEnd){
        BulkRequest request = new BulkRequest();
        for(int i=IdStart; i<=IdEnd; i++){
            IndexRequest doc = new IndexRequest(index,type,i+"");
            doc.source("name","name"+i,"age",10+i,"create_date",new Date());
            request.add(doc);
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加
     * @param index
     * @param type
     * @param list
     */
    public static void bulkAdd(String index,String type,List<Map<String,Object>> list){
        BulkRequest request = new BulkRequest();
        int id = 0;
        for(Map<String,Object> map : list){
            IndexRequest doc = new IndexRequest(index,type,id+"");
            doc.source(map);
            request.add(doc);
            id += 1;
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除
     * @param index
     * @param type
     * @param IdStart
     * @param IdEnd
     */
    public static void bulkDelete(String index,String type,int IdStart,int IdEnd){
        BulkRequest request = new BulkRequest();
        for(int i=IdStart; i<=IdEnd; i++){
            DeleteRequest doc = new DeleteRequest(index,type,i+"");
            request.add(doc);
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 批量更新
     * @param index
     * @param type
     * @param IdStart
     * @param IdEnd
     */
    public static void bulkUpdate(String index,String type,int IdStart,int IdEnd){
        BulkRequest request = new BulkRequest();
        for(int i=IdStart; i<=IdEnd; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name","name-update-"+i);
            map.put("age",20+i);
            map.put("create_date",new Date());
            UpdateRequest doc = new UpdateRequest(index,type,i+"");
            doc.doc(map);
            request.add(doc);
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            for(BulkItemResponse item : response){
                System.out.println("isFailed:"+item.isFailed());
            }
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
