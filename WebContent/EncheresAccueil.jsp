<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
					<form action="Recherche" method="post">
 					<input type="search" value="Le nom de l'article contient" id="recherche" name="recherche"/>    
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
			
			<td>
				 <form action="Recherche" method="post">
 					<input type="submit" value="Recherche" id="recherche" name="recherche"/>    
				 </form>
			</td>
		</tr>
	</table>
<br><br><br>
<!--TABLE D'ENCHERS: ajoute c:forEach une foix que les tables primitife sons crée-->
    <table border="1">
	    <tr>
	    	<td>Picture</td>
	        <td>	
	        	Title Item<br>
	            Price<br>
	            End Date<br>
	            User<br>
	        </td>
	    </tr>
    </table>
    
</body>
</html>