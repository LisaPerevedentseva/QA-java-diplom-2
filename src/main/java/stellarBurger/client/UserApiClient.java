package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurger.model.User;
import stellarBurger.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserApiClient {

        private final String JSON = "application/json";
        private final String BASE_URL = "https://stellarburgers.nomoreparties.site";
        private final String REGISTER_USER_PATH = "/api/auth/register";
        private final String AUTH_USER_PATH = "/api/auth/login";
        private final String DELETE_USER_PATH = "/api/auth/user";

        @Step ("Регистрация нового пользователя")
        public ValidatableResponse registerUser(User user){
                return given()
                        .header("Content-type", JSON)
                        .body(user)
                        .post(BASE_URL + REGISTER_USER_PATH)
                        .then();
        }

        @Step("Авторизация пользователя")
        public ValidatableResponse loginUser(UserCredentials credentials){
                return given()
                        .header("Content-type", JSON)
                        .body(credentials)
                        .post(BASE_URL + AUTH_USER_PATH)
                        .then();
        }

        @Step ("Удаление пользователя")
        public ValidatableResponse deleteUser (String authToken){
                return given()
                        .header("Authorization", authToken)
                        .delete(BASE_URL + DELETE_USER_PATH)
                        .then();

        }

}