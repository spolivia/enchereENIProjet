package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.bll.ArticlesManager;
import fr.eni.ecole.application.modele.bll.BLLException;
import fr.eni.ecole.application.modele.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.dal.DAOFactory;
import fr.eni.ecole.application.modele.bll.EncheresManager;
import fr.eni.ecole.application.modele.bo.Encheres;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProfileUserServlet")
public class ProfileUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get the userId parameter from the request
            String userIdParam = request.getParameter("userId");
            System.out.println("Visiting ID verifired as " + userIdParam);
            if (userIdParam != null && !userIdParam.isEmpty()) {
                // Parse the userId parameter to an integer
                int userId = Integer.parseInt(userIdParam);

                // Create a UtilisateursManager and retrieve the user by ID
                UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
                Utilisateurs user = utilisateursManager.getUtilisateursById(userId);

                // Set the user as an attribute to be used in the JSP
                request.setAttribute("user", user);

                // Create an ArticlesManager and fetch the user's articles filtered by userID
                ArticlesManager articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
                List<Articles> listeArticles = articlesManager.selectByUserID(userId);
               
                // Create an EncheresManager
                EncheresManager encheresManager = new EncheresManager(DAOFactory.getEncheresDAO());

                // Retrieve the highest enchere for each article in the user's list
                for (Articles article : listeArticles) {
                    Encheres enchere = encheresManager.highestEnchere(article.getNoArticle());
                    article.setEnchere(enchere);
                }                
                
                request.setAttribute("listeArticles", listeArticles);

                // Forward the request to the user profile JSP
                request.getRequestDispatcher("/WEB-INF/ProfileUser.jsp").forward(request, response);
            } else {
                // Handle the case when userIdParam is missing or invalid
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            }
        } catch (BLLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving the user's profile.");
        }
    }
}
