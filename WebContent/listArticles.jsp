<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des enchères</title>
</head>
<body>
<h1>Liste des enchères</h1>

	<table>
		<tr>
			<td>
				<h2>Filtres : </h2>
					<form action="listArticles" method="post">
						<input type="text" id="searchInput" name="searchInput" placeholder="Search articles...">
					    <input type="submit" value="Recherche">
					</form>
				<br>
				
				<label for="categorie">Categorie</label>
				<select id="categorie" name="categorie">
				    <option value="option1">Option 1</option>
				    <option value="option2">Option 2</option>
				    <option value="option3">Option 3</option>
				    <option value="option4">Option 4</option>
				    <option value="option5">Option 5</option>
				</select>
			</td>
			
		</tr>
	</table>
<br><br><br>

	<c:forEach var="article" items="${listArticles}">
	    <table border="1">
	        <tr>
	            <td>Picture</td>
	            <td>
	                <h3>${article.nomArticle}</h3>
	                <p>Description : ${article.description}</p>
	                <p>Prix Initial : ${article.prixInitial}</p>
	                <p>Debut Enchere : ${article.dateDebutEncheres}</p>
	                <p>Fin Enchere : ${article.dateFinEncheres}</p>
	                <p>Vendeur : User</p>
	            </td>
	        </tr>
	    </table>
	    <br>
	</c:forEach>
    
</body>
</html>