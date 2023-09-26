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
    <title>Update Profile</title>
</head>
<body>
    <h1>Update Your Profile</h1>
    
<%
    int userId = (int) session.getAttribute("no_utilisateur");

    UtilisateursManager utilisateursManager = new UtilisateursManager(DAOFactory.getUtilisateursDAO());
    Utilisateurs utilisateur = utilisateursManager.getUtilisateursById(userId);


%>


    <form action="ProfileUpdate.jsp" method="post">		
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
        <input type="text" id="ville" name="ville" value="<%= utilisateur.getNom() %>" required><br>
        
        <input type="submit" value="Update Profile">
        <%
    if (request.getMethod().equalsIgnoreCase("post")) {
        // Retrieve form parameters
        String newNom = request.getParameter("nom");
        String newPrenom = request.getParameter("prenom");
        String newEmail = request.getParameter("email");
        String newTelephone = request.getParameter("telephone");
        String newRue = request.getParameter("rue");
        String newCodePostal = request.getParameter("codePostal");
        String newVille = request.getParameter("ville");

        // Create a Utilisateurs object with updated values
    //    utilisateur.setNoUtilisateur(noUtilisateur);
      //  utilisateur.setPseudo(pseudo);
        utilisateur.setNom(newNom);
        utilisateur.setPrenom(newPrenom);
        utilisateur.setEmail(newEmail);
        utilisateur.setTelephone(newTelephone);
        utilisateur.setRue(newRue);
        utilisateur.setCodePostal(newCodePostal);
        utilisateur.setVille(newVille);

        try {
            // Update the user's profile
            utilisateursManager.updateUtilisateur(utilisateur);
            out.println("Profile updated successfully!");
        } catch (BLLException e) {
            out.println("Error updating profile: " + e.getMessage());
        }
    }
 
 %>
    </form>


    <br>

    <form action="listeArticles">
        <input type="submit" value="Return to Home">
    </form>
</body>
</html>
