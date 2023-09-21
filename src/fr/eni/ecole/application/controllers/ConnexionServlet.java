package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.controllers.bll.BLLException;
import fr.eni.ecole.application.controllers.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.dal.DAOFactory;
import fr.eni.ecole.application.modele.bo.Articles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/Connexion")
public class ConnexionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UtilisateursManager utilisateursManager;

    public void init() throws ServletException {
        super.init();
        utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Connexion.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pseudo = request.getParameter("pseudo");
        String motDePasse = request.getParameter("motDePasse");

        try {
            boolean isAuthenticated = utilisateursManager.login(request, pseudo, motDePasse);

            if (isAuthenticated) {
                // Fetch the authenticated user's ID
                int userID = utilisateursManager.trouverIDUtilisateur(request);

                // Redirect the authenticated user to a protected resource or dashboard
                response.sendRedirect("listeArticles.jsp");
                System.out.println("connected");
            } else {
                // If authentication fails, redirect back to the login page with an error message
                response.sendRedirect("Connexion.jsp");
                System.out.println("connection failed");
            }
        } catch (BLLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication error.");
        }
    }

}
