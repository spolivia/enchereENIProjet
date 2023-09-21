package fr.eni.ecole.application.modele.dal;

import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;

public interface ArticlesDAO extends DAO<Articles> {

	 public List<Articles> logicFiltrerTirageArticles(String searchInput, String selectedCategory) throws DALException;
	 
	 public List<Articles> filtrerArticlesParRecherche(String requeteRecherche, String filtreCategorie) throws DALException;
	 
	 public List<Articles> filtrerArticlesParCategorie(int idCategorie) throws DALException;
	 
	 public List<Articles> filtrerArticlesParLesDeuxCriteres(List<Articles> articles, String rechercheInput, String selectionCategory);
	
}
