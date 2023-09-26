package fr.eni.ecole.application.modele.dal;

import fr.eni.ecole.application.modele.dal.jdbc.ArticlesDAOJdbcImpl;
import fr.eni.ecole.application.modele.dal.jdbc.CategoriesDAOJdbcImpl;
import fr.eni.ecole.application.modele.dal.jdbc.EncheresDAOJdbcImpl;
import fr.eni.ecole.application.modele.dal.jdbc.RetraitsDAOJdbcImpl;
import fr.eni.ecole.application.modele.dal.jdbc.UtilisateursDAOJdbcImpl;

public class DAOFactory {

	public static ArticlesDAO getArticlesDAO() {
		return new ArticlesDAOJdbcImpl();
	}

	public static RetraitsDAO getRetraitsDAO() {
		return new RetraitsDAOJdbcImpl();
	}

	public static CategoriesDAO getCategoriesDAO() {
		return new CategoriesDAOJdbcImpl();
	}

	public static UtilisateursDAO getUtilisateursDAO() {
		return new UtilisateursDAOJdbcImpl();
	}

	public static EncheresDAO getEncheresDAO() {
		return new EncheresDAOJdbcImpl();
	}
}
