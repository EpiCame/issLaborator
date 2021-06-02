package model;

public class Reservation{
    private int id;
    private Spectator spectator;
    private Show show;
    private int row;
    private int seat;

    public Reservation(){}

    public Reservation(Spectator spectator, Show show, int row, int seat) {
        this.spectator = spectator;
        this.show = show;
        this.row = row;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return id + "; " + show.getName() + "; " + spectator.getUsername();
    }
}
