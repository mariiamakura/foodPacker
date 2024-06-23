package com.foody.controller;

import com.foody.model.User;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint to retrieve user profile by JWT token.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the user profile and HTTP status
     * @throws Exception if an error occurs while fetching the user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<User> findByUsername(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
