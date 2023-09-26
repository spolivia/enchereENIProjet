package fr.eni.ecole.application.modele.dal;

import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Retraits;

public interface ArticlesDAO extends DAO<Articles> {

	 public List<Articles> logicFiltrerTirageArticles(String requeteRecherche, int filtreCategorie) throws DALException;
	 
	 public List<Articles> filtrerArticlesParRecherche(String requeteRecherche) throws DALException;
	 
	 public List<Articles> filtrerArticlesParCategorie(int filtreCategorie) throws DALException;
	 
	 public List<Articles> filtrerArticlesParLesDeuxCriteres(String requeteRecherche, int filtreCategorie) throws DALException;	

	 public List<Articles> selectByUserID(int userId) throws DALException;

	public Object addArticleWithRetraits(Articles article, Retraits retraits) throws DALException; 
}
