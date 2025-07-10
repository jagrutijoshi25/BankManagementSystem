package com.bank;
import java.sql.*;
import java.util.*;
public class Customer extends User 
{
	public Customer(int id,String name,String email,String role)
	{
		this.id=id;
		this.name=name;
		this.email=email;
		this.role=role;
	}
	public void showmenu()
	{
		try (Scanner sc = new Scanner(System.in))
		{ while(true)	
		{ System.out.println("\n1.Depsoit");
		  System.out.println("\n2.Withdraw");
		  System.out.println("\n3.CheckBalance");
		  System.out.println("\n4.Logout");
		  System.out.println("Enter choice");
		int choice=sc.nextInt();
		switch(choice)
		{ case 1:
		       { System.out.println("Enter amount to deposit");
		         int amount=sc.nextInt();
		         deposit(amount);
		       }  
		case 2:
		      { System.out.println("Enter amount for withdrawal");
		        int amount=sc.nextInt();
		        withdraw(amount);
		      } 
		case 3:
			{ CheckBalance();
			}
		case 4:{
			System.out.println("\nLoggoed out");
			return;
		}
		}
		}
		}
		catch(Exception e) {}
	}
		public void deposit(int amount)
		{
			try(Connection con=DBConnection.getConnection())
			{
				PreparedStatement ps1=con.prepareStatement("select balance from users where id=?");
				ps1.setInt(1,this.id);
				ResultSet rs=ps1.executeQuery();
				if(!rs.next())
					return;
				int currbal=rs.getInt("balance");
				int newbal=currbal+amount;
				PreparedStatement ps2=con.prepareStatement("update users set balance=? where id=?");
				ps1.setInt(1, newbal);
				ps2.setInt(2,this.id);
				ps2.executeUpdate();
				PreparedStatement ps3=con.prepareStatement("insert into transactions(user_id,type,amount) values(?,?,?)");
				ps3.setInt(1,this.id);
				ps3.setString(2,"deposit");
				ps3.setInt(3, amount);	
				ps3.executeUpdate();
				System.out.println("\nAmount deposited successfully");
				Filedata.savedata(this.id,"deposit",amount,newbal);
			}
			catch(Exception e)
			{ System.out.println(e.getMessage());
			}
		}
		public void withdraw(int amount)
		{
			try(Connection con=DBConnection.getConnection())
			{
				PreparedStatement ps1=con.prepareStatement("select balance from users where id=?");
				ps1.setInt(1,this.id);
				ResultSet rs=ps1.executeQuery();
				if(!rs.next())
					return;
				int currbal=rs.getInt("balance");
				int newbal=currbal-amount;
				PreparedStatement ps2=con.prepareStatement("update users set balance=? where id=?");
				ps1.setInt(1, newbal);
				ps2.setInt(2,this.id);
				ps2.executeUpdate();	
				PreparedStatement pr=con.prepareStatement("insert into transactions(user_id,type,amount) values(?,?,?)");
				pr.setInt(1,this.id);
				pr.setString(2,"withdraw");
				pr.setInt(3, amount);
				Filedata.savedata(this.id,"withdraw",amount,newbal);
			}
			catch(Exception e)
			{ System.out.println(e.getMessage());
			}
		}
		public void CheckBalance()
		{
			try(Connection con=DBConnection.getConnection())
			{
				PreparedStatement pr=con.prepareStatement("select * from users where id=?");
				pr.setInt(1,this.id);
				ResultSet rs=pr.executeQuery();
				while(rs.next())
				{
					System.out.println("\nId:"+rs.getInt("id")+"\nName:"+rs.getString("name")+"\nEmail:"+rs.getString("email")+"\nBalance"+rs.getInt("balance"));
				}	
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
}

