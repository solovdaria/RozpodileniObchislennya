package ua.production.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.DBCPDataSource;
import ua.production.data.dao.Dao;
import ua.production.data.entity.Product;
import ua.production.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Product> {
    private Logger logger = LoggerFactory.getLogger(ProductDao.class);

    private final static String SQL_SELECT_PRODUCT_BY_ID =
            "SELECT id, name, description, output_date, production_group_id FROM production where id=?";
    private final static String SQL_SELECT_ALL_PRODUCTS =
            "SELECT id, name, description, output_date, production_group_id FROM production";
    private final static String SQL_INSERT_PRODUCT =
            "INSERT INTO production (name, description, output_date, production_group_id) VALUES (?, ?, ?, ?)";
    private final static String SQL_UPDATE_PRODUCT_BY_NAME =
            "UPDATE production SET name=?, description=?, output_date=?, production_group_id=? WHERE id=?";
    private final static String SQL_DELETE_PRODUCT_BY_ID =
            "DELETE from production WHERE id=?";
    private static final String SQL_SELECT_LAST_ID =
            "SELECT MAX(id) FROM production";

    public ProductDao() {
    }

    @Override
    public Optional<Product> get(int id) throws DaoException {
        Product product = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int productId = -1;
            while (resultSet.next() && id != productId) {
                productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate outputDate = resultSet.getDate(4).toLocalDate();
                int productGroupId = resultSet.getInt(5);
                product = new Product(productId, productName, description, outputDate, productGroupId);
            }
        } catch (SQLException e) {
            logger.error("Error when get product ", e);
            throw new DaoException();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAll() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRODUCTS);
            while (resultSet.next()) {
                int productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate outputDate = resultSet.getDate(4).toLocalDate();
                int productGroupId = resultSet.getInt(5);
                products.add(new Product(productId, productName, description, outputDate, productGroupId));
            }
        } catch (SQLException e) {
            logger.error("Error when get all products ", e);
            throw new DaoException();
        }
        return products;
    }

    @Override
    public boolean save(Product product) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {
            setParametersToStatement(product, statement);
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save product ", e);
            throw new DaoException();
        }
    }

    private void setParametersToStatement(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setDate(3, Date.valueOf(product.getOutputDate()));
        statement.setInt(4, product.getProductGroupId());
    }

    @Override
    public Product update(Product product) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_BY_NAME)) {
            setParametersToStatement(product, statement);
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when update product ", e);
            throw new DaoException();
        }
        return product;
    }

    @Override
    public boolean delete(Product product) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT_BY_ID)) {
            statement.setInt(1, product.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Error when delete product ", e);
            throw new DaoException();
        }
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
