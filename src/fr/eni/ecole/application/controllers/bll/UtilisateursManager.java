package fr.eni.ecole.application.controllers.bll;

import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.UtilisateursDAO;

import java.util.List;

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

    public void addUtilisateurs(Utilisateurs user) throws BLLException {
        try {
            utilisateursDAO.insert(user);
        } catch (DALException e) {
            throw new BLLException("Error adding user", e);
        }
    }

    public void updateUtilisateurs(Utilisateurs user) throws BLLException {
        try {
            utilisateursDAO.update(user);
        } catch (DALException e) {
            throw new BLLException("Error updating user", e);
        }
    }

    public void deleteUtilisateurs(int userId) throws BLLException {
        try {
            utilisateursDAO.delete(userId);
        } catch (DALException e) {
            throw new BLLException("Error deleting user", e);
        }
    }
}
