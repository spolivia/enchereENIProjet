package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.ArticlesDAO;
import fr.eni.ecole.application.modele.dal.CategoriesDAO;
import fr.eni.ecole.application.modele.dal.DALException;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArticlesDAO articlesDAO = DAOFactory.getArticlesDAO();
    	CategoriesDAO categoriesDAO = DAOFactory.getCategoriesDAO();

        try {
            List<Articles> listeArticles = articlesDAO.selectAll();
            List<Categories> categories = categoriesDAO.selectAll();

            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);

        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ArticlesDAO articlesDAO = DAOFactory.getArticlesDAO();
    	CategoriesDAO categoriesDAO = DAOFactory.getCategoriesDAO();
    	
        try {
            List<Categories> categories = categoriesDAO.selectAll();

            String searchInput = request.getParameter("searchInput");
            System.out.println("Recherche : " + searchInput);
            String selectedCategory = request.getParameter("selectedCategory");
            System.out.println("Catégorie sélectionnée : " + selectedCategory);

            List<Articles> listeArticles = ((ArticlesDAO) articlesDAO).logicFiltrerTirageArticles(searchInput, selectedCategory);

            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
            System.out.println("Nombre d'articles récupérés : " + listeArticles.size());

        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }
}
