package com.ynu.ssm.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Calendar;

public class getEi {
    public static void main(String[] args) throws IOException, InterruptedException {
    	int currentYear = getSysYear();
        for(int i = currentYear;i<currentYear+2; i++) {
       	System.out.println(String.valueOf(i));
        }
    }

    public static Integer getSysYear() {
        Calendar date = Calendar.getInstance();
        Integer year = Integer.valueOf(date.get(Calendar.YEAR));
        return year;
    }

	private static boolean get_Ei(String year) throws IOException, InterruptedException {
		System.out.println("start python");
		File dir = new File("");// 参数为空
        String projectPath = dir.getCanonicalPath().replace("\\", "/");
        String pythonPath = projectPath+"/src/main/resources/python/ei/ei.py";

        Process process;
        try {
            process = Runtime.getRuntime().exec("python " + pythonPath + " -d " + year);
            System.out.println("python " + pythonPath + " -d " + year);
            BufferedReader stdOut=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s=stdOut.readLine())!=null){
                System.out.println(s);
            }
            int result=process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
	}

}
