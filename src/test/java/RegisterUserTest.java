import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import stellarBurger.client.UserApiClient;
import stellarBurger.model.User;
import stellarBurger.steps.UserSteps;

public class RegisterUserTest {

    UserApiClient userApiClient = new UserApiClient();
    UserSteps userSteps = new UserSteps();



    @Test
    public void successRegisterUserTest(){

        ValidatableResponse response = userApiClient.registerUser(userSteps.createCorrectUser());
        response.statusCode(200);

    }


}
