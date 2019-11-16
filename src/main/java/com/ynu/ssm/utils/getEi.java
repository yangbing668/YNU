package com.ynu.ssm.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class getEi {
    public static void main(String[] args) throws IOException, InterruptedException {
       System.out.println(getEi("2020"));

    }

	private static boolean getEi(String year) throws IOException, InterruptedException {
		System.out.println("start python");
        Process process;
        try {
            process = Runtime.getRuntime().exec("python  C:\\Users\\Barry\\Desktop\\YNU\\trunk\\src\\main\\resources\\python\\ei\\eiV2.py -d " + year); 
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
