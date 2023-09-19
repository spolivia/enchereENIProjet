package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.jdbc.ArticlesDAOJdbcImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listArticles")
public class ListArticlesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticlesDAOJdbcImpl articlesDAO = new ArticlesDAOJdbcImpl();

        try {
            List<Articles> listArticles = articlesDAO.selectAll();
            request.setAttribute("listArticles", listArticles);
            request.getRequestDispatcher("/listArticles.jsp").forward(request, response);
            System.out.println("Number of articles retrieved: " + listArticles.size());
                        
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreure retrevant l'article");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchInput");
        ArticlesDAOJdbcImpl articlesDAO = new ArticlesDAOJdbcImpl();

        try {
            List<Articles> filteredArticles = articlesDAO.searchArticles(searchQuery);
            request.setAttribute("listArticles", filteredArticles);
            request.getRequestDispatcher("/listArticles.jsp").forward(request, response);
       
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while searching articles.");
        }
    }

}
