package repositories;

import model.Customer;
import model.RentalDetail;
import model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utilities.Utilities.tableExist;

public class MySQLRentalDetailsStorage{
    private Connection connection;
    private final String DB_URL = "jdbc:mysql://localhost:3306/vehicledb?createDatabaseIfNotExist=true";
    private final String USERNAME = "root";
    private final String PASSWORD = "1qazxsw2@_123";

    public MySQLRentalDetailsStorage() throws SQLException{
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        if (!tableExist(connection, "RentalDetails")){
            statement.execute("create table RentalDetails (startDate TEXT, endDate TEXT, brand TEXT, model TEXT, customerNumber TEXT, customerName TEXT)");
        }
    }

    public List<RentalDetail> getCustomerRentals(Customer customer) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from RentalDetails where clientNumber = ?");
        ps.setInt(1, customer.getCustomerNumber());
        ResultSet rs = ps.executeQuery();

        List<RentalDetail> rentedCars = new ArrayList<>();
        while (rs.next()) {
            RentalDetail rent = new RentalDetail();
            rent.setStartDate(rs.getString("startDate"));
            rent.setEndDate(rs.getString("endDate"));
            rent.setBrand(rs.getString("brand"));
            rent.setModel(rs.getString("model"));
            rent.setCustomerNumber(rs.getInt("customerNumber"));
            rent.setCustomerName(rs.getString("customerName"));
            rentedCars.add(rent);
        }
        return rentedCars;
    }

    public boolean rentCar(RentalDetail rentingACar) throws SQLException {
        int count = 0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(0) FROM Vehicles WHERE status='available' AND brand=? AND model=?");
        preparedStatement.setString(1, rentingACar.getBrand());
        preparedStatement.setString(2, rentingACar.getModel());
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            count = result.getInt(1);
        }
        if (count < 1) {
            return false;
        }
        else {
            preparedStatement = connection.prepareStatement("insert into RentalDetails(startDate, endDate, brand, model, customerNumber, customerName) values(?,?,?,?,?,?)");
            preparedStatement.setString(1, rentingACar.getStartDate());
            preparedStatement.setString(2, rentingACar.getEndDate());
            preparedStatement.setString(3, rentingACar.getBrand());
            preparedStatement.setString(4, rentingACar.getModel());
            preparedStatement.setInt(5, rentingACar.getCustomerNumber());
            preparedStatement.setString(6, rentingACar.getCustomerName());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("update Vehicles set status='rented' , customerNumber=? where brand=? and model=?");
            preparedStatement.setInt(1, rentingACar.getCustomerNumber());
            preparedStatement.setString(2, rentingACar.getBrand());
            preparedStatement.setString(3, rentingACar.getModel());
            preparedStatement.executeUpdate();

            return true;
        }
    }

    public boolean returnACar(Vehicle vehicle) throws SQLException {
        if (vehicle == null) {
            return false;
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from RentalDetails WHERE brand=? AND customerNumber=?");
            preparedStatement.setString(1, vehicle.getBrand());
            preparedStatement.setInt(2, vehicle.getCustomerNumber());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("update Vehicles set status='available', customerNumber=0 where brand=? and model=?");
            preparedStatement.setString(1, vehicle.getBrand());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.executeUpdate();
            return true;
        }
    }
}
