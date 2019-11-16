package com.ynu.ssm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class WebPageResource {

    /**
     * @author litao
     * @date ${date}
     * @version 1.0
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        WebPageResource t6=new WebPageResource();
        String htmls= t6.getPageSource("http://www.no5.com.cn/browse/specialprice_p2.html","GBK");
        System.out.println(htmls);
    }

    public String getPageSource(String pageUrl,String encoding) {
        StringBuffer sb = new StringBuffer();
        try {
            //构建一URL对象
            URL url = new URL(pageUrl);
            //使用openStream得到一输入流并由此构造一个BufferedReader对象
            BufferedReader in = new BufferedReader(new InputStreamReader(url
                    .openStream(), encoding));
            String line;
            //读取www资源
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            in.close();
        } catch (Exception ex) {
//            System.err.println(ex);
        }
        return sb.toString();
    }

}

