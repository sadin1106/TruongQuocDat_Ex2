package repositories;

import model.Customer;
import model.RentalDetail;
import model.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface RentalDetailsStorage {
    List<RentalDetail> getCustomerRentals(Customer customer) throws SQLException;

    boolean rentCar(RentalDetail rentingACar) throws SQLException;

    boolean returnACar(RentalDetail rentalDetail) throws SQLException;

    List<Vehicle> getAllAvailableVehicles(String startDate, String endDate) throws SQLException;
}
