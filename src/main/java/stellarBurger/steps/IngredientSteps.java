package stellarBurger.steps;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import stellarBurger.client.IngredientApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientSteps {

    IngredientApiClient ingredientApiClient = new IngredientApiClient();
    private final String PATH_TO_INGREDIENT_ID = "data._id";

    @Step("Получение хэша рандомного ингредиента")
    public String getHashOfIngredient() {
        List<String> ingredients = ingredientApiClient.getIngredients()
                .extract().jsonPath().getList(PATH_TO_INGREDIENT_ID);
        return ingredients.get(RandomUtils.nextInt(0, ingredients.size()));
    }

    @Step("Получение списка из одного рандомного ингредиента")
    public List<String> getListOfIngredient(){
        List<String> ingredients = new ArrayList<>(Arrays.asList(getHashOfIngredient()));
        return ingredients;
    }

}