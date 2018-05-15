package com.liqihua.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liqihua
 * @since 2018/5/3
 */
public class Tool {

    /**
     * 发送post请求
     * @param data
     * @param url
     * @return
     */
    public static String post(String data, String url) {
        System.out.println("--- url : "+url);
        try {
            URL postUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            if(Tool.isNotBlank(data)) {
                System.out.println("--- data : "+data);
                conn.setRequestProperty("Content-Length", data.length()+"");
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                out.write(data);
                out.flush();
                out.close();
            }
            InputStream is = null;
            System.out.println("--- post() code : "+conn.getResponseCode());
            if(conn.getResponseCode() == 200){
                is = conn.getInputStream();
            }else{
                is = conn.getErrorStream();
            }
            if(is != null){
                //获取响应内容体
                String line, result = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
                while ((line = in.readLine()) != null) {
                    result += line + "\n";
                }
                in.close();
                System.out.println("--- result : "+result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static String get(String url) {
        System.out.println("--- url : "+url);
        try {
            URL getUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            InputStream is = null;
            System.out.println("--- post() code : "+conn.getResponseCode());
            if(conn.getResponseCode() == 200){
                is = conn.getInputStream();
            }else{
                is = conn.getErrorStream();
            }
            if(is != null){
                //获取响应内容体
                String line, result = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
                while ((line = in.readLine()) != null) {
                    result += line + "\n";
                }
                in.close();
                System.out.println("--- result : "+result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送put请求
     * @param data
     * @param url
     * @return
     */
    public static String put(String data, String url) {
        System.out.println("--- url : "+url);
        try {
            URL postUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            if(Tool.isNotBlank(data)) {
                System.out.println("--- data : "+data);
                conn.setRequestProperty("Content-Length", data.length()+"");
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                out.write(data);
                out.flush();
                out.close();
            }
            InputStream is = null;
            System.out.println("--- post() code : "+conn.getResponseCode());
            if(conn.getResponseCode() == 200){
                is = conn.getInputStream();
            }else{
                is = conn.getErrorStream();
            }
            if(is != null){
                //获取响应内容体
                String line, result = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
                while ((line = in.readLine()) != null) {
                    result += line + "\n";
                }
                in.close();
                System.out.println("--- result : "+result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发送delete请求
     * @param url
     * @return
     */
    public static String delete(String url) {
        System.out.println("--- url : "+url);
        try {
            URL postUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            InputStream is = null;
            System.out.println("--- post() code : "+conn.getResponseCode());
            if(conn.getResponseCode() == 200){
                is = conn.getInputStream();
            }else{
                is = conn.getErrorStream();
            }
            if(is != null){
                //获取响应内容体
                String line, result = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
                while ((line = in.readLine()) != null) {
                    result += line + "\n";
                }
                in.close();
                System.out.println("--- result : "+result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    public static int head(String url) {
        System.out.println("--- url : "+url);
        try {
            URL postUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) postUrl.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            int code =  conn.getResponseCode();
            System.out.println("--- code : "+code);
            return code;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }




    /**
     * 判断String参数是否为空的方法，参数数量可变，如果其中有一个参数是空，就返回true
     * @param strs
     * @return
     */
    public static boolean isBlank(String ... strs){
        for(String s : strs){
            if(StringUtils.isBlank(s)){
                return true;
            }
        }
        return false;
    }


    public static boolean isNotBlank(String ... strs){
        return !isBlank(strs);
    }


    /**
     * 判断一个字符串是否为小数
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        if(isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }


    /**
     * 判断一个字符是否为数字字符
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(isBlank(str)){
            return false;
        }
        if(StringUtils.isNumeric(str)){
            return true;
        }
        return false;
    }


    /**
     * 判断一个String的值在不在可变参数里面,如果不在里面，就返回true
     * @param str
     * @param strs
     * @return
     */
    public static boolean notIn(String str,String ... strs){
        for(String s : strs){
            if(s.equals(str)){
                return false;
            }
        }
        return true;
    }


    /**
     * 方法名：getFileSuffix
     * 详述：获取文件后缀，包含.
     * @param file
     * @return
     */
    public static String getFileSuffix(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(!fileName.contains(".")){
            return null;
        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return suffix;
    }


    /**
     * MultipartFile转File
     * @param file
     * @return
     */
    public static File toFile(MultipartFile file){
        if(file != null && file.getSize()>0){
            try {
                String fileName = file.getOriginalFilename();
                if (!fileName.contains(".")) {
                    return null;
                }
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis() + suffix;
                String saveDirStr = getProjectPath() + "tmpFile/";
                File saveDir = new File(saveDirStr);
                saveDir.mkdirs();
                File newFile = new File(saveDirStr, newFileName);
                newFile.createNewFile();
                file.transferTo(newFile);
                return newFile;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 获取项目磁盘路径
     */
    public static String getProjectPath() {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        if(path.contains("!")) {
            path = path.substring(0, path.indexOf("!"));
            path = path.replace("file:", "");
            path = path.substring(0,path.lastIndexOf("/")+1);
        }else {
            path = path.substring(1);
            path = path.replace("classes/", "");
        }
        return path;
    }


    /**
     * 读取XSSFCell返回String
     * @param cell
     * @return
     */
    public static String getExcelValue(XSSFCell cell) {
        if(cell != null && cell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
            String value = null;
            if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                value = cell.getStringCellValue();
            }else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                if(HSSFDateUtil.isCellDateFormatted(cell)) {
                    value = Tool.formatDate(cell.getDateCellValue(), null);
                }else {
                    value = Tool.double2String(cell.getNumericCellValue(), null);
                    value = value.replace(".00","");
                }
            }else {
                return "-1";
            }
            return value;
        }
        return null;

    }


    /**
     * 把Date对象格式化成String对象
     * @param date
     * @param format 传null默认为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date, String format){
        SimpleDateFormat sf = null;
        if(Tool.isBlank(format)){
            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else{
            sf = new SimpleDateFormat(format);
        }
        return sf.format(date);
    }


    /**
     * 方法名：double2String
     * 详述：使转化得到的str不是科学计数法，如：4.3062319920812602E17->43062319920812602.00
     * @param d
     * @param pattern
     * @return
     */
    public static String double2String(Double d,String pattern){
        if(d != null){
            if(isBlank(pattern)){
                pattern = "0.00";
            }
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(d);
        }
        return null;
    }



}
