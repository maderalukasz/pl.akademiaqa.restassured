package jsonplaceholderCRUDDemo;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class JsonplaceholderPUTPATCHTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    //PUT
    @Test
    public void jsonplaceholderUpdateUsersPUTTest() {

                String jsonBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Lukasz Update PUT\",\n" +
                "  \"username\": \"LukaszPUT\",\n" +
                "  \"email\": \"lukaszPUT@example.com\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"Kulas Light\",\n" +
                "    \"suite\": \"Apt. 556\",\n" +
                "    \"city\": \"Gwenborough\",\n" +
                "    \"zipcode\": \"92998-3874\",\n" +
                "    \"geo\": {\n" +
                "      \"lat\": \"-37.3159\",\n" +
                "      \"lng\": \"81.1496\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"phone\": \"1-770-736-8031 x56442\",\n" +
                "  \"website\": \"hildegard.org\",\n" +
                "  \"company\": {\n" +
                "    \"name\": \"Romaguera-Crona\",\n" +
                "    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "    \"bs\": \"harness real-time e-markets\"\n" +
                "  }\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Lukasz Update PUT", json.get("name"));
        assertEquals("LukaszPUT", json.get("username"));
        assertEquals("lukaszPUT@example.com", json.get("email"));
    }

    //PATCH
    @Test
    public void jsonplaceholderUpdateUsersPATCHTest() {

        String jsonBody = "{\n" +
                "  \"email\": \"lukaszPATCH@example.com\"\n" +  //jeśli jest tylko jedno pole to nie powinno być przecinka - należy go usunąć
                "}";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .patch(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("lukaszPATCH@example.com", json.get("email"));
    }
}
