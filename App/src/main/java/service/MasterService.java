package service;

import model.Show;
import model.User;
import repository.IShowRepository;
import repository.IUserRepository;

import java.util.List;

public class MasterService {
    private IUserRepository userRepository;
    private IShowRepository showRepository;

    public MasterService(IUserRepository userRepository, IShowRepository showRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
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
}
