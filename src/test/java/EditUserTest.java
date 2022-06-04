import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.steps.UserSteps;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;


public class EditUserTest {

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
    @DisplayName("Успешное редактирование имени авторизованного пользователя")
    public void successEditNameTest(){
        User user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));
        ValidatableResponse response = userApiClient.editUser(new User(user.getEmail(), user.getPassword(), user.setName(RandomStringUtils.randomAlphabetic(3))), authToken);
        response.statusCode(HTTP_OK).and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Успешное редактирование email авторизованного пользователя")
    public void successEditEmailTest(){
        User user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));
        ValidatableResponse response = userApiClient.editUser(new User(user.setEmail(RandomStringUtils.randomAlphanumeric(3)+ "@yandex.ru"), user.getPassword(), user.getName()), authToken);
        response.statusCode(HTTP_OK).and().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Успешное редактирование пароля авторизованного пользователя")
    public void successEditPasswordTest(){
        User user = userSteps.createCorrectUser();
        authToken = userSteps.getAccessToken(userApiClient.registerUser(user));
        ValidatableResponse response = userApiClient.editUser(new User(user.getEmail(), user.setPassword(RandomStringUtils.randomAlphanumeric(6)), user.getName()), authToken);
        response.statusCode(HTTP_OK).and().assertThat().body("success", equalTo(true));
    }


    @Test
    @DisplayName("Невозможность редактирования имени неавторизованного пользователя")
    public void failEditNameTest(){
        User user = userSteps.createCorrectUser();
        ValidatableResponse response = userApiClient.editUser(new User(user.getEmail(), user.getPassword(), user.setName(RandomStringUtils.randomAlphabetic(3))), "");
        response.statusCode(HTTP_UNAUTHORIZED).and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Невозможность редактирования email неавторизованного пользователя")
    public void failEditEmailTest(){
        User user = userSteps.createCorrectUser();
        ValidatableResponse response = userApiClient.editUser(new User(user.setEmail(RandomStringUtils.randomAlphanumeric(3)+ "@yandex.ru"), user.getPassword(), user.getName()), "");
        response.statusCode(HTTP_UNAUTHORIZED).and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Невозможность редактирования пароля неавторизованного пользователя")
    public void failEditPasswordTest(){
        User user = userSteps.createCorrectUser();
        ValidatableResponse response = userApiClient.editUser(new User(user.getEmail(), user.setPassword(RandomStringUtils.randomAlphanumeric(6)), user.getName()), "");
        response.statusCode(HTTP_UNAUTHORIZED).and().assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo("You should be authorised"));
    }


}
