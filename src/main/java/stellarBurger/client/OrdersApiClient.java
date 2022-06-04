package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurger.model.Ingredients;

import static io.restassured.RestAssured.given;

public class OrdersApiClient {

    private final String JSON = "application/json";
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private final String GET_ORDER_PATH = "/api/orders";
    public final String CREATE_ORDER_PATH = "/api/orders";


    @Step("Получение заказов конкретного пользователя")
    public ValidatableResponse getOrders(String authToken){
        return given()
                .header("Authorization", authToken)
                .get(BASE_URL + GET_ORDER_PATH)
                .then();
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String authToken, Ingredients ingredients){
        return  given()
                .header("Authorization", authToken)
                .header("Content-type", JSON)
                .body(ingredients)
                .post(BASE_URL + CREATE_ORDER_PATH)
                .then();
    }


}
