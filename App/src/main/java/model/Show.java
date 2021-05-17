package model;

public class Show{
    private int id;
    private String name;
    private int nrRows;
    private int nrSeatsPerRow;
    private String date;
    private double price;
    private String theaterId;
    private int nrSeats;

    public Show(String name, int nrRows, int nrSeatsPerRow, String date, double price, String theater) {
        this.name = name;
        this.nrRows = nrRows;
        this.nrSeatsPerRow = nrSeatsPerRow;
        this.date = date;
        this.price = price;
        this.theaterId = theater;
    }

    public Show(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrSeats(){
        return this.nrRows * this.nrSeatsPerRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNrRows() {
        return nrRows;
    }

    public void setNrRows(int nrRows) {
        this.nrRows = nrRows;
    }

    public int getNrSeatsPerRow() {
        return nrSeatsPerRow;
    }

    public void setNrSeatsPerRow(int nrSeatsPerRow) {
        this.nrSeatsPerRow = nrSeatsPerRow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(String theaterId) {
        this.theaterId = theaterId;
    }
}
