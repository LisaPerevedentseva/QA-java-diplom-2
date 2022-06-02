import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.model.UserCredentials;
import stellarBurger.steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class AuthorizationUserTest {

    UserApiClient userApiClient = new UserApiClient();
    UserSteps userSteps = new UserSteps();
    User user;
    String authToken;

    // перед тестом создаем пользователя и получаем его AccessToken
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

    // успешная авторизация
    @Test
    @DisplayName("Авторизация с корректными кредами")
    public void successLoginTest(){
        ValidatableResponse response = userApiClient.loginUser(new UserCredentials(user.getEmail(), user.getPassword()));
        response.statusCode(HTTP_OK)
                .and().assertThat().body("success", equalTo(true));
    }

    // авторизация с некорректным паролем
    @Test
    @DisplayName("Авторизация с некорректным паролем")
    public void failLoginWithIncorrectPasswordTest(){
        user.setPassword(RandomStringUtils.randomAlphanumeric(6));
        ValidatableResponse response = userApiClient.loginUser(new UserCredentials(user.getEmail(), user.getPassword()));
        response.statusCode(HTTP_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }

    // авторизация с некорректным логином
    @Test
    @DisplayName("Авторизация с некорректным логином")
    public void failLoginWithIncorrectLoginTest(){
        user.setEmail(RandomStringUtils.randomAlphanumeric(3) + "@yandex.ru");
        ValidatableResponse response = userApiClient.loginUser(new UserCredentials(user.getEmail(), user.getPassword()));
        response.statusCode(HTTP_UNAUTHORIZED)
                .and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("email or password are incorrect"));
    }

}
