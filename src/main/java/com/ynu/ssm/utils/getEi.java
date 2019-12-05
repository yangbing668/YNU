package com.ynu.ssm.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Calendar;

public class getEi {
    public static void main(String[] args) throws IOException, InterruptedException {
    	int currentYear = getSysYear();
//        for(int i = currentYear;i<currentYear+2; i++) {
        get_Ei();
//        }
    }

    public static Integer getSysYear() {
        Calendar date = Calendar.getInstance();
        Integer year = Integer.valueOf(date.get(Calendar.YEAR));
        return year;
    }

	private static void get_Ei() throws IOException, InterruptedException {
		System.out.println("start python");
        File dir = new File("");// 参数为空
        String storePath = dir.getCanonicalPath() + "\\src\\main\\resources\\python\\wos";
        String projectPath = dir.getCanonicalPath().replace("\\", "/");
        String pythonPath = projectPath+"/src/main/resources/python/wos/get_wos.py";
        String[] arguments = new String[] {"python", pythonPath ,"2013","2019","CPCI-S",storePath};
        try {

            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            in.close();
            int re = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
