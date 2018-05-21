package com.liqihua.demo.offic;

import org.junit.Test;

/**
 * @author liqihua
 * @since 2018/5/21
 */
public class ESUtilsTest {

    @Test
    public void matchAll(){
        String index = "index_aa";
        ESUtils.matchAll(index,null);
    }


    @Test
    public void indexList(){
        ESUtils.indexList();
    }

    @Test
    public void deleteIndex(){
        String index = "index_aa";
        ESUtils.deleteIndex(index);
    }




}
