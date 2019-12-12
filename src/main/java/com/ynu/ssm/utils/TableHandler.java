package com.ynu.ssm.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.*;
import java.util.ArrayList;
/**

 * @author litao

 * @date ${date}

 * @version 1.0

 */
public class TableHandler {
    public static String url = "http://webapi.fenqubiao.com/api/user/get?user=yunnandaxue&password=kjc000000";
    /**
     * 返回指定wos全部数据的分区，按行的顺序返回，其中含有0的列说明api返回值错误
     */
    @SuppressWarnings("resource")
	public static ArrayList<String> getSections(String  dataFile,String handled_file){
        ArrayList<String> sections = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            WebPageResource t6 = new WebPageResource();
            bReader = new BufferedReader(
                    new FileReader(dataFile));
            String line;

            /**
             * Looping the read block until all lines in the file are read.
             */
            line = bReader.readLine();
            String colmNames[] = line.split("\t");
            int ISSNIndex = 0, WCIndex = 0, PYIndex = 0,SOIndex =0,EIIndex=0;
            for (int i = 0; i < colmNames.length; i++) {
                if (colmNames[i].equals("SN")) {
                    ISSNIndex = i;

                } else if (colmNames[i].equals("WC")) {
                    WCIndex = i;
                } else if (colmNames[i].equals("PY")) {
                    PYIndex = i;
                } else if (colmNames[i].equals("SO")) {
                    SOIndex = i;
                } else if (colmNames[i].equals("EI")) {
                    EIIndex = i;
                }
            }
            int unknownNum = 0;
            while ((line = bReader.readLine()) != null) {

                /**
                 * Splitting the content of tabbed separated line
                 */
                String datavalue[] = line.split("\t");
                String ISSN_name = datavalue[ISSNIndex].toLowerCase();
                String []WC_names = datavalue[WCIndex].toUpperCase().split("; ");
//                String WC_name2 = "";
//                if (WC_name.contains("; ")) {
//                    String[] strs = WC_name.split("; ");
//                    WC_name = strs[1];
//                    WC_name2 = strs[0];
//
//                }
                String Year = datavalue[PYIndex];

                String htmls = t6.getPageSource(url + "&year=2018" + "&keyword=" + ISSN_name, "utf-8");
                if (htmls.equals("")){
                    ISSN_name = datavalue[SOIndex];
                    ISSN_name=ISSN_name.replace(" ","%20");
//                    Thread.sleep(100);

                    htmls = t6.getPageSource(url + "&year=2018" + Year + "&keyword=" + ISSN_name, "utf-8");
                    if (htmls.equals("")){
                        ISSN_name = datavalue[EIIndex];
                        ISSN_name=ISSN_name.replace(" ","%20");
//                        Thread.sleep(100);
                        htmls = t6.getPageSource(url + "&year=2018" + Year + "&keyword=" + ISSN_name, "utf-8");
                    }
                    if (htmls.equals("")){
                    	unknownNum+=1;
                        System.out.print("未知分区个数：" + unknownNum);
                    }
                }
                try {

                    JSONObject obj = new JSONObject(htmls);
                    JSONArray array = obj.getJSONArray("JCR");
                    boolean sectionIsAdded = false;
                    for(String WC_name:WC_names) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject child = array.getJSONObject(i);
                            if (child.getString("Name").equals(WC_name)) {
                                sections.add(child.getString("Section"));
                                sectionIsAdded = true;
                                break;
                            }

                        }
                        if (sectionIsAdded){
                            break;
                        }
                    }
                    if (!sectionIsAdded) {
                        sections.add("0");
                    }
                } catch (JSONException e) {
//                    e.printStackTrace();
                    sections.add("0");
                    System.out.println(htmls);
                }

            }
            bReader = new BufferedReader(new FileReader(dataFile));
            int i = 0;
            line = bReader.readLine();
            String data=line.replace("\n","")+"\tjcr_section\n";
            ArrayList<String> save_data= new ArrayList<>();
            save_data.add(data);

            while ((line = bReader.readLine()) != null) {
                data=line.replace("\n","")+sections.get(i)+"\n";
                save_data.add(data);
//                out.write(data);
                i++;
            }
            File file =new File(handled_file);
            Writer out =new FileWriter(file);
            for (String save_str:
                 save_data) {
                out.write(save_str);

            }

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

       return sections;
    }
    public static void main(String[] args) {
        for (int i=2013;i<=2019;i++){
            System.out.println(getSections("C:\\Users\\Barry\\Desktop\\YNU\\trunk\\src\\main\\resources\\python\\wos\\SCI\\"+String.valueOf(i)+".txt","C:\\Users\\Barry\\Desktop\\YNU\\trunk\\src\\main\\resources\\python\\wos\\SCI\\"+String.valueOf(i)+".txt"));
        }
    }


}



