package com.liqihua.demo.restful;

import com.liqihua.utils.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/7
 */
public class AliasDemo {


    /**
     * 添加别名
     * {"actions":[{"add":{"index":"index_aa","alias":"index_1"}}]}
     */
    @Test
    public void add(){
        String index = "index_aa";
        String alias = "index_1";

        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        JSONObject item = new JSONObject();
        JSONObject add = new JSONObject();
        add.element("index",index);
        add.element("alias",alias);
        item.element("add",add);
        arr.add(item);
        data.element("actions",arr);

        String url = ESConfig.URL + "/_aliases";
        Tool.post(data.toString(),url);
    }

    /**
     * 删除别名
     * {"actions":[{"remove":{"index":"index_aa","alias":"index_1"}}]}
     */
    @Test
    public void remove(){
        String index = "index_aa";
        String alias = "index_1";

        JSONObject data = new JSONObject();
        JSONArray arr = new JSONArray();
        JSONObject item = new JSONObject();
        JSONObject remove = new JSONObject();
        remove.element("index",index);
        remove.element("alias",alias);
        item.element("remove",remove);
        arr.add(item);
        data.element("actions",arr);

        String url = ESConfig.URL + "/_aliases";
        Tool.post(data.toString(),url);
    }


    /**
     * 查看某个index有哪些别名
     */
    @Test
    public void get(){
        String index = "index_bb";
        String url = ESConfig.URL + "/"+ index +"/_alias" ;
        Tool.get(url);
    }


}
