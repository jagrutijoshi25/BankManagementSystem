package com.bank;
import java.sql.*;

public class DBConnection {
	public static Connection getConnection()throws Exception
	{
		String url="jdbc:postgresql://localhost:5432/bankdb";
		String user="postgres";
		String password="jagruti";
		return DriverManager.getConnection(url,user,password);
	}

}
