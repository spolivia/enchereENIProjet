package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.controllers.bll.ArticlesManager;
import fr.eni.ecole.application.controllers.bll.BLLException;
import fr.eni.ecole.application.controllers.bll.CategoriesManager;
import fr.eni.ecole.application.modele.dal.DAOFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listeArticles")
public class ListArticlesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoriesManager categoriesManager;
    private ArticlesManager articlesManager;

    public void init() throws ServletException {
        super.init();
        categoriesManager = new CategoriesManager(DAOFactory.getCategoriesDAO());
        articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Articles> listeArticles = articlesManager.getAllArticles();

            List<Categories> categories = categoriesManager.getAllCategories();

            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
        } catch (BLLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Categories> categories = categoriesManager.getAllCategories();

            String searchInput = request.getParameter("searchInput");
            String selectedCategory = request.getParameter("selectedCategory");

            List<Articles> listeArticles = articlesManager.logicFiltrerTirageArticles(searchInput, selectedCategory);

            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
        } catch (BLLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }
}
