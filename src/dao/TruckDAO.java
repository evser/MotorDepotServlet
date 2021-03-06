package dao;

import entity.Truck;
import entity.util.TruckState;
import exception.DAOException;

import java.util.List;

/**
 * DAO interface used to operate on TRUCK table
 * Created by USER on 15.06.2016.
 */
public interface TruckDAO extends GenericDAO {

    /**
     * Gets all the trucks
     * @return list of trucks
     * @throws DAOException
     */
    List<Truck> getAllTrucks() throws DAOException;

    /**
     * Changes truck state
     * @param truckId id of truck
     * @param truckStateToSet state to set
     * @throws DAOException in case of DML error
     */
    void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException;

    /**
     * Add new truck to the database
     * @param capacity capacity parameter
     * @return resulting truck entity
     * @throws DAOException in case of DML error
     */
    Truck addNewTruck(int capacity) throws DAOException;
}
