package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection 
{
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		//System.out.println("Driver Loaded.......!!");
		
		Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","InSane");
		//Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdbname","root/username","password"); 
		//This is for Mysql Workbench
		// if you want to do something else with it then you can do it for that, its driver URL and port will be different.
		//System.out.println("COnnection Successfull....!\n");
		
		return conn;
	}
}


