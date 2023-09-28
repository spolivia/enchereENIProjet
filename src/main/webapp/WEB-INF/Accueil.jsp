<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.ecole.application.modele.bll.SessionManager" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/styles_olivia.css">
    <title>Liste des enchères</title>
</head>
<body>

<div class="global">
<div class="liens">
    <c:choose>
        <c:when test="${sessionScope.no_utilisateur > 0}">
            <a href="#">Enchères</a>
            <a href="ArticleCreationServlet">Vendre un article</a>
            <a href="ProfileMonServlet">Mon Profil</a>
            ${sessionScope.pseudo}
            <a href="Deconnexion">Déconnexion</a>      
        </c:when>
        <c:otherwise>
            <a href="Connexion">S'inscrire - Se connecter</a>
        </c:otherwise>
    </c:choose>
</div>

<div class="baniere">
	<img src="img/banniere.png" alt="banniere">
</div>

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
							<input type="text" id="requeteRecherche" name="requeteRecherche" placeholder="Le nom ou la description de l'article contient">
                        </td>
                        <td>
                            <button type="submit" class="btnAccept">Rechercher</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Catégorie :
							<select id="filtreCategorie" name="filtreCategorie" class="rollCategorie">
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


    <c:choose>
         <c:when test="${sessionScope.no_utilisateur > 0}">
            <table class="formFiltre">
			<tr>
				<td>
				    <form id="radioAchats" class="radio">
				        <label class="labelRadio">
				            <input type="radio" name="options" id="achatsRadio" value="achats" class="checkBox" checked> Achats
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox1" value="enchères ouvertes" class="checkBox" checked> enchères ouvertes
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox2" value="mes enchères en cours" class="checkBox"> mes enchères en cours
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox3" value="mes enchères remportées" class="checkBox"> mes enchères remportées
				        </label><br>
				    </form>
				</td>
				
				<td>
				    <form id="radioVentes" class="radio">
				        <label class="labelRadio">
				            <input type="radio" name="options" id="ventesRadio" value="ventes" class="checkBox"> Ventes 
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox4" value="mes ventes en cours" class="checkBox" disabled> mes ventes en cours
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox5" value="ventes non débutées" disabled class="checkBox"> ventes non débutées
				        </label><br>
				        <label class="labelRadio">
				            <input type="checkbox" id="checkbox6" value="ventes terminées" disabled class="checkBox"> ventes terminées
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
    
<c:choose>
    <c:when test="${empty listeArticles}">
        <p align="center">Aucun article trouvé.</p>
    </c:when>
    <c:otherwise>
            <!-- Section pour l'affichage des articles -->
        <table>
            <tr>
                <td colspan="2" align="center">
                    <h2>Mes enchères</h2>
                </td>
            </tr>
            <c:forEach var="article" items="${listeArticles}" varStatus="loop">
                <c:if test="${loop.index % 2 == 0}">
                    <tr>
                </c:if>
                <td>
                    <table border="1" 
                           <c:choose>
                               <c:when test="${sessionScope.no_utilisateur > 0}">
                                   onclick="window.location.href='ArticleDetailsServlet?articleId=${article.noArticle}'" <!-- TODO : ca renvoit vers où ?  -->
                               </c:when>
                               <c:otherwise>
                               </c:otherwise>
                           </c:choose>
                    >
                        <!-- Contenu de l'article -->
                        <tr>
                            <td>Photo</td>
                            <td>
                                <h3>${article.nomArticle}</h3>
                                <c:choose>
								    <c:when test="${article.enchere.montant_enchere == null || article.enchere.montant_enchere == 0}">
								        Prix : ${article.prixVente}
								    </c:when>
								    <c:otherwise>
								        <p>Prix : ${article.enchere.montant_enchere}</p>
								    </c:otherwise>
								</c:choose>
                                	<p>Fin Enchere : ${article.dateFinEncheres}</p>
                                <c:choose>
                                    <c:when test="${sessionScope.no_utilisateur > 0}">
                                        <c:choose>
                                            <c:when test="${sessionScope.no_utilisateur == article.utilisateur.noUtilisateur}">
                                                <p><b>Vendeur : <a href="ProfileMonServlet">${article.utilisateur.pseudo}</a></b></p>
                                            </c:when>
                                            <c:otherwise>
                                                <p><b>Vendeur : <a href="ProfileUserServlet?userId=${article.utilisateur.noUtilisateur}">${article.utilisateur.pseudo}</a></b></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <p><b>Vendeur : ${article.utilisateur.pseudo}</b></p>
                                    </c:otherwise>
                                </c:choose>
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
</div>
    
</body>
</html>
