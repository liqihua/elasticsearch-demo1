package com.liqihua.demo.client.controller;

import com.liqihua.demo.client.ESClient;
import com.liqihua.utils.Tool;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liqihua
 * @since 2018/5/15
 */
@RestController
@RequestMapping("/excelController")
public class ExcelController {
    @Resource
    private RestHighLevelClient esClient;

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String test1(){
        return "--test1--";
    }

    /**
     * 读取excel示例
     * @param file
     * @return
     */
    @RequestMapping(value = "/readFromExcel", method = RequestMethod.POST)
    public String readFromExcel(MultipartFile file){
        if(file != null && file.getSize() > 0){
            String suffix = Tool.getFileSuffix(file);
            if(Tool.notIn(suffix, ".xls",".xlsx")) {
                return "文件后缀必须是xls或者xlsx";
            }

            // 读取上传的excel文件
            File f = Tool.toFile(file);
            if(f == null){
                return "函数toFile发生错误";
            }
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(f);
                for(int i=0;i<workbook.getNumberOfSheets();i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    if(sheet != null) {
                        for(int r=0;r<=sheet.getLastRowNum();r++){
                            XSSFRow row = sheet.getRow(r);
                            if(row != null) {
                                for(int c=0;c<row.getLastCellNum();c++) {
                                    XSSFCell cell = row.getCell(c);
                                    String value = Tool.getExcelValue(cell);
                                    System.out.print(value+" ("+c+")| ");
                                }
                                System.out.println("--- : "+r);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "-- readFromExcel --";
    }



    @RequestMapping(value = "/excelToIndex", method = RequestMethod.POST)
    public String excelToIndex(MultipartFile file,
                               String index,
                               String type){
        if(file != null && file.getSize() > 0){
            String suffix = Tool.getFileSuffix(file);
            if(Tool.notIn(suffix, ".xls",".xlsx")) {
                return "文件后缀必须是xls或者xlsx";
            }

            // 读取上传的excel文件
            File f = Tool.toFile(file);
            if(f == null){
                return "函数toFile发生错误";
            }
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(f);
                XSSFSheet sheet = workbook.getSheetAt(0);
                if(sheet != null) {
                    List<String> fieldList = new ArrayList<>();
                    List<String> dataList = new ArrayList<>();
                    for(int r=0;r<=sheet.getLastRowNum();r++){
                        XSSFRow row = sheet.getRow(r);
                        if(row != null) {
                            for(int c=0;c<row.getLastCellNum();c++) {
                                XSSFCell cell = row.getCell(c);
                                String value = Tool.getExcelValue(cell);
                                if(c == 0){
                                    fieldList.add(value);
                                }else{
                                    dataList.add(value);
                                }
                                System.out.print(value+" ("+c+")| ");
                            }
                            System.out.println("--- : "+r);
                        }
                    }

                    if(fieldList != null && fieldList.size() > 0 && dataList != null && dataList.size() > 0){
                        //创建index
                        CreateIndexRequest request = new CreateIndexRequest(index);
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("numeric_detection",true);
                        map.put("date_detection",true);
                        map.put("dynamic_date_formats","yyyy-MM-dd HH:mm:ss");
                        request.mapping(type,map);
                        try {
                            CreateIndexResponse response = ESClient.client.indices().create(request);
                            Tool.jsonPrint(response);
                            if(response.isAcknowledged()){
                                //批量请求
                                BulkRequest bulkRequest = new BulkRequest();
                                for (int i = 0; i < dataList.size(); i++){
                                    Map<String, Object> dataMap = new HashMap<>();
                                    dataMap.put(fieldList.get(i % fieldList.size()),dataList.get(i));
                                    bulkRequest.add(new IndexRequest(index, type, i+"").source(dataMap));
                                }
                                BulkResponse bulkResponse = esClient.bulk(bulkRequest);
                                return bulkResponse.status()+" - "+bulkResponse.buildFailureMessage();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "-- readFromExcel --";
    }


}
