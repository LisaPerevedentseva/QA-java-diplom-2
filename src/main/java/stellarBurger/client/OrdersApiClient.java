package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurger.model.Ingredients;

import static io.restassured.RestAssured.given;

public class OrdersApiClient extends BaseSpec {

    private final String JSON = "application/json";
    private final String GET_ORDER_PATH = "/api/orders";
    public final String CREATE_ORDER_PATH = "/api/orders";


    @Step("Получение заказов конкретного пользователя")
    public ValidatableResponse getOrders(String authToken){
        return given()
                .spec(getBaseUri())
                .header("Authorization", authToken)
                .get(GET_ORDER_PATH)
                .then();
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String authToken, Ingredients ingredients){
        return  given()
                .spec(getBaseUri())
                .header("Authorization", authToken)
                .header("Content-type", JSON)
                .body(ingredients)
                .post(CREATE_ORDER_PATH)
                .then();
    }


}
