<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/ListeArticleFormat.css">
	ENI-Encheres
	<title>Liste des enchères</title>
	<a href="">S'inscrire - Se connecter</a>
</head>
<body>
	<h1>Liste des enchères</h1>

<table>
    <tr>
        <td>
            <h2>Filtres : </h2>
        </td>
    </tr>
    <tr>
        <td>
            <form action="listeArticles" method="post">
                <table>
                    <tr>
                        <td>
                            <input type="text" id="searchInput" name="searchInput" placeholder="Le nom de l'article contient">
                        </td>
                        <td>
                            <input type="submit" value="Rechercher">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Catégorie :
                        
                            <select id="categorie" name="selectedCategory">
                                <option value="">Toutes</option> <!-- Default option -->
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.noCategorie}">${category.libelle}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>


<br><br>

	<c:choose>
	    <c:when test="${empty listeArticles}">
	        <p>Aucun article trouvé.</p>
	    </c:when>
	    <c:otherwise>
	        <c:forEach var="article" items="${listeArticles}">
	            <table border="1">
	                <tr>
	                    <td>Picture</td>
	                    <td>
	                        <h3>${article.nomArticle}</h3>
	                        <p>Prix : BESOIN MONTANT_ENCHERE</p>
	                        <p>Fin Enchere : ${article.dateFinEncheres}</p>
	                        <p>Vendeur : BESOIN PSEUDO(UTILISATEUR)</p>
	                    </td>
	                </tr>
	            </table>
	            <br>
	        </c:forEach>
	    </c:otherwise>
	</c:choose>
    
</body>
</html>
