package dao.impl.jdbc;

import dao.RequestDAO;
import dao.util.ColumnName;
import dao.util.DatabaseQuery;
import dao.util.pool.ConnectionPool;
import entity.Request;
import exception.DAOException;
import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class RequestDAOJDBCImpl implements RequestDAO {

    @Override
    public List<Request> getAllRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_REQUSTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Request> getUnassignedRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_UNASSIGNED_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addNewRequest(int cargoWeight) throws DAOException {
        if(cargoWeight < 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_REQUEST)) {
                statement.setInt(1, cargoWeight);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Request> getRequestsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Request> requestList = new ArrayList<>();
        while(resultSet.next()) {
            Integer requestId = resultSet.getInt(ColumnName.ID);
            Integer cargoWeight = resultSet.getInt(ColumnName.CARGO_WEIGHT);
            Request request = new Request();
            request.setId(requestId);
            request.setCargoWeight(cargoWeight);
            requestList.add(request);
        }
        return requestList;
    }
}
