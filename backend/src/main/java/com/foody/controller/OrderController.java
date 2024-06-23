package com.foody.controller;

import com.foody.model.CartItem;
import com.foody.model.Order;
import com.foody.model.User;
import com.foody.request.AddCartItemRequest;
import com.foody.request.OrderRequest;
import com.foody.service.OrderService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint to create a new order.
     *
     * @param req the request body containing the order details
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the created order and HTTP status
     * @throws Exception if an error occurs during order creation
     */
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve order history for a user.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing a list of user's orders and HTTP status
     * @throws Exception if an error occurs while fetching order history
     */
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



}
