<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.controllers.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.controllers.bll.BLLException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DALException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Account</title>
</head>
<body>
    <h1>Create Your Account</h1>

    <form action="ProfileCreationServlet" method="post">
        <label for="pseudo">Pseudo:</label>
        <input type="text" id="pseudo" name="pseudo" required><br>

        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br>

        <label for="prenom">Prenom:</label>
        <input type="text" id="prenom" name="prenom" required><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="telephone">Telephone:</label>
        <input type="text" id="telephone" name="telephone" required><br>

        <label for="rue">Rue:</label>
        <input type="text" id="rue" name="rue" required><br>

        <label for="codePostal">Code Postal:</label>
        <input type="text" id="codePostal" name="codePostal" required><br>

        <label for="ville">Ville:</label>
        <input type="text" id="ville" name="ville" required><br>

        <label for="motDePasse">Mot de passe:</label>
        <input type="password" id="motDePasse" name="motDePasse" required><br>

        <input type="submit" value="Create Account">
    </form>

    <br>

    <a href="Connexion">Deja un compte? Connecter ici!</a>
</body>
</html>
