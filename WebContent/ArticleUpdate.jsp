<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Retraits" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Article</title>
</head>
<body>
    <h1>Article Update</h1>

        <form action="ArticleUpdateServlet" method="post" onsubmit="displaySuccessMessage()">
            <input type="hidden" name="articleId" value="${requestScope.article.noArticle}">
            
            <label for="nomArticle">Nom d'article : </label>
            <input type="text" id="nomArticle" name="nomArticle" value="${requestScope.article.nomArticle}" required><br>

            <label for="description">Description :</label>
            <input type="text" id="description" name="description" value="${requestScope.article.description}" required><br>

            <label for="dateDebutEncheresStr">Date de début d'enchères :</label>
            <input type="text" id="dateDebutEncheresStr" name="dateDebutEncheresStr" value="${requestScope.article.dateDebutEncheres}" required><br>

            <label for="dateFinEncheresStr">Date de fin d'enchères :</label>
            <input type="text" id="dateFinEncheresStr" name="dateFinEncheresStr" value="${requestScope.article.dateFinEncheres}" required><br>

            <label for="prixInitial">Prix initial :</label>
            <input type="number" id="prixInitial" name="prixInitial" value="${requestScope.article.prixInitial}" required><br>

            <label for="categorie">Catégorie:</label>
            <select id="categorie" name="categorie">
                <option value="1" ${requestScope.article.noCategorie == 1 ? 'selected' : ''}>Informatique</option>
                <option value="2" ${requestScope.article.noCategorie == 2 ? 'selected' : ''}>Ameublement</option>
                <option value="3" ${requestScope.article.noCategorie == 3 ? 'selected' : ''}>Vetement</option>
                <option value="4" ${requestScope.article.noCategorie == 4 ? 'selected' : ''}>Sports</option>
                <option value="5" ${requestScope.article.noCategorie == 5 ? 'selected' : ''}>Loisirs</option>
            </select><br>
            
            <label for="retraitRue">Rue de retrait :</label>
            <input type="text" id="retraitRue" name="retraitRue" value="${requestScope.retrait.rue}" required><br>
            
            <label for="retraitCodePostale">Code Postal de retrait :</label>
            <input type="text" id="retraitCodePostale" name="retraitCodePostale" value="${requestScope.retrait.codePostale}" required><br>
            
            <label for="retraitVille">Ville de retrait :</label>
            <input type="text" id="retraitVille" name="retraitVille" value="${requestScope.retrait.ville}" required><br>

            <input type="submit" value="Update Article">
        </form>

    <script>
        function displaySuccessMessage() {
            alert("Success Updating Article");
        }
    </script>
    <br>

    <a href="ProfileMon.jsp">Cancel</a>
</body>
</html>
