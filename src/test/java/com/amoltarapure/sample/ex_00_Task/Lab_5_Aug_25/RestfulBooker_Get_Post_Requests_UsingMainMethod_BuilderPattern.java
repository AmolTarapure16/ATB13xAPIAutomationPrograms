package com.amoltarapure.sample.ex_00_Task.Lab_5_Aug_25;

import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

public class RestfulBooker_Get_Post_Requests_UsingMainMethod_BuilderPattern
{
    public RestfulBooker_Get_Post_Requests_UsingMainMethod_BuilderPattern Get()
    {
        //Get Ping Request
        RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/ping")
                .when().log().all().get()
                .then().log().all().statusCode(201);
        return this;
    }

    public RestfulBooker_Get_Post_Requests_UsingMainMethod_BuilderPattern Post() {
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
        return this;
    }

    public static void main(String[] args) {
        RestfulBooker_Get_Post_Requests_UsingMainMethod_BuilderPattern gp = new RestfulBooker_Get_Post_Requests_UsingMainMethod_BuilderPattern();
        gp.Get().Post();
    }
}
