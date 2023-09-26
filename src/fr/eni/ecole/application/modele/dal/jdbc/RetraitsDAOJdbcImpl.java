package fr.eni.ecole.application.modele.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.application.modele.bo.Retraits;
import fr.eni.ecole.application.modele.dal.DALException;
import fr.eni.ecole.application.modele.dal.RetraitsDAO;

public class RetraitsDAOJdbcImpl implements RetraitsDAO {

	private static final String SELECT_BY_ID = "SELECT * FROM Retraits WHERE no_article = ?";
	private static final String SELECT_ALL = "SELECT * FROM Retraits";
	private static final String UPDATE = "UPDATE Retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private static final String INSERT = "INSERT INTO Retraits (no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM Retraits WHERE no_article = ?";

	@Override
	public Retraits selectById(int id) throws DALException {
		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToRetraits(resultSet);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération des informations de retrait par ID", e);
		}
	}

	@Override
	public List<Retraits> selectAll() throws DALException {
		List<Retraits> articles = new ArrayList<>();
		try (Connection connection = JdbcTools.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
			while (resultSet.next()) {
				Retraits article = resultSetToRetraits(resultSet);
				articles.add(article);
			}
		} catch (SQLException e) {
			throw new DALException("Erreur lors de la récupération de la liste des retraits", e);
		}
		return articles;
	}

	@Override
	public void update(Retraits a) throws DALException {
		Retraits existingArticle = selectById(a.getNoArticle());
		if (existingArticle == null) {
			throw new DALException(
					"Le Retrait lié à l'objet" + a.getNoArticle() + " n'existe pas dans la base de données.");
		}

		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

			preparedStatement.setString(1, a.getRue());
			preparedStatement.setInt(2, a.getCodePostale());
			preparedStatement.setString(3, a.getVille());

			preparedStatement.setInt(4, a.getNoArticle());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Erreur lors de la mise à jour de l'adresse de retrait", e);
		}
	}

	@Override
	public void insert(Retraits a) throws DALException {
		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT,
						Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, a.getNoArticle());
			preparedStatement.setString(2, a.getRue());
			preparedStatement.setInt(3, a.getCodePostale());
			preparedStatement.setString(4, a.getVille());

			preparedStatement.executeUpdate();

		} catch (

		SQLException e) {
			throw new DALException("Erreur lors de l'insertion de l'article", e);
		}
	}

	@Override
	public void delete(int id) throws DALException {
		Retraits existingArticle = selectById(id);
		if (existingArticle == null) {
			throw new DALException("Le Retraits lié à l'objet " + id + " n'existe pas dans la base de données.");
		}

		try (Connection connection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DALException("Erreur lors de la suppression du Retraits", e);
		}

	}

	@Override
	public void delete(Retraits a) throws DALException {
		this.delete(a.getNoArticle());

	}

	private Retraits resultSetToRetraits(ResultSet resultSet) throws SQLException {

		int noArticle = resultSet.getInt("noArticle");
		String rue = resultSet.getString("rue");
		int codePostale = resultSet.getInt("codePostale");
		String ville = resultSet.getString("ville");

		Retraits retraits = new Retraits(noArticle, rue, codePostale, ville);

		return retraits;
	}

}
