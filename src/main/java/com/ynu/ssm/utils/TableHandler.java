package test;

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
    public static String dataFile = new TableHandler().getClass().getResource("/").getPath() + "savedrecs.txt";
    public static String url = "http://webapi.fenqubiao.com/api/user/get?user=yunnandaxue&password=kjc000000";
    /**
     * 返回指定wos全部数据的分区，按行的顺序返回，其中含有0的列说明api返回值错误
     */
    public static ArrayList<String> getSections(String  dataFile){
        ArrayList<String> sections = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(
                    new FileReader(dataFile));
            String line;

            /**
             * Looping the read block until all lines in the file are read.
             */
            line = bReader.readLine();
            String colmNames[] = line.split("\t");
            int ISSNIndex = 0, WCIndex = 0, PYIndex = 0;
            for (int i = 0; i < colmNames.length; i++) {
                if (colmNames[i].equals("SN")) {
                    ISSNIndex = i;

                } else if (colmNames[i].equals("WC")) {
                    WCIndex = i;
                } else if (colmNames[i].equals("PY")) {
                    PYIndex = i;
                }
            }
            while ((line = bReader.readLine()) != null) {

                /**
                 * Splitting the content of tabbed separated line
                 */
                String datavalue[] = line.split("\t");
                String ISSN_name = datavalue[ISSNIndex];
                String []WC_names = datavalue[WCIndex].toUpperCase().split("; ");
//                String WC_name2 = "";
//                if (WC_name.contains("; ")) {
//                    String[] strs = WC_name.split("; ");
//                    WC_name = strs[1];
//                    WC_name2 = strs[0];
//
//                }
                String Year = datavalue[PYIndex];
                WebPageResource t6 = new WebPageResource();
                String htmls = t6.getPageSource(url + "&year=" + Year + "&keyword=" + ISSN_name, "utf-8");
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
                    e.printStackTrace();
                    sections.add("0");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return sections;
    }
    public static void main(String[] args) {
        System.out.println(TableHandler.getSections(TableHandler.dataFile));

    }


}



