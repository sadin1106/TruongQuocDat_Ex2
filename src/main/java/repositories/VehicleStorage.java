package repositories;

import model.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleStorage {
    List<Vehicle> getAllVehicles() throws SQLException;

    boolean deleteAVehicle(Vehicle vehicle) throws SQLException;

    boolean addNewVehicle(Vehicle vehicle) throws SQLException;

    List<Vehicle> getVehiclesInCSV(String path) throws SQLException;

    void addVehiclesFromCSV(String path) throws SQLException;

    void exportAllVehiclesToCSV(String path) throws SQLException;

    boolean doesVehicleExist(Vehicle vehicle) throws SQLException;
}
