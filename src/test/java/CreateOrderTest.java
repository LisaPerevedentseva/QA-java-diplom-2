import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarBurger.client.OrdersApiClient;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.Ingredients;
import stellarBurger.model.User;
import stellarBurger.steps.IngredientSteps;
import stellarBurger.steps.UserSteps;

import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest {


    OrdersApiClient ordersApiClient = new OrdersApiClient();
    IngredientSteps ingredientSteps = new IngredientSteps();
    UserApiClient userApiClient = new UserApiClient();
    UserSteps userSteps = new UserSteps();


    Ingredients ingredients;
    String authToken;
    User user;

    @Before
    public void createUser(){
        user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));

    }

    @After
    public void clearData () {
        if(authToken!=null){
            userApiClient.deleteUser(authToken);
        }
    }


    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингридиентами")
    public void successCreateOrderAuthUserTest(){
    ingredients = new Ingredients(ingredientSteps.getListOfIngredient());
    ValidatableResponse response = ordersApiClient.createOrder(authToken, ingredients);
    response.statusCode(HTTP_OK).and().assertThat().body("success", equalTo(true));
    String orderNumber = response.extract().jsonPath().getString("order.number");
        Assert.assertNotNull(orderNumber);

    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без ингридиентов")
    public void failCreateOrderAuthUserTest(){
        ingredients = new Ingredients(new ArrayList<>());
        ValidatableResponse response = ordersApiClient.createOrder(authToken, ingredients);
        response.statusCode(HTTP_BAD_REQUEST).and().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and().assertThat().body("success", equalTo(false));
    }


    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void failCreateOrderNoAuthUserTest(){
        ingredients = new Ingredients(ingredientSteps.getListOfIngredient());
        ValidatableResponse response = ordersApiClient.createOrder("", ingredients);
        response.statusCode(HTTP_OK).and().assertThat().body("success", equalTo(true));
        String orderNumber = response.extract().jsonPath().getString("order.number");
        Assert.assertNotNull(orderNumber);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингридиента")
    public void failCreateOrderWithIncorrectIngredient(){
        ingredients = new Ingredients(List.of(RandomStringUtils.randomAlphabetic(3)));
        ValidatableResponse response = ordersApiClient.createOrder(authToken, ingredients);
        response.statusCode(HTTP_INTERNAL_ERROR);
    }

}
