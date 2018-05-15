package com.liqihua.demo.client.controller;

import com.liqihua.utils.Tool;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author liqihua
 * @since 2018/5/15
 */
@RestController
@RequestMapping("/excelController")
public class ExcelController {

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String test1(){
        return "--test1--";
    }

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




}
