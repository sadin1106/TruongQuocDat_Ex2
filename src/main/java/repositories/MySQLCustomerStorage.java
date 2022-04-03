package repositories;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utilities.Utilities.tableExist;

public class MySQLCustomerStorage{
    private Connection connection;
    private final String DB_URL = "jdbc:mysql://localhost:3306/vehicledb?createDatabaseIfNotExist=true";
    private final String USERNAME = "root";
    private final String PASSWORD = "1qazxsw2@_123";

    public MySQLCustomerStorage() throws SQLException{
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        if (!tableExist(connection, "Customers")){
            statement.execute("create table Customers (customerNumber INTEGER, name TEXT, address TEXT, contact TEXT)");
        }
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        if (!checkIfCustomerExist(customer.getCustomerNumber())) {
            PreparedStatement ps = connection.prepareStatement("insert into Customers (customerNumber, name, address, contact) values (?,?,?,?)");
            ps.setInt(1, customer.getCustomerNumber());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getContact());
            ps.executeUpdate();
            return true;
        } else {
            return false;
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customers");
        ResultSet result = preparedStatement.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (result.next()) {
            Customer customer = new Customer();
            customer.setCustomerNumber(result.getInt("customerNumber"));
            customer.setName(result.getString("name"));
            customer.setAddress(result.getString("address"));
            customer.setContact(result.getString("contact"));
            customers.add(customer);
        }
        return customers;
    }

    public boolean checkIfCustomerExist(int customerNumber) throws SQLException{
        boolean bool = false;
        PreparedStatement preparedStatement = connection.prepareStatement("select customerNumber from Customers where customerNumber = ?");
        preparedStatement.setInt(1, customerNumber);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()){
            bool = true;
        }
        return bool;
    }
}
