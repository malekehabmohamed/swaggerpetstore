# Swagger Petstore API Test Suite

This repository contains Java test cases for the Swagger Petstore API. The test cases cover CRUD operations for the User, Tag, and Order classes.

## Dependencies

- Java
- TestNG
- RestAssured
- JSON library (org.json)

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/malekehabmohamed/swaggerpetstore
   cd swaggerpetstore

2. To Run the test cases, you can directly run each case inside the soultion at path "swaggerpetstore\src\test\java\TestCases":

## Test Cases
   
1. User API
   - testCreateUser: Creates a user and verifies the successful creation.
   - testGetUserByUsername: Retrieves a user by username and verifies the correct data.
   - testUpdateUser: Updates user information and verifies the changes.
   - testDeleteUserByUsername: Deletes a user and verifies that the user is no longer accessible.

2. Tag API
   - testCreateTag: Creates a tag and verifies the successful creation.
   - testGetTagByName: Retrieves a tag by name and verifies the correct data.
   - testUpdateTag: Updates tag information and verifies the changes.
   - testDeleteTagByName: Deletes a tag and verifies that the tag is no longer accessible.
  
3. Order API
   - testCreateOrder: Creates an order and verifies the successful creation.
   - testGetOrderById: Retrieves an order by ID and verifies the correct data.
   - testUpdateOrder: Updates order information and verifies the changes.
   - testDeleteOrderById: Deletes an order and verifies that the order is no longer accessible.

Notes
The base URL for the Swagger Petstore API is set to https://petstore.swagger.io/v2.
Adjustments to the test cases may be required based on the Swagger Petstore API documentation.
