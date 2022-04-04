import model.Customer;
import model.RentalDetail;
import model.Vehicle;
import repositories.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        printMenu();
    }

    private static void printMenu() throws SQLException {
        CustomerStorage customerStorage = new MySQLCustomerStorage();
        Scanner scan = new Scanner(System.in);
        VehicleStorage vehicleStorage = new MySQLVehicleStorage();
        RentalDetailsStorage rentalDetailsStorage = new MySQLRentalDetailsStorage();
        try {
            while (true) {
                System.out.println("--------------------------------------------------");
                System.out.println("---------------Vehicle Rental System--------------");
                System.out.println("--------------------------------------------------");
                System.out.println("1. Print all vehicles belong to the rental fleet");
                System.out.println("2. Rent a vehicle");
                System.out.println("3. Return the rented vehicle");
                System.out.println("4. Print all available vehicles in a specified time");
                System.out.println("5. Add new vehicle to the rental fleet");
                System.out.println("6. Cancellation of a vehicle from the rental fleet");
                System.out.println("7. Importing vehicles from a CSV file");
                System.out.println("8. Exporting all vehicle to a CSV file");
                System.out.println("9. Register a new customer");
                System.out.println("0. Exit");
                System.out.println("--------------------------------------------------");
                System.out.print("Your option: ");
                int input = scan.nextInt();
                switch (input){
                    case 1:
                        List<Vehicle> vehicles = vehicleStorage.getAllVehicles();
                        if (vehicles.size() < 1){
                            System.out.println("There are no record of vehicles in the database!");
                        }
                        for (Vehicle vehicle : vehicles){
                            System.out.println(vehicle + "\n");
                        }
                        break;
                    case 2:
                        System.out.println("Enter your start date (in format of yyyy-MM-dd): ");
                        String startDate = scan.next();
                        System.out.println("Enter your end date (in format of yyyy-MM-dd): ");
                        String endDate = scan.next();
                        System.out.println("Enter your vehicle brand: ");
                        String brand = scan.next();
                        System.out.println("Enter your vehicle model: ");
                        String model = scan.next();
                        System.out.println("Enter your customer number: ");
                        int customerNumber = scan.nextInt();
                        System.out.println("Enter your customer name: ");
                        String customerName = scan.next();
                        if (customerStorage.checkIfCustomerExist(customerNumber)) {
                            RentalDetail rentalDetail = new RentalDetail(startDate, endDate, brand, model, customerNumber, customerName);
                            if (rentalDetailsStorage.rentCar(rentalDetail)) {
                                System.out.println(customerName + " have rented the " + brand + " " + model + " from " + startDate + " to " + endDate);
                                System.out.println("Here is your receipt :");
                                System.out.println(rentalDetail);
                            } else {
                                System.out.println("Try again!");
                            }
                        } else {
                            System.out.println("This customer has not existed");
                        }
                        break;
                    case 3:
                        System.out.println("Enter the customer number :");
                        int cn = scan.nextInt();
                        System.out.println("Enter the vehicle brand: ");
                        String b = scan.next();
                        System.out.println("Enter the vehicle model: ");
                        String m = scan.next();
                        Customer customer = new Customer();
                        customer.setCustomerNumber(cn);
                        List<RentalDetail> rentalDetails = rentalDetailsStorage.getCustomerRentals(customer);
                        if (rentalDetails.size() > 0) {
                            RentalDetail rentalDetail1 = new RentalDetail();
                            rentalDetail1.setCustomerNumber(cn);
                            rentalDetail1.setBrand(b);
                            rentalDetail1.setModel(m);
                            if (rentalDetailsStorage.returnACar(rentalDetail1)) {
                                System.out.println("You have succeed in returning this " + b + " " + m);
                            } else {
                                System.out.println("Try again!");
                            }
                        } else {
                            System.out.println("You have not rented any vehicles yet!");
                        }
                        break;
                    case 4:
                        System.out.println("Enter the start date (in format yyyy-MM-dd):");
                        String sd = scan.next();
                        System.out.println("Enter the end date (in format yyyy-MM-dd):");
                        String ed = scan.next();
                        List<Vehicle> vehicles1 = rentalDetailsStorage.getAllAvailableVehicles(sd, ed);
                        if (vehicles1.size() > 0) {
                            for (Vehicle vehicle : vehicles1) {
                                System.out.println(vehicle + "\n");
                            }
                        } else {
                            System.out.println("There is no record match your search!");
                        }
                        break;
                    case 5:
                        System.out.println("Enter the vehicle code: ");
                        long code = scan.nextLong();
                        System.out.println("Enter the vehicle brand: ");
                        String bra = scan.next();
                        System.out.println("Enter the vehicle model: ");
                        String mod = scan.next();
                        System.out.println("Enter the vehicle seats: ");
                        int seats = scan.nextInt();
                        System.out.println("Enter the vehicle licensePlate: ");
                        String licensePlate = scan.next();
                        System.out.println("Enter the vehicle status: ");
                        String status = scan.next();
                        Vehicle vehicle1 = new Vehicle(code, bra, mod, seats, licensePlate, status);
                        if (vehicleStorage.addNewVehicle(vehicle1)){
                            System.out.println("Add new vehicle successfully");
                        } else {
                            System.out.println("Try again!");
                        }
                        break;
                    case 6:
                        System.out.println("Enter the vehicle code: ");
                        long c = scan.nextLong();
                        Vehicle vehicle2 = new Vehicle();
                        vehicle2.setCode(c);
                        if (vehicleStorage.deleteAVehicle(vehicle2)){
                            System.out.println("Delete successfully");
                        } else {
                            System.out.println("Try again!");
                        }
                        break;
                    case 7:
                        vehicleStorage.addVehiclesFromCSV("src\\main\\resources\\data.csv");
                        break;
                    case 8:
                        vehicleStorage.exportAllVehiclesToCSV("src\\main\\resources\\export.csv");
                        break;
                    case 9:
                        System.out.println("Enter your customer number (choose between 1 and 1000): ");
                        int cN = scan.nextInt();
                        System.out.println("Enter your name: ");
                        String name = scan.next();
                        System.out.println("Enter your name: ");
                        String address = scan.next();
                        System.out.println("Enter your name: ");
                        String contact = scan.next();
                        if (customerStorage.addCustomer(new Customer(cN, name, address, contact))){
                            System.out.println("You have been registered successfully");
                        } else {
                            System.out.println("This customer has existed already");
                        }
                        break;
                    case 0:
                        System.out.println("Bye!");
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Invalid Number");
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
