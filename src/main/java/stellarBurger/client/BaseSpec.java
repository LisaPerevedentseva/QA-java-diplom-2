package stellarBurger.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseSpec {

    public final String BASE_URL = "https://stellarburgers.nomoreparties.site";

     RequestSpecification getBaseUri(){
         return new RequestSpecBuilder().setBaseUri(BASE_URL).build();
     }

}
