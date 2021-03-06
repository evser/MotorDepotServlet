package dao;

import entity.User;
import exception.DAOException;

/**
 * DAO interface used to operate on USER table
 * Created by USER on 15.06.2016.
 */
public interface UserDAO extends GenericDAO {

    /**
     * Checks if a given login is already occupied
     * @param login login to check
     * @throws DAOException
     */
    boolean isLoginOccupied(String login) throws DAOException;

    /**
     * Checks if the given combination of login and password corresponds to any driver
     * @param login login to check
     * @param pass password to check
     * @return list where user entity is kept or empty list if there is no such user
     * @throws DAOException
     */
    User authenticateUser(String login, String pass) throws DAOException;

    /**
     * Adds new user to the database
     * @return user entity that is saved to the database
     * @throws DAOException in case of DML error
     */
    User addNewUser(String login, String password) throws DAOException;
}
