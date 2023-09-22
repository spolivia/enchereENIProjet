<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" type="text/css" href="css/signin.css"> 
</head>
<body>
    <div class="container">
        <h2>Sign In</h2>
        <form action="Connexion" method="post">
            <label for="pseudo">Pseudo : </label>
            <input type="text" id="pseudo" name="pseudo" required><br>

            <label for="motDePasse">Mot De Passe : </label>
            <input type="password" id="motDePasse" name="motDePasse" required><br>

            <button type="submit">Connecter</button>
        </form>

        <p><a href="ProfileCreation.jsp">Créer un compte</a></p>
    </div>
</body>
</html>
