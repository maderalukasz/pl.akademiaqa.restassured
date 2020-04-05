package jsonplaceholderCRUDDemo;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class JsonplaceholderGETTwoTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    //GIVEN - konfiguracja
    //W HEN - wysłanie requesta
    //THEN - asercje

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name"); //chcemy poznać zawartość listy
        assertEquals(10, names.size());

        //Rekomendowane pisanie testów z użyciem extract
    }

    @Test
    public void jsonplaceholderReadOneUser() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));  //po kropkach schodzimy w dół

//        System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderReadOneUserWithPathVariable() {

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));  //po kropkach schodzimy w dół

//        System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderReadOneUsersWithQueryParams() {

        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));  //po kropkach schodzimy w dół

//        System.out.println(response.asString());
    }
}
