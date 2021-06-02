package model;

public class Theater{
    private String name;
    private String address;

    public Theater(String address) {
        this.address = address;
    }

    public Theater(){};

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
}
