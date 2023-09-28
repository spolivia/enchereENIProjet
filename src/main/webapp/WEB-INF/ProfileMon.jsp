<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %>
<%@ page import="fr.eni.ecole.application.modele.bll.ArticlesManager" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="now" value="<%= new java.util.Date() %>" />


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/maeva.css">
    <title>User Profile</title>
</head>
<body>
<h2 class="logo">RECYCLO</h2>
    <h1>Votre Profile</h1>
    

<%
    int userId = (int) session.getAttribute("no_utilisateur");
    Utilisateurs utilisateur = null; // Declare the utilisateur variable

    // Create an ArticlesManager and fetch the user's articles
    ArticlesManager articlesManager = new ArticlesManager(DAOFactory.getArticlesDAO());
    List<Articles> listeArticles = articlesManager.selectByUserID(userId);
    request.setAttribute("listeArticles", listeArticles);
    
    // Fetch the user's information
    UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
    utilisateur = utilisateursManager.getUtilisateursById(userId);
%>


    <table>
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <tr>
            <td>Pseudo</td>
            <td><%= utilisateur.getPseudo() %></td>
        </tr>
        <tr>
            <td>Nom</td>
            <td><%= utilisateur.getNom() %></td>
        </tr>
        <tr>
            <td>Prénom</td>
            <td><%= utilisateur.getPrenom() %></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><%= utilisateur.getEmail() %></td>
        </tr>
        <tr>
            <td>Téléphone</td>
            <td><%= utilisateur.getTelephone() %></td>
        </tr>
        <tr>
            <td>Rue</td>
            <td><%= utilisateur.getRue() %></td>
        </tr>
        <tr>
            <td>Code Postal</td>
            <td><%= utilisateur.getCodePostal() %></td>
        </tr>
        <tr>
            <td>Ville</td>
            <td><%= utilisateur.getVille() %></td>
        </tr>
    </table>
    
    <br>
    
    <form action="listeArticles">
        <input type="submit" value="Retour à l'accueil">
    </form>
	<form action="ProfileUpdate.jsp" method="post">
	    <input type="submit" value="Changer vos détails">
	</form>
	
	
	
	<c:choose>
	    <c:when test="${empty listeArticles}">
	        <p align="center">Aucun article trouvé.</p>
	    </c:when>
	    <c:otherwise>
	        <table border="0" cellspacing="20" align="center">
	        <h2><center>Mes enchères</center></h2>
	            <c:forEach var="article" items="${listeArticles}" varStatus="loop">
	                <c:if test="${loop.index % 2 == 0}">
	                    <tr>
	                </c:if>
	                <td>
						<table border="1"
	                        <c:choose>
	                        	<c:when test="${sessionScope.no_utilisateur > 0}">
	                            	onclick="window.location.href='ArticleDetailsServlet?articleId=${article.noArticle}'"
	                            </c:when>
	                            <c:otherwise>
	                            </c:otherwise>
                           	</c:choose>
                    	>
						   	<tr>
						   	<td>Photo</td>
						        <td>
						            <h3>${article.nomArticle}</h3>
										<c:choose>
										    <c:when test="${article.enchere == null || article.enchere.montant_enchere == 0}">
										        Prix : ${article.prixInitial}
										    </c:when>
										    <c:otherwise>
										        <p>Prix : ${article.enchere.montant_enchere}</p>
										    </c:otherwise>
										</c:choose>

						            <p>Fin Enchere : ${article.dateFinEncheres}</p>
						            <p><b>Vendeur : <%= utilisateur.getPseudo() %></b></p>
									<c:choose>
									    <c:when test="${article.dateDebutEncheres.after(now)}">
									        <p><a href="ArticleUpdateServlet?articleId=${article.noArticle}">Edit</a></p>
									    </c:when>
									</c:choose>

						        </td>
						    </tr>
						</table>
	                </td>
	                <c:if test="${loop.index % 2 == 1 || loop.last}">
	                    </tr>
	                </c:if>
	          </c:forEach>
	        </table>
	    </c:otherwise>
	</c:choose>

</body>
</html>
