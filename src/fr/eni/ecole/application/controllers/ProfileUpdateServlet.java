package fr.eni.ecole.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.controllers.bll.BLLException;
import fr.eni.ecole.application.controllers.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DAOFactory;

@WebServlet("/ProfileUpdateServlet")
public class ProfileUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ProfileUpdate.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String newNom = request.getParameter("nom");
        String newPrenom = request.getParameter("prenom");
        String newEmail = request.getParameter("email");
        String newTelephone = request.getParameter("telephone");
        String newRue = request.getParameter("rue");
        String newCodePostal = request.getParameter("codePostal");
        String newVille = request.getParameter("ville");

        // Get the user's pseudo from the session
        String pseudo = (String) request.getSession().getAttribute("pseudo");

        // Create a Utilisateurs object with updated values
        Utilisateurs user = new Utilisateurs();
        user.setPseudo(pseudo); // Set the pseudo
        user.setNom(newNom);
        user.setPrenom(newPrenom);
        user.setEmail(newEmail);
        user.setTelephone(newTelephone);
        user.setRue(newRue);
        user.setCodePostal(newCodePostal);
        user.setVille(newVille);

        // Create an instance of UtilisateursManager and update the user's profile
        UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
        try {
            utilisateursManager.updateUtilisateurProfile(user);
            request.setAttribute("successMessage", "Profile updated successfully!");
        } catch (BLLException e) {
            request.setAttribute("errorMessage", "Error updating profile: " + e.getMessage());
        }

        // Forward the request to the JSP
        response.sendRedirect(request.getContextPath() + "/listeArticles");
    }
}

