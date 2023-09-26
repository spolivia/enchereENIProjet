<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Articles" %>
<%@ page import="fr.eni.ecole.application.modele.bll.ArticlesManager" %>
<%@ page import="fr.eni.ecole.application.modele.bo.Utilisateurs" %>
<%@ page import="fr.eni.ecole.application.modele.bll.UtilisateursManager" %>
<%@ page import="fr.eni.ecole.application.modele.dal.DAOFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
</head>
<body>
    <h1>Profile of ${user.pseudo}</h1>

   <table>

        <tr>
            <td>Nom</td>
            <td>${user.nom}</td>
        </tr>
        <tr>
            <td>Prénom</td>
            <td>${user.prenom}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${user.email}</td>
        </tr>
        <tr>
            <td>Téléphone</td>
            <td>${user.telephone}</td>
        </tr>
        <tr>
            <td>Rue</td>
            <td>${user.rue}</td>
        </tr>
        <tr>
            <td>Code Postal</td>
            <td>${user.codePostal}</td>
        </tr>
        <tr>
            <td>Ville</td>
            <td>${user.ville}</td>
        </tr>
    </table>
    
    <br>
    
    <form action="listeArticles">
        <input type="submit" value="Retour à l'accueil">
    </form>

    <c:choose>
        <c:when test="${!empty listeArticles}">
        <h2><center>Enchères de ${user.pseudo}</center></h2>
            <table border="0" cellspacing="20" align="center">
                <c:forEach var="article" items="${listeArticles}" varStatus="loop">
                    <c:if test="${loop.index % 2 == 0}">
                        <tr>
                    </c:if>
                    <td>
                                            <table border="1"
                           <c:choose>
                               <c:when test="${sessionScope.no_utilisateur > 0}">
                                   onclick="window.location.href='ArticleDetailsServlet?articleId=${article.noArticle}'"
                               </c:when>
                               <c:otherwise>
                               </c:otherwise>
                           </c:choose>
                    >
                            <tr>
                                <td>Photo</td>
                                <td>

                                    <h3>${article.nomArticle}</h3>
                                    <p>Prix : BESOIN MONTANT_ENCHERE</p>
                                    <p>Fin Enchere : ${article.dateFinEncheres}</p>
                                    <p><b>Vendeur : ${user.pseudo}</b></p>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <c:if test="${loop.index % 2 == 1 || loop.last}">
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p align="center">Aucun article trouvé.</p>
        </c:otherwise>
    </c:choose>

</body>
</html>
