package phase3Proj;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class JunitTest1 {


    public static final String BASE_URI = "https://reqres.in";

    final Logger LOGGER = LogManager.getLogger(JunitTest1.class.getSimpleName());  //com.org.JunitTest1


    @Before
    public void before(){
        PropertyConfigurator.configure("log4j.properties") ;
        LOGGER.info("Setting up base uri to :"+BASE_URI);
        RestAssured.baseURI= BASE_URI;
    }

    @Test
    public void getRequest() {
        String API_PATH = "/api/users";

        LOGGER.info("Sending request to " + API_PATH + "?page=2");

        try {
            //setup and executing request
            Response response = given()
                    .get(API_PATH + "?page=2")
                    .then()
                    .extract().response();

            JsonPath jp = response.jsonPath();

            LOGGER.debug("Total pages= " + jp.getString("total_pages"));
            LOGGER.debug("Total contents = " + jp.getString(""));

            Assert.assertEquals(200,response.statusCode());
            Assert.assertEquals(2, jp.getInt("page"));
            Assert.assertEquals("michael.lawson@reqres.in", jp.getString("data[0].email"));

            LOGGER.info("Request completed successfully");
        }catch(Exception e){
            // e.printStackTrace();
            LOGGER.error("Error executing get request:"+API_PATH+" :: " +e.getMessage());
        }
    }

    @Test
    public void postRequest() {
        String API_PATH = "/api/users";

        LOGGER.info("Sending request Create User to " + API_PATH);

        //setup and executing request

        String requestBody=" {\"name\": \"Ann\", \"job\": \"Analyst\"}  ";
        try{
            Response response = given()
                    .body(requestBody)
                    .post(API_PATH)
                    .then()
                    .extract().response();

            JsonPath jp = response.jsonPath();

            Assert.assertEquals(201,response.statusCode());

            LOGGER.debug("Total contents = " + jp.getString(""));
            LOGGER.info("Request completed successfully");
        }catch(Exception e){
            // e.printStackTrace();
            LOGGER.error("Error executing POST request:"+API_PATH+" :: " +e.getMessage());
        }


    }
}
