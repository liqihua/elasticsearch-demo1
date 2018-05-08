package com.liqihua.demo.client.test;

import com.liqihua.demo.client.IndexDemo;
import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/8
 */
public class IndexTest {


    @Test
    public void create(){
        String index = "index_dd";
        IndexDemo.create(index);
    }

    @Test
    public void createWithMapping(){
        String index = "index_ee";
        IndexDemo.createWithMapping(index);
    }



}
