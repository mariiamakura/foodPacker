package com.foody.service;

import com.foody.model.*;
import com.foody.repository.*;
import com.foody.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderIteamRepository orderIteamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Creates a new order based on the order request and associates it with the user.
     * Saves the order items from the user's cart and calculates the total price.
     *
     * @param order The order request containing order details
     * @param user  The user placing the order
     * @return The created Order object
     * @throws Exception If there are issues with saving addresses, finding restaurants, or calculating totals
     */
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);
        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setRestaurant(restaurant);
        createdOrder.setDeliveryAddress(savedAddress);

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderIteamRepository.save(orderItem);
            orderItems.add(savedOrderItem);

        }

        createdOrder.setItems(orderItems);
        Long  totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);
        return createdOrder;
    }

    /**
     * Updates the status of an order identified by orderId to the specified orderStatus.
     *
     * @param orderId     The ID of the order to update
     * @param orderStatus The new status of the order
     * @return The updated Order object
     * @throws Exception If the order with the given ID doesn't exist or the orderStatus is invalid
     */
    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select valid order status");
    }

    /**
     * Cancels an order identified by orderId.
     *
     * @param orderId The ID of the order to cancel
     * @throws Exception If the order with the given ID doesn't exist
     */
    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    /**
     * Retrieves all orders placed by a specific user identified by userId.
     *
     * @param userId The ID of the user whose orders to retrieve
     * @return List of orders placed by the user
     * @throws Exception If the user with the given ID doesn't exist
     */
    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {

        return orderRepository.findByCustomerId(userId);
    }

    /**
     * Retrieves all orders received by a specific restaurant identified by restaurantId.
     * Optionally filters by order status.
     *
     * @param restaurantId The ID of the restaurant whose orders to retrieve
     * @param orderStatus  Optional status to filter orders (can be null)
     * @return List of orders received by the restaurant
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    /**
     * Finds an order by its ID.
     *
     * @param orderId The ID of the order to find
     * @return The found Order object
     * @throws Exception If the order with the given ID doesn't exist
     */
    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isEmpty()) {
            throw new Exception("order not found");
        }
        return optOrder.get();
    }
}
