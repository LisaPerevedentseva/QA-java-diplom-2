package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientApiClient extends BaseSpec {

    private final String JSON = "application/json";
    private final String GET_INGREDIENT_PATH="/api/ingredients";

    @Step("Получение ингредиентов")
    public ValidatableResponse getIngredients(){
        return given()
                .spec(getBaseUri())
                .header("Content-type", JSON)
                .get(GET_INGREDIENT_PATH)
                .then();
    }

}