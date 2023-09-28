package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;
import fr.eni.ecole.application.modele.bo.Retraits;
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
        article.setPrixVente(rs.getInt("prix_initial"));
        article.setNoUtilisateur(rs.getInt("no_utilisateur"));
        article.setNoCategorie(rs.getInt ("no_categorie"));
        
        System.out.println("ResultSetArticles returning to method");
        return article;
    }
    
    @Override
    public Articles selectById(int articleId) throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        Articles article = null;
        Connection connection = null;

        try {
            connection = JdbcTools.getConnection();
            System.out.println("Executing selectById: Articles");
            rqt = connection.prepareStatement(
                    "SELECT * FROM ARTICLES_VENDUS " +
                            "WHERE no_article = ?");
            rqt.setInt(1, articleId);

            rs = rqt.executeQuery();

            if (rs.next()) {
                article = resultSetToArticles(rs);
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
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();                
                }
            }
        }

        return article;
    }



    @Override
    public List<Articles> selectAll() throws DALException {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement rqt = connection.prepareStatement("SELECT * FROM ARTICLES_VENDUS");
             ResultSet rs = rqt.executeQuery()) {
            System.out.println("Executing selectAll: Articles");
            
            while (rs.next()) {
                Articles article = resultSetToArticles(rs);
                listeArticles.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_SELECT_ALL - ", e);
        }

        return listeArticles;
    }



   
    @Override
    public void delete(int articleId) throws DALException {
        PreparedStatement rqt = null;
        Connection connection = null;

        try {
            String deleteQuery = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
            System.out.println("Executing delete: Articles");
            connection = JdbcTools.getConnection();
            rqt = connection.prepareStatement(deleteQuery);
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
            } finally {
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();               
                }
            }
        }
    }
   
    @Override
    public void insert(Articles article) throws DALException {
        PreparedStatement rqt = null;
        Connection connection = null;

        try {
            System.out.println("Executing insert: Articles");
            String insertQuery = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            connection = JdbcTools.getConnection();
            rqt = connection.prepareStatement(insertQuery);
            rqt.setString(1, article.getNomArticle());
            rqt.setString(2, article.getDescription());
            rqt.setDate(3, new java.sql.Date(article.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(article.getDateFinEncheres().getTime()));
            rqt.setInt(5, article.getPrixInitial());
            rqt.setInt(6, article.getPrixVente());
            rqt.setInt(7, article.getNoUtilisateur());
            rqt.setInt(8, article.getNoCategorie()); 

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
            } finally {
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();               
                }
            }
        }
    }


  
    @Override
    public void update(Articles updatedArticle) throws DALException {
        PreparedStatement rqt = null;
        Connection connection = null;

        try {
            System.out.println("Executing update: Articles");
            String updateQuery = "UPDATE ARTICLES_VENDUS " +
                                "SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, " +
                                "prix_initial = ? " +
                                "WHERE no_article = ?";
            
            connection = JdbcTools.getConnection();
            rqt = connection.prepareStatement(updateQuery);
            
            rqt.setString(1, updatedArticle.getNomArticle());
            rqt.setString(2, updatedArticle.getDescription());
            rqt.setDate(3, new java.sql.Date(updatedArticle.getDateDebutEncheres().getTime()));
            rqt.setDate(4, new java.sql.Date(updatedArticle.getDateFinEncheres().getTime()));
            rqt.setInt(5, updatedArticle.getPrixInitial());
            rqt.setInt(6, updatedArticle.getNoArticle());

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
            } finally {
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();               
                }
            }
        }
    }

  
    @Override
    public List<Articles> logicFiltrerTirageArticles(String requeteRecherche, int filtreCategorie) throws DALException {
        List<Articles> listeArticles = new ArrayList<>();
        System.out.println("Executing FilterLogicSelector: Articles");
        try {
            if (requeteRecherche == null) {
                requeteRecherche = "";
                System.out.println("Search Empty");
            }

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
                System.out.println("General Display Initiated");
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
        System.out.println("Executing FilterByString: Articles");
        
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
        System.out.println("Executing FilterByCategorie: Articles");
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<Articles>();
        Connection connection = null;

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
                article.setNoUtilisateur(rs.getInt("no_utilisateur"));

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
            } finally {
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
            }
        }

        return articlesFiltres;
    }


    @Override
    public List<Articles> filtrerArticlesParLesDeuxCriteres(String requeteRecherche, int filtreCategorie) throws DALException {
        System.out.println("Executing FilterByCategoryAndString: Articles");
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesFiltres = new ArrayList<>();
        Connection connection = null;

        try {
            String query = "SELECT * FROM ARTICLES_VENDUS WHERE "
                    + "(no_categorie = ?) AND (nom_article LIKE ? OR description LIKE ?)";

            rqt = JdbcTools.getConnection().prepareStatement(query);
            rqt.setInt(1, filtreCategorie);
            rqt.setString(2, "%" + requeteRecherche + "%");
            rqt.setString(3, "%" + requeteRecherche + "%");

            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setNoUtilisateur(rs.getInt("no_utilisateur"));

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
            } finally {
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                	e.printStackTrace();
                }
            }
        }

        return articlesFiltres;
    }

    @Override
    public List<Articles> selectByUserID(int userId) throws DALException {
        System.out.println("Executing selectByUserId: Articles");
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Articles> articlesList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = JdbcTools.getConnection();

            rqt = connection.prepareStatement(
                    "SELECT * FROM ARTICLES_VENDUS " +
                            "WHERE no_utilisateur = ?");
            rqt.setInt(1, userId);

            rs = rqt.executeQuery();
            
            while (rs.next()) {
                Articles article = resultSetToArticles(rs);
                articlesList.add(article);
                
                System.out.println("Retrieved article: " + article.getNomArticle());

            }
            System.out.println("Number of articles retrieved: " + articlesList.size());

        } catch (SQLException e) {
            throw new DALException("Error fetching articles by user ID", e);
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
                try {
                    if (connection != null) {
                        JdbcTools.closeConnection();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();                
                }
            }
        }

        return articlesList;
    }

	@Override
	public void delete(Articles a) throws DALException {
		// TODO Auto-generated method stub
	}

    @Override
    public Object addArticleWithRetraits(Articles article, Retraits retraits) throws DALException {
        Connection connection = null;
        PreparedStatement articleStatement = null;
        PreparedStatement retraitsStatement = null;

        try {
            connection = JdbcTools.getConnection();
            connection.setAutoCommit(false); // Start a transaction

            // Insert the article
            String insertArticleQuery = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            articleStatement = connection.prepareStatement(insertArticleQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            articleStatement.setString(1, article.getNomArticle());
            articleStatement.setString(2, article.getDescription());
            articleStatement.setDate(3, new java.sql.Date(article.getDateDebutEncheres().getTime()));
            articleStatement.setDate(4, new java.sql.Date(article.getDateFinEncheres().getTime()));
            articleStatement.setInt(5, article.getPrixInitial());
            articleStatement.setInt(6, article.getPrixVente());
            articleStatement.setInt(7, article.getNoUtilisateur());
            articleStatement.setInt(8, article.getNoCategorie());

            int affectedRows = articleStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DALException("Failed to insert article, no rows affected.");
            }

            ResultSet generatedKeys = articleStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedArticleId = generatedKeys.getInt(1);
                article.setNoArticle(generatedArticleId);
            } else {
                throw new DALException("Failed to retrieve generated article ID.");
            }

            String insertRetraitsQuery = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
            retraitsStatement = connection.prepareStatement(insertRetraitsQuery);

            retraitsStatement.setInt(1, article.getNoArticle());
            retraitsStatement.setString(2, retraits.getRue());
            retraitsStatement.setInt(3, retraits.getCodePostale());
            retraitsStatement.setString(4, retraits.getVille());

            retraitsStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DALException("Error adding article with retraits", e);
        } finally {
            try {
                if (articleStatement != null) {
                    articleStatement.close();
                }
                if (retraitsStatement != null) {
                    retraitsStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    JdbcTools.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return article;
    }


}
