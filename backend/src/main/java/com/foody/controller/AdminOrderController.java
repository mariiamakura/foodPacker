package com.foody.controller;

import com.foody.model.Order;
import com.foody.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves the order history for a specific restaurant.
     *
     * @param id the ID of the restaurant
     * @param orderStatus optional parameter to filter orders by status
     * @return ResponseEntity containing a list of orders and HTTP status
     * @throws Exception if an error occurs while fetching orders
     */
    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false) String orderStatus) throws Exception {
        List<Order> orders = orderService.getRestaurantOrder(id, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Updates the status of a specific order.
     *
     * @param id the ID of the order to update
     * @param orderStatus the new status to set for the order
     * @return ResponseEntity containing the updated order and HTTP status
     * @throws Exception if an error occurs while updating the order status
     */
    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus) throws Exception {
       Order order = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
