package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurger.model.User;

import static io.restassured.RestAssured.given;

public class UserApiClient {

        private final String JSON = "application/json";
        private final String BASE_URL = "https://stellarburgers.nomoreparties.site";
        private final String REGISTER_USER_PATH = "/api/auth/register";
        private final String AUTH_USER_PATH = "/api/auth/login";

        @Step ("Регистрация нового пользователя")
        public ValidatableResponse registerUser(User user){
                return given()
                        .header("Content-type", JSON)
                        .body(user)
                        .post(BASE_URL + REGISTER_USER_PATH)
                        .then();
        }

        }