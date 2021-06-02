package repository;

import model.Show;
import model.Theater;

import java.util.List;

public interface IShowRepository extends ICrudRepository<Integer, Show>{
    List<Show> findByDate(String date);
    Theater getTheater();
}
