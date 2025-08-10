package com.amoltarapure.sample.ex_00_Task.Lab_8_Aug_25;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITesting_Restfulbooker_E2E_WithFullUpdate
{
    RequestSpecification r;
    Response response;
    ValidatableResponse vr;
    int bookingid;
    String token;

    @Test(priority = 0)
    public void test_GET_PingRequest() {
        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/ping");

        response = r.when().log().all().get();
        vr = response.then().log().all();
        vr.statusCode(201);
    }

    @Test(priority = 1)
    public void test_POST_CreateToken() {
        String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body(payload);

        response = r.when().log().all().post();
        vr = response.then().log().all();
        vr.statusCode(200);

        token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Test(priority = 2)
    public void test_POST_CreateBooking() {
        String payload = "{\n" +
                "    \"firstname\" : \"Narayana\",\n" +
                "    \"lastname\" : \"Dubbaka\",\n" +
                "    \"totalprice\" : 350,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2025-07-25\",\n" +
                "        \"checkout\" : \"2025-07-26\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(payload);

        response = r.when().log().all().post();
        vr = response.then().log().all();
        vr.statusCode(200);

        bookingid = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID: " + bookingid);
        Assert.assertTrue(bookingid > 0, "Booking ID should be greater than 0");
    }

    @Test(priority = 3, dependsOnMethods = {"test_POST_CreateBooking"}, alwaysRun = true)
    public void test_GET_BookingByID_Initial() {
        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingid);

        response = r.when().log().all().get();
        vr = response.then().log().all();
        vr.statusCode(200);
    }

    @Test(priority = 4, dependsOnMethods = {"test_POST_CreateToken", "test_POST_CreateBooking"}, alwaysRun = true)
    public void test_PUT_UpdateBooking() {
        String payload = "{\n" +
                "    \"firstname\" : \"NarayanaLipi\",\n" +
                "    \"lastname\" : \"Dubbaka\",\n" +
                "    \"totalprice\" : 640,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2025-07-21\",\n" +
                "        \"checkout\" : \"2025-07-29\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";

        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingid)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(payload).log().all();

        response = r.when().put();
        vr = response.then().log().all();
        vr.statusCode(200);
    }

    @Test(priority = 5, dependsOnMethods = {"test_PUT_UpdateBooking"}, alwaysRun = true)
    public void test_GET_BookingByID_AfterUpdate() {
        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingid);

        response = r.when().log().all().get();
        vr = response.then().log().all();
        vr.statusCode(200);
    }

    @Test(priority = 6, dependsOnMethods = {"test_POST_CreateToken", "test_POST_CreateBooking"}, alwaysRun = true)
    public void test_DELETE_Booking() {
        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingid)
                .contentType(ContentType.JSON)
                .cookie("token", token);

        response = r.when().log().all().delete();
        vr = response.then().log().all();
        vr.statusCode(201);
    }

    @Test(priority = 7, dependsOnMethods = {"test_DELETE_Booking"}, alwaysRun = true)
    public void test_GET_BookingByID_AfterDelete() {
        r = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingid);

        response = r.when().log().all().get();
        vr = response.then().log().all();
        vr.statusCode(404);
    }
}
