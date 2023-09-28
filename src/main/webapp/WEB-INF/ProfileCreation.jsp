<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.bll.BLLException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DALException" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/maeva.css">
    <title>Create Account</title>
</head>
<body>
	<h2 class="logo">RECYCLO</h2>
    <h1>Creer votre compte</h1>

	<div class="profile">
    <form action="ProfileCreationServlet" method="post" >
        <label class="creatio" for="pseudo">Pseudo:</label>
        <input type="text" class="creation" id="pseudo" name="pseudo" required><br>

        <label class="creatio" for="nom">Nom:</label>
        <input type="text" class="creation" id="nom" name="nom" required><br>

        <label class="creatio" for="prenom">Prenom:</label>
        <input type="text" class="creation" id="prenom" name="prenom" required><br>

        <label class="creatio" for="email">Email:</label>
        <input type="email" class="creation" id="email" name="email" required><br>

        <label class="creatio" for="telephone">Telephone:</label>
        <input type="text" class="creation" id="telephone" name="telephone" required><br>

        <label class="creatio" for="rue">Rue:</label>
        <input type="text" class="creation" id="rue" name="rue" required><br>

        <label class="creatio" for="codePostal">Code Postal:</label>
        <input type="text" class="creation" id="codePostal" name="codePostal" required><br>

        <label class="creatio" for="ville">Ville:</label>
        <input type="text" class="creation" id="ville" name="ville" required><br>

        <label class="creatio" for="motDePasse">Mot de passe:</label>
        <input type="password" class="creation" id="motDePasse" name="motDePasse" required><br>
	 </div>
	 </form>
        <button type="submit" class="btnAccept">Creer compte</button>
   
    <br>

    <a href="Connexion">Deja un compte? Connecter ici!</a>
</body>
</html>
