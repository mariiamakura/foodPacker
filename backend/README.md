## Requirements

- MySQL
- Java 17

## Build and run the projects

Before building add `.env` file to the root of this folder:
```
MYSQL_HOST_LOCAL=your_host
MYSQL_PORT=your_port
MYSQL_DATABASE=your_database
MYSQL_USERNAME=your_username
MYSQL_PASSWORD=your_password
```

Build:
```
./mvnw clean package
```

Run:
```
java -jar target/Online_Food_Orgering-0.0.1-SNAPSHOT.jar
```
## Endpoints

### Authentication Endpoints

User Signup
- URL: /auth/signup
- Method: POST
- Headers: Content-Type: application/json
- Description: Registers a new user, creates a cart for them, and returns a JWT for authentication.

***Once you singed up, save token you received and use it for other endpoints requests***

User Signin
- URL: /auth/signin
- Method: POST
- Headers: Content-Type: application/json
- Description: Authenticates an existing user and returns a JWT for authentication.



### User Endpoints

Get User Profile
- URL: /api/users/profile
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Fetches the profile information of the user based on the provided JWT token. 

### Restaurant Endpoints

Search Restaurants
- URL: /api/restaurants/search
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Query Parameters: keyword (String)
- Description: Searches for restaurants matching the given keyword.

Get All Restaurants
- URL: /api/restaurants
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves a list of all restaurants.

Get Restaurant by ID
- URL: /api/restaurants/{id}
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Path Parameters: id (Long)
- Description: Fetches details of a restaurant by its ID.

Add Restaurant to Favorites
- URL: /api/restaurants/{id}/add-favorites
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Path Parameters: id (Long)
- Description: Adds a restaurant to the user's favorites list.

### Order Endpoints

Create Order
- URL: /api/order
- Method: POST
- Headers: Authorization: Bearer <jwt>
- Body: OrderRequest (JSON)
- Description: Creates a new order based on the provided order request.

Get User Order History
- URL: /api/order/user
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves the order history for the authenticated user.

### Ingredient Endpoints

Create Ingredient Category
- URL: /api/admin/ingredients/category
- Method: POST
- Headers: Content-Type: application/json
- Description: Creates a new ingredient category for a restaurant.

Create Ingredient Item
- URL: /api/admin/ingredients
- Method: POST
- Headers: Content-Type: application/json
- Description: Creates a new ingredient item for a restaurant. 

Update Ingredient Stock
- URL: /api/admin/ingredients/{id}/stock
- Method: PUT
- Headers: Content-Type: application/json
- Description: Updates the stock for a specific ingredient item.

Get Restaurant Ingredients
- URL: /api/admin/ingredients/restaurant/{id}
- Method: GET
- Headers: Content-Type: application/json
- Description: Retrieves all ingredient items for a specific restaurant.

Get Restaurant Ingredient Categories
- URL: /api/admin/ingredients/restaurant/{id}/category
- Method: GET
- Headers: Content-Type: application/json
- Description: Retrieves all ingredient categories for a specific restaurant.

### Food Endpoints

Search food
- URL: /api/food/search
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Searches for food items by name.

Get Restaurant Food
- URL: /api/food/restaurant/{restaurantId}
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves food items from a specific restaurant based on various filters such as vegetarian, seasonal, non-vegetarian, and optional food category.

### Category Endpoints

Create Category
- URL: /api/admin/category
- Method: POST
- Headers: Authorization: Bearer <jwt>
- Description: Creates a new category for a restaurant by an authenticated admin user.

Get Restaurant Categories
- URL: /api/category/restaurant
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves all categories for the authenticated user's restaurant.

### Cart Endpoints

Add Item to Cart
- URL: /api/cart/add
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Description: Adds an item to the authenticated user's cart.

Update Cart Item Quantity
- URL: /api/cart-item/update
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Description: Updates the quantity of a specific item in the authenticated user's cart.

Remove Cart Item
- URL: /api/cart-item/{id}/remove
- Method: DELETE
- Headers: Authorization: Bearer <jwt>
- Description: Removes a specific item from the authenticated user's cart.

Clear Cart
- URL: /api/cart/clear
- Method: DELETE
- Headers: Authorization: Bearer <jwt>
- Description: Clears all items from the authenticated user's cart.

Find User Cart
- URL: /api/cart
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves the cart for the authenticated user.

### Admin Restaurant Endpoint

Create Restaurant
- URL: /api/admin/restaurants
- Method: POST
- Headers: Authorization: Bearer <jwt>
- Description: Creates a new restaurant for the authenticated admin user.

Update Restaurant
- URL: /api/admin/restaurants/{id}
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Description: Updates the details of a specific restaurant for the authenticated admin user.

Delete Restaurant
- URL: /api/admin/restaurants/{id}
- Method: DELETE
- Headers: Authorization: Bearer <jwt>
- Description: Deletes a specific restaurant for the authenticated admin user.

Update Restaurant Status
- URL: /api/admin/restaurants/{id}/status
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Description: Updates the status of a specific restaurant for the authenticated admin user.

Find Restaurant by User ID
- URL: /api/admin/restaurants/user
- Method: GET
- Headers: Authorization: Bearer <jwt>
- Description: Retrieves the restaurant associated with the authenticated admin user.

### Admin Order Endpoints

Get Order History
- URL: /api/admin/order/restaurant/{id}
- Method: GET
- Headers: Content-Type: application/json
- Description: Retrieves the order history for a specific restaurant. Optional query parameter orderStatus can be used to filter orders by status.

Update Order Status
- URL: /api/admin/order/{id}/{orderStatus}
- Method: PUT
- Headers: Content-Type: application/json
- Description: Updates the status of a specific order for a restaurant.

### Admin Food Endpoints
Create Food
- URL: api/admin/food
- Method: POST
- Headers: Authorization: Bearer <jwt>
- Description: Creates a new food item for a specific restaurant.

Delete Food
- URL: api/admin/food/{id}
- Method: DELETE
- Headers: Authorization: Bearer <jwt>
- Description: Deletes a specific food item.

Update Food Availability Status
- URL: api/admin/food/{id}
- Method: PUT
- Headers: Authorization: Bearer <jwt>
- Description: Updates the availability status of a specific food item.