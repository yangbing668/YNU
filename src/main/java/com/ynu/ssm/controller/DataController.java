package com.ynu.ssm.controller;

import com.ynu.ssm.utils.SCISelectFilter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
            InputStream in = new FileInputStream(storefile);
            response.setContentType("text/plain");//设置文件类型
            response.setHeader("content-disposition", "attachment;filename=" + outpath);
            response.setHeader("Content-Length", "" + file.length());
            OutputStream out = response.getOutputStream();//
            IOUtils.copy(in, out);
            out.flush();
            out.close();
            // 将内容按字节从一个InputStream对象复制到一个OutputStream对象，
            // 并返回复制的字节数。同样有一个方法支持从Reader对象复制到Writer对象
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


