package fr.eni.ecole.application.controllers;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.jdbc.ArticlesDAOJdbcImpl;
import fr.eni.ecole.application.modele.dal.jdbc.CategoriesDAOJdbcImpl;

import java.io.IOException;
import java.util.ArrayList;
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
        ArticlesDAOJdbcImpl articlesDAO = new ArticlesDAOJdbcImpl();
        CategoriesDAOJdbcImpl categoriesDAO = new CategoriesDAOJdbcImpl();

        try {
            // Récupérer les articles et les catégories
            List<Articles> listeArticles = articlesDAO.selectAll();
            List<Categories> categories = categoriesDAO.selectAll();

            // Définir les articles et les catégories en tant qu'attributs dans la requête
            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);

            // Transmettre la requête à la JSP
            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
            System.out.println("Nombre d'articles récupérés : " + listeArticles.size());
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArticlesDAOJdbcImpl articlesDAO = new ArticlesDAOJdbcImpl();
        CategoriesDAOJdbcImpl categoriesDAO = new CategoriesDAOJdbcImpl();

        try {
            // Récupérer les catégories
            List<Categories> categories = categoriesDAO.selectAll();

            // Vérifier si le paramètre searchInput est présent dans la requête
            String searchInput = request.getParameter("searchInput");
            System.out.println("Recherche : '" + searchInput + "'");

            // Vérifier si le paramètre selectedCategory est présent dans la requête
            String selectedCategory = request.getParameter("selectedCategory");
            	System.out.println("Catégorie sélectionnée : " + selectedCategory);
            
            List<Articles> listeArticles;

            if ((selectedCategory != null && !selectedCategory.isEmpty()) && (searchInput != null && !searchInput.isEmpty())) {
                // Si à la fois searchInput et selectedCategory sont fournis, recherchez les articles correspondant aux deux critères
                listeArticles = articlesDAO.rechercherArticles(searchInput, selectedCategory);
            } else if (selectedCategory != null && !selectedCategory.isEmpty()) {
                // Si seule selectedCategory est fournie, filtrez les articles par catégorie
                listeArticles = articlesDAO.filtrerArticlesParCategorie(Integer.parseInt(selectedCategory));
            } else if (searchInput != null && !searchInput.isEmpty()) {
                // Si seule searchInput est fournie, recherchez les articles correspondant à la requête
                listeArticles = articlesDAO.rechercherArticles(searchInput, null); // Passez null pour categoryFilter
            } else {
                // Si ni searchInput ni selectedCategory ne sont fournis, récupérez tous les articles
                listeArticles = articlesDAO.selectAll();
            }

            // Filtrer la liste pour inclure uniquement les articles correspondant aux deux critères
            listeArticles = filtrerArticlesParLesDeuxCriteres(listeArticles, searchInput, selectedCategory);

            // Définir les articles et les catégories en tant qu'attributs dans la requête
            request.setAttribute("listeArticles", listeArticles);
            request.setAttribute("categories", categories);

            // Transmettre la requête à la JSP
            request.getRequestDispatcher("/listeArticles.jsp").forward(request, response);
            System.out.println("Nombre d'articles récupérés : " + listeArticles.size());
        } catch (DALException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur s'est produite lors de la récupération des articles.");
        }
    }

    private List<Articles> filtrerArticlesParLesDeuxCriteres(List<Articles> articles, String rechercheInput, String selectionCategory) {
        List<Articles> articlesFiltres = new ArrayList<>();

        if (articles != null) {
            for (Articles article : articles) {
                Categories categorie = article.getCategorie();
                
                boolean correspondACategorie = categorie == null || selectionCategory == null || selectionCategory.isEmpty() ||
                        String.valueOf(categorie.getNoCategorie()).equals(selectionCategory);
                
                boolean correspondARecherche = rechercheInput == null || rechercheInput.isEmpty() ||
                        (article.getNomArticle() != null && article.getNomArticle().contains(rechercheInput)) ||
                        (article.getDescription() != null && article.getDescription().contains(rechercheInput));

                if (correspondACategorie && correspondARecherche) {
                    articlesFiltres.add(article);
                }
            }
        }

        return articlesFiltres;
    }
}
