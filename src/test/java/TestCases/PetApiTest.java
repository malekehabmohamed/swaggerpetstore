package TestCases;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetApiTest {

    @BeforeSuite
    public void DTDsetUp() {
        // Set the JVM argument to explicitly load DTD from an HTTP URL
        System.setProperty("testng.dtd.http", "true");
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testGetPetById_1() {
        // Capture the response
        String response =
                given()
                        .pathParam("petId", 1)
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(1))
                        .extract().asString();

        // Print the response
        System.out.println("Response: " + response);
    }

    @Test
    public void testGetPetById_2() {
        // Capture the response
        String response =
                given()
                        .pathParam("petId", 2)
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(2))
                        .extract().asString();

        // Print the response
        System.out.println("Response: " + response);
    }

    @Test
    public void testGetPetById_3() {
        // Capture the response
        String response =
                given()
                        .pathParam("petId", 3)
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(3))
                        .extract().asString();

        // Print the response
        System.out.println("Response: " + response);
    }

    @Test
    public void testGetPetById_4() {
        // Capture the response
        String response =
                given()
                        .pathParam("petId", 4)
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(4))
                        .extract().asString();

        // Print the response
        System.out.println("Response: " + response);
    }

    @Test
    public void testGetPetById_5() {
        // Capture the response
        String response =
                given()
                        .pathParam("petId", 5)
                        .when()
                        .get("/pet/{petId}")
                        .then()
                        .statusCode(200)
                        .body("id", equalTo(5))
                        .extract().asString();

        // Print the response
        System.out.println("Response: " + response);
    }

}
