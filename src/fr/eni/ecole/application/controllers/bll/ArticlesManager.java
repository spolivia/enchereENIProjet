package fr.eni.ecole.application.controllers.bll;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.dal.*;

import java.util.List;

public class ArticlesManager {
    private ArticlesDAO articleDAO;

    public ArticlesManager(ArticlesDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public List<Articles> getAllArticles() throws BLLException {
        try {
            return articleDAO.selectAll();
        } catch (DALException e) {
            throw new BLLException("Error retrieving articles", e);
        }
    }

    public Articles getArticleById(int articleId) throws BLLException {
        try {
            return articleDAO.selectById(articleId);
        } catch (DALException e) {
            throw new BLLException("Error retrieving article by ID", e);
        }
    }

    public void addArticle(Articles article) throws BLLException {
        try {
            articleDAO.insert(article);
        } catch (DALException e) {
            throw new BLLException("Error adding article", e);
        }
    }

    public void updateArticle(Articles article) throws BLLException {
        try {
            articleDAO.update(article);
        } catch (DALException e) {
            throw new BLLException("Error updating article", e);
        }
    }

    public void deleteArticle(int articleId) throws BLLException {
        try {
            articleDAO.delete(articleId);
        } catch (DALException e) {
            throw new BLLException("Error deleting article", e);
        }
    }

    public List<Articles> logicFiltrerTirageArticles(String requeteRecherche, int filtreCategorie) throws BLLException {
        try {
            return articleDAO.logicFiltrerTirageArticles(requeteRecherche, filtreCategorie);
        } catch (DALException e) {
            throw new BLLException("Error filtering articles", e);
        }
    }

    public List<Articles> filtrerArticlesParRecherche(String requeteRecherche) throws BLLException {
        try {
            return articleDAO.filtrerArticlesParRecherche(requeteRecherche);
        } catch (DALException e) {
            throw new BLLException("Error filtering articles by search", e);
        }
    }

    public List<Articles> filtrerArticlesParCategorie(int filtreCategorie) throws BLLException {
        try {
            return articleDAO.filtrerArticlesParCategorie(filtreCategorie);
        } catch (DALException e) {
            throw new BLLException("Error filtering articles by category", e);
        }
    }
    
    public List<Articles> filtrerArticlesParLesDeuxCriteres(String requeteRecherche, int filtreCategorie) throws BLLException {
        try {
            return articleDAO.filtrerArticlesParLesDeuxCriteres(requeteRecherche, filtreCategorie);
        } catch (DALException e) {
            throw new BLLException("Error filtering articles by both criteria", e);
        }
    }

}
