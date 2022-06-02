import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.steps.UserSteps;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class ParameterizedRegisterUserTest {

    UserApiClient userApiClient = new UserApiClient();
    UserSteps userSteps = new UserSteps();

   private final User user;
   private final int expectedStatusCode;
   private final String expectedMessage;

   public ParameterizedRegisterUserTest(User user, int expectedStatusCode, String expectedMessage){
       this.user = user;
       this.expectedStatusCode = expectedStatusCode;
       this.expectedMessage = expectedMessage;
   }

   @Parameterized.Parameters
   public static Object[] getData() {
       return new Object[][]{
               {new User("", RandomStringUtils.randomAlphanumeric(6), RandomStringUtils.randomAlphabetic(3)), HTTP_FORBIDDEN, "Email, password and name are required fields"},
               {new User(RandomStringUtils.randomAlphanumeric(3) + "@yandex.ru", "", RandomStringUtils.randomAlphabetic(3)), HTTP_FORBIDDEN, "Email, password and name are required fields"},
               {new User(RandomStringUtils.randomAlphanumeric(3) + "@yandex.ru", RandomStringUtils.randomAlphanumeric(6), ""), HTTP_FORBIDDEN, "Email, password and name are required fields"}

       };

   }
       @Test
       @DisplayName("Регистрация с незаполненным обязательным полем")
       public void failRegisterWithoutFieldTest(){
           ValidatableResponse response = userApiClient.registerUser(user);
           response.statusCode(expectedStatusCode)
                   .and().assertThat().body("message", equalTo(expectedMessage));
   }

}
