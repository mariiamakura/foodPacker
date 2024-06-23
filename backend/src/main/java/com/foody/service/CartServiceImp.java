package com.foody.service;

import com.foody.model.Cart;
import com.foody.model.CartItem;
import com.foody.model.Food;
import com.foody.model.User;
import com.foody.repository.CartItemRepository;
import com.foody.repository.CartRepository;
import com.foody.repository.FoodRepository;
import com.foody.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    /**
     * Adds a new item to the user's cart or updates the quantity if the item is already present.
     *
     * @param req The request containing item details to add to the cart
     * @param jwt The JWT token for user authentication
     * @return The added or updated CartItem
     * @throws Exception if there are errors while adding or updating the item
     */
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    /**
     * Updates the quantity of a cart item.
     *
     * @param cartItemId The ID of the cart item to update
     * @param quantity   The new quantity of the cart item
     * @return The updated CartItem
     * @throws Exception if the cart item with the given ID is not found
     */
    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("cart item not found");
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    /**
     * Removes an item from the user's cart.
     *
     * @param cartItemId The ID of the cart item to remove
     * @param jwt        The JWT token for user authentication
     * @return The updated Cart after item removal
     * @throws Exception if the cart item with the given ID is not found
     */
    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("cart item not found");
        }
        CartItem item = cartItem.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    /**
     * Calculates the total cost of items in the cart.
     *
     * @param cart The cart for which to calculate the total
     * @return The total cost of items in the cart
     * @throws Exception if there are errors while calculating the total
     */
    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;
        for (CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    /**
     * Finds a cart by its ID.
     *
     * @param id The ID of the cart to find
     * @return The found Cart entity
     * @throws Exception if the cart with the given ID is not found
     */
    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new Exception("cart not found");
        }

        return optionalCart.get();
    }

    /**
     * Finds the user's cart by user ID.
     *
     * @param userId The ID of the user whose cart to find
     * @return The user's cart
     * @throws Exception if there are errors while finding the cart
     */
    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    /**
     * Clears the user's cart by removing all items.
     *
     * @param userId The ID of the user whose cart to clear
     * @return The cleared Cart entity
     * @throws Exception if there are errors while clearing the cart
     */
    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
