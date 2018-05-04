package com.liqihua.demo.restful;

import com.liqihua.utils.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.awt.image.RescaleOp;

/**
 * @author liqihua
 * @since 2018/5/4
 */
public class QueryDemo {
    //host地址
    public static final String IP = "47.106.100.193";
    //端口
    public static final int PORT = 9555;
    //请求url
    public static final String URL = "http://"+IP+":"+PORT;






    /**
     * 查出所有index
     */
    @Test
    public void catIndices(){
        String url = URL+"/_cat/indices?v";
        System.out.println(url);
        Tool.get(url);
    }


    /**
     * matchAll查询
     * {"query":{"match_all":{}}}
     */
    @Test
    public void match_all(){
        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_all = new JSONObject();
        query.element("match_all",match_all);
        json.element("query",query);

        //索引名，如果值为""，则查出所有index的数据
        String index = "index_pm_stage_product_1";
        //索引名还能使用通配符
        //String index = "index_pm_stage_product*";

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }


    /**
     * multi_match查询
     * 将文本或短语与多个字段匹配
     * {"query":{"multi_match":{"query":"11","fields":["create_by","op_by"]}}}
     */
    @Test
    public void multi_match(){
        //关键词
        String keyword = "11";
        //索引名
        String index = "index_pm_stage_product";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject multi_match = new JSONObject();
        multi_match.element("query",keyword);
        multi_match.element("fields",new String[]{"create_by","op_by"});
        query.element("multi_match",multi_match);
        json.element("query",query);

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }




    /**
     * query_string
     * 关键字查询
     * {"query":{"query_string":{"query":"新款"}}}
     */
    @Test
    public void query_string(){
        //关键词
        String keyword = "新款";
        //索引名
        String index = "index_pm_stage_product";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject query_string = new JSONObject();
        query_string.element("query",keyword);
        query.element("query_string",query_string);
        json.element("query",query);

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }


    /**
     * 期限等级查询
     * 这些查询主要处理结构化数据，如数字，日期和枚举
     * {"query":{"term":{"product_id":"1000054193"}}}
     */
    @Test
    public void term(){
        //字段名
        String field = "product_id";
        //字段值
        String value = "1000054193";
        //索引名
        String index = "index_pm_stage_product";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject term = new JSONObject();
        term.element(field,value);
        query.element("term",term);
        json.element("query",query);

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }


    /**
     * 范围查询
     * 此查询用于查找值的范围之间的值的对象。 为此，需要使用类似 -
     * gte − 大于和等于
     * gt − 大于
     * lte − 小于和等于
     * lt − 小于
     */
    @Test
    public void range(){
        //字段名
        String field = "product_price";
        //索引名
        String index = "index_pm_stage_product_1";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject fieldJson = new JSONObject();
        JSONObject scope = new JSONObject();
        scope.element("gte","1000");
        fieldJson.element(field,scope);
        query.element("range",fieldJson);
        json.element("query",query);

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }


    /**
     * 类型查询 - 具有特定类型的文档
     */
    @Test
    public void type(){
        //类型
        String type = "pm_stage_product";
        //索引名
        String index = "index_pm_stage_product";

        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject value = new JSONObject();
        value.element("value",type);
        query.element("type",value);
        json.element("query",query);

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        Tool.post(json.toString(),url);
    }










    @Test
    public void convert(){
        JSONObject json = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject match_all = new JSONObject();
        query.element("match_all",match_all);
        json.element("query",query);

        //索引名，如果值为""，则查出所有index的数据
        String index = "index_pm_stage_product";
        //索引名还能使用通配符
        //String index = "index_pm_stage_product*";

        String url = URL+"/"+index+"/_search";

        System.out.println(url);
        System.out.println(json.toString());

        String result = Tool.post(json.toString(),url);
        JSONObject resultJson = JSONObject.fromObject(result);
        JSONObject hits = resultJson.getJSONObject("hits");
        JSONArray hitsArr = hits.getJSONArray("hits");
        System.out.println(hitsArr.toString());
        for(int i=0; i<hitsArr.size(); i++){
            boolean update = false;
            JSONObject product = hitsArr.getJSONObject(i).getJSONObject("_source");
            String product_price = product.getString("product_price");
            if(Tool.isNumber(product_price)){
                product.element("product_price",Integer.parseInt(product_price));
                update = true;
            }
            if(Tool.isDouble(product_price)){
                product.element("product_price",Double.parseDouble(product_price));
                update = true;
            }
            if(update){
                String id = hitsArr.getJSONObject(i).getString("_id");
                System.out.println("update:"+product.toString());
                //String updateResult = Tool.put(product.toString(),URL+"/"+index+"/pm_stage_product/"+id);
                //System.out.println("updateResult:"+updateResult);
            }
        }
        System.out.println(hitsArr.toString());

    }



}
