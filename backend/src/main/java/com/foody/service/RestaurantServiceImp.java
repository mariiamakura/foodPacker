package com.foody.service;

import com.foody.dto.RestaurantDto;
import com.foody.model.Address;
import com.foody.model.Restaurant;
import com.foody.model.User;
import com.foody.repository.AddressRepository;
import com.foody.repository.RestaurantRepository;
import com.foody.repository.UserRepository;
import com.foody.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new restaurant based on the request and associates it with the owner user.
     *
     * @param req  The request containing restaurant details
     * @param user The owner user of the restaurant
     * @return The created Restaurant object
     */
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    /**
     * Updates an existing restaurant with new details.
     *
     * @param restaurantId      The ID of the restaurant to update
     * @param updateRestaurant  The updated details of the restaurant
     * @return The updated Restaurant object
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if (restaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if (restaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    /**
     * Deletes a restaurant identified by its ID.
     *
     * @param restaurantId The ID of the restaurant to delete
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    /**
     * Retrieves all restaurants stored in the database.
     *
     * @return List of all restaurants
     */
    @Override
    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    /**
     * Searches for restaurants based on a keyword.
     *
     * @param keyword The keyword to search for in restaurant names or descriptions
     * @return List of restaurants matching the search criteria
     */
    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    /**
     * Finds a restaurant by its ID.
     *
     * @param restaurantId The ID of the restaurant to find
     * @return The found Restaurant object
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(restaurantId);
        if (opt.isEmpty()) {
            throw new Exception("Restaurant not found with id: " + restaurantId);
        }
        return opt.get();
    }

    /**
     * Retrieves the restaurant associated with a specific user identified by userId.
     *
     * @param userId The ID of the user whose restaurant to retrieve
     * @return The Restaurant object associated with the user
     * @throws Exception If the user doesn't have a restaurant associated with them
     */
    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with user id: " + userId);
        }
        return restaurant;
    }

    /**
     * Adds or removes a restaurant to/from the user's list of favorite restaurants.
     *
     * @param restaurantId The ID of the restaurant to add or remove from favorites
     * @param user         The user performing the action
     * @return The updated RestaurantDto object representing the restaurant's simplified information
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);
        if (user.getFavorites().contains(dto)) {
            user.getFavorites().remove(dto);
        } else {
            user.getFavorites().add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    /**
     * Updates the open status of a restaurant identified by its ID.
     *
     * @param id The ID of the restaurant to update
     * @return The updated Restaurant object
     * @throws Exception If the restaurant with the given ID doesn't exist
     */
    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
