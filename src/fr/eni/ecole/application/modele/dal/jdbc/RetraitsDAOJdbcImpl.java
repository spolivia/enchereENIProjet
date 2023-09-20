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

	private static final String SELECT_BY_ID = "SELECT * FROM Retraits WHERE idRetraits = ?";
	private static final String SELECT_ALL = "SELECT * FROM Retraits";

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
			throw new DALException("Erreur lors de la récupération de l'article par ID", e);
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
			throw new DALException("Erreur lors de la récupération de tous les articles", e);
		}
		return articles;
	}

	@Override
	public void update(Retraits a) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Retraits a) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Retraits a) throws DALException {
		// TODO Auto-generated method stub

	}

	private Retraits resultSetToRetraits(ResultSet resultSet) throws SQLException {
		int idRetraits = resultSet.getInt("idRetrait");
		int noArticle = resultSet.getInt("noArticle");
		String rue = resultSet.getString("rue");
		int codePostale = resultSet.getInt("codePostale");
		String ville = resultSet.getString("ville");

		Retraits retraits = new Retraits(idRetraits, noArticle, rue, codePostale, ville);

		return retraits;
	}

}
