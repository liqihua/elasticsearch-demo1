package com.liqihua.demo.jest;

import io.searchbox.client.JestResult;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liqihua
 * @since 2018/5/21
 */
public class JestUtilsTest {


    @Test
    public void createIndex(){
        String index = "index_aa";
        JestResult result = JestUtils.createIndex(index);
        System.out.println(result.getJsonString());
    }

    @Test
    public void setMapping(){
        String index = "index_aa";
        String type = "my_type";
        String json = "{\n" +
                "\t\"my_type\": {\n" +
                "\t\t\"properties\": {\n" +
                "\t\t\t\"message\": {\n" +
                "\t\t\t\t\"type\": \"text\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        JestResult result = JestUtils.setMapping(index,type,json);
        System.out.println(result.getJsonString());
    }


    @Test
    public void addDoc(){
        String index = "index_aa";
        String type = "my_type";
        String id = null;
        String json = "{\"user\":\"ggtt\"}";
        JestResult result = JestUtils.addDoc(index,type,id,json);
        System.out.println(result.getJsonString());
    }



    @Test
    public void getDoc(){
        String index = "index_aa";
        String type = "my_type";
        String id = "Dbu9gWMBLujCRXCkRDRc";
        JestResult result = JestUtils.getDoc(index,type,id);
        System.out.println(result.getJsonString());
    }


    @Test
    public void updateDoc(){
        String index = "index_aa";
        String type = "my_type";
        String id = "Dbu9gWMBLujCRXCkRDRc";
        String script = "{\n" +
                "\t\"script\": \"ctx._source.user='liqihua'\"\n" +
                "}";
        JestResult result = JestUtils.updateDoc(index,type,id,script);
        System.out.println(result.getJsonString());
    }


    @Test
    public void deleteDoc(){
        String index = "index_aa";
        String type = "my_type";
        String id = "Dbu9gWMBLujCRXCkRDRc";
        JestResult result = JestUtils.deleteDoc(index,type,id);
        System.out.println(result.getJsonString());
    }



    @Test
    public void bulkAddDoc(){
        String index = "index_aa";
        String type = "my_type";
        List<Object> list = new LinkedList<>();
        for(int i=0;i<10;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("user","user"+i);
            list.add(map);
        }
        JestResult result = JestUtils.bulkAddDoc(index,type,list);
        System.out.println(result.getJsonString());
    }

    @Test
    public void bulkDeleteDoc(){
        String index = "index_aa";
        String type = "my_type";
        List<String> list = new LinkedList<>();
        list.add("EbvpgWMBLujCRXCkLzSy");
        list.add("ErvpgWMBLujCRXCkLzSy");
        list.add("E7vpgWMBLujCRXCkLzSy");
        list.add("FLvpgWMBLujCRXCkLzSy");
        JestResult result = JestUtils.bulkDeleteDoc(index,type,list);
        System.out.println(result.getJsonString());
    }























    @Test
    public void search(){
        String index = "index_aa";
        String type = "my_type";
        String json = "{\"query\":{\"match_all\":{}}}";
        JestResult result = JestUtils.search(index,type,json);
        System.out.println(result.getJsonString());
    }


}
