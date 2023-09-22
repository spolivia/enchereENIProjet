package fr.eni.ecole.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.ecole.application.controllers.bll.BLLException;
import fr.eni.ecole.application.controllers.bll.UtilisateursManager;
import fr.eni.ecole.application.modele.dal.DAOFactory;

@WebServlet("/ProfileDeleteServlet")
public class ProfileDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProfileDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ProfileDelete.jsp").forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		int userId = (int) request.getSession().getAttribute("no_utilisateur");

        UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
         	try {
				utilisateursManager.deleteUtilisateur(userId);
			} catch (BLLException e) {
				e.printStackTrace();
			}
        	System.out.println("USER DELETED");
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/listeArticles");
    }
}
