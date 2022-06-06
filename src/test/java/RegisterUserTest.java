import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserTest {

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
    @DisplayName("Регистрация нового пользователя")
    public void successRegisterUserTest(){

        ValidatableResponse response = userApiClient.registerUser(userSteps.createCorrectUser());
        response.statusCode(HTTP_OK)
                .and().assertThat().body("accessToken", notNullValue())
                .and().assertThat().body("refreshToken", notNullValue());
        // получение токена для удаления пользователя
        authToken = userSteps.getAccessToken(response);

    }

    @Test
    @DisplayName("Регистрация существующего пользователя")
    public void failRegisterExistingUserTest(){
        User user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));
        ValidatableResponse response = userApiClient.registerUser(user);
        response.statusCode(HTTP_FORBIDDEN).and().assertThat().body("message", equalTo("User already exists"));
    }



}
