<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Retraits" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Categories" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails de l'Article</title>
</head>
<body>
    <h1>Détails de l'Article</h1>

    <c:if test="${not empty requestScope.article}">
		<h2>Propriétaire: ${requestScope.utilisateur.pseudo}</h2>
        <h3>${requestScope.article.nomArticle}</h3>
        <p>Description: ${requestScope.article.description}</p>
        <p>Date de début: ${requestScope.article.dateDebutEncheres}</p>
        <p>Date de fin: ${requestScope.article.dateFinEncheres}</p>
        <p>Prix initial: ${requestScope.article.prixVente}</p>
        <p>Montant Courant: BESOIN MONTANT_ENCHERE</p>
       	<p>Catégorie:
		    <c:choose>
		        <c:when test="${requestScope.article.noCategorie == 1}">Informatique</c:when>
		        <c:when test="${requestScope.article.noCategorie == 2}">Ameublement</c:when>
		        <c:when test="${requestScope.article.noCategorie == 3}">Vêtement</c:when>
		        <c:when test="${requestScope.article.noCategorie == 4}">Sports</c:when>
		        <c:when test="${requestScope.article.noCategorie == 5}">Loisirs</c:when>
		        <c:otherwise>Unknown</c:otherwise>
		    </c:choose>
		</p>


        
        <h2>Adresse de Retrait</h2>
        <p>Rue: ${requestScope.retrait.rue}</p>
        <p>Code Postal: ${requestScope.retrait.codePostale}</p>
        <p>Ville: ${requestScope.retrait.ville}</p>
    </c:if>

    <br>

    <a href="listeArticles">Retour à la Liste d'Articles</a>
</body>
</html>
