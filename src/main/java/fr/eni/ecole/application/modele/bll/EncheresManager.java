package fr.eni.ecole.application.modele.bll;

import java.util.List;

import fr.eni.ecole.application.modele.bo.Encheres;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.EncheresDAO;

public class EncheresManager {

	private EncheresDAO encheresDAO;

	public EncheresManager(EncheresDAO encheresDAO) {
		this.encheresDAO = encheresDAO;
	}

	public List<Encheres> getAllEncheres() throws BLLException {
		try {
			return encheresDAO.selectAll();
		} catch (DALException e) {
			throw new BLLException("Erreur récuperation enchères", e);
		}
	}

	public List<Encheres> getEncheresByArticle(int articleId) throws BLLException {
		try {
			return encheresDAO.selectByArticleId(articleId);
		} catch (DALException e) {
			throw new BLLException("Erreur récupération enchères par article", e);
		}

	}

	public List<Encheres> selectEncheresByUserId(int userId) throws BLLException {
		try {
			return encheresDAO.selectByUserId(userId);
		} catch (DALException e) {
			throw new BLLException("Erreur récupération enchères par utilisateur", e);
		}
	}

	public Encheres selectEncheresById(int encheresId) throws BLLException {
		try {
			return encheresDAO.selectById(encheresId);
		} catch (DALException e) {
			throw new BLLException("Erreur récupération enchères par ID", e);
		}
	}

	public List<Encheres> selectAllEncheres() throws BLLException {
		try {
			return encheresDAO.selectAll();
		} catch (DALException e) {
			throw new BLLException("Erreur récupération enchères ", e);
		}
	}

	public void updateEncheres(Encheres encheres) throws BLLException {
		try {
			encheresDAO.update(encheres);
		} catch (DALException e) {
			throw new BLLException("Erreur mise à jour enchères ", e);
		}
	}

	public void insertEncheres(Encheres encheres) throws BLLException {
		try {
			encheresDAO.insert(encheres);
		} catch (DALException e) {
			throw new BLLException("Erreur insertion enchère ", e);
		}

	}

	public void deleteEncheresByID(int encheresId) throws BLLException {

		try {
			encheresDAO.delete(encheresId);
		} catch (DALException e) {
			throw new BLLException("Erreur suppression enchère par ID ", e);
		}
	}

	public void deleteEncheres(Encheres encheres) throws BLLException {

		try {
			encheresDAO.delete(encheres);
		} catch (DALException e) {
			throw new BLLException("Erreur suppression enchère ", e);
		}
	}

	public int lastEnchere(int no_article) throws BLLException {

		try {
			return encheresDAO.lastEnchere(no_article);
		} catch (DALException e) {
			throw new BLLException("Erreur récupération dernière enchère ", e);
		}
	}

}
