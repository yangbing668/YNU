package test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SCISelectFilter {
    public HashMap<String, String> getChineseTitelNameMap(String file) {
        HashMap<String, String> map = new HashMap<>();
        try {
            BufferedReader bReader = new BufferedReader(
                    new FileReader(file));


            FileReader fileReader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            lineNumberReader.skip(Long.MAX_VALUE);
            long lines = lineNumberReader.getLineNumber() + 1;
            fileReader.close();
            lineNumberReader.close();
            System.out.println(lines);
            for (int i = 0; i < lines; i = i + 3) {
                String key = bReader.readLine().replace("\t", "").replace("\n", "");
                String value = bReader.readLine().replace("\t", "").replace("\n", "");
                System.out.println(key + "\t" + value);
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
//        fliter.getChineseTitelNameMap("E:\\\\黑马练习\\\\baisic-code\\\\t.txt");
        filter.filter("E:\\黑马练习\\baisic-code\\out\\production\\day04_code\\", "E:\\黑马练习\\baisic-code\\out\\production\\savedrecs filtered.xls", 2017, 2019);
//        fliter.getSelect("E:\\黑马练习\\baisic-code\\out\\production\\day04_code\\savedrecs1.txt", "E:\\黑马练习\\baisic-code\\out\\production\\savedrecs1.xls");
    }

    public void filter(String sourcePath, String newFile, int startYear, int endYear) {
        ArrayList<String> keywords = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            keywords.add(String.valueOf(i));
        }
        try {
            ArrayList<String[]> datas = new ArrayList();
            ArrayList<String> first_authors = new ArrayList();
            ArrayList<String> first_author_colleges = new ArrayList();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    newFile), "UTF-8"));
            List<File> files = SCISelectFilter.searchFiles(new File(sourcePath), keywords);

            for (File file : files) {
                getSelect(file,datas,first_authors,first_author_colleges);

            }
            HashMap<String, String> map = getChineseTitelNameMap("E:\\\\黑马练习\\\\baisic-code\\\\wos_dic.txt");
            BufferedReader bReader1 = new BufferedReader(
                    new FileReader(files.get(0)));
            String[] abTitle = bReader1.readLine().split("\t");
            for (int i = 0; i < abTitle.length; i++) {
                bw.write(map.get(abTitle[i]));
                bw.write("\t");

//                System.out.println(abTitle[i]+"\t"+map.get(abTitle[i]));
            }
            bw.write("第一作者\t第一作者学院\n");
            for (int i = 0; i < datas.size(); i++) {
                String[] data = datas.get(i);
                for (String item : data) {
                    bw.write(item);
                    bw.write("\t");
                }
                bw.write(first_authors.get(i));
                bw.write("\t");
                bw.write(first_author_colleges.get(i));
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

    public void getSelect(File file, ArrayList<String[]> datas, ArrayList<String> first_authors, ArrayList<String> first_author_colleges) {
        /* 读取数据 */
        try {
//            File file = new File(rootPath+ time + ".csv");


            BufferedReader bReader = new BufferedReader(
                    new FileReader(file));
            String line;

            /**
             * Looping the read block until all lines in the file are read.
             */
            line = bReader.readLine();
            String colmNames[] = line.split("\t");
            int C1_index = 0;
            line = bReader.readLine();
            for (int i = 0; i < colmNames.length; i++) {
                if (colmNames[i].equals("C1")) {
                    C1_index = i;
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
                System.out.println(author_college);

                String thisCollege_ = author_college.split("] ")[1].split("; ")[0];

                if (thisCollege_.contains("Yunnan Univ,")) {
                    datas.add(datavalue);
                    System.out.println(thisCollege_);
                    first_author_colleges.add(thisCollege_.split("Univ, ")[1].split(", Kunming")[0]);
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
        System.out.println(folder);
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
