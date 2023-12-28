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

public class TagApiTest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String TAG_NAME = "TestTag";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreateTag() {
        // Given
        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("id", 0);
        requestBodyJson.put("name", TAG_NAME);

        // When
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBodyJson.toString())
                .post("/tag");

        // Then
        assertEquals(response.getStatusCode(),200 );
        assertEquals( response.jsonPath().getString("name"),TAG_NAME);
    }

    @Test(dependsOnMethods = "testCreateTag")
    public void testGetTagByName() {
        // When
        Response response = given()
                .pathParam("tagName", TAG_NAME)
                .get("/pet/tag/{tagName}");

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals(TAG_NAME, response.jsonPath().getString("name"));
    }

    @Test(dependsOnMethods = {"testCreateTag", "testGetTagByName"})
    public void testUpdateTag() {
        // Given
        JSONObject updateRequestBodyJson = new JSONObject();
        updateRequestBodyJson.put("id", 0);  // Assuming the tag ID to update
        updateRequestBodyJson.put("name", "UpdatedTag");

        // When
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("tagName", TAG_NAME)
                .body(updateRequestBodyJson.toString())
                .put("/pet/tag/{tagName}");

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("UpdatedTag", response.jsonPath().getString("name"));
    }

    @Test(dependsOnMethods = {"testCreateTag", "testUpdateTag"})
    public void testDeleteTagByName() {
        // When
        Response response = given()
                .pathParam("tagName", TAG_NAME)
                .delete("/pet/tag/{tagName}");

        // Then
        assertEquals(200, response.getStatusCode());

        // Verify that the tag has been deleted
        response = given()
                .pathParam("tagName", TAG_NAME)
                .get("/pet/tag/{tagName}");

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Tag not found"));
    }
}
