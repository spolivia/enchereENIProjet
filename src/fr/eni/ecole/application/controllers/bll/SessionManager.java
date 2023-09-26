package fr.eni.ecole.application.controllers.bll;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni.ecole.application.modele.bo.Articles;

import java.util.List;

public class SessionManager {
	
    public static List<Articles> getArticlesFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (List<Articles>) session.getAttribute("articles");
    }

    public static void setArticlesInSession(HttpServletRequest request, List<Articles> articles) {
        HttpSession session = request.getSession();
        session.setAttribute("articles", articles);
    }
}
