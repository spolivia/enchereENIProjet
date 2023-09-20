package fr.eni.ecole.application.modele.dal;

import fr.eni.ecole.application.modele.dal.jdbc.RetraitsDAOJdbcImpl;

public class DAOFactory {

	// public static ArticleDAO getArticleDAO() {
	// return new ArticlesDAOJdbcImpl();
	// }

	public static RetraitsDAO getRetraitsDAO() {
		return new RetraitsDAOJdbcImpl();
	}
}
