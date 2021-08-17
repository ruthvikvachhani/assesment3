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
			
			balance = retrive(statement,username);
			

			new_balance = balance + depositAmmount;
			update(statement,new_balance,username);
			
				
			balance = retrive(statement,username);
			System.out.println(balance);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		


	private static void update(Statement statement, float new_balance, String username) throws SQLException {
		// TODO Auto-generated method stub
		statement.executeUpdate("UPDATE USER_ACCOUNT SET BALANCE_AMOUNT = "+new_balance+" WHERE USER_NAME LIKE '" + username + "';");
	}

	private static float retrive(Statement statement ,String username) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USER_ACCOUNT WHERE USER_NAME LIKE '" + username + "';");
		
		resultSet.next();
		return resultSet.getFloat("BALANCE_AMOUNT");
	}

	public static void withdraw() {
		String userName;
		float withdrawalAmmount;
		

		Scanner scanner = new Scanner(System.in);

		System.out.println("enter the username: ");
		userName = scanner.nextLine();
		
		System.out.println("entwer ammount");
		withdrawalAmmount = scanner.nextFloat();
		
		updateBalance(userName,withdrawalAmmount);
	}

	public static void updateBalance(String username, float withdrawalAmmount) {
		String DB_URL = "jdbc:mysql://localhost/bank";
		String DB_USER = "root";
		String DB_PASSWORD = "Nuvelabs123$";
		float balance = 0;
		float new_balance = 0;

		
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();) {
			balance = retrive(statement, username);
			
			if(balance >= withdrawalAmmount)
			{
				new_balance = balance - withdrawalAmmount;
				
				update(statement,new_balance,username);
				balance = retrive(statement,username);
				System.out.println(balance);
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
