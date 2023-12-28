package TestCases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserApiTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpassword";
    private static final String EMAIL = "testuser@example.com";


    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateUser() {
        // Given
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("id", 1);
        requestBodyJson.put("username", USERNAME);
        requestBodyJson.put("firstName", "John");
        requestBodyJson.put("lastName", "Doe");
        requestBodyJson.put("email", EMAIL);
        requestBodyJson.put("password", PASSWORD);
        requestBodyJson.put("phone", "1234567890");

        String requestBody = requestBodyJson.toString();

        // When
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/user");

        // Then
        assertEquals(response.getStatusCode(),200);
        System.out.println("Response Body: " + response.getBody().asString());

    }

    @Test
    public void testGetUserByUsername() {
        // When
        Response response = given()
                .pathParam("username", USERNAME)
                .get("/user/{username}");

        // Then
        assertEquals(response.getStatusCode(),200);
        assertEquals(response.jsonPath().getString("username"), USERNAME);
        assertEquals(response.jsonPath().getString("email"), EMAIL);
    }

    @Test
    public void testDeleteUserByUsername() {
        // Given
        testCreateUser(); // Create a user for deletion

        // When
        Response response = given()
                .pathParam("username", USERNAME)
                .delete("/user/{username}");

        // Then
        assertEquals(200, response.getStatusCode());

        // Verify that the user has been deleted
        response = given()
                .pathParam("username", USERNAME)
                .get("/user/{username}");

        assertEquals(response.getStatusCode(),404);
        assertTrue(response.getBody().asString().contains("User not found"));
    }

    @Test
    public void testUpdateUser() {
        // Given
        testCreateUser(); // Create the user to be updated

        // When
        JSONObject updateRequestBodyJson = new JSONObject();
        updateRequestBodyJson.put("id", 1);
        updateRequestBodyJson.put("username", USERNAME);
        updateRequestBodyJson.put("firstName", "UpdatedFirstName");
        updateRequestBodyJson.put("lastName", "UpdatedLastName");
        updateRequestBodyJson.put("email", "updatedemail@example.com");
        updateRequestBodyJson.put("password", PASSWORD);
        updateRequestBodyJson.put("phone", "9876543210");

        String updateRequestBody = updateRequestBodyJson.toString();

        Response updateResponse = given()
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .put("/user/{username}", USERNAME);

        // Then
        assertEquals(200, updateResponse.getStatusCode());

        // Optionally, you can print the response body
        System.out.println("Update Response Body: " + updateResponse.getBody().asString());
    }
}
