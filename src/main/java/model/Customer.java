package model;

public class Customer {
    private int customerNumber;
    private String name;
    private String address;
    private String contact;

    public Customer() {
    }

    public Customer(int customerNumber, String name, String address, String contact) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerNumber=" + customerNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
