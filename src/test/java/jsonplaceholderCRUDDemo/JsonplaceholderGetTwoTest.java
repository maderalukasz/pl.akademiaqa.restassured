package jsonplaceholderCRUDDemo;

import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonplaceholderGetTwoTest {

    //GIVEN - konfiguracja
    //WHEN - wysłanie requesta
    //THEN - asercje

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name"); //chcemy poznać zawartość listy

        names.stream()
                .forEach(System.out::println); ///wypisujemy wszystkie nazwy userów

        Assertions.assertEquals(10, names.size());
        //System.out.println(response.asString());

    }

    @Test
    public void jsonplaceholderReadOneUser() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));  //po kropkach schodzimy w dół

        System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderReadOneUserWithPathVariable() {

        Response response = given()
                .pathParam("userId",1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));  //po kropkach schodzimy w dół

        System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderReadOneUsersWithQueryParams() {

        Response response = given()
                .queryParam("username","Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals(200, response.getStatusCode());

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0));
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));  //po kropkach schodzimy w dół

        System.out.println(response.asString());
    }
}
