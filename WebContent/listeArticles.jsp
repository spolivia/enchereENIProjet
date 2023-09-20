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

</head>
<body>
	<div style="float : right;"><a href="">S'inscrire - Se connecter</a></div>

	<h1 align="center">Liste des enchères</h1>

<table align="center">
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
                            <input type="text" id="searchInput" name="searchInput" placeholder="Le nom ou la description de l'article contient">
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


<br>

<c:choose>
    <c:when test="${empty listeArticles}">
        <p align="center">Aucun article trouvé.</p>
    </c:when>
    <c:otherwise>
        <table border="0" cellspacing="20" align="center">
            <c:forEach var="article" items="${listeArticles}" varStatus="loop">
                <c:if test="${loop.index % 2 == 0}">
                    <tr> <!-- Start a new row for every even-indexed item -->
                </c:if>
                <td>
                    <table border="1">
                        <tr>
                            <td>Photo</td>
                            <td>
                                <h3>${article.nomArticle}</h3>
                                <p>Prix : BESOIN MONTANT_ENCHERE</p>
                                <p>Fin Enchere : ${article.dateFinEncheres}</p>
                                <p>Vendeur : BESOIN PSEUDO(UTILISATEUR)</p>
                            </td>
                        </tr>
                    </table>
                </td>
                <c:if test="${loop.index % 2 == 1 || loop.last}">
                    </tr> <!-- Close the row for every odd-indexed item or the last item -->
                </c:if>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
    
</body>
</html>
