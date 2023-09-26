package fr.eni.ecole.application.modele.dal;

import java.util.List;

public interface DAO<T> {

	public T selectById(int id) throws DALException;

	public List<T> selectAll() throws DALException;

	public void update(T a) throws DALException;

	public void insert(T a) throws DALException;

	public void delete(int id) throws DALException;

	public void delete(T a) throws DALException;

}