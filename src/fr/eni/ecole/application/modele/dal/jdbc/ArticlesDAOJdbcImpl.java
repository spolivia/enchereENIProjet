package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.dal.ArticlesDAO;
import fr.eni.ecole.application.modele.dal.DALException;

public class ArticlesDAOJdbcImpl implements ArticlesDAO{


    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private Articles resultSetToArticles(ResultSet rs) throws SQLException {
        Articles article = new Articles();

        article.setNoArticle(rs.getInt("no_article"));
        article.setNomArticle(rs.getString("nom_article"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
        article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
        article.setPrixInitial(rs.getInt("prix_initial"));

        return article;
    }
    
    @Override
    public Articles selectById(int articleId) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        Articles article = null;

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT * FROM ARTICLES_VENDUS " +
                            "WHERE no_article = ?");
            rqt.setInt(1, articleId);

            rs = rqt.executeQuery();

            if (rs.next()) {
                article = resultSetToArticles(rs); // Use the modified method to get a single Articles object
            }

        } catch (SQLException e) {
            throw new DALException("Error fetching article by ID", e);
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
            } finally {
                JdbcTools.closeConnection();
            }
        }

        return article;
    }

    @Override
    public List<Articles> selectAll() throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> listeArticles = new ArrayList<>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT * FROM ARTICLES_VENDUS");
            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = resultSetToArticles(rs);
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
   
    @Override     
    public void delete(int articleId) throws DALException {
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
   
    @Override
    public void insert(Articles article) throws DALException {
        PreparedStatement rqt = null;

        try {
            String insertQuery = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";
            
            rqt = JdbcTools.getConnection().prepareStatement(insertQuery);
            rqt.setString(1, article.getNomArticle());
            rqt.setString(2, article.getDescription());
            rqt.setDate(3, new java.sql.Date(article.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(article.getDateFinEncheres().getTime()));
            rqt.setInt(5, article.getPrixInitial());

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
  
    @Override
    public void update(Articles updatedArticle) throws DALException {
        PreparedStatement rqt = null;

        try {
            String updateQuery = "UPDATE ARTICLES_VENDUS " +
                                "SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, " +
                                "prix_initial = ? " +
                                "WHERE no_article = ?";
            
            rqt = JdbcTools.getConnection().prepareStatement(updateQuery);
            
            rqt.setString(1, updatedArticle.getNomArticle());
            rqt.setString(2, updatedArticle.getDescription());
            rqt.setDate(3, new java.sql.Date(updatedArticle.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(updatedArticle.getDateFinEncheres().getTime()));
            rqt.setInt(5, updatedArticle.getPrixInitial());
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
  
    @Override
    public List<Articles> logicFiltrerTirageArticles(String requeteRecherche, int filtreCategorie) throws DALException {
        List<Articles> listeArticles = new ArrayList<>();

        try {
            if (requeteRecherche == null) {
                requeteRecherche = "";
            }

            // Check if both search input and category are provided
            if (!requeteRecherche.isEmpty() && filtreCategorie != 0) {
                listeArticles = filtrerArticlesParLesDeuxCriteres(requeteRecherche, filtreCategorie);
                System.out.println("Dual Search Initiated");
                
            } else if (filtreCategorie != 0) {
                listeArticles = filtrerArticlesParCategorie(filtreCategorie);
                System.out.println("Category Search Initiated");
                
            } else if (!requeteRecherche.isEmpty()) {
                listeArticles = filtrerArticlesParRecherche(requeteRecherche);
                System.out.println("Searchbar Search Initiated");
            } else {
                listeArticles = selectAll();
            }
        } catch (DALException e) {
            throw new DALException("ERREUR_FILTER_AND_RETRIEVE_ARTICLES - ", e);
        }

        return listeArticles;
    }

 
    @Override
    public List<Articles> filtrerArticlesParRecherche(String requeteRecherche) throws DALException {
        List<Articles> tousLesArticles = selectAll();
        List<Articles> articlesFiltres = new ArrayList<Articles>();
        
        if (requeteRecherche != null && !requeteRecherche.isEmpty()) {
            for (Articles article : tousLesArticles) {
                String nomArticle = article.getNomArticle();
                String description = article.getDescription();

                if ((nomArticle != null && nomArticle.contains(requeteRecherche)) || 
                    (description != null && description.contains(requeteRecherche))) {
                    articlesFiltres.add(article);
                }
            }
        } else {
            articlesFiltres.addAll(tousLesArticles);
        }

        return articlesFiltres;
    }
   
  
    @Override
    public List<Articles> filtrerArticlesParCategorie(int filtreCategorie) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<Articles>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT * FROM ARTICLES_VENDUS " +
                    "WHERE no_categorie = ?");
            
            rqt.setInt(1, filtreCategorie);

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

    @Override
    public List<Articles> filtrerArticlesParLesDeuxCriteres(String requeteRecherche, int filtreCategorie) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<>();

        try {
        	String query = "SELECT * FROM ARTICLES_VENDUS WHERE "
                    + "(no_categorie = ?) AND (nom_article LIKE ? OR description LIKE ?)";

            

            rqt = JdbcTools.getConnection().prepareStatement(query);
            rqt.setInt(1, filtreCategorie);
            System.out.println("Int Set");
            rqt.setString(2, "%" + requeteRecherche + "%");
            rqt.setString(3, "%" + requeteRecherche + "%");
            System.out.println("String Set");

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
            throw new DALException("ERREUR_FILTRER_PAR_LES_DEUX_CRITERES - ", e);
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


      








	@Override
	public void delete(Articles a) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
