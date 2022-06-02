package stellarBurger.client;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersApiClient {

    private final String JSON = "application/json";
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private final String GET_ORDER_PATH = "/api/orders";

    public ValidatableResponse getOrders(String authToken){
        return given()
                .header("Authorization", authToken)
                .get(BASE_URL + GET_ORDER_PATH)
                .then();
    }


}
