package com.fundoo;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class FundooPushTest {

    @Test
    public void givenUser_IfLogin_ShouldReturn200StatusCode() throws ParseException {
        Response response = RestAssured.given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"priyankagund18@gmail.com\",\"password\":\"abc123\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body = response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Logged in Successfully", message);
    }

    @Test
    public void givenUser_IfEmailIdIsNull_ShouldReturn400StatusCode() throws ParseException {
        Response response = RestAssured.given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"\",\"password\":\"abc123\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(400, statusCode);
        Assert.assertEquals("email is required", message);

    }

    @Test
    public void givenUser_IfPasswordIsNull_ShouldReturn400StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"priyankagund18@gmail.com\",\"password\":\"\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(400, statusCode);
        Assert.assertEquals("password is required", message);

    }

    @Test
    public void givenUser_IfNotRegister_ShouldReturn404StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"khushi123@gmail.com\",\"password\":\"abc123\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/login");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(404, statusCode);
        Assert.assertEquals("Not a registered user", message);
    }

    @Test
    public void givenUser_IfRegistered_ShouldReturn200StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"yashu123@gmail.com\",\"password\":\"abc123\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals("register successfully", message);
    }

    @Test
    public void givenUser_IfAlreadyExit_ShouldReturn409StatusCode() throws ParseException {
        Response response = RestAssured.given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"priyankagund128@gmail.com\",\"password\":\"abc123\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/registration");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(409, statusCode);
        Assert.assertEquals("Email id already exists", message);
    }

}
