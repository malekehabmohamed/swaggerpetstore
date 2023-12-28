package TestCases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrderApiTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final int PET_ID = 1;
    private static final int QUANTITY = 1;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateOrder() {
        // Given
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("id", 0);
        requestBodyJson.put("petId", PET_ID);
        requestBodyJson.put("quantity", QUANTITY);
        requestBodyJson.put("shipDate", "2023-12-31T23:59:59.999Z");
        requestBodyJson.put("status", "placed");
        requestBodyJson.put("complete", true);

        // When
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBodyJson.toString())
                .post("/store/order");

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals(response.jsonPath().getInt("petId"),PET_ID);
        assertEquals(response.jsonPath().getInt("quantity"),QUANTITY);
    }

    @Test(dependsOnMethods = "testCreateOrder")
    public void testGetOrderById() {
        // When
        Response response = given()
                .pathParam("orderId", 1)  // Assuming the order ID to get
                .get("/store/order/{orderId}");

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals( response.jsonPath().getInt("petId"), PET_ID);
        assertEquals(response.jsonPath().getInt("quantity"), QUANTITY);
    }

    @Test(dependsOnMethods = {"testCreateOrder", "testGetOrderById"})
    public void testUpdateOrder() {
        // Given
        JSONObject updateRequestBodyJson = new JSONObject();
        updateRequestBodyJson.put("id", 1);  // Assuming the order ID to update
        updateRequestBodyJson.put("petId", PET_ID);
        updateRequestBodyJson.put("quantity", QUANTITY + 1);
        updateRequestBodyJson.put("shipDate", "2023-12-31T23:59:59.999Z");
        updateRequestBodyJson.put("status", "approved");
        updateRequestBodyJson.put("complete", true);

        // When
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("orderId", 1)  // Assuming the order ID to update
                .body(updateRequestBodyJson.toString())
                .put("/store/order/{orderId}");

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals(response.jsonPath().getInt("quantity"),QUANTITY + 1);
        assertEquals(response.jsonPath().getString("status"),"approved");
    }

    @Test(dependsOnMethods = {"testCreateOrder", "testUpdateOrder"})
    public void testDeleteOrderById() {
        // When
        Response response = given()
                .pathParam("orderId", 1)  // Assuming the order ID to delete
                .delete("/store/order/{orderId}");

        // Then
        assertEquals(200, response.getStatusCode());

        // Verify that the order has been deleted
        response = given()
                .pathParam("orderId", 1)  // Assuming the order ID to get
                .get("/store/order/{orderId}");

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Order not found"));
    }
}

