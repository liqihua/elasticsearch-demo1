package com.liqihua.demo;

import net.sf.json.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
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
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    public static final String HOST_NAME = "120.78.9.212";
    public static final int PORT = 9200;


    public static void main(String[] args) {
        /*JSONObject json = new JSONObject();
        json.element("name", "liqihua");
        json.element("age", 18);
        json.element("id", "111");
        saveSync("index-1","user","111",json.toString());*/

        //getSync("index-1","user","111");

        /*JSONObject json = new JSONObject();
        json.element("name", "liqihua");
        json.element("age", 19);
        json.element("id", "111");
        json.element("city", "广州");*/

        //updateSync("index-1","user","111",json.toString());
        //updateAsync("index-1","user","111",json.toString());

        //getSync("index-1","user","111");

        //deleteAsync("index-1","user","111");

        queryDemo1();
        queryDemo2();
        queryDemo3();
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
    }


    /**
     * 更新数据-同步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id
     * @param data json数据
     */
    public static void updateSync(String indexName,String type,String id,String data){
        UpdateRequest request = new UpdateRequest(indexName,type,id);
        request.doc(data,XContentType.JSON);
        RestHighLevelClient client = getClient();
        try {
            UpdateResponse response = client.update(request);
            closeClient(client);
            System.out.println("--- "+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新数据-异步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id
     * @param data json数据
     */
    public static void updateAsync(String indexName,String type,String id,String data){
        UpdateRequest request = new UpdateRequest(indexName,type,id);
        request.doc(data,XContentType.JSON);
        RestHighLevelClient client = getClient();
        client.updateAsync(request, new ActionListener<UpdateResponse>() {
            public void onResponse(UpdateResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- "+response.toString());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * 删除数据-同步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id
     */
    public static void deleteSync(String indexName,String type,String id){
        DeleteRequest request = new DeleteRequest(indexName,type,id);
        RestHighLevelClient client = getClient();
        try {
            DeleteResponse response = client.delete(request);
            closeClient(client);
            System.out.println("--- "+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除数据-异步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id
     */
    public static void deleteAsync(String indexName,String type,String id){
        DeleteRequest request = new DeleteRequest(indexName,type,id);
        RestHighLevelClient client = getClient();
        client.deleteAsync(request, new ActionListener<DeleteResponse>() {
            public void onResponse(DeleteResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- "+response.toString());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * 获取数据-同步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id id
     * @return
     */
    public static String getSync(String indexName,String type,String id){
        GetRequest request = new GetRequest(indexName,type,id);
        RestHighLevelClient client = getClient();
        try {
            GetResponse response = client.get(request);
            closeClient(client);
            System.out.println("--- "+response.toString());
            JSONObject json = JSONObject.fromObject(response.toString());
            boolean found = json.getBoolean("found");
            if(found){
                return json.getString("_source");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据-异步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id id
     */
    public static void getAsync(String indexName,String type,String id){
        GetRequest request = new GetRequest(indexName,type,id);
        RestHighLevelClient client = getClient();
        client.getAsync(request, new ActionListener<GetResponse>() {
            public void onResponse(GetResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- result:"+response.toString());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 保存数据-同步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id id
     * @param data json数据
     */
    public static void saveSync(String indexName,String type,String id,String data){
        IndexRequest request = new IndexRequest(indexName,type,id);
        request.source(data, XContentType.JSON);
        RestHighLevelClient client = getClient();
        try {
            IndexResponse response = client.index(request);
            System.out.println("--- result:"+response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }

    /**
     * 保存数据-异步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id id
     * @param data json数据
     */
    public static void saveAsync(String indexName,String type,String id,String data){
        IndexRequest request = new IndexRequest(indexName,type,id);
        request.source(data, XContentType.JSON);
        RestHighLevelClient client = getClient();
        client.indexAsync(request, new ActionListener<IndexResponse>() {
            public void onResponse(IndexResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- result:"+response.toString());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onResponse");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 关闭索引-同步
     * 针对不使用的index，建议close，减少内存占用。因为只要索引处于open状态，索引库中的segement就会占用内存，close之后就只会占用磁盘空间
     * @param indexName
     */
    public static void closeIndexSync(String ... indexName){
        CloseIndexRequest request = new CloseIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        try {
            CloseIndexResponse response = client.indices().close(request);
            System.out.println("--- "+response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }


    /**
     * 关闭索引-异步
     * 针对不使用的index，建议close，减少内存占用。因为只要索引处于open状态，索引库中的segement就会占用内存，close之后就只会占用磁盘空间
     * @param indexName
     */
    public static void closeIndexAsync(String ... indexName){
        CloseIndexRequest request = new CloseIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        client.indices().closeAsync(request, new ActionListener<CloseIndexResponse>() {
            public void onResponse(CloseIndexResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- "+response.isAcknowledged());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });

    }




    /**
     * 打开索引-同步
     * 针对不使用的index，建议close，减少内存占用。因为只要索引处于open状态，索引库中的segement就会占用内存，close之后就只会占用磁盘空间
     * @param indexName
     */
    public static void openIndexSync(String ... indexName){
        OpenIndexRequest request = new OpenIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        try {
            OpenIndexResponse response = client.indices().open(request);
            System.out.println("--- "+response.isAcknowledged()+","+response.isShardsAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }

    /**
     * 打开索引-异步
     * 针对不使用的index，建议close，减少内存占用。因为只要索引处于open状态，索引库中的segement就会占用内存，close之后就只会占用磁盘空间
     * @param indexName
     */
    public static void openIndexAsync(String ... indexName){
        OpenIndexRequest request = new OpenIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        client.indices().openAsync(request, new ActionListener<OpenIndexResponse>() {
            public void onResponse(OpenIndexResponse response) {
                System.out.println("--- onResponse");
                System.out.println("--- "+response.isAcknowledged()+","+response.isShardsAcknowledged());
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * 删除索引-异步
     * @param indexName
     */
    public static void deleteIndexAsync(String ... indexName){
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        client.indices().deleteAsync(request,new ActionListener<DeleteIndexResponse>() {
            public void onResponse(DeleteIndexResponse response) {
                System.out.println("--- onResponse");
                boolean acknowledged = response.isAcknowledged();
                System.out.println("--- "+acknowledged);
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                if(e instanceof ElasticsearchException){
                    System.out.println(e.getMessage());
                }else{
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 删除索引-同步
     * @param indexName
     */
    public static void deleteIndexSync(String ... indexName){
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        try {
            DeleteIndexResponse response = client.indices().delete(request);
            boolean acknowledged = response.isAcknowledged();
            System.out.println("--- "+acknowledged);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                System.out.println("--- 节点不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }


    /**
     * 创建索引-同步
     * @param indexName 索引名称
     */
    public static void createIndexSync(String indexName){
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        try {
            CreateIndexResponse response = client.indices().create(request);
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            System.out.println("--- "+acknowledged+","+shardsAcknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeClient(client);
    }


    /**
     * 创建索引-异步
     * @param indexName
     */
    public static void createIndexAsync(String indexName){
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        RestHighLevelClient client = getClient();
        client.indices().createAsync(request,new ActionListener<CreateIndexResponse>(){
            public void onResponse(CreateIndexResponse response) {
                System.out.println("--- onResponse");
                boolean acknowledged = response.isAcknowledged();
                boolean shardsAcknowledged = response.isShardsAcknowledged();
                System.out.println("--- "+acknowledged+","+shardsAcknowledged);
            }
            public void onFailure(Exception e) {
                System.out.println("--- onFailure");
                e.printStackTrace();
            }
        });
    }





    /**
     * 打开连接
     * @return
     */
    public static RestHighLevelClient getClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("120.78.9.212", 9200, "http")));
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
