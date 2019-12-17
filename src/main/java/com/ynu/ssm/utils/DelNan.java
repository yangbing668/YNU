package com.ynu.ssm.utils;
/*
*功能：读取txt文件，检测每行是否含有Yunnan University , 若没有则删除该行
*/
import java.io.*;
import java.util.Scanner;


public class DelNan{

	public static String DataClean(String filePath,String savePath) {
	    String newline = "";
        File inFile = new File(filePath);
        BufferedReader br = null;
        String readedLine;
        BufferedWriter bw = null;
        int num = 0;
        try
        {
        	br = new BufferedReader(new FileReader(inFile));
        	readedLine = br.readLine();
        	newline+=readedLine + "\n";
            while((readedLine = br.readLine()) != null) 
            {

                if(readedLine.contains("Yunnan University")||readedLine.contains("Yunnan Univ,"))
                {
                	newline+=readedLine + "\n";
                	num += 1;
                    continue;
                }
            }
        }catch (Exception e) 
        {
            e.printStackTrace();
        }finally 
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        FileWriter fw = null;
        
		try
		{
			File outFile = new File(savePath);
			fw = new FileWriter(outFile);
		}catch (IOException e)
		{
			e.printStackTrace();
			return "fail";
		}
		
        bw = new BufferedWriter(fw);
        
        try
        {
			bw.write(newline);
		}catch (IOException e)
        {
			e.printStackTrace();
			return "fail";
		}
        try
        {
			if(bw != null)
			{
//				System.out.println(newline);
				bw.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
			return "fail";
		}
		return "success:"+String.valueOf(num);
	}
	
	public static void main(String[] args) {
	    for (int i=2013;i<=2019;i++){
            System.out.println(DataClean("C:\\Users\\Barry\\Desktop\\YNU\\trunk\\src\\main\\resources\\python\\wos\\SCI\\"+String.valueOf(i)+".txt","C:\\Users\\Barry\\Desktop\\YNU\\trunk\\src\\main\\resources\\python\\wos\\SCI\\"+String.valueOf(i)+".txt"));
        }

    }
}