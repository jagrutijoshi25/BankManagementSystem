package com.bank;
import java.sql.*;
import java.util.*;

public class Admin extends User {

	public Admin(int id,String name,String email,String role)
	{
		this.id=id;
		this.name=name;
		this.email=email;
		this.role="admin";
	}
	public void showmenu()
	{
		try(Scanner scanner=new Scanner(System.in))
		{
		while(true)	
		{	System.out.println("\n1.View All customers");
		System.out.println("\n2.View All transactions");
		System.out.println("\n3.Logout");
		System.out.println("\nEnter your choice");
		int choice=scanner.nextInt();
		switch(choice)
		{
		case 1:{
			   viewallcust();
		       }
		case 2:
		      {
			  viewalltrans();
		      }
		case 3:{
			   System.out.println("Logged out");
			   return;
		       }
		}
	 }
	}	
   }		
		public void viewallcust()
		{	try(Connection con=DBConnection.getConnection())
		   {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select id,name,email,balance from users where role='customer'");
			System.out.println("\nCustomer information");
			while(rs.next())
			{
				System.out.println("ID:"+rs.getInt("id")+"\nName:"+rs.getString("name")+"\nEmail:"+rs.getString("email")+"\nBalance"+rs.getInt("balance"));
			}
		   }
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	  }	
	 public void viewalltrans()
	 {
		 try(Connection con=DBConnection.getConnection())
		 {
			 Statement st=con.createStatement();
			 ResultSet rs=st.executeQuery("select * from transactions");
			 System.out.println("\nCustomer information");
			 while(rs.next())
			 {
				 System.out.println("\nUser_id"+rs.getInt("user_id")+"\nType:"+rs.getString("type")+"\nAmount:"+rs.getInt("amount"));				 
			 }
		 }
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
	 }
}
