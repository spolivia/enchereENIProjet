<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.bll.BLLException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DALException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>

<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Article</title>
    <script>
        function toggleAddressFields() {
            var checkbox = document.getElementById("useOwnAddressCheckbox");
            var addressFields = document.getElementById("addressFields");

            if (checkbox.checked) {
                addressFields.style.display = "none";
            } else {
                addressFields.style.display = "block";
            }
        }
    </script>
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
        <label for="noUtilisateurs">User Identity Number:</label>
        <input type="number" id="noUtilisateurs" name="noUtilisateurs" value="<%= utilisateur.getNoUtilisateur() %>" readonly required><br>

        <label for="nomArticle">Nom d'article :</label>
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
            <option value="3">Vêtement</option>
            <option value="4">Sports</option>
            <option value="5">Loisirs</option>
        </select><br>

        <label for="useOwnAddressCheckbox">Use My Address for Retraits</label>
        <input type="checkbox" id="useOwnAddressCheckbox" name="useOwnAddressCheckbox" checked onclick="toggleAddressFields()"><br>

        <div id="addressFields" style="display: none;">
            <label for="rue">Rue :</label>
            <input type="text" id="rue" name="rue" value="<%= utilisateur.getRue() %>"><br>

            <label for="codePostale">Code Postale :</label>
            <input type="text" id="codePostale" name="codePostale" value="<%= utilisateur.getCodePostal() %> "><br>

            <label for="ville">Ville :</label>
            <input type="text" id="ville" name="ville" value="<%= utilisateur.getVille() %>"><br>
        </div>

        <input type="submit" value="Create Article">
    </form>

    <br>

    <a href="listeArticles">Cancel</a>
</body>
</html>
