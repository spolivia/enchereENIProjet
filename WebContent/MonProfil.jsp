<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.controllers.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
    <h1>User Profile</h1>
    
    <%
        int userId = (int) session.getAttribute("no_utilisateur");

        // Create a UtilisateursManager and fetch the user's information
        UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
        Utilisateurs utilisateur = utilisateursManager.getUtilisateursById(userId);
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

    <form action="listeArticles">
        <input type="submit" value="Retour à l'accueil">
    </form>
</body>
</html>
