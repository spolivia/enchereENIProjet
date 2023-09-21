<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="css/signin.css"> <!-- You can link to your custom CSS for styling -->
</head>
<body>
    <div class="container">
        <h2>Sign In</h2>
        <form action="Connexion" method="post"> <!-- Replace "signinServlet" with the actual URL where your sign-in logic is handled -->
            <label for="pseudo">Pseudo : </label>
            <input type="text" id="pseudo" name="pseudo" required><br>

            <label for="motDePasse">Password:</label>
            <input type="password" id="motDePasse" name="motDePasse" required><br>

            <button type="submit">Connecter</button>
        </form>

        <!-- Optionally, provide a link to the registration page -->
        <p><a href="compteCreation.jsp">Créer un compte</a></p>
    </div>
</body>
</html>
