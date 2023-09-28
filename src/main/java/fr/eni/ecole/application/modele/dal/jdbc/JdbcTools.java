package fr.eni.ecole.application.modele.dal.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.eni.ecole.application.modele.dal.Settings;

public class JdbcTools {
	private static Connection connection;

	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed())  {
			connection = DriverManager.getConnection(Settings.getUrlDB(), Settings.getUserDB(), Settings.getPwDB());
		}
		return connection;
	}
	
	public static void closeConnection() throws SQLException {
	    if (connection != null && !connection.isClosed()) {
	        connection.close();
	    }
	}

}