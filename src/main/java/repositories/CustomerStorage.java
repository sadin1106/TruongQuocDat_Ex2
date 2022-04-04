package repositories;

import model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerStorage {
    boolean addCustomer(Customer customer) throws SQLException;

    List<Customer> getAllCustomers() throws SQLException;

    boolean checkIfCustomerExist(int customerNumber) throws SQLException;
}
