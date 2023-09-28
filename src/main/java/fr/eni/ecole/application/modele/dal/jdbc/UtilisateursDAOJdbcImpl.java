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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	}

    @Override
    public boolean login(HttpServletRequest request, String username, String password) throws DALException {
        String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ? AND mot_de_passe = ?";
        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("no_utilisateur");
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId); 
                    return true; 
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DALException("Error during login", e);
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Before logout - no_utilisateur: " + session.getAttribute("no_utilisateur"));
            
            session.setAttribute("no_utilisateur", -1);
            
            System.out.println("After logout - no_utilisateur: " + session.getAttribute("no_utilisateur"));
        }
    }


    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") != null;
    }
    
    public int authenticateUser(String pseudo, String motDePasse) throws DALException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = JdbcTools.getConnection();

            if (connection.isClosed()) {
                connection = JdbcTools.getConnection();
            }

            String sql = "SELECT no_utilisateur FROM UTILISATEURS WHERE pseudo = ? AND mot_de_passe = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pseudo);
            preparedStatement.setString(2, motDePasse);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("User authenticated successfully");
                return rs.getInt("no_utilisateur");
            } else {
                System.out.println("Authentication failed");
                return -1; 
            }
        } catch (SQLException e) {
            throw new DALException("Error while authenticating user", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public Utilisateurs getUtilisateursByPseudo(String pseudo) throws DALException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcTools.getConnection();

            String sql = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pseudo);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetToUtilisateur(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DALException("Error while fetching user by pseudo", e);
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

    @Override
    public void updateUtilisateurProfile(Utilisateurs user) throws DALException {
        String sql = "UPDATE UTILISATEURS " +
                "SET nom = ?, prenom = ?, email = ?, telephone = ?, " +
                "rue = ?, code_postal = ?, ville = ? " +
                "WHERE no_utilisateur = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        Utilisateurs currentUser = getUtilisateursByPseudo(user.getPseudo());
        user.setNoUtilisateur(currentUser.getNoUtilisateur());
        
        try {
            connection = JdbcTools.getConnection();
            
            user.setNoUtilisateur(currentUser.getNoUtilisateur());
           

                statement = connection.prepareStatement(sql);
                statement.setString(1, user.getNom());
                statement.setString(2, user.getPrenom());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getTelephone());
                statement.setString(5, user.getRue());
                statement.setString(6, user.getCodePostal());
                statement.setString(7, user.getVille());
                statement.setInt(8, user.getNoUtilisateur());
                statement.executeUpdate();
        
        } catch (SQLException e) {
            throw new DALException("Error while updating user profile", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    JdbcTools.closeConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public Utilisateurs selectByArticle(int articleId) throws DALException {
        String sql = "SELECT u.* " +
                     "FROM UTILISATEURS u " +
                     "INNER JOIN ARTICLES_VENDUS a ON u.no_utilisateur = a.no_utilisateur " +
                     "WHERE a.no_article = ?";

        try (Connection connection = JdbcTools.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, articleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToUtilisateur(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Error fetching user by article ID", e);
        }

        return null;
    }
}