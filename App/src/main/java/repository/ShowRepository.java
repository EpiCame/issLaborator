package repository;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ShowRepository implements IShowRepository{
    private JdbcUtil jdbcUtil;
    private static final Logger LOGGER = LogManager.getLogger(User.class);

    public ShowRepository(Properties properties){
        this.jdbcUtil = new JdbcUtil(properties);
    }

    @Override
    public Theater getTheater() {
        String selectString = "select * from teatre";
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("nume");
                    String address = resultSet.getString("adresa");
                    Theater theater = new Theater(address);
                    theater.setName(name);
                    return theater;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Show> getAll() {
        String selectString = "select * from spectacole";
        List<Show> shows = new ArrayList<>();
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int nrRows = resultSet.getInt("nrRows");
                    double price = resultSet.getDouble("pret");
                    int nrSeatsPerRow = resultSet.getInt("nrSeatsPerRow");
                    String date = resultSet.getString("data");
                    String theaterId = resultSet.getString("teatru");
                    Show show = new Show(name, nrRows, nrSeatsPerRow, date, price, theaterId);
                    show.setId(id);
                    shows.add(show);
                }
                return shows;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Show create(Show show) {
        String insertString = "insert into spectacole (name, nrRows,nrSeatsPerRow, data, pret, teatru) values(?,?,?,?,?,?);";
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(insertString)) {
            stm.setString(1, show.getName());
            stm.setInt(2, show.getNrRows());
            stm.setInt(3, show.getNrSeatsPerRow());
            stm.setString(4, show.getDate());
            stm.setDouble(5, show.getPrice());
            stm.setString(6, show.getTheaterId());
            stm.executeUpdate();
            LOGGER.debug("show creater");
            return show;
        } catch (SQLException e) {
            LOGGER.debug("show creation failed");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Show delete(Integer integer) {
        return null;
    }

    @Override
    public Show update(Show entity, Integer integer) {
        return null;
    }

    @Override
    public Show findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Show> findByDate(String date) {
        String selectString = "select * from spectacole where data = ?";
        List<Show> shows = new ArrayList<>();
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            stm.setString(1, date);
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int nrRows = resultSet.getInt("nrRows");
                    double price = resultSet.getDouble("pret");
                    int nrSeatsPerRow = resultSet.getInt("nrSeatsPerRow");
                    String theaterId = resultSet.getString("teatru");
                    Show show = new Show(name, nrRows, nrSeatsPerRow, date, price, theaterId);
                    show.setId(id);
                    shows.add(show);
                }
                return shows;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
