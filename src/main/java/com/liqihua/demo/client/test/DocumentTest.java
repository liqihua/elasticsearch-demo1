package com.liqihua.demo.client.test;

import com.liqihua.demo.client.DocumentDemo;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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



}
