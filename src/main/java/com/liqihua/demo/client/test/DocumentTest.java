package com.liqihua.demo.client.test;

import com.liqihua.demo.client.DocumentDemo;
import org.junit.Test;

import java.util.*;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class DocumentTest {


    @Test
    public void add(){
        String index = "index_cc";
        String type = "user";
        String id = "1";
        Map<String,Object> source = new HashMap<String,Object>();
        source.put("name","liqihua");
        source.put("age",25);
        source.put("create_date",new Date());
        DocumentDemo.add(index,type,id,source);
    }

    @Test
    public void get(){
        String index = "index_cc";
        String type = "user";
        String id = "1";
        DocumentDemo.get(index,type,id);
    }

    @Test
    public void delete(){
        String index = "index_cc";
        String type = "user";
        String id = "1";
        DocumentDemo.delete(index,type,id);
    }

    @Test
    public void update(){
        String index = "index_cc";
        String type = "user";
        String id = "1";
        Map<String,Object> source = new HashMap<String,Object>();
        source.put("name","liqihua");
        source.put("age",25);
        source.put("create_date",new Date());
        DocumentDemo.update(index,type,id,source);
    }



    @Test
    public void bulkAddUser(){
        String index = "index_user";
        String type = "user";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0; i<10; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name","name"+i);
            map.put("age",20+i);
            list.add(map);
        }
        DocumentDemo.bulkAdd(index,type,list);
    }


    @Test
    public void bulkDelete(){
        String index = "index_cc";
        String type = "user";
        int IdStart = 10;
        int IdEnd = 20;
        DocumentDemo.bulkDelete(index,type,IdStart,IdEnd);
    }


    @Test
    public void bulkUpdate(){
        String index = "index_cc";
        String type = "user";
        int IdStart = 10;
        int IdEnd = 20;
        DocumentDemo.bulkUpdate(index,type,IdStart,IdEnd);
    }




}
