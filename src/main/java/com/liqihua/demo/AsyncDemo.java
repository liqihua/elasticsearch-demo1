package com.liqihua.demo;

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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @author liqihua
 * @since 2018/4/28
 */
public class AsyncDemo {

    /**
     * 更新数据-异步
     * @param indexName 索引名称
     * @param type 数据类型
     * @param id
     * @param data json数据
     */
    public static void updateAsync(String indexName,String type,String id,String data){
        UpdateRequest request = new UpdateRequest(indexName,type,id);
        request.doc(data, XContentType.JSON);
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
