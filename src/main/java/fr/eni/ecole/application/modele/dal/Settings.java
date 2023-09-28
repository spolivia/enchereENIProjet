package fr.eni.ecole.application.modele.dal;

import fr.eni.ecole.application.modele.dal.jdbc.JdbcTools;


//Info de connexion pour adminsitrateur.
public class Settings extends JdbcTools{
	public static String urlDB = "jdbc:sqlserver://127.0.0.1;databasename=TP_ENCHERES_DB;trustservercertificate=true";
	public static String userDB = "ADMIN_ENCHERES";
	public static String pwDB = "admin";

	public static String getUrlDB() {
		return urlDB;
	}
	public static void setUrlDB(String urlDB) {
		Settings.urlDB = urlDB;
	}
	public static String getUserDB() {
		return userDB;
	}
	public static void setUserDB(String userDB) {
		Settings.userDB = userDB;
	}
	public static String getPwDB() {
		return pwDB;
	}
	public static void setPwDB(String pwDB) {
		Settings.pwDB = pwDB;
	}
}