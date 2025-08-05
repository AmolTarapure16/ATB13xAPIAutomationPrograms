package com.amoltarapure.sample.ex_00_Task.Lab_5_Aug_25;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RestfulBooker_Get_Post_Requests_UsingTestNG
{
    @Test
    public void Get() {
        //Get Ping Request
        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().log().all().get()
                .then().log().all().statusCode(201);
    }

    @Test
    public void Post() {
        Map<String, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");

        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", "Amol");
        booking.put("lastname", "Tarapure");
        booking.put("totalprice", "999");
        booking.put("depositpaid", "true");
        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds", "Breakfast");

        // Post Create Booking Request
        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking")
                .header("Content-Type", "application/json")
                .body(booking)
                .when().post()
                .then().log().all().statusCode(200);
    }
}
