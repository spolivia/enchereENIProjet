package fr.eni.ecole.application.modele.dal.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.eni.ecole.application.modele.dal.Settings;

public class JdbcTools {
	private static Connection connection;

	
	//Classe a tirer l'info de connexion par administrateur a travers Settings.java dans la couche DAL
	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(Settings.getUrlDB(), Settings.getUserDB(), Settings.getPwDB());
		}
		return connection;
	}
	
	
	//Classe de fermeture de connexion.
	public static Connection closeConnection() {
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection=null;
		}
		return connection;
	}
}