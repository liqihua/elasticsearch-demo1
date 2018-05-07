package com.liqihua.demo.restful;

import com.liqihua.utils.Tool;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/7
 */
public class DocumentDemo {


    /**
     * 添加或更新记录
     */
    @Test
    public void put(){
        String index = "index_aa";//index名，如果不存在自动创建
        String type = "user";//type名，如果不存在自动创建
        String id = "1";//记录id，如果本来就存在，则视为更新操作，覆盖原来的记录

        JSONObject data = new JSONObject();
        data.element("user","kimchy");
        data.element("post_date","2018-02-02 16:22:37");
        data.element("message","trying out Elasticsearch");

        String url = ESConfig.URL + "/" + index + "/" + type + "/" + id;
        Tool.put(data.toString(),url);

    }


    /**
     * 查找记录
     */
    @Test
    public void get(){
        String index = "index_bb";//index名
        String type = "product";//type名
        String id = "1361813";//记录id

        String url = ESConfig.URL + "/" + index + "/" + type + "/" + id;
        Tool.get(url);
    }

    /**
     * 删除记录
     */
    @Test
    public void delete(){
        String index = "index_bb";//index名
        String type = "product";//type名
        String id = "1361813";//记录id
        String url = ESConfig.URL + "/" + index + "/" + type + "/" + id;
        Tool.delete(url);
    }



    /**
     * 判断记录是否存在
     * The API also allows to check for the existence of a document using HEAD
     */
    @Test
    public void exist(){
        String index = "index_aa";
        String type = "user";
        String id = "1";
        String url = ESConfig.URL + "/" + index + "/" + type + "/" + id;
        int code = Tool.head(url);
        if(200 == code){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

    }






}
