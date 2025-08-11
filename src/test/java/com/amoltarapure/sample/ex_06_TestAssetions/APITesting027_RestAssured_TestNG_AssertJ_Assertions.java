package com.amoltarapure.sample.ex_06_TestAssetions;
import static org.assertj.core.api.Assertions.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITesting027_RestAssured_TestNG_AssertJ_Assertions
{
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    String token;
    Integer bookingId;

    @Test
    public void test_POST()
    {

        String payload_POST = "{\n" +
                "    \"firstname\" : \"Amol\",\n" +
                "    \"lastname\" : \"Tarapure\",\n" +
                "    \"totalprice\" : 123,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2025-08-01\",\n" +
                "        \"checkout\" : \"2025-09-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";


        requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://restful-booker.herokuapp.com/");
        requestSpecification.basePath("/booking");
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payload_POST).log().all();

        Response response = requestSpecification.when().post();

        // Get Validate response to perform validation
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        // Rest Assured -> import org.hamcrest.Matchers; %4-%5
        // Matchers.equalto()

        validatableResponse.body("bookingid", Matchers.notNullValue());
        validatableResponse.body("booking.firstname", Matchers.equalTo("Amol"));
        validatableResponse.body("booking.lastname", Matchers.equalTo("Tarapure"));
        validatableResponse.body("booking.depositpaid", Matchers.equalTo(false));

        // Extraction
        // Concept #1 - Normal( TestNG or Assertj) IS IMP
        bookingId = response.then().extract().path("bookingid");
        String firstname = response.then().extract().path("booking.firstname");
        String lastname = response.then().extract().path("booking.lastname");

        // Concept #2 - (Jsonpath class) Another mechanism to extract the Keys, value by JSON Path
        JsonPath jp = new JsonPath(response.asString());
        String bookingID1 = jp.getString("bookingid");

        assertThat(jp.getInt("bookingid")).isEqualTo(732);
        assertThat(jp.getString("booking.firstname")).isEqualTo("Amol");
        assertThat(jp.getString("booking.lastname")).isEqualTo("Tarapure");
        assertThat(jp.getInt("booking.totalprice")).isEqualTo(3000);
        assertThat(jp.getBoolean("booking.depositpaid")).isTrue();


        // TestNG - Extract the details of the firstname, bookingId, lastname from Response.
        // TestNG Assertions - 75%
        // SoftAssert vs HardAssert (90%)
        // This means that if any assertion fails,
        // the remaining statements in that test method will not be executed.
        Assert.assertEquals(firstname,"Amol");
        Assert.assertEquals(lastname,"Tarapure");
        Assert.assertNotNull(bookingId);

        if(!firstname.contains("Amol"))
        {
            Assert.fail("Failed kar diya Test");
        }

        // AssertJ( 3rd- Lib to Assertions) - 20%

        assertThat(bookingId).isNotZero().isNotNull().isPositive();
        assertThat(firstname).isNotBlank().isNotEmpty().isNotNull().isEqualTo("Amol");

//      assertThat(deposit).isTrue();

    }
}
