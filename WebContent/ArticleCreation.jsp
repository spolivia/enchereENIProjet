<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.bll.BLLException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DALException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>
<%@ page import="java.text.SimpleDateFormat" %> <!-- Import SimpleDateFormat -->

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Article</title>
</head>
<body>
    <%
        int userId = (int) session.getAttribute("no_utilisateur");
        UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
        Utilisateurs utilisateur = utilisateursManager.getUtilisateursById(userId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define date format
    %>

    <h1>Create Your Article</h1>

    <form action="ArticleCreationServlet" method="post">
        <label for="noUtilisateurs">User Identity Number: </label>
        <input type="number" id="noUtilisateurs" name="noUtilisateurs" value="<%= utilisateur.getNoUtilisateur() %>" readonly required><br>

        <label for="nomArticle">Nom d'article : </label>
        <input type="text" id="nomArticle" name="nomArticle" required><br>

        <label for="description">Description :</label>
        <input type="text" id="description" name="description" required><br>

        <label for="dateDebutEncheresStr">Date de début d'enchères :</label>
        <input type="text" id="dateDebutEncheresStr" name="dateDebutEncheresStr" value="<%= dateFormat.format(new java.util.Date()) %>" required><br>

        <label for="dateFinEncheresStr">Date de fin d'enchères :</label>
        <input type="text" id="dateFinEncheresStr" name="dateFinEncheresStr" value="<%= dateFormat.format(new java.util.Date()) %>" required><br>

        <label for="prixInitial">Prix initial :</label>
        <input type="number" id="prixInitial" name="prixInitial" required><br>

        <label for="categorie">Catégorie:</label>
        <select id="categorie" name="categorie">
            <option value="1">Informatique</option>
            <option value="2">Ameublement</option>
            <option value="3">Vetement</option>
            <option value="4">Sports</option>
            <option value="5">Loisirs</option>
        </select><br>

        <input type="submit" value="Create Article">
    </form>

    <br>

    <a href="listeArticles">Cancel</a>
</body>
</html>
