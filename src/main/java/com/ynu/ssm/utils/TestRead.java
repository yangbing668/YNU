package com.ynu.ssm.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestRead {
    static String later_line = "";
    public static String DataSelect(String filePath,String savePath) {
        try {
            String srcPath = filePath;
            String charset = "utf-8";
            String[] line = null;
            String[] columns = null;
            String[] data = null;
            int list_1 = 6;//表示字段为Author affiliation所在的列数,默认为6
            int list_2 = 5;//表示字段为Author所在的列数
            int sum = 0;
            int sum2 = 0;
            try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcPath)), charset))).build()) {
                Iterator<String[]> iterator = csvReader.iterator();

                //将筛选后的数据写入新的文件
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(savePath),true));
                CSVWriter cwriter = new CSVWriter(writer,',');

                //获取列名的集合
                columns = iterator.next();
                List<String> list2 = new ArrayList();
                for (int i = 0; i < columns.length; i++) {
                    list2.add(columns[i]);
                }
                list2.add("The first author");
                cwriter.writeNext((String[]) list2.toArray(new String[list2.size()]));

                for(int i=0;i<columns.length;i++){
                    if (columns[i].equals("Author affiliation")){
                        list_1 = i;
                    }
                    if (columns[i].equals("Author")){
                        list_2 = i;
                    }
                }

                while (iterator.hasNext()) {
                    line = iterator.next();
                    if (line!=null) {
                        sum += 1;
//                        System.out.println(sum);
//                        if (true || line[6].contains("Yunnan University")) {
                        if (line[list_1].contains("Yunnan University,")) {
                            String item[] = line[list_2].split(";");
                            List<String> list = new ArrayList();
                            for (int i = 0; i < line.length; i++) {
                                list.add(line[i]);
                            }
                            list.add(item[0]);
                            cwriter.writeNext((String[]) list.toArray(new String[list.size()]));
                        }
                    }else {
                        sum2++;
                        break;
                    }
                }
                System.out.println("sum2:\t"+sum2);
                cwriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static String[] printFiles(File dir,int tab) {
        if(dir.isDirectory()) {
            File next[]=dir.listFiles();
            String[] s = new String[next.length];
            for (int i = 0; i < next.length; i++) {
                s[i] = next[i].getName();
            }
            return s;
        }else{
            String[] s = new String[1];
            return s;
        }

    }
    public static void main(String[] args) {
        String filePath=System.getProperty("user.dir");
        String relativelyPath = filePath+"/src/main/resources/python/ei/ei_compress_files";
        String store_Path = filePath+"/src/main/resources/python/ei/ei_files";
        String[] path = printFiles(new File(relativelyPath), 1);

        for(int i=0;i<path.length;i++){
            System.out.println(DataSelect(relativelyPath+"/"+path[i],store_Path+"/"+path[i]));
        }
    }
}
