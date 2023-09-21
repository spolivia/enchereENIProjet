package fr.eni.ecole.application.modele.dal;

import javax.servlet.http.HttpServletRequest;

import fr.eni.ecole.application.modele.bo.Utilisateurs;

public interface UtilisateursDAO extends DAO<Utilisateurs> {

    void logout(HttpServletRequest request);

    boolean login(HttpServletRequest request, String username, String password) throws DALException;

    boolean isAuthenticated(HttpServletRequest request);
    
    public int trouverIDUtilisateur(HttpServletRequest request);
}