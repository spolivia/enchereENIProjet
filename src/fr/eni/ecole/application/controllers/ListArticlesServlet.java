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

    private ArticlesManager articlesManager;
    private CategoriesManager categoriesManager;

    public void init() throws ServletException {
        super.init();
        articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
        categoriesManager = new CategoriesManager(DAOFactory.getCategoriesDAO());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Categories> categories = categoriesManager.getAllCategories();

            String requeteRecherche = "";
            int filtreCategorie = 0;
            List<Articles> listeArticles = articlesManager.logicFiltrerTirageArticles(requeteRecherche, filtreCategorie);

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

            String requeteRecherche = request.getParameter("requeteRecherche");
            int filtreCategorie = Integer.parseInt(request.getParameter("filtreCategorie"));

            request.getSession().setAttribute("requeteRecherche", requeteRecherche);
            request.getSession().setAttribute("filtreCategorie", filtreCategorie);

            List<Articles> listeArticles = articlesManager.logicFiltrerTirageArticles(requeteRecherche, filtreCategorie);

            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
            
        } catch (BLLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }



}
