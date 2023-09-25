<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
    <h1>User Profile</h1>
    
    <c:choose>
        <c:when test="${user != null}">
            <p><strong>User ID:</strong> ${user.noUtilisateur}</p>
            <p><strong>Username:</strong> ${user.pseudo}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>First Name:</strong> ${user.prenom}</p>
            <p><strong>Last Name:</strong> ${user.nom}</p>
            <!-- Add more user profile fields as needed -->
        </c:when>
        <c:otherwise>
            <p>User not found or invalid user ID.</p>
        </c:otherwise>
    </c:choose>
    
    <!-- Add links, buttons, or other content as needed -->
</body>
</html>
