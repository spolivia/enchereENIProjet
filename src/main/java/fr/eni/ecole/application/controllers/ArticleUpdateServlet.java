package fr.eni.ecole.application.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.modele.bll.ArticlesManager;
import fr.eni.ecole.application.modele.bll.BLLException;
import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Retraits;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.DAOFactory;
import fr.eni.ecole.application.modele.dal.RetraitsDAO;

@WebServlet("/ArticleUpdateServlet")
public class ArticleUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the article ID from the request parameter
		String articleIdStr = request.getParameter("articleId");
		if (articleIdStr != null) {
			int articleId = Integer.parseInt(articleIdStr);

			// Fetch the article and retrait information based on the article ID
			ArticlesManager articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
			Articles article = null;
			try {
				article = articlesManager.getArticleById(articleId);
			} catch (BLLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RetraitsDAO retraitsDAO = DAOFactory.getRetraitsDAO();
			Retraits retrait = null;
			try {
				retrait = retraitsDAO.selectById(articleId);
				System.out.println("Retrait info retrieved");
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Set the attributes in the request scope
			request.setAttribute("article", article);
			request.setAttribute("retrait", retrait);
		}

		// Forward the request to the JSP for rendering
		request.getRequestDispatcher("/WEB-INF/ArticleUpdate.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		String dateDebutEncheresStr = request.getParameter("dateDebutEncheresStr");
		String dateFinEncheresStr = request.getParameter("dateFinEncheresStr");
		String prixInitialStr = request.getParameter("prixInitial");
		String categorieStr = request.getParameter("categorie");

		String retraitRue = request.getParameter("retraitRue");
		String retraitCodePostalStr = request.getParameter("retraitCodePostale");
		int codePostal = Integer.parseInt(retraitCodePostalStr);
		String retraitVille = request.getParameter("retraitVille");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDebutEncheres = null;
		Date dateFinEncheres = null;

		try {
			dateDebutEncheres = dateFormat.parse(dateDebutEncheresStr);
			dateFinEncheres = dateFormat.parse(dateFinEncheresStr);
		} catch (ParseException e) {
			// Handle date parsing error
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error parsing date.");
			// Redirect back to the form or display an error message as needed
			// You may want to add additional error handling logic here
			response.sendRedirect(request.getContextPath() + "/your-form-page.jsp");
			return; // Exit the method to avoid further processing
		}

		int prixInitial = Integer.parseInt(prixInitialStr);
		int categorie = Integer.parseInt(categorieStr);

		int userId = (int) request.getSession().getAttribute("no_utilisateur");

		ArticlesManager articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
		RetraitsDAO retraitsDAO = DAOFactory.getRetraitsDAO();

		// Update the article
		Articles article = new Articles();
		article.setNoArticle(articleId);
		article.setNomArticle(nomArticle);
		article.setDescription(description);
		article.setDateDebutEncheres(dateDebutEncheres);
		article.setDateFinEncheres(dateFinEncheres);
		article.setPrixInitial(prixInitial);
		article.setPrixVente(prixInitial);
		article.setNoCategorie(categorie);
		article.setNoUtilisateur(userId);

		// Update the retrait
		Retraits retrait = new Retraits();
		retrait.setNoArticle(articleId); // Set the article ID
		retrait.setRue(retraitRue);
		retrait.setCodePostale(codePostal);
		retrait.setVille(retraitVille);

		try {
			// Update the article and retrait
			articlesManager.updateArticle(article);
			retraitsDAO.update(retrait);
			request.setAttribute("successMessage", "Article Updated Successfully!");
		} catch (BLLException | DALException e) {
			// Handle exceptions
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error updating article.");
		}

		// Redirect to the list of articles
		response.sendRedirect(request.getContextPath() + "/ProfileMon.jsp");
	}

}
