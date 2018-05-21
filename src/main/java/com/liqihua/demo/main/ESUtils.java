package com.liqihua.demo.main;

import com.liqihua.config.ESConfig;
import com.liqihua.demo.client.ESClient;
import com.liqihua.utils.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author liqihua
 * @since 2018/5/21
 */
public class ESUtils {

    /**
     * 查看所有index
     * GET http://ip:port/_cat/indices?v
     * @return
     */
    public String indexList(){
        String url = ESConfig.URL+"/_cat/indices?v";
        return Tool.get(url);
    }


    /**
     * 创建索引
     * @param index
     * @param type
     * @param mapping Map类型
     * @param alias 别名
     * @return
     */
    public String createIndex(String index,String type,Map<String,Object> mapping,String alias){
        if(Tool.isNotBlank(index)){
            if(Tool.isBlank(type)){
                type = index;
            }
            CreateIndexRequest request = new CreateIndexRequest(index);
            if(mapping == null){
                mapping = new HashMap<String,Object>();
                mapping.put("numeric_detection",true);
                mapping.put("date_detection",true);
                mapping.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
            }
            request.mapping(type,mapping);
            if(Tool.isNotBlank(alias)){
                request.alias(new Alias(alias));
            }
            try {
                CreateIndexResponse response = ESClient.client.indices().create(request);
                return Tool.jsonPrint(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     *
     * @param index
     * @param type
     * @param mapping String，json格式
     * @param alias 别名
     * @return
     */
    public String createIndex(String index,String type,String mapping,String alias){
        if(Tool.isNotBlank(index)){
            if(Tool.isBlank(type)){
                type = index;
            }
            CreateIndexRequest request = new CreateIndexRequest(index);
            if(Tool.isNotBlank(mapping)){
                request.mapping(type,mapping,XContentType.JSON);
            }else{
                Map<String,Object> mappingMap = new HashMap<String,Object>();
                mappingMap.put("numeric_detection",true);
                mappingMap.put("date_detection",true);
                mappingMap.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
                request.mapping(type,mapping);
            }
            if(Tool.isNotBlank(alias)){
                request.alias(new Alias(alias));
            }
            try {
                CreateIndexResponse response = ESClient.client.indices().create(request);
                return Tool.jsonPrint(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 删除索引
     * @param index
     * @return
     */
    public String deleteIndex(String index){
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            DeleteIndexResponse response = ESClient.client.indices().delete(request);
            return Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 打开索引
     * @param index
     * @return
     */
    public String openIndex(String index){
        OpenIndexRequest request = new OpenIndexRequest(index);
        try {
            OpenIndexResponse response = ESClient.client.indices().open(request);
            return Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 关闭索引
     * @param index
     */
    public String closeIndex(String index){
        CloseIndexRequest request = new CloseIndexRequest(index);
        try {
            CloseIndexResponse response = ESClient.client.indices().close(request);
            return Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 重建索引：
     * 新建一个索引，把旧索引的数据同步过去新索引
     * POST http://ip:port/_reindex
     * {"source":{"index":"sourceIndex"},"dest":{"index":"destIndex"}}
     * @param sourceIndex 源索引
     * @param destIndex 新索引
     * @return
     */
    public String reIndex(String sourceIndex,String destIndex){
        String json = "{\n" +
                "\t\"source\": {\n" +
                "\t\t\"index\": \""+sourceIndex+"\"\n" +
                "\t},\n" +
                "\t\"dest\": {\n" +
                "\t\t\"index\": \""+destIndex+"\"\n" +
                "\t}\n" +
                "}";

        String url = ESConfig.URL + "/_reindex";
        return Tool.post(json.toString(),url);
    }



    /**
     * 判断index是否存在
     * HEAD http://ip:port/indexName
     * @param index
     * @return
     */
    public boolean indexExist(String index){
        String url = ESConfig.URL + "/" + index;
        int code = Tool.head(url);
        if(200 == code){
            return true;
        }else{
            return false;
        }
    }



    /**
     * 判断type是否存在
     * HEAD http://ip:port/indexName/_mapping/typeName
     * @param index
     * @param type
     * @return
     */
    public boolean typeExist(String index,String type){
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;
        int code = Tool.head(url);
        if(200 == code){
            return true;
        }else{
            return false;
        }
    }



    /**
     * 设置mapping
     * PUT http://ip:port/indexName/_mapping/typeName
     * @param index
     * @param type
     * @param json 如：{"properties":{"age":{"type":"double"}}}
     */
    public String setMapping(String index,String type,String json){
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;
        return Tool.put(json.toString(),url);
    }


    /**
     * 查询type的mapping
     * GET http://ip:port/indexName/_mapping/typeName
     * @param index
     * @param type
     * @return
     */
    public String getTypeMapping(String index,String type){
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;
        return Tool.get(url);
    }


    /**
     * 查询field的mapping
     * GET http://ip:port/indexName/_mapping/typeName/field/fieldName
     * @param index
     * @param type
     * @param field 字段名
     * @return
     */
    public String getFieldMapping(String index,String type,String field){
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type + "/field/" + field;
        return Tool.get(url);
    }



    /**
     * 添加别名
     * POST http://ip:port/_aliases
     * @param index
     * @param alias 别名
     * @param json 如：{"actions":[{"add":{"index":"index_aa","alias":"index_1"}}]}
     */
    public String addAlias(String index,String alias,String json){
        String url = ESConfig.URL + "/_aliases";
        return Tool.post(json,url);
    }

    /**
     * 删除别名
     * POST http://ip:port/_aliases
     * @param index
     * @param alias 别名
     * @param json 如：{"actions":[{"remove":{"index":"index_aa","alias":"index_1"}}]}
     * @return
     */
    public String removeAlias(String index,String alias,String json){
        String url = ESConfig.URL + "/_aliases";
        return Tool.post(json,url);
    }


    /**
     * 查看某个index有哪些别名
     * POST http://ip:port/indexName/_aliases
     * @param index
     * @return
     */
    public String getAlias(String index){
        String url = ESConfig.URL + "/"+ index +"/_alias" ;
        return Tool.get(url);
    }


    /**
     * 添加文档
     * @param index
     * @param type
     * @param id
     * @param doc
     * @return
     */
    public IndexResponse addDoc(String index,String type,String id,Map<String,Object> doc){
        IndexRequest request = new IndexRequest(index, type, id);
        request.source(doc);
        try {
            IndexResponse response = ESClient.client.index(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    public GetResponse getDoc(String index,String type,String id){
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
     * @param index
     * @param type
     * @param id
     * @return
     */
    public DeleteResponse deleteDoc(String index,String type,String id){
        DeleteRequest request = new DeleteRequest(index,type,id);
        try {
            DeleteResponse response = ESClient.client.delete(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    public UpdateResponse updateDoc(String index,String type,String id){
        Map<String,Object> doc = null;
        UpdateRequest request = new UpdateRequest(index,type,id);
        request.doc(doc);
        try {
            UpdateResponse response = ESClient.client.update(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过脚本更新
     * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-update.html
     * @param index
     * @param type
     * @param id
     * @param script
     * @return
     */
    public UpdateResponse updateDocByScript(String index,String type,String id,Script script){
        UpdateRequest request = new UpdateRequest(index,type,id);
        //Map<String,Object> param = new HashMap<>();
        //param.put("count",1);
        //Script script = new Script(ScriptType.INLINE, "painless", "ctx._source.comments[0].stars += params.count ", param);
        request.script(script);
        try {
            UpdateResponse response = ESClient.client.update(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量添加
     * @param index
     * @param type
     * @param docList 每个Map必须包含一个key为id
     * @return
     */
    public BulkResponse bulkAddDoc(String index,String type,List<Map<String,Object>> docList){
        BulkRequest request = new BulkRequest();
        docList.forEach(doc -> {
            request.add(new IndexRequest(index,type,doc.get("id")+""));
        });
        try {
            BulkResponse response = ESClient.client.bulk(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量删除
     * @param index
     * @param type
     * @param idList
     * @return
     */
    public BulkResponse bulkDeleteDoc(String index,String type,List<String> idList){
        BulkRequest request = new BulkRequest();
        idList.forEach(id -> {
            request.add(new DeleteRequest(index,type,id));
        });
        try {
            BulkResponse response = ESClient.client.bulk(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量更新
     * @param index
     * @param type
     * @param docList
     * @return
     */
    public BulkResponse bulkUpdateDoc(String index,String type,List<Map<String,Object>> docList){
        BulkRequest request = new BulkRequest();
        docList.forEach(doc -> {
            request.add(new UpdateRequest(index,type,doc.get("id")+""));
        });
        try {
            BulkResponse response = ESClient.client.bulk(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断文档是否存在
     * HEAD http://ip:port/indexName/typeName/id
     * @param index
     * @param type
     * @param id
     * @return
     */
    public boolean docExist(String index,String type,String id){
        String url = ESConfig.URL + "/" + index + "/" + type + "/" + id;
        int code = Tool.head(url);
        if(200 == code){
            return true;
        }else{
            return false;
        }
    }


    /**
     * matchAll查询
     * @param index
     * @param type
     * @return
     */
    public SearchResponse matchAll(String index,String type){
        SearchRequest request = new SearchRequest();
        if(Tool.isNotBlank(index)){
            request.indices(index);
        }
        if(Tool.isNotBlank(type)){
            request.types(type);
        }
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            Tool.jsonPrint(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
