package com.liqihua.demo.jest;

import com.liqihua.config.ESConfig;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liqihua
 * @since 2018/5/21
 * jest client的使用
 */

public class JestUtils {
    public static JestClient client;

    static {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://"+ ESConfig.IP+":"+ESConfig.PORT)
                .multiThreaded(true)
                .build());
        client = factory.getObject();
    }


    /**
     * 创建索引
     * @param index
     * @return
     */
    public static JestResult createIndex(String index){
        Settings.Builder settingsBuilder = Settings.builder();
        settingsBuilder.put("number_of_shards",1);
        settingsBuilder.put("number_of_replicas",1);
        try {
            return client.execute(new CreateIndex.Builder(index).settings(settingsBuilder.build()).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 设置mapping
     * @param index
     * @param type
     * @param json
     * @return
     */
    public static JestResult setMapping(String index,String type,String json){
        PutMapping putMapping = new PutMapping.Builder(index,type,json).build();
        try {
            return client.execute(putMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 添加文档
     * @param indexName
     * @param type
     * @param id 如果null则自动生成
     * @param source 如："{\"user\":\"ggtt\"}"、或者Map，或者Bean
     * @return
     */
    public static JestResult addDoc(String indexName,String type,String id,Object source){
        Index index = new Index.Builder(source).index(indexName).type(type).id(id).build();
        try {
            return client.execute(index);
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
    public static JestResult getDoc(String index,String type,String id){
        Get get = new Get.Builder(index, id).type(type).build();
        try {
            return client.execute(get);
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
     * @param script 更新脚本
     * @return
     */
    public static JestResult updateDoc(String index,String type,String id,String script){
        try {
            return client.execute(new Update.Builder(script).index(index).type(type).id(id).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除文档
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static JestResult deleteDoc(String index,String type,String id){
        try {
            return client.execute(new Delete.Builder(id).index(index).type(type).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量添加文档
     * @param index
     * @param type
     * @param list
     * @return
     */
    public static JestResult bulkAddDoc(String index,String type,List<Object> list){
        Bulk.Builder builder = new Bulk.Builder().defaultIndex(index).defaultType(type);
        list.forEach(obj -> {
            builder.addAction(new Index.Builder(obj).build());
        });
        Bulk bulk = builder.build();
        try {
            return client.execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 批量删除文档
     * @param index
     * @param type
     * @param idList
     * @return
     */
    public static JestResult bulkDeleteDoc(String index,String type,List<String> idList){
        Bulk.Builder builder = new Bulk.Builder().defaultIndex(index).defaultType(type);
        idList.forEach(id -> {
            builder.addAction(new Delete.Builder(id).index(index).type(type).build());
        });
        Bulk bulk = builder.build();
        try {
            return client.execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






    /**
     * 查询
     * @param index
     * @param type
     * @param json
     * @return
     */
    public static JestResult search(String index,String type,String json){
        Search search = new Search.Builder(json)
                .addIndex(index)
                .addType(type)
                .build();
        try {
            return client.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
