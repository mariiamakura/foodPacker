package com.foody.service;

import com.foody.config.JwtProvider;
import com.foody.model.User;
import com.foody.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * Retrieves the user associated with the provided JWT token.
     *
     * @param jwt The JWT token containing user information
     * @return The User object associated with the JWT token
     * @throws Exception If no user is found with the email extracted from the JWT token
     */
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception();
        }
        return user;
    }

    /**
     * Retrieves the user associated with the provided email address.
     *
     * @param email The email address of the user to retrieve
     * @return The User object associated with the provided email address
     * @throws Exception If no user is found with the provided email address
     */
    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        return user;
    }
}
