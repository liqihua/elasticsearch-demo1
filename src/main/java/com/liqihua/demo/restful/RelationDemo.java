package com.liqihua.demo.restful;

import com.liqihua.config.ESConfig;
import com.liqihua.utils.Tool;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/11
 * 父子关系
 */
public class RelationDemo {


    /**
     * 设置父级type
     * 父type是不存在的type，如果父级type已经存在会报
     * can't add a _parent field that points to an already existing type
     * http://ip:port/indexName/_mapping/typeName
     * {"_parent":{"type":"order"}}
     */
    @Test
    public void createChildType(){
        String index = "index_order";
        String typeParent = "order";
        String typeChild = "orderItem";

        JSONObject _parent = new JSONObject();
        JSONObject type = new JSONObject();
        type.element("type",typeParent);
        _parent.element("_parent",type);

        String url = ESConfig.URL + "/" + index +"/_mapping/" + typeChild;
        Tool.put(_parent.toString(),url);
    }



    /*
    @Test
    public void bulkAdd(){
        String index = "index_order";
        String type = "order";
        String data = "";
        for(int i=0;i<10;i++){
            JSONObject indexJson = new JSONObject();
            JSONObject id = new JSONObject();
            id.element("_id",i);
            indexJson.element("index",id);

            JSONObject doc = new JSONObject();
            doc.element("order_no",System.currentTimeMillis()+i);

            data += (indexJson.toString()+"\n"+doc.toString()+"\n");
        }
        String url = ESConfig.URL+"/"+index+"/"+type+"/_bulk";
        Tool.post(data,url);
    }*/



}
