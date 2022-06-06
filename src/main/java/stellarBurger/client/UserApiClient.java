package stellarBurger.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurger.model.User;
import stellarBurger.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserApiClient extends BaseSpec {

        private final String JSON = "application/json";
        private final String REGISTER_USER_PATH = "/api/auth/register";
        private final String AUTH_USER_PATH = "/api/auth/login";
        private final String DELETE_USER_PATH = "/api/auth/user";
        private final String EDIT_USER_PATH = "/api/auth/user";

        @Step ("Регистрация нового пользователя")
        public ValidatableResponse registerUser(User user){
                return given()
                        .spec(getBaseUri())
                        .header("Content-type", JSON)
                        .body(user)
                        .post(REGISTER_USER_PATH)
                        .then();
        }

        @Step("Авторизация пользователя")
        public ValidatableResponse loginUser(UserCredentials credentials){
                return given()
                        .spec(getBaseUri())
                        .header("Content-type", JSON)
                        .body(credentials)
                        .post(AUTH_USER_PATH)
                        .then();
        }

        @Step("Изменение данных пользователя")
        public ValidatableResponse editUser (User user, String authToken){
                return given()
                        .spec(getBaseUri())
                        .header("Authorization", authToken)
                        .body(user)
                        .patch(EDIT_USER_PATH)
                        .then();
        }

        @Step ("Удаление пользователя")
        public ValidatableResponse deleteUser (String authToken){
                return given()
                        .spec(getBaseUri())
                        .header("Authorization", authToken)
                        .delete(DELETE_USER_PATH)
                        .then();

        }

}