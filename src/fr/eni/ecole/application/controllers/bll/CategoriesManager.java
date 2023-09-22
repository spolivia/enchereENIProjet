package fr.eni.ecole.application.controllers.bll;

import fr.eni.ecole.application.modele.bo.Categories;
import fr.eni.ecole.application.modele.dal.CategoriesDAO;
import fr.eni.ecole.application.modele.dal.DALException;

import java.util.List;

public class CategoriesManager {
    private CategoriesDAO categoriesDAO;

    public CategoriesManager(CategoriesDAO categoriesDAO) {
        this.categoriesDAO = categoriesDAO;
    }

    public Categories getCategorieById(int categoryId) throws BLLException {
        try {
            return categoriesDAO.selectById(categoryId);
        } catch (DALException e) {
            throw new BLLException("Error retrieving category by ID", e);
        }
    }

    public List<Categories> getAllCategories() throws BLLException {
        try {
            return categoriesDAO.selectAll();
        } catch (DALException e) {
            throw new BLLException("Error retrieving all categories", e);
        }
    }

    public void addCategorie(Categories category) throws BLLException {
        try {
            categoriesDAO.insert(category);
        } catch (DALException e) {
            throw new BLLException("Error adding category", e);
        }
    }

    public void updateCategorie(Categories category) throws BLLException {
        try {
            categoriesDAO.update(category);
        } catch (DALException e) {
            throw new BLLException("Error updating category", e);
        }
    }

    public void deleteCategorie(int categoryId) throws BLLException {
        try {
            categoriesDAO.delete(categoryId);
        } catch (DALException e) {
            throw new BLLException("Error deleting category", e);
        }
    }
}
