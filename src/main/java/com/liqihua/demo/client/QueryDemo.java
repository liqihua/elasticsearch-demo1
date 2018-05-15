package com.liqihua.demo.client;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;



/**
 * @author liqihua
 * @since 2018/5/9
 */
public class QueryDemo {


    @Test
    public void matchAllQuery(){
        SearchRequest request = new SearchRequest();
        request.indices("index_user");//指定index
        request.types("user");//指定type
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            jsonPrint(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void sort(){
        SearchRequest request = new SearchRequest();
        request.indices("index_1");//指定index
        request.types("product");//指定type
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("product_name","Nike 新款系带气垫缓震运动鞋"));
        sourceBuilder.from(2);
        sourceBuilder.size(2);
        sourceBuilder.sort("create_on", SortOrder.DESC);
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            for(SearchHit hit : response.getHits().getHits()){
                System.out.println(hit.getSourceAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void term(){
        String field = "product_sku";
        String value = "151160010137";

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(field, value));
        //sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //sourceBuilder.from(0);
        //sourceBuilder.size(5);
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            System.out.println(JSONArray.fromObject(response.getHits().getHits()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void match(){
        String field = "product_name";
        String keyword = "Nike 新款系带气垫缓震运动鞋";

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field,keyword));
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            System.out.println(JSONArray.fromObject(response.getHits().getHits()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void scroll(){
        String index = "index_1";
        String field = "product_name";
        String keyword = "Nike 新款系带气垫缓震运动鞋";
        try {
            final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.scroll(scroll);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery(field, keyword));
            sourceBuilder.sort("create_on", SortOrder.DESC);
            //sourceBuilder.size(2);
            searchRequest.source(sourceBuilder);

            SearchResponse response = ESClient.client.search(searchRequest);
            String scrollId = response.getScrollId();
            SearchHit[] searchHits = response.getHits().getHits();
            int total = searchHits.length;
            while (searchHits != null && searchHits.length > 0) {
                total += searchHits.length;
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                response = ESClient.client.searchScroll(scrollRequest);
                scrollId = response.getScrollId();
                searchHits = response.getHits().getHits();
                for(SearchHit hit : searchHits){
                    System.out.println(hit.getSourceAsString());
                }
            }
            System.out.println("total:"+total);
            /*ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = ESClient.client.clearScroll(clearScrollRequest);
            boolean succeeded = clearScrollResponse.isSucceeded();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void nestedQuery(){
        SearchRequest request = new SearchRequest();
        request.indices("index_article");//指定index
        request.types("article");//指定type
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title","eggs"))
                .must(QueryBuilders.nestedQuery("comments",
                        QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("comments.name","john"))
                        ,ScoreMode.Avg));
        System.out.println("---data:"+builder.toString());
        sourceBuilder.query(builder);
        request.source(sourceBuilder);
        try {
            SearchResponse response = ESClient.client.search(request);
            for(SearchHit hit : response.getHits().getHits()){
                System.out.println(hit.getSourceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









    /**
     * 打印对象
     * @param obj
     */
    public static void jsonPrint(Object obj){
        System.out.println(JSONObject.fromObject(obj).toString());
    }

}
