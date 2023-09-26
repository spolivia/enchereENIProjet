package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Encheres;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.EncheresDAO;

public class EncheresDAOJdbcImpl implements EncheresDAO {

	private static final String SELECT_BY_ID = "SELECT * FROM Encheres WHERE id_encheres = ?";
	private static final String SELECT_ALL = "SELECT * FROM Encheres";
	private static final String UPDATE = "UPDATE Encheres SET no_utilisateur = ?, no_article = ?, date_enchere = ?,  montant_enchere = ? WHERE id_encheres = ?";
	private static final String INSERT = "INSERT INTO Encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM Encheres WHERE id_encheres = ?";
	private static final String SELECT_BY_ARTICLE_ID = "SELECT * FROM Encheres WHERE no_article = ?";
	private static final String SELECT_BY_USER_ID = "SELECT * FROM Encheres WHERE no_utilisateur = ?";
	private static final String HIGHEST_ENCHERE = "SELECT MAX(montant_enchere) FROM Encheres WHERE no_article = ?";

	@Override
	public Encheres selectById(int id) throws DALException {
		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToEncheres(resultSet);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de l'enchères par ID", e);
		}
	}

	@Override
	public List<Encheres> selectAll() throws DALException {
		List<Encheres> encheres = new ArrayList<>();
		try (Connection connection = JdbcTools.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
			while (resultSet.next()) {
				Encheres enchere = resultSetToEncheres(resultSet);
				encheres.add(enchere);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de la liste des enchères", e);
		}
		return encheres;
	}

	@Override
	public void update(Encheres a) throws DALException {
		Encheres existingArticle = selectById(a.getId_encheres());
		if (existingArticle == null) {
			throw new DALException(
					"L'enchère avec l'ID " + a.getId_encheres() + " n'existe pas dans la base de données.");
		}

		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

			preparedStatement.setInt(1, a.getNo_utilisateur());
			preparedStatement.setInt(2, a.getNo_article());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(a.getDate_enchere()));
			preparedStatement.setInt(4, a.getMontant_enchere());
			preparedStatement.setInt(5, a.getId_encheres());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Erreur lors de la mise à jour de l'enchère", e);
		}

	}

	@Override
	public void insert(Encheres a) throws DALException {
		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
						Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, a.getNo_utilisateur());
			preparedStatement.setInt(2, a.getNo_article());
			preparedStatement.setTimestamp(3, Timestamp.valueOf(a.getDate_enchere()));
			preparedStatement.setInt(4, a.getMontant_enchere());

			preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int generatedId = generatedKeys.getInt(1);
					a.setId_encheres(generatedId);
				} else {
					throw new SQLException("La génération des clés a échoué");
				}
			}

		} catch (

		SQLException e) {
			throw new DALException("Erreur lors de l'insertion de l'article", e);
		}

	}

	@Override
	public void delete(int id) throws DALException {
		Encheres existingEnchere = selectById(id);
		if (existingEnchere == null) {
			throw new DALException("L'enchère avec l'ID " + id + " n'existe pas dans la base de données.");
		}

		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Erreur lors de la suppression de l'enchère", e);
		}

	}

	@Override
	public void delete(Encheres a) throws DALException {
		this.delete(a.getId_encheres());

	}

	private Encheres resultSetToEncheres(ResultSet resultSet) throws SQLException {
		int id_encheres = resultSet.getInt("id_encheres");
		int no_utilisateur = resultSet.getInt("no_utilisateur");
		int no_article = resultSet.getInt("no_article");
		LocalDateTime date_enchere = resultSet.getTimestamp("date_enchere").toLocalDateTime();
		int montant_enchere = resultSet.getInt("montant_enchere");

		Encheres encheres = new Encheres(id_encheres, no_utilisateur, no_article, date_enchere, montant_enchere);

		return encheres;
	}

	@Override
	public List<Encheres> selectByArticleId(int articleId) throws DALException {
		List<Encheres> encheres = new ArrayList<>();
		try (Connection connection = JdbcTools.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_BY_ARTICLE_ID)) {
			while (resultSet.next()) {
				Encheres enchere = resultSetToEncheres(resultSet);
				encheres.add(enchere);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de la liste des enchères par article", e);
		}
		return encheres;
	}

	@Override
	public List<Encheres> selectByUserId(int userId) throws DALException {
		List<Encheres> encheres = new ArrayList<>();
		try (Connection connection = JdbcTools.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_BY_USER_ID)) {
			while (resultSet.next()) {
				Encheres enchere = resultSetToEncheres(resultSet);
				encheres.add(enchere);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de la liste des enchères par article", e);
		}
		return encheres;
	}

	@Override
	public Encheres highestEnchere(int no_article) throws DALException {

		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(HIGHEST_ENCHERE)) {
			preparedStatement.setInt(1, no_article);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToEncheres(resultSet);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de l'enchère la plus haute", e);
		}
	}
}
