package ua.production.data.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.production.data.DBCPDataSource;
import ua.production.data.dao.Dao;
import ua.production.data.entity.Parameter;
import ua.production.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParameterDao implements Dao<Parameter> {
    private Logger logger = LoggerFactory.getLogger(ParameterDao.class);

    private final static String SQL_SELECT_PARAMETER_BY_ID =
            "SELECT id, name, value, measure_unit, product_id, parameter_group_id FROM parameters where id=?";
    private final static String SQL_SELECT_ALL_PARAMETERS =
            "SELECT id, name, value, measure_unit, product_id, parameter_group_id FROM parameters";
    private final static String SQL_INSERT_PARAMETER =
            "INSERT INTO parameters (name, value, measure_unit, product_id, parameter_group_id) VALUES (?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE_PARAMETER_BY_NAME =
            "UPDATE parameters SET name=?, value=?, measure_unit=?, product_id=?, parameter_group_id=? WHERE id=?";
    private final static String SQL_DELETE_PARAMETER_BY_ID =
            "DELETE from parameters WHERE id=?";

    public ParameterDao() {
    }

    @Override
    public Optional<Parameter> get(int id) throws DaoException {
        Parameter parameter = null;
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PARAMETER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int parameterId = -1;
            while (resultSet.next() && id != parameterId) {
                parameterId = resultSet.getInt(1);
                String parameterName = resultSet.getString(2);
                String parameterValue = resultSet.getString(3);
                String parameterMeasureUnit = resultSet.getString(4);
                int productId = resultSet.getInt(5);
                int parameterGroupId = resultSet.getInt(6);
                parameter = new Parameter(parameterId, parameterName, parameterValue, parameterMeasureUnit, productId, parameterGroupId);
            }
        } catch (SQLException e) {
            logger.error("Error when get parameter ", e);
            throw new DaoException();
        }
        return Optional.ofNullable(parameter);
    }

    @Override
    public List<Parameter> getAll() throws DaoException {
        List<Parameter> parameters = new ArrayList<>();
        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PARAMETERS);
            while (resultSet.next()) {
                int parameterId = resultSet.getInt(1);
                String parameterName = resultSet.getString(2);
                String parameterValue = resultSet.getString(3);
                String parameterMeasureUnit = resultSet.getString(4);
                int productId = resultSet.getInt(5);
                int parameterGroupId = resultSet.getInt(6);
                parameters.add(new Parameter(parameterId, parameterName, parameterValue, parameterMeasureUnit, productId, parameterGroupId));
            }
        } catch (SQLException e) {
            logger.error("Error when get all parameters ", e);
            throw new DaoException();
        }
        return parameters;
    }

    @Override
    public boolean save(Parameter parameter) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PARAMETER)) {
            setParametersToStatement(parameter, statement);
            statement.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Error when save parameter ", e);
            throw new DaoException();
        }
    }

    private void setParametersToStatement(Parameter parameter, PreparedStatement statement) throws SQLException {
        statement.setString(1, parameter.getName());
        statement.setString(2, parameter.getValue());
        statement.setString(3, parameter.getMeasureUnit());
        statement.setInt(4, parameter.getProductId());
        statement.setInt(5, parameter.getParameterGroupId());
    }

    @Override
    public Parameter update(Parameter parameter) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PARAMETER_BY_NAME)) {
            setParametersToStatement(parameter, statement);
            statement.setInt(6, parameter.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when update parameter ", e);
            throw new DaoException();
        }
        return parameter;
    }

    @Override
    public boolean delete(Parameter parameter) throws DaoException {
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PARAMETER_BY_ID)){
            statement.setInt(1, parameter.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error when delete parameter ", e);
            throw new DaoException();
        }
        return false;
    }
}
