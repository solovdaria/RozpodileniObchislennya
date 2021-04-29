package ua.production.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.DBCPDataSource;
import ua.production.data.dao.Dao;
import ua.production.data.entity.ProductGroup;
import ua.production.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductGroupDao implements Dao<ProductGroup> {
    private Logger logger = LoggerFactory.getLogger(ProductGroupDao.class);

    private final static String SQL_SELECT_PRODUCT_GROUP_BY_ID =
            "SELECT id, name FROM production_groups where id=?";
    private final static String SQL_SELECT_ALL_PRODUCT_GROUPS =
            "SELECT id, name FROM production_groups";
    private final static String SQL_PRODUCT_GROUP =
            "INSERT INTO production_groups (name) VALUES (?)";
    private final static String SQL_UPDATE_PRODUCT_GROUP_BY_NAME =
            "UPDATE production_groups SET name=? WHERE id=?";
    private final static String SQL_DELETE_PRODUCT_GROUP_BY_ID =
            "DELETE from production_groups WHERE id=?";
    private static final String SQL_SELECT_LAST_ID =
            "SELECT MAX(id) FROM production_groups";

    public ProductGroupDao() {
    }

    @Override
    public Optional<ProductGroup> get(int id) throws DaoException {
        ProductGroup productGroup = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCT_GROUP_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int productGroupid = -1;
            while (resultSet.next() && id != productGroupid) {
                productGroupid = resultSet.getInt(1);
                String productGroupName = resultSet.getString(2);
                productGroup = new ProductGroup(productGroupid, productGroupName);
            }
        } catch (SQLException e) {
            logger.error("Error when get product group ", e);
            throw new DaoException();
        }
        return Optional.ofNullable(productGroup);
    }

    @Override
    public List<ProductGroup> getAll() throws DaoException {
        List<ProductGroup> productGroups = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRODUCT_GROUPS);
            while (resultSet.next()) {
                int parameterId = resultSet.getInt(1);
                String parameterName = resultSet.getString(2);
                productGroups.add(new ProductGroup(parameterId, parameterName));
            }
        } catch (SQLException e) {
            logger.error("Error when get all product groups ", e);
            throw new DaoException();
        }
        return productGroups;
    }

    @Override
    public boolean save(ProductGroup productGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PRODUCT_GROUP)) {
            statement.setString(1, productGroup.getName());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save product group ", e);
            throw new DaoException();
        }
    }

    @Override
    public ProductGroup update(ProductGroup productGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_GROUP_BY_NAME)) {
            statement.setString(1, productGroup.getName());
            statement.setInt(2, productGroup.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when update product group ", e);
            throw new DaoException();
        }
        return productGroup;
    }

    @Override
    public boolean delete(ProductGroup productGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT_GROUP_BY_ID)){
            statement.setInt(1, productGroup.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when delete product group ", e);
            throw new DaoException();
        }
        return false;
    }

    public int getLastInsertedId() throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_LAST_ID);
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("Error when get all products ", e);
            throw new DaoException();
        }
    }
}
