package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.DALException;

public class ArticlesDAOJdbcImpl {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Articles> selectAll() throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> listeArticles = new ArrayList<>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.no_categorie, c.libelle AS nom_categorie " +
                            "FROM ARTICLES_VENDUS a " +
                            "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie");
            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));
                
                Categories categorie = new Categories();
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("nom_categorie"));
                
                article.setCategorie(categorie);

                listeArticles.add(article);
            }

        } catch (SQLException e) {
            throw new DALException("ERREUR_SELECT_ALL - ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }
        return listeArticles;
    }
    
    public void deleteArticle(int articleId) throws DALException {
        PreparedStatement rqt = null;

        try {
            String deleteQuery = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
            
            rqt = JdbcTools.getConnection().prepareStatement(deleteQuery);
            rqt.setInt(1, articleId);

            int rowsAffected = rqt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DALException("Article not found for deletion.");
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_DELETE_ARTICLE - ", e);
        } finally {
            try {
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }
    }
    
    public void insertArticle(Articles article) throws DALException {
        PreparedStatement rqt = null;

        try {
            String insertQuery = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_categorie) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";
            
            rqt = JdbcTools.getConnection().prepareStatement(insertQuery);
            rqt.setString(1, article.getNomArticle());
            rqt.setString(2, article.getDescription());
            rqt.setDate(3, new java.sql.Date(article.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(article.getDateFinEncheres().getTime()));
            rqt.setInt(5, article.getPrixInitial());
            rqt.setInt(6, article.getCategorie().getNoCategorie());

            rqt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("ERREUR_INSERT_ARTICLE - ", e);
        } finally {
            try {
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }
    }

    public void updateArticle(Articles updatedArticle) throws DALException {
        PreparedStatement rqt = null;

        try {
            String updateQuery = "UPDATE ARTICLES_VENDUS " +
                                "SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, " +
                                "prix_initial = ?, no_categorie = ? " +
                                "WHERE no_article = ?";
            
            rqt = JdbcTools.getConnection().prepareStatement(updateQuery);
            
            rqt.setString(1, updatedArticle.getNomArticle());
            rqt.setString(2, updatedArticle.getDescription());
            rqt.setDate(3, new java.sql.Date(updatedArticle.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(updatedArticle.getDateFinEncheres().getTime()));
            rqt.setInt(5, updatedArticle.getPrixInitial());
            rqt.setInt(6, updatedArticle.getCategorie().getNoCategorie());
            rqt.setInt(7, updatedArticle.getNoArticle());

            int rowsAffected = rqt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DALException("Article not found for update.");
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_UPDATE_ARTICLE - ", e);
        } finally {
            try {
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }
    }

    
    public List<Articles> rechercherArticles(String requeteRecherche, String filtreCategorie) throws DALException {
        List<Articles> tousLesArticles = selectAll();
        List<Articles> articlesFiltres = new ArrayList<>();

        System.out.println("Requête de recherche : " + requeteRecherche);

        if (requeteRecherche != null && !requeteRecherche.isEmpty()) {
            for (Articles article : tousLesArticles) {
                String nomArticle = article.getNomArticle();
                String description = article.getDescription();

                System.out.println("Nom de l'article : " + nomArticle);
                System.out.println("Description : " + description);

                if ((nomArticle != null && nomArticle.contains(requeteRecherche)) || 
                    (description != null && description.contains(requeteRecherche))) {
                    articlesFiltres.add(article);
                }
            }
        } else {
            articlesFiltres.addAll(tousLesArticles);
        }

        System.out.println("Articles filtrés : " + articlesFiltres.size());

        return articlesFiltres;
    }
    
    public List<Articles> filtrerArticlesParCategorie(String filtreCategorie) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<>();

        try {
            String requete = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial " +
                           "FROM ARTICLES_VENDUS " +
                           "WHERE no_categorie = ?";
            rqt = JdbcTools.getConnection().prepareStatement(requete);
            rqt.setString(1, filtreCategorie);

            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));

                articlesFiltres.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_FILTRER_ARTICLES_PAR_CATEGORIE - ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }
        return articlesFiltres;
    }

    public List<Articles> filtrerArticlesParCategorie(int idCategorie) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<Articles>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial " +
                    "FROM ARTICLES_VENDUS " +
                    "WHERE no_categorie = ?");
            
            rqt.setInt(1, idCategorie);

            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));

                articlesFiltres.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_FILTRER_PAR_CATEGORIE - ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rqt != null) {
                    rqt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcTools.closeConnection();
        }

        return articlesFiltres;
    }

    
}
