package service;

import model.Reservation;
import model.Show;
import model.User;
import repository.IReservationRepository;
import repository.IShowRepository;
import repository.IUserRepository;

import java.util.List;

public class MasterService {
    private IUserRepository userRepository;
    private IShowRepository showRepository;
    private IReservationRepository reservationRepository;

    public MasterService(IUserRepository userRepository, IShowRepository showRepository, IReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.reservationRepository = reservationRepository;
    }
    public User findUser(String username){
        return userRepository.findOne(username);
    }

    public List<User> getAllUsers(){
        return userRepository.getAll();
    }

    public List<Show> getAllShows(){
        return showRepository.getAll();
    }

    public List<Show> getShowsByDate(String date){
        return showRepository.findByDate(date);
    }

    public String getTheater() {
        return this.showRepository.getTheater().getName();
    }

    public User addUser(User user){
        return this.userRepository.create(user);
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.getAll();
    }

    public List<Reservation> findReservationByShow(Show show) {
        return reservationRepository.findByShow(show);
    }

    public Reservation addReservation(Reservation reservation){
        return this.reservationRepository.create(reservation);
    }

    public Reservation findReservationByRowSeat(int row, int seat, Show show) {
        return this.reservationRepository.findByRowSeat(row, seat, show);
    }

    public Show addShow(String name, int rows, int seats, double price, String date) {
        Show show = new Show(name, rows, seats, date, price, getTheater());
        return showRepository.create(show);
    }

    public Show deleteShow(Show selectedShow) {
        List<Reservation> reservations = this.findReservationByShow(selectedShow);
        for(int i = 0 ; i < reservations.size(); ++i)
            this.reservationRepository.delete(reservations.get(i).getId());
        Show show = showRepository.delete(selectedShow.getId());
        return show;
    }
}
