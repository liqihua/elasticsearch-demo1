package com.liqihua.demo.client;

import com.liqihua.utils.Tool;
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
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class DocumentDemo {


    /**
     * 添加记录
     */
    @Test
    public void add(){
        String index = "";
        String type = "";
        String id = "";
        Map<String,Object> doc = null;
        IndexRequest request = new IndexRequest(index, type, id);
        request.source(doc);
        try {
            IndexResponse response = ESClient.client.index(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加nested嵌套数据
     */
    @Test
    public void addNested(){
        String index = "index_order";
        String type = "order";
        String id = "1";
        Map<String, Object> child1 = new HashMap<>();
        Map<String, Object> child2 = new HashMap<>();
        child1.put("name","John Smith");
        child1.put("comment","Great article");
        child1.put("age",28);
        child1.put("stars",4);
        child1.put("date","2014-09-01");

        child2.put("name","Alice White");
        child2.put("comment","More like this please");
        child2.put("age",31);
        child2.put("stars",5);
        child2.put("date","2014-10-22");

        List<Map<String, Object>> childList = new ArrayList<>();
        childList.add(child1);
        childList.add(child2);

        Map<String, Object> doc = new HashMap<>();
        doc.put("title","Nest eggs");
        doc.put("body","Making your money work...");
        doc.put("tags",new String[]{"cash","shares"});
        doc.put("comments",childList);

        IndexRequest request = new IndexRequest(index, type, id);
        request.source(doc);
        try {
            IndexResponse response = ESClient.client.index(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取记录
     */
    @Test
    public GetResponse get(){
        String index = "";
        String type = "";
        String id = "";
        GetRequest request = new GetRequest(index,type,id);
        try {
            GetResponse response = ESClient.client.get(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除记录
     */
    @Test
    public void delete(){
        String index = "";
        String type = "";
        String id = "";
        DeleteRequest request = new DeleteRequest(index,type,id);
        try {
            DeleteResponse response = ESClient.client.delete(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新记录
     */
    @Test
    public void update(){
        String index = "";
        String type = "";
        String id = "";
        Map<String,Object> doc = null;
        UpdateRequest request = new UpdateRequest(index,type,id);
        request.doc(doc);
        try {
            UpdateResponse response = ESClient.client.update(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过脚本更新
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-update.html
     */
    @Test
    public void updateByScript(){
        String index = "index_article";
        String type = "article";
        String id = "2";
        UpdateRequest request = new UpdateRequest(index,type,id);
        Map<String,Object> param = new HashMap<>();
        param.put("count",1);
        Script script = new Script(ScriptType.INLINE, "painless", "ctx._source.comments[0].stars += params.count ", param);
        request.script(script);
        try {
            UpdateResponse response = ESClient.client.update(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量添加
     */
    @Test
    public static void bulkAdd(){
        String index = "";
        String type = "";
        int IdStart = 0;
        int IdEnd = 10;
        BulkRequest request = new BulkRequest();
        for(int i=IdStart; i<=IdEnd; i++){
            IndexRequest doc = new IndexRequest(index,type,i+"");
            doc.source("name","name"+i,"age",10+i,"create_date",new Date());
            request.add(doc);
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加
     */
    @Test
    public void bulkAddMap(){
        String index = "";
        String type = "";
        List<Map<String,Object>> list = null;
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
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量删除
     */
    @Test
    public static void bulkDelete(){
        String index = "";
        String type = "";
        int IdStart = 0;
        int IdEnd = 10;
        BulkRequest request = new BulkRequest();
        for(int i=IdStart; i<=IdEnd; i++){
            DeleteRequest doc = new DeleteRequest(index,type,i+"");
            request.add(doc);
        }
        try {
            BulkResponse response = ESClient.client.bulk(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 批量更新
     */
    @Test
    public void bulkUpdate(){
        String index = "";
        String type = "";
        int IdStart = 0;
        int IdEnd = 10;
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




    


}
