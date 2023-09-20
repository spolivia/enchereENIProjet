package fr.eni.ecole.application.modele.dal.jdbc;

import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.DALException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAOJdbcImpl {

    public List<Categories> selectAll() throws DALException {
        PreparedStatement rqt = null;
        ResultSet rs = null;
        List<Categories> listeCategories = new ArrayList<>();

        try {
            rqt = JdbcTools.getConnection().prepareStatement(
                    "SELECT * FROM CATEGORIES");
            rs = rqt.executeQuery();

            while (rs.next()) {
                Categories categorie = new Categories();
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("libelle"));

                listeCategories.add(categorie);
            }

        } catch (SQLException e) {
            throw new DALException("ERREUR_SELECT_ALL_CATEGORIES - ", e);
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
        return listeCategories;
    }
}
