package model;

import java.util.Date;

public class RentalDetail {
    private String startDate;
    private String endDate;
    private String brand;
    private String model;
    private int customerNumber;
    private String customerName;

    public RentalDetail() {
    }

    public RentalDetail(String startDate, String endDate, String brand, String model, int customerNumber, String customerName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.brand = brand;
        this.model = model;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "RentalDetail{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", customerNumber=" + customerNumber +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
