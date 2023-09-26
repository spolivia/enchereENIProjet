package fr.eni.ecole.application.modele.dal;

import java.util.List;

import fr.eni.ecole.application.modele.bo.Encheres;

public interface EncheresDAO extends DAO<Encheres> {

	public List<Encheres> selectByArticleId(int articleId) throws DALException;

	public List<Encheres> selectByUserId(int userId) throws DALException;

}
