package com.ynu.ssm.controller;

import com.ynu.ssm.utils.SCISelectFilter;
import com.ynu.ssm.utils.UserSelect;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void downloadFile(@RequestParam(value = "startYear",required = false) Integer StartYear,
                               @RequestParam(value = "endYear",required = false) Integer EndYear,
                               @RequestParam(value = "level",required = false) Integer level,
                               @RequestParam(value = "type",required = false) String type,
                               @RequestParam(value = "author",required = false) String author,
                               HttpServletResponse response) throws Exception {

        System.out.println(author);
        SCISelectFilter filter = new SCISelectFilter();
        File dir = new File("C:\\Users\\Barry\\Desktop\\YNU\\trunk\\");// 参数为空
        String storefile="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        long time;
        time = System.currentTimeMillis();// new Date()为获取当前系统时间
        String newPath =  dir.getCanonicalPath() + "\\src\\main\\resources\\python\\selected_folder\\";


        if (type.equals("SCI")){
            try {
                String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\SCI\\";
                storefile=newPath + time +".xls";
                if(level==0){
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",null,null,StartYear, EndYear);
                }
                else if(level==1){
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",author,null,StartYear, EndYear);
                }
                else{
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",null,author,StartYear, EndYear);
                }
                int totalLines = getTotalLines(new File(storefile)) - 1;
                String outpath = "SCI_"+ StartYear+"-"+EndYear+"-"+totalLines+".xls";
                download(response,storefile,outpath,totalLines);
                delFile(new File(storefile));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(type.equals("CPCI")){
            try {

                String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\CPCI-S\\";
                storefile=newPath + time +".xls";
                if(level==0){
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",null,null,StartYear, EndYear);
                }
                else if(level==1){
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",author,null,StartYear, EndYear);
                }
                else{
                    filter.filter(storePath,storefile,storePath+"wos_dic.txt",author,null,StartYear, EndYear);
                }
                int totalLines = getTotalLines(new File(storefile)) - 1;
                String outpath = "CPCI_"+ StartYear+"-"+EndYear+"-"+totalLines+".xls";
                download(response,storefile,outpath,totalLines);
                delFile(new File(storefile));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            try {
                String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\ei\\";
                UserSelect us =new UserSelect();
                storefile=us.UserSelector(StartYear,EndYear,level,author,dir.getCanonicalPath());
                int totalLines = -1;
                try{
                    totalLines = getTotalLines(new File(storefile)) - 1;
                }catch (Exception e){totalLines = -1;}
                String EIoutpath = "EI_"+ StartYear+"-"+EndYear+"-"+totalLines+".csv";
                download(response,storefile,EIoutpath,totalLines);
                delFile(new File(storefile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void download(HttpServletResponse response, String filename,String outpath,int totalLines) throws IOException {
        //得到要下载的文件
        File file = new File(filename);
        if (totalLines==-1){
            response.setContentType("text/html; charset=UTF-8");//注意text/html，和application/html
            response.getWriter().print("<html><body><script type='text/javascript'>alert('您要查询的数据为空！');</script></body></html>");
            response.getWriter().close();
            System.out.println("您要查询的数据为空！！");
            return;
        }
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
        byte buffer[] = new byte[1024]; // 缓冲区的大小设置是个迷
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

    public int getTotalLines(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        int lines = reader.getLineNumber();
        reader.close();
        long endTime = System.currentTimeMillis();

        System.out.println("统计文件行数运行时间： " + (endTime - startTime) + "ms");
        return lines;
    }
}



