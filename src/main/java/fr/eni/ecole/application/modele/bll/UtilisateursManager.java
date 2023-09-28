package fr.eni.ecole.application.modele.bll;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.UtilisateursDAO;

public class UtilisateursManager {
	private UtilisateursDAO utilisateursDAO;

	public UtilisateursManager(UtilisateursDAO utilisateursDAO) {
		this.utilisateursDAO = utilisateursDAO;
	}

	public Utilisateurs getUtilisateursById(int userId) throws BLLException {
		try {
			return utilisateursDAO.selectById(userId);
		} catch (DALException e) {
			throw new BLLException("Error retrieving user by ID", e);
		}
	}

	public List<Utilisateurs> getAllUtilisateurs() throws BLLException {
		try {
			return utilisateursDAO.selectAll();
		} catch (DALException e) {
			throw new BLLException("Error retrieving all users", e);
		}
	}

	public void addUtilisateur(Utilisateurs user) throws BLLException {
		try {
			utilisateursDAO.insert(user);
		} catch (DALException e) {
			throw new BLLException("Error adding user", e);
		}
	}

	public void updateUtilisateur(Utilisateurs user) throws BLLException {
		try {
			utilisateursDAO.update(user);
		} catch (DALException e) {
			throw new BLLException("Error updating user", e);
		}
	}

	public void deleteUtilisateur(int userId) throws BLLException {
		try {
			utilisateursDAO.delete(userId);
		} catch (DALException e) {
			throw new BLLException("Error deleting user", e);
		}
	}

	public boolean login(HttpServletRequest request, String username, String password) throws BLLException {
		try {
			return utilisateursDAO.login(request, username, password);
		} catch (DALException e) {
			throw new BLLException("Error during login", e);
		}
	}

	public void logout(HttpServletRequest request) {
		utilisateursDAO.logout(request);
	}

	public boolean isAuthenticated(HttpServletRequest request) {
		return utilisateursDAO.isAuthenticated(request);
	}

	public int authenticateUser(String pseudo, String motDePasse) throws DALException {
		return utilisateursDAO.authenticateUser(pseudo, motDePasse);
	}

	public Utilisateurs getUtilisateursByPseudo(String pseudo) throws DALException {
		return utilisateursDAO.getUtilisateursByPseudo(pseudo);
	}

	public void updateUtilisateurProfile(Utilisateurs user) throws BLLException {
		try {
			utilisateursDAO.updateUtilisateurProfile(user);
		} catch (DALException e) {
			throw new BLLException("Error updating user", e);
		}
	}

	public Utilisateurs getUtilisateursByArticle(int articleId) throws BLLException {
		try {
			return utilisateursDAO.selectByArticle(articleId);
		} catch (DALException e) {
			throw new BLLException("Error retrieving user by article", e);
		}
	}
}
