package repository;

import model.Manager;
import model.Spectator;
import utils.JdbcUtil;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UserRepository implements IUserRepository {

    private JdbcUtil jdbcUtil;
    private static final Logger LOGGER = LogManager.getLogger(User.class);

    public UserRepository(Properties properties){
        this.jdbcUtil = new JdbcUtil(properties);
    }

    @Override
    public List<User> getAll() {
        String selectString = "select * from users";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {

            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String type = resultSet.getString("type");
                    String theaterId = resultSet.getString("teatru");
                    if(type.equals("spectator")) {
                        Spectator spectator = new Spectator(password, theaterId);
                        spectator.setUsername(username);
                        users.add(spectator);
                    }
                    else {
                        Manager manager = new Manager(password, theaterId);
                        manager.setUsername(username);
                        users.add(manager);
                    }
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User create(User user) {
        String insertString = "insert into Users (username,password, type, teatru) values(?,?,?,?);";
        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(insertString)) {
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            if(user instanceof Manager)
                stm.setString(3, "manager");
            else
                stm.setString(3, "spectator");
            stm.setString(4, user.getTheaterId());
            stm.executeUpdate();
            LOGGER.debug("login - successful");
            return user;
        } catch (SQLException e) {
            LOGGER.debug("login - fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User entity, String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findOne(String username) {
        String findString = "select * from users where username=?;";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(findString)) {
            stm.setString(1, username);
            try (ResultSet result = stm.executeQuery()) {
                if (result.next()) {
                    String username_found = result.getString("username");
                    String password_found = result.getString("password");
                    String type_found = result.getString("type");
                    String theaterId = result.getString("teatru");
                    if(type_found.equals("manager")){
                        Manager manager = new Manager(password_found, theaterId);
                        manager.setUsername(username_found);
                        LOGGER.debug("user found");
                        return manager;
                    }
                    else{
                        Spectator spectator = new Spectator(password_found, theaterId);
                        spectator.setUsername(username_found);
                        LOGGER.debug("user found");
                        return spectator;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("user not found");
            e.printStackTrace();
        }
        return null;
    }
}