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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSelect {
    static String later_line = "";

    public static String UserSelector(int startYear, int endYear,int author_type,String author_name,String filePath ){
        String savepath="";
        try{
//            String filePath=System.getProperty("user.dir");
            String relatively_Path = filePath+"\\src\\main\\resources\\python\\ei\\ei_compress_files";
            String store_Path = filePath+"\\src\\main\\resources\\python\\ei\\ei_files_download";
            String[] path = printFiles(new File(relatively_Path), 1);
            long time;
            time = System.currentTimeMillis();
//            System.out.println(path.toString());
            int num = 0;
            for (int i=0;i<path.length;i++){
                String item[] = path[i].split("_");
//                System.out.println(item);
                int year = Integer.parseInt( item[0] );
                if(year >= startYear && year <= endYear){
                    savepath=store_Path+"\\"+time+".csv";
                    DataSelect(relatively_Path+"\\"+path[i],savepath,author_type,author_name,num);
                    num += 1;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(savepath);
        return savepath;
    }

    /*
    *传入的是源文件和需要保存的文件
    **/
    public static void DataSelect(String filePath,String savePath,int author_type,String author_name,int count) {
        try {
            String srcPath = filePath;
            String charset = "utf-8";
            String[] line = null;
            String[] columns = null;
            String[] data = null;
            int list_1 = 6;
            int list_4 = 6;
            int list_3 = 6;//表示字段为Author affiliation所在的列数,默认为6
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
                if(count == 0){
                    cwriter.writeNext(columns);
                }
                //需要传入类型，默认为0表示全部；第一作者为1；通讯作者为2；


                //找出第一作者和通讯作者所在的列数
                for(int i=0;i<columns.length;i++){
                    if (columns[i].equals("The first author")){
                        list_1 = i;
                    }
                    if (columns[i].equals("Corresponding author")){
                        list_2 = i;
                    }
                    if (columns[i].equals("Author affiliation")){
                        list_3 = i;
                    }
                    if (columns[i].equals("Author")){
                        list_4 = i;
                    }
                }

                while (iterator.hasNext()) {
                    line = iterator.next();
                    if (line!=null) {
                        sum += 1;
//                        System.out.println(sum);

                        if(author_type == 1){
                        	String[] first_author_address = line[list_3].split("\\(");
                        	if(first_author_address.length>1) {
	                        	if(first_author_address[1].contains("Yunnan University,")) {
//	                        		System.sout.println(first_author_address[1]);
	                        		if(author_name!=null){
	                                    if (line[list_1].contains(author_name)) {
	                                        cwriter.writeNext(line);
	                                    }
	                                }
	                        		else {
	                        			cwriter.writeNext(line);
	                        		}
	                        	}}
                        }else if(author_type == 2){
                        	String coAu = line[list_2];
                        	String[] Aus = line[list_4].split(";");
                        	for(int q=0;q<Aus.length;q++) {
                        		if(!coAu.split(",")[0].equals("")) {
                        			if(Aus[q].contains(coAu.split(",")[0])) {
                        				if(Aus[q].contains("(")) {
                        					String au_num = Aus[q].substring(Aus[q].indexOf("(")+1,Aus[q].indexOf(")"));
                                			String[] au_nums = au_num.split(",");//1,2
                                			String[] first_author_address = line[list_3].split("\\(");
                                			for(int p=0;p<au_nums.length;p++) {
                                				if(first_author_address[Integer.valueOf(au_nums[p].trim())].contains("Yunnan University,")) {
                                					if(author_name!=null){
                                                        if (line[list_2].contains(author_name)) {
                                                            cwriter.writeNext(line);
                                                        }
                                                    }
                                					else {
                	                        			cwriter.writeNext(line);
                	                        		}
                                				}
                                			}
                        				}       			
                            		}
                        		}
                        	}
                        }
                        else if(author_type == 0){
                        	String coAu = line[list_2];
                        	String[] Aus = line[list_4].split(";");
                        	for(int q=0;q<Aus.length;q++) {
                        		if(!coAu.split(",")[0].equals("")) {
                        			if(Aus[q].contains(coAu.split(",")[0])) {
                        				if(Aus[q].contains("(")) {
                        					String au_num = Aus[q].substring(Aus[q].indexOf("(")+1,Aus[q].indexOf(")"));
                                			String[] au_nums = au_num.split(",");//1,2
                                			String[] first_author_address = line[list_3].split("\\(");
                                			for(int p=0;p<au_nums.length;p++) {
                                				if(first_author_address[Integer.valueOf(au_nums[p].trim())].contains("Yunnan University,")||first_author_address[1].contains("Yunnan University,")) {
                                					if(author_name!=null){
                                                        if (line[list_2].contains(author_name)) {
                                                            cwriter.writeNext(line);
                                                        }
                                                    }
                                					else {
                	                        			cwriter.writeNext(line);
                	                        		}
                                				}
                                			}
                        				}       			
                            		}
                        		}
                        	}
                        }
                        else{
                        	String[] first_author_address = line[list_3].split("\\(");
                        	for(String add:first_author_address) {
                        		if(add.contains("Yunnan University,")) {
                        			if(author_name!=null){
                                        if (line[list_1].contains(author_name)||line[list_2].contains(author_name)) {
                                            cwriter.writeNext(line);
                                        }
                                    }else{
                                        cwriter.writeNext(line);
                                    }
                        			continue;
                        		}
                        	}
                            
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


        UserSelector(2016,2016,2,null,System.getProperty("user.dir"));
//        String filePath=System.getProperty("user.dir");
//        String relativelyPath = filePath+"/src/main/resources/python/ei/ei_compress_files";
//        String store_Path = filePath+"/src/main/resources/python/ei/ei_files";
//        String[] path = printFiles(new File(relativelyPath), 1);
//
//        for(int i=0;i<path.length;i++){
//            System.out.println(DataSelect(relativelyPath+"/"+path[i],store_Path+"/"+path[i]));
//        }
    }

}
