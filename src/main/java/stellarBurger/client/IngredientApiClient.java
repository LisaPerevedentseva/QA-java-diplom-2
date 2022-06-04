package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientApiClient {

    private final String JSON = "application/json";
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private final String GET_INGREDIENT_PATH="/api/ingredients";

    @Step("Получение ингредиентов")
    public ValidatableResponse getIngredients(){
        return given()
                .header("Content-type", JSON)
                .get(BASE_URL+GET_INGREDIENT_PATH)
                .then();
    }

}