package fr.eni.ecole.application.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.jdbc.UtilisateursDAOJdbcImpl;

@WebServlet("/compteCreation")
public class CreerCompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("compteCreation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");

        Utilisateurs user = new Utilisateurs();
        user.setPseudo(username);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setRue(rue);
        user.setCodePostal(codePostal);
        user.setVille(ville);
        user.setMotDePasse(password);

        UtilisateursDAOJdbcImpl userDao = new UtilisateursDAOJdbcImpl();
        try {
            userDao.insert(user);
            System.out.println("Compte Créer");
            request.getRequestDispatcher("/listeArticlesConnecter").forward(request, response);

        	} catch (DALException e) {
            e.printStackTrace(); 
            System.out.println("Echec");     
        }
    }
}
