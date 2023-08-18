package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.runner.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAPITheMovieDB {
    String baseUrl = "https://api.themoviedb.org";
    String endpoint(String endpoint){
        return baseUrl+endpoint;
    }
    String myToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5MTliYWNkZjMxN2EzZDVkYzIzNjFkYWYwMjg0MTU3MSIsInN1YiI6IjY0ZGI3MTFjNzcxOWQ3MDBjODYwNjA1ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YSidBNErlzZdT2S-nfgaMQXD9yj0DtMheM7IfVSoXHo";

    @Test
    public void testGetMovieNowPlaying(){
        given()
                .header("Authorization", "Bearer "+myToken)
                .when()
                .get(endpoint("/3/movie/now_playing"))
                .then()
                .statusCode(200)
                .body("results.id[1]",equalTo(667538));
    }

    @Test
    public void testGetMoviePopular(){
        given()
                .header("Authorization", "Bearer "+myToken)
                .when()
                .get(endpoint("/3/movie/popular"))
                .then()
                .statusCode(200)
                .body("results.id[2]",equalTo(667538));
    }

    @Test
    public void testPostMovieRating(){
        JSONObject request = new JSONObject();
        request.put("value",7.50);
        System.out.println(request.toJSONString());

        given()
                .header("Authorization", "Bearer "+myToken)
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post(endpoint("/3/movie/667538/rating"))
                .then()
                .statusCode(201)
                .log().all();
    }
}