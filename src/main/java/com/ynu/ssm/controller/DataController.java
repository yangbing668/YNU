package com.ynu.ssm.controller;

import com.ynu.ssm.utils.SCISelectFilter;
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
    public String downloadFile(@RequestParam(value = "startYear",required = false) String startYear,
                               @RequestParam(value = "endYear",required = false) String endYear,
                               @RequestParam(value = "level",required = false) String level,
                               @RequestParam(value = "id",required = false) String id,
                               @RequestParam(value = "author",required = false) String author,
                               HttpServletResponse response) throws Exception {
        // 取得文件名。

        int StartYear = Integer.parseInt(startYear);
        int EndYear=Integer.parseInt(startYear);
        String outpath =("/save_out.xls");
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
        File dir = new File("");// 参数为空
        String storefile="";
        try {
            String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\SCI\\";
            String newPath =  dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\selected_folder\\";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println();// new Date()为获取当前系统时间
            storefile=newPath +"SCI"+df.format(new Date())+".xls";
            filter.filter(storePath,storefile,storePath+"wos_dic.txt",StartYear, EndYear);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(outpath);
        String filename = file.getName();
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();// 取得文件的后缀名。
        try {
            //读取服务器磁盘上文件的二进制数据
//                InputStream fis = new BufferedInputStream(new FileInputStream(path));
            InputStream in = new FileInputStream(storefile);
//                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            OutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[in.available()];
            response.reset();
//                将输入流转换为字符数组输出流
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");
//            byte[] buffer = new byte[1024*1024];
//            int len;
//            while ((len = in.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
            in.read(buffer);
            in.close();

            out.write(buffer);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }return "download";
    }
}


