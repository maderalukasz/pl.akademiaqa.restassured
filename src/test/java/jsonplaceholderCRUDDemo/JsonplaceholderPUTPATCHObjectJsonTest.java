package jsonplaceholderCRUDDemo;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class JsonplaceholderPUTPATCHObjectJsonTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    //PUT
    @Test
    public void jsonplaceholderUpdateUsersPUTTest() {

        JSONObject user = new JSONObject();
        user.put("name", "Lukasz Update Object PUT");
        user.put("username", "Lukasz Object PUT");
        user.put("email", "lukaszobjectput@example.com");
        user.put("phone","711222333");
        user.put("website","www.zwqa.pl");

        JSONObject geo = new JSONObject();
        geo.put("lat","-37.3159");
        geo.put("lng","81.1496"); //najpierw musimy utworzyć obiekt

        JSONObject address = new JSONObject();
        address.put("street","Kulas Light");
        address.put("suite","Apt. 556");
        address.put("city","Gwenborough");
        address.put("zipcode","92998-3874");
        address.put("geo", geo); //dodajemy utworzony wyzej obiekt geo bo to kolejne zagłębienie json'a
        //zeby go nastepnie dodac

        user.put("address", address);

        JSONObject company = new JSONObject();
        company.put("name","Romaguera-Crona");
        company.put("catchPhrase","Multi-layered client-server neural-net");
        company.put("bs","harness real-time e-markets");

        user.put("company", company);

        System.out.println(user.toString());

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Lukasz Update Object PUT", json.get("name"));
        assertEquals("Lukasz Object PUT", json.get("username"));
        assertEquals("lukaszobjectput@example.com", json.get("email"));
    }

    //PATCH
    @Test
    public void jsonplaceholderUpdateUsersPATCHTest() {

        JSONObject userDetails = new JSONObject();
        userDetails.put("email", "lukaszobjectpatch@example.com");

        JSONObject addressDetails = new JSONObject();
        addressDetails.put("city","Katowice");

        userDetails.put("address", addressDetails);

        System.out.println(userDetails.toString());

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .when()
                .patch(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("lukaszobjectpatch@example.com", json.get("email"));
        assertEquals("Katowice", json.get("address.city"));
    }
}
