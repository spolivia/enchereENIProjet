<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.ecole.application.controllers.bll.SessionManager" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %> 
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
    <c:choose>
        <c:when test="${sessionScope.no_utilisateur > 0}">
            <a href="#">Enchères</a>
            <a href="#">Vendre un article</a>
            <a href="ProfileMon.jsp">Mon Profil</a>
            ${sessionScope.pseudo}
            <a href="Deconnexion">Déconnexion</a>
            
        </c:when>
        <c:otherwise>
            <a href="Connexion.jsp">S'inscrire - Se connecter</a>
        </c:otherwise>
    </c:choose>
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
            <form action="listeArticles" method="post">
                <table>
                    <tr>
                        <td>
							<input type="text" id="requeteRecherche" name="requeteRecherche" placeholder="Le nom ou la description de l'article contient">
                        </td>
                        <td>
                            <input type="submit" value="Rechercher">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Catégorie :
                        
							<select id="filtreCategorie" name="filtreCategorie">
							    <option value="0">Toutes</option>
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
         <c:when test="${sessionScope.no_utilisateur > 0}">
         	<center><p style="color:red;"><b>Functionaliter des Radio et Checkboxes pas actif</b></p></center>
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
		        </c:when>
    </c:choose>
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
                               <p><b>Vendeur : ${article.utilisateur.pseudo}</p>
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
