package com.liqihua.demo.restful;

import com.liqihua.config.ESConfig;
import com.liqihua.utils.Tool;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/7
 */
public class IndexDemo {


    /**
     * 查出所有index
     * http://ip:port/_cat/indices?v
     */
    @Test
    public void catIndices(){
        String url = ESConfig.URL+"/_cat/indices?v";
        Tool.get(url);
    }


    /**
     * 添加索引
     * http://port:ip/indexName
     */
    @Test
    public void create(){
        String index = "index_order";
        String url = ESConfig.URL + "/" + index;
        Tool.put(null,url);
    }




    /**
     * 删除index
     * 如果索引不存在返回404
     * http://ip:port/indexName
     */
    @Test
    public void delete(){
        String index = "index_order";
        String url = ESConfig.URL + "/" + index;
        Tool.delete(url);
    }


    /**
     * 判断index是否存在
     * The HTTP status code indicates if the index exists or not. A 404 means it does not exist, and 200 means it does.
     * http://ip:port/indexName
     */
    @Test
    public void exist(){
        String index = "index_aa";
        String url = ESConfig.URL + "/" + index;
        int code = Tool.head(url);
        if(200 == code){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

    }



    /**
     * 添加索引同时设置mapping
     * http://ip:port/indexName
     * {"mappings":{"product":{"numeric_detection":"true","date_detection":"true","dynamic_date_formats":"yyyy-MM-dd HH:mm:ss"}}}
     */
    @Test
    public void createWithMapping(){
        String index = "index_user";
        String type = "user";
        String url = ESConfig.URL + "/" + index;

        JSONObject data = new JSONObject();
        JSONObject mappings = new JSONObject();
        JSONObject dynamicType = new JSONObject();
        dynamicType.element("numeric_detection","true");
        dynamicType.element("date_detection","true");
        dynamicType.element("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
        mappings.element(type,dynamicType);
        data.element("mappings",mappings);

        Tool.put(data.toString(),url);
    }


    /**
     * 给索引的新字段设置mapping（字段类型）
     * 索引必须已存在，否则返回404
     * 字段必须不存在，否则返回400
     * http://ip:port/indexName/_mapping/typeName
     * {"properties":{"age":{"type":"double"}}}
     */
    @Test
    public void addFieldMapping(){
        String index = "index_1";//index名
        String type = "product";//type名

        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;

        JSONObject data = new JSONObject();
        JSONObject properties = new JSONObject();
        JSONObject mappingType = new JSONObject();
        //指定字段age的类型为long
        mappingType.element("type","double");
        properties.element("age",mappingType);
        data.element("properties",properties);

        Tool.put(data.toString(),url);
    }


    /**
     * 查询字段类型
     * http://ip:port/index_1/_mapping/typeName
     */
    @Test
    public void getTypeMapping(){
        //查某个index的所有字段类型
        String index = "index_1";
        String type = "product";
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;
        Tool.get(url);
    }


    /**
     * 查询某个字段的类型
     * http://ip:port/index_1/_mapping/typeName/field/fieldName
     */
    @Test
    public void getFieldMapping(){
        //查某个index的所有字段类型
        String index = "index_1";
        String type = "product";
        String field = "create_on";
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type + "/field/" + field;
        Tool.get(url);
    }





    /**
     * 判断type是否存在
     * http://ip:port/indexName/_mapping/typeName
     */
    @Test
    public void typeExist(){
        String index = "index_bb";
        String type = "product";
        String url = ESConfig.URL + "/" + index + "/_mapping/" + type;
        int code = Tool.head(url);
        if(200 == code){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }


    /**
     * 重建索引：
     * 新建一个索引，把旧索引的数据同步过去新索引
     * http://ip:port/_reindex
     * {"source":{"index":"index_bb"},"dest":{"index":"index_aa"}}
     */
    @Test
    public void reIndex(){
        String sourceIndex = "index_bb";
        String destIndex = "index_aa";
        JSONObject data = new JSONObject();
        JSONObject source = new JSONObject();
        JSONObject dest = new JSONObject();
        source.element("index",sourceIndex);
        dest.element("index",destIndex);
        data.element("source",source);
        data.element("dest",dest);

        String url = ESConfig.URL + "/_reindex";
        Tool.post(data.toString(),url);
    }
















}
