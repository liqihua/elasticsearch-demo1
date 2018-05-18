package com.liqihua.demo.client;

import com.liqihua.utils.Tool;
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
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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
     */
    @Test
    public void create(){
        String index = "";
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加索引同时设置mapping
     */
    @Test
    public void createWithMapping(){
        String index = "";
        String type = "";
        CreateIndexRequest request = new CreateIndexRequest(index);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("numeric_detection",true);
        map.put("date_detection",true);
        map.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
        request.mapping(type,map);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建mapping为nested父子嵌套结构的index
     */
    @Test
    public void createWithNested(){
        String index = "index_order";
        String type = "order";
        CreateIndexRequest request = new CreateIndexRequest(index);
        String data = "{\"order\": {\n" +
                "      \"properties\": {\n" +
                "          \"order_no\":{\n" +
                "          \"type\":\"text\"\n" +
                "        },\n" +
                "        \"comments\": {\n" +
                "          \"type\": \"nested\", \n" +
                "          \"properties\": {\n" +
                "            \"name\":    { \"type\": \"text\"  },\n" +
                "            \"comment\": { \"type\": \"text\"  },\n" +
                "            \"age\":     { \"type\": \"short\"   },\n" +
                "            \"stars\":   { \"type\": \"short\"   },\n" +
                "            \"date\":    { \"type\": \"date\"    }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }}";
        request.mapping(type,data.toString(), XContentType.JSON);
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建同时设置别名
     */
    @Test
    public void createWithAlias(){
        String index = "";
        String alias = "";
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.alias(new Alias(alias));
        try {
            CreateIndexResponse response = ESClient.client.indices().create(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 删除索引
     */
    @Test
    public void delete(){
        String index = "";
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            DeleteIndexResponse response = ESClient.client.indices().delete(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开索引
     */
    @Test
    public static void open(){
        String index = "";
        OpenIndexRequest request = new OpenIndexRequest(index);
        try {
            OpenIndexResponse response = ESClient.client.indices().open(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭索引
     */
    @Test
    public void close(){
        String index = "";
        CloseIndexRequest request = new CloseIndexRequest(index);
        try {
            CloseIndexResponse response = ESClient.client.indices().close(request);
            Tool.jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }














}
