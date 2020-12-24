package com.boxcinemas;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;

import com.boxcinemas.Logger;

// This class can be used to initialize the database connection 
public class DatabaseConnection { 
	
	protected static Connection initializeDatabase() throws SQLException, ClassNotFoundException 
	{ 
		
		// Initialize all the information regarding 
		// Database Connection 
		String dbDriver = "com.mysql.cj.jdbc.Driver"; 
		String dbURL = "jdbc:mysql://localhost:3306/"; 
		// Database name to access 
		String dbName = "BOXCINEMAS"; 
		String dbUsername = "root"; 
		String dbPassword = "pass123123"; 

		Class.forName(dbDriver); 
		Connection con = DriverManager.getConnection(dbURL + dbName, 
													dbUsername, 
													dbPassword); 
		return con; 
	}
	
} 
