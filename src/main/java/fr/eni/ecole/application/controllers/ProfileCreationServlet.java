package fr.eni.ecole.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.modele.bll.BLLException;
import fr.eni.ecole.application.modele.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.DAOFactory;

@WebServlet("/ProfileCreationServlet")
public class ProfileCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to the JSP for account creation
		request.getRequestDispatcher("/WEB-INF/ProfileCreation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form parameters
		String pseudo = request.getParameter("pseudo");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		String motDePasse = request.getParameter("motDePasse");

		// Create a Utilisateurs object with default values for credit and
		// isAdministrateur
		Utilisateurs user = new Utilisateurs();
		user.setPseudo(pseudo);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setRue(rue);
		user.setCodePostal(codePostal);
		user.setVille(ville);
		user.setMotDePasse(motDePasse);
		user.setCredit(0); // Set credit to 0 by default
		user.setAdministrateur(false); // Set isAdministrateur to false by default

		// Create an instance of UtilisateursManager and add the new user
		UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
		try {
			// Add the new user
			utilisateursManager.addUtilisateur(user);

			// Retrieve the newly created user by pseudo
			Utilisateurs newUser = utilisateursManager.getUtilisateursByPseudo(user.getPseudo());

			// Set the user in the session
			request.getSession().setAttribute("no_utilisateur", newUser.getNoUtilisateur());
			request.getSession().setAttribute("pseudo", user.getPseudo());

			request.setAttribute("successMessage", "Account created successfully!");
		} catch (BLLException e) {
			request.setAttribute("errorMessage", "Error creating account: " + e.getMessage());
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Redirect to the other servlet
		response.sendRedirect(request.getContextPath() + "/listeArticles");

	}
}
