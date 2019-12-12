package com.ynu.ssm.controller;

import com.ynu.ssm.utils.SCISelectFilter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author 陈铁
 * @Api_注释
 */
@Controller
public class DataController {
    /**
     * 下载文件
     *
     * @param
     * @param
     */
//    @RequestParam(value = "Startyear",required = false) Integer Startyear,
//    @RequestParam(value = "Endyear",required = false) Integer Endyear,
    @ResponseBody
    @RequestMapping("/download")
    public void downloadFile(@RequestParam(value = "startYear",required = false) String startYear,
                               @RequestParam(value = "endYear",required = false) String endYear,
                               @RequestParam(value = "level",required = false) String level,
                               @RequestParam(value = "id",required = false) String id,
                               @RequestParam(value = "author",required = false) String author,
                               HttpServletResponse response) throws Exception {
        // 取得文件名。

        int StartYear = Integer.parseInt(startYear);
        int EndYear=Integer.parseInt(startYear);
        String outpath =("save_out.xls");
        System.out.println(id);
        System.out.println(startYear);
        System.out.println(endYear);
        System.out.println(level);
        System.out.println(author);
        SCISelectFilter filter = new SCISelectFilter();
        if (level.equals("SCI")){
            System.out.println(level);
        }else if(level.equals("CPCI")){
            System.out.println(level);
        }
        File dir = new File("C:\\Users\\Barry\\Desktop\\YNU\\trunk\\");// 参数为空
        String storefile="";
        File file = new File(outpath);
        try {
            String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\SCI\\";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            long time;
            time = System.currentTimeMillis();// new Date()为获取当前系统时间
            String newPath =  dir.getCanonicalPath() + "\\src\\main\\resources\\python\\selected_folder\\";
            storefile=newPath + time +".xls";
            filter.filter(storePath,storefile,storePath+"wos_dic.txt",null,null,StartYear, EndYear);
            download(response,storefile,outpath);
            delFile(new File(storefile));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void download(HttpServletResponse response, String filename,String outpath) throws IOException {
        //得到要下载的文件
        File file = new File(filename);
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");//注意text/html，和application/html
            response.getWriter().print("<html><body><script type='text/javascript'>alert('您要下载的资源已被删除！');</script></body></html>");
            response.getWriter().close();
            System.out.println("您要下载的资源已被删除！！");
            return;
        }
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + outpath);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(filename);
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024]; // 缓冲区的大小设置是个迷  我也没搞明白
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        out.flush();
        // 关闭输出流
        out.close();
    }
    static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
}



