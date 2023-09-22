<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create User</title>
</head>
<body>
    <h1>Create a New User Account</h1>
    
    <form action="listeArticles" method="post">
        <label for="pseudo">Pseudo:</label>
        <input type="text" id="pseudo" name="pseudo" required><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

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
        
        <input type="submit" value="Create Account">
    </form>
</body>
</html>