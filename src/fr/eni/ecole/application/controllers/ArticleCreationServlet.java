package fr.eni.ecole.application.controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.controllers.bll.BLLException;
import fr.eni.ecole.application.controllers.bll.ArticlesManager;
import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.dal.DAOFactory;

@WebServlet("/ArticleCreationServlet")
public class ArticleCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP for account creation
        request.getRequestDispatcher("/ArticleCreation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String nomArticle = request.getParameter("nomArticle");
        String description = request.getParameter("description");
        String dateDebutEncheresStr = request.getParameter("dateDebutEncheresStr"); // Updated field name
        String dateFinEncheresStr = request.getParameter("dateFinEncheresStr"); // Updated field name
        String prixInitialStr = request.getParameter("prixInitial");

        // Validate and parse form input into appropriate data types
        Date dateDebutEncheres = Date.valueOf(dateDebutEncheresStr);
        Date dateFinEncheres = Date.valueOf(dateFinEncheresStr);
        int prixInitial = Integer.parseInt(prixInitialStr);
        int prixVente = 0;

        // Retrieve selected category
        String categorieStr = request.getParameter("categorie");
        int categorie = Integer.parseInt(categorieStr);

        // Create an Articles object
        int userId = (int) request.getSession().getAttribute("no_utilisateur");

        Articles article = new Articles();
        article.setNomArticle(nomArticle);
        article.setDescription(description);
        article.setDateDebutEncheres(dateDebutEncheres);
        article.setDateFinEncheres(dateFinEncheres);
        article.setPrixInitial(prixInitial);
        article.setPrixVente(prixVente);
        article.setNoUtilisateur(userId);
        article.setNoCategorie(categorie);

        ArticlesManager articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
        try {
            articlesManager.addArticle(article);
            request.setAttribute("successMessage", "Article Created Successfully!");
            response.sendRedirect(request.getContextPath() + "/listeArticles");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Error parsing numeric values: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Error parsing date values: " + e.getMessage());
        } catch (BLLException e) {
            request.setAttribute("errorMessage", "Error creating article: " + e.getMessage());
        }
    }

}
