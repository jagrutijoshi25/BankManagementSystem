package com.bank;

import java.sql.*;
import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Welcome to Bank App ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Perform Transaction");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                	{
                		registerUser(sc);
                	}
                case 2:
                	{
                		loginUser(sc);
                	}
                case 3:
                	{
                		performTransaction(sc);
                	}
                case 4 : {
                    System.out.println("thank you for using the Bank App!");
                    return;
                }
                default:
                	{
                		System.out.println("Invalid choice. Try again.");
                	}
            }
        }
    }
    private static void registerUser(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            System.out.print("Are you an admin? (yes/no): ");
            String role = sc.nextLine().equalsIgnoreCase("yes") ? "admin" : "customer";

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(name, email, password, role, balance) VALUES (?, ?, ?, ?, 0)"
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.executeUpdate();

            System.out.println("Registration successful!");

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    private static void loginUser(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");

                System.out.println("Login successful! Welcome, " + name + " (" + role + ")");

                if (role.equalsIgnoreCase("admin")) {
                    Admin admin = new Admin(id, name, email,role);
                    admin.showmenu();
                } else {
                    Customer customer = new Customer(id, name, email,role);
                    customer.showmenu();
                }
            } else {
                System.out.println("❌ Invalid email or password.");
            }

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    private static void performTransaction(Scanner sc) {
        try {
            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter transaction type (deposit/withdraw): ");
            String type = sc.nextLine().trim();

            System.out.print("Enter amount: ₹");
            int amount =Integer.parseInt(sc.nextLine());

            TransactionThread t = new TransactionThread(userId, type, amount);
            t.start();
            t.join();

            System.out.println("Transaction completed.");
        } catch (Exception e) {
            System.out.println("Failed to perform transaction: " + e.getMessage());
        }
    }
    static class TransactionThread extends Thread {
        private final int userId;
        private final String type;
        private final int amount;

        public TransactionThread(int userId, String type, int amount) {
            this.userId = userId;
            this.type = type;
            this.amount = amount;
        }
        public void run() {
            synchronized (TransactionThread.class) {
                try {
                    Customer customer = new Customer(userId, "ThreadUser", "thread@bank.com", type);

                    if (type.equalsIgnoreCase("deposit")) {
                        customer.deposit(amount);
                    } else if (type.equalsIgnoreCase("withdraw")) {
                        customer.withdraw(amount);
                    } else {
                        System.out.println("Invalid transaction type.");
                    }
                } catch (Exception e) {
                    System.out.println("Thread error: " + e.getMessage());
                }
            }
        }
    }
}
