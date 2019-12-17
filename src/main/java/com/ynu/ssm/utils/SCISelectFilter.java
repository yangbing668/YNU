package com.ynu.ssm.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SCISelectFilter {
    public HashMap<String, String> getChineseTitelNameMap(String file) {
        HashMap<String, String> map = new HashMap<>();
        try {


            FileInputStream in = new FileInputStream(file);
            // 指定读取文件时以UTF-8的格式读取
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            lineNumberReader.skip(Long.MAX_VALUE);
            long lines = lineNumberReader.getLineNumber() + 1;
            fileReader.close();
            lineNumberReader.close();
//            System.out.println(lines);
            for (int i = 0; i < lines; i = i + 3) {
                String key = bReader.readLine().replace("\t", "").replace("\n", "");
                String value = bReader.readLine().replace("\t", "").replace("\n", "");
//                System.out.println(key + "\t" + value);
//                System.out.println(key);
                bReader.readLine();
                map.put(key, value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        SCISelectFilter filter = new SCISelectFilter();
        File dir = new File("");// 参数为空
        String storePath = null;
        try {
            storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\SCI\\";
            String newPath =  dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos\\SCI\\selected_folder\\";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            System.out.println();// new Date()为获取当前系统时间
            filter.filter(storePath,newPath +"SCIy"+".xls",storePath+"wos_dic.txt","Du, Lin","Zhao, QH", 2017, 2018);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void filter(String sourcePath, String newFile,String dicFile,String author ,String correspondingAuthor, int startYear, int endYear) {
        ArrayList<String> keywords = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            keywords.add(String.valueOf(i));
        }
        try {
            ArrayList<String[]> datas = new ArrayList();
            ArrayList<String> first_authors = new ArrayList();
            ArrayList<String> corresponding_authors = new ArrayList();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    newFile), "GBK"));
            List<File> files = SCISelectFilter.searchFiles(new File(sourcePath), keywords);
            if(files.size()==0){
                bw.close();
                return;
            }
            for (File file : files) {
                getSelect(file,datas,author,correspondingAuthor,first_authors,corresponding_authors);
            }

            HashMap<String, String> map = getChineseTitelNameMap(dicFile);
            FileInputStream in = new FileInputStream(files.get(0));
            // 指定读取文件时以UTF-8的格式读取
            BufferedReader bReader1 = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String[] abTitle = bReader1.readLine().split("\t");
            for (int i = 0; i < abTitle.length; i++) {
//                System.out.println(abTitle[i]);
                bw.write(map.get(abTitle[i]));
                bw.write("\t");

//                System.out.println(abTitle[i]+"\t"+map.get(abTitle[i]));
            }
            bw.write("第一作者\t通讯作者\n");
            for (int i = 0; i < datas.size(); i++) {
                String[] data = datas.get(i);
                for (String item : data) {
                    bw.write(item);
                    bw.write("\t");
                }
                bw.write(first_authors.get(i));
                bw.write("\t");
                bw.write(corresponding_authors.get(i));
                bw.write("\n");
            }
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getSelect(File file, ArrayList<String[]> datas, String firstAuthor, String correspondingAuthor, ArrayList<String> first_authors, ArrayList<String> corresponding_authors) {
        /* 读取数据 */
        try {
//            File file = new File(rootPath+ time + ".csv");


            FileInputStream in = new FileInputStream(file);
            // 指定读取文件时以UTF-8的格式读取
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;

            /**
             * Looping the read block until all lines in the file are read.
             */
            line = bReader.readLine();
            String colmNames[] = line.split("\t");
            int C1_index = 0;
            int RP_index = 0;
//            line = bReader.readLine();
            for (int i = 0; i < colmNames.length; i++) {
                if (colmNames[i].equals("C1")) {
                    C1_index = i;
                }else if (colmNames[i].equals("RP")){
                    RP_index = i;
                }

            }

            while ((line = bReader.readLine()) != null) {

                /**
                 * Splitting the content of tabbed separated line
                 */
                String datavalue[] = line.split("\t");
                if (datavalue.length==0){
                    continue;
                }
                String author_college = datavalue[C1_index];

                String author = author_college.split("; ")[0].replace("[", "");
                String corresponding_author = datavalue[RP_index].split(" \\(")[0];
//                System.out.println(author_college);
                if(firstAuthor!=null)
                    if(!author.contains(firstAuthor)){
                        continue;
                    }
                if(correspondingAuthor!=null)
                    if(!corresponding_author.contains(correspondingAuthor)){
                        continue;
                    }
                String thisCollege_ = author_college.split("] ")[1].split("; ")[0];
                String correspondingCollege = datavalue[RP_index].split("\\.; ")[0];

                if (thisCollege_.contains("Yunnan Univ,")||correspondingCollege.contains("Yunnan Univ,")||thisCollege_.contains("Yunnan University")||correspondingCollege.contains("Yunnan University")) {
//                    if(!thisCollege_.contains("Yunnan Univ,"))
//                    System.out.println(thisCollege_+"----------------"+correspondingCollege);
//                    System.out.println(datavalue[RP_index].replace("\n",""));
                    datas.add(datavalue);
//                    System.out.println(thisCollege_);
//                    thisCollege_.split("Univ, ")[1].split(", Kunming")[0]
                    corresponding_authors.add(corresponding_author);
                    first_authors.add(author);
                }
            }



            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<File> searchFiles(File folder, ArrayList<String> keywords) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile())
            result.add(folder);
//        System.out.println(folder);
        File[] subFile = folder.listFiles();
        for (File fileTemp : subFile) {

            // 指定文件名的匹配比较
            for (String keyword : keywords) {
                String fileName = fileTemp.getName();
                if (fileName.contains(keyword)) {
                    result.add(fileTemp);
                    break;
                }
            }

        }
        return result;


    }
}
