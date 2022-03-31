package repositories;

import com.sun.security.auth.UnixNumericUserPrincipal;
import model.Customer;
import model.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utilities.Utilities.tableExist;

public class MySQLVehicleStorage{
    private Connection connection;
    private final String DB_URL = "jdbc:mysql://localhost:3306/vehicledb?createDatabaseIfNotExist=true";
    private final String USERNAME = "root";
    private final String PASSWORD = "admin";

    public MySQLVehicleStorage() throws SQLException{
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        if (!tableExist(connection, "Vehicles")){
            statement.execute("create table Vehicles (code LONG, brand TEXT, model TEXT, seats INTEGER, licensePlate TEXT, status TEXT, customerNumber INTEGER)");
        }
    }

    public List<Vehicle> getVehiclesInCSV(){
        File csv = new File("src\\main\\resources\\data.csv");
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            Scanner scan = new Scanner(csv);
            while (scan.hasNextLine()){
                String data = scan.nextLine();
                String[] splitData = data.split(",");
                vehicles.add(new Vehicle(Long.parseLong(splitData[0]), splitData[1], splitData[2], Integer.parseInt(splitData[3]), splitData[4], splitData[5], Integer.parseInt(splitData[6])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Vehicles");
        ResultSet result = preparedStatement.executeQuery();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        while (result.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setCode(result.getLong("code"));
            vehicle.setBrand(result.getString("brand"));
            vehicle.setModel(result.getString("model"));
            vehicle.setSeats(result.getInt("seats"));
            vehicle.setLicensePlate(result.getString("licensePlate"));
            vehicle.setStatus(result.getString("status"));
            vehicle.setCustomerNumber(result.getInt("customerNumber"));
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    public boolean deleteAVehicle(Vehicle vehicle) throws SQLException {
        if (vehicle == null){
            return false;
        }else {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from Vehicles where code=?");
            preparedStatement.setLong(1, vehicle.getCode());
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public List<Vehicle> getAllAvailableVehicles(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Vehicles where status='available'");
        ResultSet result = preparedStatement.executeQuery();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        while (result.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setCode(result.getLong("code"));
            vehicle.setBrand(result.getString("brand"));
            vehicle.setModel(result.getString("model"));
            vehicle.setSeats(result.getInt("seats"));
            vehicle.setLicensePlate(result.getString("licensePlate"));
            vehicle.setStatus(result.getString("status"));
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    public boolean addNewVehicle(Vehicle vehicle) throws SQLException {
        if (vehicle != null) {
            PreparedStatement ps = connection.prepareStatement("insert into Vehicles (code, brand, model, seats, licensePlate, status, customerNumber) values (?,?,?,?,?,?,?)");
            ps.setLong(1, vehicle.getCode());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setInt(4, vehicle.getSeats());
            ps.setString(5, vehicle.getLicensePlate());
            ps.setString(6, vehicle.getStatus());
            ps.setInt(7, 0);
            ps.executeUpdate();
            return true;
        } else {
            return false;
        }
    }

    public void addVehiclesFromCSV() throws SQLException {
        List<Vehicle> vehicles = getVehiclesInCSV();
        for (Vehicle vehicle : vehicles) {
            PreparedStatement ps = connection.prepareStatement("insert into Vehicles (code, brand, model, seats, licensePlate, status) values (?,?,?,?,?,?)");
            ps.setLong(1, vehicle.getCode());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setInt(4, vehicle.getSeats());
            ps.setString(5, vehicle.getLicensePlate());
            ps.setString(6, vehicle.getStatus());
            ps.executeUpdate();
        }
    }
}
