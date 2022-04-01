import model.RentalDetail;
import model.Vehicle;
import repositories.MySQLCustomerStorage;
import repositories.MySQLRentalDetailsStorage;
import repositories.MySQLVehicleStorage;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws SQLException {
        printMenu();
    }

    private static void printMenu() throws SQLException {
        MySQLCustomerStorage customerStorage = new MySQLCustomerStorage();
        Scanner scan = new Scanner(System.in);
        MySQLVehicleStorage vehicleStorage = new MySQLVehicleStorage();
        MySQLRentalDetailsStorage rentalDetailsStorage = new MySQLRentalDetailsStorage();
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
                System.out.println("0. Exit");
                System.out.println("--------------------------------------------------");
                System.out.print("Your option: ");
                int input = scan.nextInt();
                switch (input){
                    case 1:
                        List<Vehicle> vehicles = vehicleStorage.getAllVehicles();
                        for (Vehicle vehicle : vehicles){
                            System.out.println(vehicle + "\n");
                        }
                        break;
                    case 2:
                        System.out.println("Enter your start date (in format of YYYY-MM-DD): ");
                        String startDate = scan.next();
                        System.out.println("Enter your end date (in format of YYYY-MM-DD): ");
                        String endDate = scan.next();
                        System.out.println("Enter your vehicle brand: ");
                        String brand = scan.next();
                        System.out.println("Enter your vehicle model: ");
                        String model = scan.next();
                        System.out.println("Enter your customer number: ");
                        int customerNumber = scan.nextInt();
                        System.out.println("Enter your customer name: ");
                        String customerName = scan.next();
                        RentalDetail rentalDetail =  new RentalDetail(startDate, endDate, brand, model, customerNumber, customerName);
                        if (rentalDetailsStorage.rentCar(rentalDetail)){
                            System.out.println(customerName + " have rented the " + brand + " " + model + " from " + startDate + " to " + endDate);
                            System.out.println("Here is your receipt :");
                            System.out.println(rentalDetail);
                        } else {
                            System.out.println("Try again!");
                        }
                        break;
                    case 3:
                        System.out.println("Enter the customer number :");
                        int cn = scan.nextInt();
                        System.out.println("Enter the vehicle brand: ");
                        String b = scan.next();
                        System.out.println("Enter the vehicle model: ");
                        String m = scan.next();
                        RentalDetail rentalDetail1 = new RentalDetail();
                        rentalDetail1.setCustomerNumber(cn);
                        rentalDetail1.setBrand(b);
                        rentalDetail1.setModel(m);
                        if (rentalDetailsStorage.returnACar(rentalDetail1)){
                            System.out.println("You have succeed in returning this " + b + " " + m);
                        } else {
                            System.out.println("Try again!");
                        }
                        break;
                    case 4:
                        List<Vehicle> vehicles2 = vehicleStorage.getAllAvailableVehicles();
                        for (Vehicle vehicle1 : vehicles2){
                            System.out.println(vehicle1 + "\n");
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
                        vehicleStorage.exportAllVehiclesToCSV();
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
