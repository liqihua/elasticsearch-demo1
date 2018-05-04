package com.liqihua.demo.restful;

import com.liqihua.utils.Tool;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/4
 */
public class MappingDemo {
    //host地址
    public static final String IP = "47.106.100.193";
    //端口
    public static final int PORT = 9555;
    //请求url
    public static final String URL = "http://"+IP+":"+PORT;


    @Test
    public void test1(){
        String index = "index_pm_stage_product_1";
        String field = "product_price_a";

        JSONObject properties = new JSONObject();
        JSONObject type = new JSONObject();
        type.element("type","double");
        properties.element(field,type);

        JSONObject pm_stage_product = new JSONObject();
        pm_stage_product.element("properties",properties);

        JSONObject mappings = new JSONObject();
        mappings.element("pm_stage_product",pm_stage_product);

        JSONObject json = new JSONObject();
        json.element("mappings",mappings);

        String url = URL+"/"+index;

        System.out.println(url);
        System.out.println(json.toString());

        Tool.put(json.toString(),url);
    }


    @Test
    public void test2(){
        String index = "index_pm_stage_product_1";

        JSONObject detection = new JSONObject();
        detection.element("numeric_detection",true);
        detection.element("date_detection",true);

        JSONObject mappings = new JSONObject();
        mappings.element("pm_stage_product",detection);

        JSONObject json = new JSONObject();
        json.element("mappings",mappings);

        String url = URL+"/"+index;

        System.out.println(url);
        System.out.println(json.toString());

        //Tool.put(json.toString(),url);
    }



    @Test
    public void updateMapping(){
        String index = "index_pm_stage_product_1";
        String typeName = "pm_stage_product";
        String field = "product_price_a";

        JSONObject properties = new JSONObject();
        JSONObject type = new JSONObject();
        type.element("type","double");
        properties.element(field,type);

        JSONObject pm_stage_product = new JSONObject();
        pm_stage_product.element("properties",properties);

        JSONObject mappings = new JSONObject();
        mappings.element("pm_stage_product",pm_stage_product);

        JSONObject json = new JSONObject();
        json.element("mappings",mappings);

        String url = URL+"/"+index+"/"+typeName+"_mapping?pretty";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.put(json.toString(),url);
    }

}
