package fr.eni.ecole.application.modele.dal.jdbc;

import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.CategoriesDAO;
import fr.eni.ecole.application.modele.dal.DALException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAOJdbcImpl implements CategoriesDAO {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
    private Categories resultSetToCategorie(ResultSet rs) throws SQLException {
        Categories categorie = null;
            categorie = new Categories();
            categorie.setNoCategorie(rs.getInt("no_categorie"));
            categorie.setLibelle(rs.getString("libelle"));
        return categorie;
    }

    @Override
    public Categories selectById(int categorieId) throws DALException {
        String sql = "SELECT * FROM CATEGORIES WHERE no_categorie = ?";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categorieId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSetToCategorie(resultSet);
            }
        } catch (SQLException e) {
            throw new DALException("Error fetching category by ID", e);
        }
    }
    
    @Override
    public List<Categories> selectAll() throws DALException {
        List<Categories> listeCategories = new ArrayList<>();

        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM CATEGORIES");
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Categories categorie = resultSetToCategorie(rs);
                listeCategories.add(categorie);
            }
        } catch (SQLException e) {
            throw new DALException("ERREUR_SELECT_ALL_CATEGORIES - ", e);
        }

        return listeCategories;
    }

    @Override
    public void update(Categories a) throws DALException {
        // TODO Auto-generated method stub
    }

    @Override
    public void insert(Categories a) throws DALException {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(int id) throws DALException {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(Categories a) throws DALException {
        // TODO Auto-generated method stub
    }
}
