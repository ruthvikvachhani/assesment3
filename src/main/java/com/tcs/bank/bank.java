package com.tcs.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class bank {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Select one of the option below");
		System.out.println("1.withdraw");
		System.out.println("2.deposit");

		byte choice = scanner.nextByte();

		switch (choice) {
		case 1:
			withdraw();
			break;
		case 2:
				deposit();
			break;
		}

	}

	private static void deposit() {
		// TODO Auto-generated method stub
		String userName;
		float depositAmmount;
		Scanner scanner = new Scanner(System.in);

		System.out.println("enter the username: ");
		userName = scanner.nextLine();
		
		System.out.println("enter ammount");
		depositAmmount = scanner.nextFloat();
		
		depositBalance(userName,depositAmmount);
	}

	public static void depositBalance(String username, float depositAmmount) {
		String DB_URL = "jdbc:mysql://localhost/bank";
		String DB_USER = "root";
		String DB_PASSWORD = "Nuvelabs123$";
		float balance = 0;
		float new_balance = 0;
		int id = 0;

		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();) {
			
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM USER_ACCOUNT WHERE USER_NAME LIKE '" + username + "';");
			
			resultSet.next();
			balance = resultSet.getFloat("BALANCE_AMOUNT");

			new_balance = balance + depositAmmount;
			statement.executeUpdate("UPDATE USER_ACCOUNT SET BALANCE_AMOUNT = "+new_balance+" WHERE USER_NAME LIKE '" + username + "';");
				
			resultSet = statement.executeQuery("SELECT * FROM USER_ACCOUNT WHERE USER_NAME LIKE '" + username + "';");				
			resultSet.next();
			System.out.println(resultSet.getFloat("BALANCE_AMOUNT"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		


	public static void withdraw() {
		String userName;
		float withdrawalAmmount;
		

		Scanner scanner = new Scanner(System.in);

		System.out.println("enter the username: ");
		userName = scanner.nextLine();
		
		System.out.println("entwer ammount");
		withdrawalAmmount = scanner.nextFloat();
		
		retriveBalance(userName,withdrawalAmmount);
	}

	public static void retriveBalance(String username, float withdrawalAmmount) {
		String DB_URL = "jdbc:mysql://localhost/bank";
		String DB_USER = "root";
		String DB_PASSWORD = "Nuvelabs123$";
		float balance = 0;
		float new_balance = 0;
		int id = 0;

		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();) {
			System.out.println("SELECT * FROM USER_ACCOUNT WHERE USER_NAME == '" + username + "'");
			ResultSet resultSet = statement.executeQuery("SELECT * FROM USER_ACCOUNT WHERE USER_NAME LIKE '" + username + "';");
			
			
			resultSet.next();
			id = resultSet.getInt("USER_ID");
			balance = resultSet.getFloat("BALANCE_AMOUNT");
			
			if(balance >= withdrawalAmmount)
			{
				new_balance = balance - withdrawalAmmount;
				statement.executeUpdate("UPDATE USER_ACCOUNT SET BALANCE_AMOUNT = "+new_balance+" WHERE USER_NAME LIKE '" + username + "';");
				resultSet = statement.executeQuery("SELECT * FROM USER_ACCOUNT WHERE USER_NAME LIKE '" + username + "';");
				
				resultSet.next();
				System.out.println(resultSet.getFloat("BALANCE_AMOUNT"));
			}
			else
			{
				System.out.println("Insufficeint balance");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}	
