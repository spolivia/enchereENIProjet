package fr.eni.ecole.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.application.modele.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.UtilisateursDAO;
import fr.eni.ecole.application.modele.dal.jdbc.UtilisateursDAOJdbcImpl;

@WebServlet("/Connexion")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtilisateursManager utilisateursManager;

	public void init() throws ServletException {
		UtilisateursDAO utilisateursDAO = new UtilisateursDAOJdbcImpl();
		utilisateursManager = new UtilisateursManager(utilisateursDAO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/Connexion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String motDePasse = request.getParameter("motDePasse");

		int no_utilisateur;
		try {
			no_utilisateur = utilisateursManager.authenticateUser(pseudo, motDePasse);
			if (no_utilisateur != -1) {
				HttpSession session = request.getSession();
				session.setAttribute("no_utilisateur", no_utilisateur);
				session.setAttribute("pseudo", pseudo);

				response.sendRedirect("listeArticles");
				System.out.println("Connected");

			} else {
				response.sendRedirect("/WEB-INF/Connexion.jsp");
				System.out.println("Failed Connection");
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
}
