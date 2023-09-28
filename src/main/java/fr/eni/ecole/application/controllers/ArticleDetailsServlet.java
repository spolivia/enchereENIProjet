package fr.eni.ecole.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.modele.bll.ArticlesManager;
import fr.eni.ecole.application.modele.bll.BLLException;
import fr.eni.ecole.application.modele.bll.EncheresManager;
import fr.eni.ecole.application.modele.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Encheres;
import fr.eni.ecole.application.modele.bo.Retraits;
import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.DAOFactory;
import fr.eni.ecole.application.modele.dal.RetraitsDAO;

@WebServlet("/ArticleDetailsServlet")
public class ArticleDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArticlesManager articlesManager;
	private RetraitsDAO retraitsDAO;
	private UtilisateursManager utilisateursManager;
	private EncheresManager encheresManager;

	public void init() throws ServletException {
		super.init();
		articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
		retraitsDAO = DAOFactory.getRetraitsDAO();
		utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
		encheresManager = new EncheresManager(DAOFactory.getEncheresDAO());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String articleIdStr = request.getParameter("articleId");
			if (articleIdStr != null) {
				int articleId = Integer.parseInt(articleIdStr);

				Articles article = articlesManager.getArticleById(articleId);
				Retraits retrait = retraitsDAO.selectById(articleId);
				Utilisateurs utilisateur = utilisateursManager.getUtilisateursByArticle(articleId);
				Encheres enchere = encheresManager.highestEnchere(article.getNoArticle());

				request.setAttribute("article", article);
				request.setAttribute("retrait", retrait);
				request.setAttribute("utilisateur", utilisateur);
				request.setAttribute("enchere", enchere);

				request.getRequestDispatcher("/WEB-INF/ArticleDetails.jsp").forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/listeArticles");
			}
		} catch (BLLException | DALException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error occurred while fetching article details.");
		}
	}
}