package com.petprojects.community.service;

import com.petprojects.community.entity.User;
import com.petprojects.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findUserByToken(String token) {
        User user = userRepository.findUserByToken(token);
//        return deletePassword(user);
        return user;
    }

    public User findUserByEmailAddressAndPassword(String emailAddress, String password) {
        User user = userRepository.findUserByEmailAddressAndPassword(emailAddress, password);
//        return deletePassword(user);
        return user;
    }

    @Transactional
    public void updateTokenByUser(String token, User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        User dbUser;
        if (userOptional.isPresent()) {
            dbUser = userOptional.get();
            dbUser.setToken(token);
            userRepository.saveAndFlush(dbUser);
        }
        // otherwise we can throw an Exception
    }

    private User deletePassword(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
}
