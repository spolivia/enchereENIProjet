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
    <title>Update Profile</title>
</head>
<body>
    <h1>Update Your Profile</h1>
    
    <%
        int userId = (int) session.getAttribute("no_utilisateur");
        UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
        Utilisateurs utilisateur = utilisateursManager.getUtilisateursById(userId);
    %>

    <form id="updateProfileForm" action="ProfileUpdateServlet" method="post" onsubmit="displaySuccessMessage()">		
        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" value="<%= utilisateur.getNom() %>" required><br>

        <label for="prenom">Prenom:</label>
        <input type="text" id="prenom" name="prenom" value="<%= utilisateur.getPrenom() %>" required><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= utilisateur.getEmail() %>" required><br>

        <label for="telephone">Telephone:</label>
        <input type="text" id="telephone" name="telephone" value="<%= utilisateur.getTelephone() %>" required><br>

        <label for="rue">Rue:</label>
        <input type="text" id="rue" name="rue" value="<%= utilisateur.getRue() %>" required><br>

        <label for="codePostal">Code Postal:</label>
        <input type="text" id="codePostal" name="codePostal" value="<%= utilisateur.getCodePostal() %>" required><br>
        
        <label for="ville">Ville:</label>
        <input type="text" id="ville" name="ville" value="<%= utilisateur.getVille() %>" required><br>
        
        <input type="submit" value="UpdateProfile">
    </form>
	<script>
	    function displaySuccessMessage() {
	        alert("Success Updating Profile");
	    }
	    
	    function displayDeleteMessage() {
	    	alert("Account Deleted");
	    }
	</script>

    <br>
	<form id="DeleteProfileButton" action="ProfileDeleteServlet" method="post" onsubmit="return confirm('Are you sure you want to delete your account?');"">
        <input type="submit" value="Delete Account">
    </form>
    <form action="listeArticles">
    
        <input type="submit" value="Return to Home">
    </form>
</body>
</html>
