package gr.hua.agricoop.service;


import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.hua.agricoop.service.CooperativeService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            // ... other properties

            userRepository.save(existingUser);
        }
        return existingUser;
    }
    @Transactional
    public  List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Transactional
    public  void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public  User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public  List<User> getUserWithoutCooperative() {
        List<User> users = userRepository.findAll();
        users.removeIf(user -> user.getCooperative() != null);
        return users;
    }








    @Transactional
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}

