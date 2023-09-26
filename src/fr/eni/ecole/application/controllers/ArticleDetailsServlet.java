package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bll.ArticlesManager;
import fr.eni.ecole.application.modele.bll.BLLException;
import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Retraits;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.DAOFactory;
import fr.eni.ecole.application.modele.dal.RetraitsDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ArticleDetailsServlet")
public class ArticleDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ArticlesManager articlesManager;
    private RetraitsDAO retraitsDAO;

    public void init() throws ServletException {
        super.init();
        articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
        retraitsDAO = DAOFactory.getRetraitsDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve the article ID from the request parameter
            String articleIdStr = request.getParameter("articleId");
            if (articleIdStr != null) {
                int articleId = Integer.parseInt(articleIdStr);

                // Fetch the article and retrait information based on the article ID
                Articles article = articlesManager.getArticleById(articleId);
                Retraits retrait = retraitsDAO.selectById(articleId);

                // Set the attributes in the request scope
                request.setAttribute("article", article);
                request.setAttribute("retrait", retrait);

                // Forward the request to the JSP for rendering
                request.getRequestDispatcher("/ArticleDetails.jsp").forward(request, response);
            } else {
                // Handle the case when the articleId parameter is not provided
                response.sendRedirect(request.getContextPath() + "/listeArticles");
            }
        } catch (BLLException | DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching article details.");
        }
    }
}
