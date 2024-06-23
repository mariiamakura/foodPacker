package com.foody.controller;

import com.foody.model.Cart;
import com.foody.model.CartItem;
import com.foody.model.User;
import com.foody.request.AddCartItemRequest;
import com.foody.request.UpdateCartIteamRequest;
import com.foody.service.CartService;
import com.foody.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint to add an item to the user's cart.
     *
     * @param req the request body containing item details to add
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the added cart item and HTTP status
     * @throws Exception if an error occurs during item addition
     */
    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addIteamToCart(@RequestBody AddCartItemRequest req,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    /**
     * Endpoint to update the quantity of a cart item.
     *
     * @param req the request body containing item ID and updated quantity
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the updated cart item and HTTP status
     * @throws Exception if an error occurs during item quantity update
     */
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody UpdateCartIteamRequest req,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    /**
     * Endpoint to remove a cart item by ID.
     *
     * @param id the ID of the cart item to remove
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the updated cart and HTTP status
     * @throws Exception if an error occurs during cart item removal
     */
    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartIteam(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart= cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Endpoint to clear all items from the user's cart.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the updated cart and HTTP status
     * @throws Exception if an error occurs during cart clearing
     */
    @DeleteMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart= cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve the user's cart.
     *
     * @param jwt the JWT token from the authorization header
     * @return ResponseEntity containing the user's cart and HTTP status
     * @throws Exception if an error occurs while fetching the cart
     */
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart= cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

}
