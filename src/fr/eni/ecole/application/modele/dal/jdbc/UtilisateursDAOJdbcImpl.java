package fr.eni.ecole.application.modele.dal.jdbc;

import fr.eni.ecole.application.modele.bo.Utilisateurs;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.UtilisateursDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateursDAOJdbcImpl implements UtilisateursDAO {

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private Utilisateurs resultSetToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateurs utilisateur = new Utilisateurs();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setTelephone(rs.getString("telephone"));
            utilisateur.setRue(rs.getString("rue"));
            utilisateur.setCodePostal(rs.getString("code_postal"));
            utilisateur.setVille(rs.getString("ville"));
            utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
            utilisateur.setCredit(rs.getInt("credit"));
            utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
        return utilisateur;
    }
    
    @Override
    public Utilisateurs selectById(int userId) throws DALException {
        String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToUtilisateur(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Error fetching user by ID", e);
        }
        return null;
    }
    
    @Override
    public List<Utilisateurs> selectAll() throws DALException {
        List<Utilisateurs> users = new ArrayList<>();
        String sql = "SELECT * FROM UTILISATEURS";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(resultSetToUtilisateur(resultSet));
            }
        } catch (SQLException e) {
            throw new DALException("Error fetching all users", e);
        }
        return users;
    }
    
    @Override
    public void delete(int userId) throws DALException {
        String sql = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Error deleting user", e);
        }
    }
    
    @Override
    public void	insert(Utilisateurs user) throws DALException {
        String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getPseudo());
            statement.setString(2, user.getNom());
            statement.setString(3, user.getPrenom());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getTelephone());
            statement.setString(6, user.getRue());
            statement.setString(7, user.getCodePostal());
            statement.setString(8, user.getVille());
            statement.setString(9, user.getMotDePasse());
            statement.setInt(10, user.getCredit());
            statement.setBoolean(11, user.isAdministrateur());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Error inserting user", e);
        }
    }
    
    @Override
    public void update(Utilisateurs user) throws DALException {
        String sql = "UPDATE UTILISATEURS " +
                "SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, " +
                "rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, credit = ?, administrateur = ? " +
                "WHERE no_utilisateur = ?";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getPseudo());
            statement.setString(2, user.getNom());
            statement.setString(3, user.getPrenom());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getTelephone());
            statement.setString(6, user.getRue());
            statement.setString(7, user.getCodePostal());
            statement.setString(8, user.getVille());
            statement.setString(9, user.getMotDePasse());
            statement.setInt(10, user.getCredit());
            statement.setBoolean(11, user.isAdministrateur());
            statement.setInt(12, user.getNoUtilisateur());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Error updating user", e);
        }
    }

	@Override
	public void delete(Utilisateurs a) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
