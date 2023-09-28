<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" type="text/css" href="css/maeva.css"> 
</head>
<body>
<h2 class="logo">RECYCLO</h2>
    <div class="container">
        <h1>Se connecter</h1>
        <form action="Connexion" method="post">
            <label for="pseudo">Pseudo : </label>
            <input type="text" id="pseudo" name="pseudo" required><br>

            <label for="motDePasse">Mot De Passe : </label>
            <input type="password" id="motDePasse" name="motDePasse" required><br>

            <button type="submit" class="btnAccept">Connecter</button>
        </form>

        <p><a href="ProfileCreationServlet">Créer un compte</a></p>
        <p><a href="listeArticles">Retourner a l'accueil</a></p>
    </div>
</body>
</html>
