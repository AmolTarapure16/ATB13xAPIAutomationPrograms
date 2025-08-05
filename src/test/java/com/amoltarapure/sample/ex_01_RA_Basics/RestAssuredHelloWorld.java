package com.amoltarapure.sample.ex_01_RA_Basics;

import io.restassured.RestAssured;

public class RestAssuredHelloWorld
{
    public static void main(String[] args)
    {
        // https://restful-booker.herokuapp.com/ping

        RestAssured
                .given()
                    .baseUri("https://restful-booker.herokuapp.com")
                    .basePath("/ping")
                .when()
                    .log().all().get()
                .then()
                    .statusCode(201);



    }
}
