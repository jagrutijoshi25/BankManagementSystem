package com.bank;
import java.io.*;

public class Filedata
{
   public static void savedata(int userId,String type,int amount,int updamt)
   {
	   try
	   {   String filename="Data.txt";
	        FileWriter fw=new FileWriter(filename,true);
		   fw.write("Transaction mode:"+type);
		   fw.write("Amount:"+amount);
		   fw.write("Balance:"+updamt);
		   fw.close();
	   }
	   catch(Exception e)
	   {
		   System.out.println(e.getMessage());
	   }
   }
}
