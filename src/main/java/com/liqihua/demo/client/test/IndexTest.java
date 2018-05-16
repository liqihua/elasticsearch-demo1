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
        String index = "index_cc";
        IndexDemo.create(index);
    }

    @Test
    public void createWithMapping(){
        String index = "index_cc";
        String type = "user";
        IndexDemo.createWithMapping(index,type);
    }

    @Test
    public void addAlias(){
        String index = "index_ff";
        String alias = "index_2";
        IndexDemo.createWithAlias(index,alias);
    }

    @Test
    public void delete(){
        String index = "index_sys_user";
        IndexDemo.delete(index);
    }

    @Test
    public void open(){
        String index = "index_bb";
        IndexDemo.open(index);
    }

    @Test
    public void close(){
        String index = "index_bb";
        IndexDemo.close(index);
    }


}
