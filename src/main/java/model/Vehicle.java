package model;

public class Vehicle {
    private long code;
    private String brand;
    private String model;
    private int seats;
    private String licensePlate;
    private String status;
    private int customerNumber;

    public Vehicle() {
    }

    public Vehicle(long code, String brand, String model, int seats, String licensePlate, String status) {
        this.code = code;
        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.status = status;
    }

    public Vehicle(long code, String brand, String model, int seats, String licensePlate, String status, int customerNumber) {
        this.status = status;
        this.code = code;
        this.brand = brand;
        this.model = model;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.customerNumber = customerNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        return "Vehicle {" +
                "code =" + code +
                ", brand ='" + brand + '\'' +
                ", model ='" + model + '\'' +
                ", seats =" + seats +
                ", licensePlate ='" + licensePlate + '\'' +
                ", status ='" + status + '\'' +
                ", customerNumber =" + customerNumber +
                '}';
    }
}
