package com.liqihua.demo.client;

import net.sf.json.JSONObject;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
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
     * 打印对象
     * @param obj
     */
    public static void jsonPrint(Object obj){
        System.out.println(JSONObject.fromObject(obj).toString());
    }


}
