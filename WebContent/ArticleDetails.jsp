<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Retraits" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Article Details</title>
</head>
<body>
    <h1>Article Details</h1>

    <c:if test="${not empty requestScope.article}">
        <h2>${requestScope.article.nomArticle}</h2>
        <p>Description: ${requestScope.article.description}</p>
        <p>Start Date: ${requestScope.article.dateDebutEncheres}</p>
        <p>End Date: ${requestScope.article.dateFinEncheres}</p>
        <p>Initial Price: ${requestScope.article.prixInitial}</p>
        <p>Montant Courant: BESOIN MONTANT_ENCHERE</p>
        <p>Category: ${requestScope.article.noCategorie}</p>
        
        <h2>Retrait Address</h2>
        <p>Rue: ${requestScope.retrait.rue}</p>
        <p>Code Postal: ${requestScope.retrait.codePostale}</p>
        <p>Ville: ${requestScope.retrait.ville}</p>
    </c:if>

    <br>

    <a href="listeArticles">Back to Articles List</a>
</body>
</html>
