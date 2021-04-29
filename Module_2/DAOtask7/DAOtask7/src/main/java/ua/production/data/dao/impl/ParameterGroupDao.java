package ua.production.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.DBCPDataSource;
import ua.production.data.dao.Dao;
import ua.production.data.entity.ParameterGroup;
import ua.production.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParameterGroupDao implements Dao<ParameterGroup> {
    private Logger logger = LoggerFactory.getLogger(ProductGroupDao.class);

    private final static String SQL_SELECT_PARAMETER_GROUP_BY_ID =
            "SELECT id, name, production_group_id FROM parameter_groups where id=?";
    private final static String SQL_SELECT_ALL_PARAMETER_GROUPS =
            "SELECT id, name, production_group_id FROM parameter_groups";
    private final static String SQL_PARAMETER_GROUP =
            "INSERT INTO parameter_groups (name, production_group_id) VALUES (?, ?)";
    private final static String SQL_UPDATE_PARAMETER_GROUP_BY_NAME =
            "UPDATE parameter_groups SET name=?, production_group_id=? WHERE id=?";
    private final static String SQL_DELETE_PARAMETER_GROUP_BY_ID =
            "DELETE from parameter_groups WHERE id=?";

    public ParameterGroupDao() {
    }

    @Override
    public Optional<ParameterGroup> get(int id) throws DaoException {
        ParameterGroup parameterGroup = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PARAMETER_GROUP_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int productGroupid = -1;
            while (resultSet.next() && id != productGroupid) {
                productGroupid = resultSet.getInt(1);
                String productGroupName = resultSet.getString(2);
                int productGroupId = resultSet.getInt(3);
                parameterGroup = new ParameterGroup(productGroupid, productGroupName, productGroupId);
            }
        } catch (SQLException e) {
            logger.error("Error when get product group ", e);
            throw new DaoException();
        }
        return Optional.ofNullable(parameterGroup);
    }

    @Override
    public List<ParameterGroup> getAll() throws DaoException {
        List<ParameterGroup> productGroups = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PARAMETER_GROUPS);
            while (resultSet.next()) {
                int parameterGroupId = resultSet.getInt(1);
                String parameterGroupName = resultSet.getString(2);
                int productGroupId = resultSet.getInt(3);
                productGroups.add(new ParameterGroup(parameterGroupId, parameterGroupName, productGroupId));
            }
        } catch (SQLException e) {
            logger.error("Error when get all product groups ", e);
            throw new DaoException();
        }
        return productGroups;
    }

    @Override
    public boolean save(ParameterGroup parameterGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PARAMETER_GROUP)) {
            statement.setString(1, parameterGroup.getName());
            statement.setInt(2, parameterGroup.getProductGroupId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save parameter group ", e);
            throw new DaoException();
        }
    }

    @Override
    public ParameterGroup update(ParameterGroup parameterGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PARAMETER_GROUP_BY_NAME)) {
            statement.setString(1, parameterGroup.getName());
            statement.setInt(2, parameterGroup.getProductGroupId());
            statement.setInt(3, parameterGroup.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when update parameter group ", e);
            throw new DaoException();
        }
        return parameterGroup;
    }

    @Override
    public boolean delete(ParameterGroup parameterGroup) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PARAMETER_GROUP_BY_ID)){
            statement.setInt(1, parameterGroup.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when delete parameter group ", e);
            throw new DaoException();
        }
        return false;
    }
}
