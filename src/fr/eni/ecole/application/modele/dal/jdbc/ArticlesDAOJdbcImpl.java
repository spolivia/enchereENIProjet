package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Articles;
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
        List<Articles> listArticles = new ArrayList<Articles>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial FROM ARTICLES_VENDUS");
            rs = rqt.executeQuery();

            while (rs.next()) {
                Articles article = new Articles();
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
                article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
                article.setPrixInitial(rs.getInt("prix_initial"));

                listArticles.add(article);
            }
            
        } catch (SQLException e) {
            throw new DALException("SELECT_ALL ERROR - ", e);
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
        return listArticles;
    }
    
    
    
    public List<Articles> searchArticles(String searchQuery) throws DALException {
        List<Articles> allArticles = selectAll();
        List<Articles> filteredArticles = new ArrayList<>();

        System.out.println("Search Query: " + searchQuery);

        if (searchQuery != null && !searchQuery.isEmpty()) {
            for (Articles article : allArticles) {
                String nomArticle = article.getNomArticle();
                String description = article.getDescription();

                System.out.println("Nom Article: " + nomArticle);
                System.out.println("Description: " + description);

                if ((nomArticle != null && nomArticle.contains(searchQuery)) || 
                    (description != null && description.contains(searchQuery))) {
                    filteredArticles.add(article);
                }
            }
        } else {
            filteredArticles.addAll(allArticles);
        }

        System.out.println("Filtered Articles: " + filteredArticles.size());

        return filteredArticles;
    }
    
    
    




}
