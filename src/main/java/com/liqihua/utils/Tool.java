package com.liqihua.utils;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
     * 发送put请求
     * @param data
     * @param url
     * @return
     */
    public static String put(String data, String url) {
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



}
