package edu.eci.arsw.game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.game.model.User;
import edu.eci.arsw.game.persistence.user.UserRepository;

@Service("userService")
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByConfirmation(String confirmation) {
        return userRepository.findByConfirmation(confirmation);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
