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
		<div style="float : right;">
			<a href="">Enchères</a>
			<a href="">Vendre un article</a>
			<a href="">Mon Profil</a>
			<a href="">Déconnexion</a>
		</div>
	
		<h1 align="center">Liste des enchères</h1>
	
		<table align="center">
		    <tr>
		        <td>
		            <h2>Filtres : </h2>
		        </td>
		    </tr>
		    <tr>
		        <td>
		            <form action="listeArticlesConnecter" method="post">
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
		<table align="center">
			<tr>
				<td>
				    <form id="radioAchats">
				        <label>
				            <input type="radio" name="options" id="achatsRadio" value="achats" checked> Achats
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox1" value="enchères ouvertes" checked> enchères ouvertes
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox2" value="mes enchères en cours"> mes enchères en cours
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox3" value="mes enchères remportées"> mes enchères remportées
				        </label><br>
				    </form>
				</td>
				<td>
				    <form id="radioVentes">
				        <label>
				            <input type="radio" name="options" id="ventesRadio" value="ventes"> Ventes
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox4" value="mes ventes en cours" disabled> mes ventes en cours
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox5" value="ventes non débutées" disabled> ventes non débutées
				        </label><br>
				        <label>
				            <input type="checkbox" id="checkbox6" value="ventes terminées" disabled> ventes terminées
				        </label><br>
				    </form>
				</td>
			</tr>
		</table>
		
		<script>
		    const achatsRadio = document.getElementById('achatsRadio');
		    const ventesRadio = document.getElementById('ventesRadio');
		    const achatsCheckboxes = document.querySelectorAll('#radioAchats input[type="checkbox"]');
		    const ventesCheckboxes = document.querySelectorAll('#radioVentes input[type="checkbox"]');
		
		    function disableCheckboxes(checkboxes) {
		        checkboxes.forEach((checkbox) => {
		            checkbox.disabled = true;
		        });
		    }
		
		    function enableCheckboxes(checkboxes) {
		        checkboxes.forEach((checkbox) => {
		            checkbox.disabled = false;
		        });
		    }
		
		    function resetOtherRadio(selectedRadio, otherRadio) {
		        if (selectedRadio.checked) {
		            otherRadio.checked = false;
		        }
		    }
		
		    function resetCheckboxes(checkboxes) {
		        checkboxes.forEach((checkbox) => {
		            checkbox.checked = false;
		        });
		    }
		
		    achatsRadio.addEventListener('change', () => {
		        enableCheckboxes(achatsCheckboxes);
		        disableCheckboxes(ventesCheckboxes);
		        resetOtherRadio(achatsRadio, ventesRadio);
		        resetCheckboxes(ventesCheckboxes);
		    });
		
		    ventesRadio.addEventListener('change', () => {
		        enableCheckboxes(ventesCheckboxes);
		        disableCheckboxes(achatsCheckboxes);
		        resetOtherRadio(ventesRadio, achatsRadio);
		        resetCheckboxes(achatsCheckboxes);
		    });
		</script>
    
<br>

		<c:choose>
		    <c:when test="${empty listeArticles}">
		        <p align="center">Aucun article trouvé.</p>
		    </c:when>
		    <c:otherwise>
		        <table border="0" cellspacing="20" align="center">
		            <c:forEach var="article" items="${listeArticles}" varStatus="loop">
		                <c:if test="${loop.index % 2 == 0}">
		                    <tr> 
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
		                    </tr>
		                </c:if>
		            </c:forEach>
		        </table>
		    </c:otherwise>
		</c:choose>
	    
	</body>
</html>
