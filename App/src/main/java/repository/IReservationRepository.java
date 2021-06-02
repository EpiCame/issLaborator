package repository;

import model.Reservation;
import model.Show;

import java.util.List;

public interface IReservationRepository extends ICrudRepository<Integer, Reservation>{
    Reservation findByRowSeat(int row, int seat, Show show);
    List<Reservation> findByShow(Show show);
}
