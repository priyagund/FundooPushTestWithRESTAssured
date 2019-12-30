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

import java.io.File;

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

    @Test
    public void givenUser_IfLoggedOutSuccessfully_ShouldReturn200StatusCode() throws ParseException {
        Response response= (Response) RestAssured.given().
                 contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .headers("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                        "eyJkYXRhIjp7Il9pZCI6IjVlMDk5ZGExNGQyMjY3MDAzMjUzMGY0MCJ9LCJpYXQiOjE1Nzc3MDcxMzcsImV4cCI6MTU3Nzc5MzUzN30" +
                        ".VjuBfDjqM9pmc7eWHCcYTscr_gcwo9UHSeL4suSpJVM")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
        ResponseBody body=response.getBody();
        JSONObject object = (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Logged out successfully from the system", message);

    }

    @Test
    public void givenUser_IfNotLoggedOut_ShouldReturn500StatusCode() throws ParseException {
        Response response=RestAssured.given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .headers("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJkYXRhI" +
                        "jp7Il9pZCI6IjVlMDk5ZGExNGQyMjY3MDAzMjUzMGY0MCJ9LCJpYXQiOjE1Nzc3MDcxMzcsImV4cCI6MTU3Nzc5MzUzN30")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/logout");
                ResponseBody body=response.getBody();
                JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(500, statusCode);
        Assert.assertEquals(null, message);
    }

    @Test
    public void givenUser_IfRedirect_ShouldreturnStatusCode()
    {
        File TEST_FILE_PATH = new File("/home/admin142/Downloads/2019-12-12.jpg");

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDk5ZGExNGQyMjY3MDAzMjUzMGY0MCJ9LCJpYXQiOjE1Nzc3MTEzNDAsImV4cCI6MTU3Nzc5Nzc0MH0.jjpfGS2PWUHu5vkyKkvr-SfvfBjck-RGRyir5AfRgH0")
                .formParam("image","/home/admin142/Downloads/2019-12-12.jpg")
                .formParam("title", "Democode")
                .formParam("description", "Demo  code image for tdd presentation")
                .formParam("redirect_link", "https://fundoopush-backend-dev.bridgelabz.com/api/#/user/login")
                .formParam("is_published",false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_link",false)
                .formParam("video_link",false)
                .multiPart(TEST_FILE_PATH)
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        int statusCode = response.getStatusCode();
        System.out.println("Redirect Successfull..." + statusCode);
        Assert.assertEquals(200, statusCode); // response.then().
    }
}
