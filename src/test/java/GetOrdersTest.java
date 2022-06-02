import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import stellarBurger.client.OrdersApiClient;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {


    OrdersApiClient ordersApiClient = new OrdersApiClient();
    UserApiClient userApiClient = new UserApiClient();
    UserSteps userSteps = new UserSteps();
    String authToken;


    @After
    public void clearData () {
        if(authToken!=null){
            userApiClient.deleteUser(authToken);
        }
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    public void successGetOrdersTest(){
        User user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));
        ValidatableResponse response = ordersApiClient.getOrders(authToken);
        response.statusCode(HTTP_OK)
                .and().assertThat().body("success", equalTo(true));

    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя")
    public void failGetOrdersUnauthorizedUserTest(){
        ValidatableResponse response = ordersApiClient.getOrders("");
        response.statusCode(HTTP_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));

    }


}
