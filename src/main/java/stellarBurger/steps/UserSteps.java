package stellarBurger.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import stellarBurger.model.User;

public class UserSteps {

    @Step("Создание корректного нового пользователя")
    public User createCorrectUser(){
        String name = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphanumeric(3) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(6);
        return new User(email, password, name);
    }


    @Step("Получение id созданного юзера")
    public String getAccessToken (ValidatableResponse response){
        String accessToken = response.extract().path("accessToken");
        return accessToken;
    }


}
