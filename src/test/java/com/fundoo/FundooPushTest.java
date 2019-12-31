package com.fundoo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    public void givenUser_IfRedirectPost_Shouldreturn200StatusCode() throws ParseException {
        File TEST_FILE_PATH = new File("/home/admin142/Downloads/2019-12-12.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .multiPart("image",TEST_FILE_PATH)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NzU2OTksImV4cCI6MTU3Nzg2MjA5OX0.X9rcy7q4JTGGkN0CsfkqexeepzFhRUxSmtn-ybp7bOk")
                .formParam("title", "Democode")
                .formParam("description", "Demo  code image for tdd presentation")
                .formParam("redirect_link", "https://fundoopush-backend-dev.bridgelabz.com/api/#/user/login")
                .formParam("is_published",false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_link",false)
                .formParam("video_link",false)
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(201, statusCode);
        Assert.assertEquals("Redirect added Successfully",message);
    }

    @Test
    public void IfRedirectPost_IfTokenIncorect_Shouldreturn401StatusCode() throws ParseException {
        File TEST_FILE_PATH = new File("/home/admin142/Downloads/2019-12-12.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .multiPart("image",TEST_FILE_PATH)
                .header("token", "IsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NjczOTYsImV4cCI6MTU3Nzg1Mzc5Nn0.nYDtd9tVD2QCxkV5wEELXOa933jXPcILKIy4UsigHVc")
                .formParam("title", "Democode")
                .formParam("description", "Demo  code image for tdd presentation")
                .formParam("redirect_link", "https://fundoopush-backend-dev.bridgelabz.com/api/#/user/login")
                .formParam("is_published",false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_link",false)
                .formParam("video_link",false)
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(401, statusCode);
        Assert.assertEquals("Unauthorised Login",message);
    }

    @Test
    public void givenUser_IfRedirectPut_ShouldReturn200StatusCode() throws ParseException {
        File TEST_FILE_PATH = new File("/home/admin142/Downloads/2019-12-12.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .multiPart("image",TEST_FILE_PATH)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NzIzNTMsImV4cCI6MTU3Nzg1ODc1M30.yMzV-a5KW9QzG0IaljjEhg7lQ09Gq2ayXvgiHNKMick")
                .formParam("title", "Democode")
                .formParam("_id","5e0adda14d2267003253102e")
                .formParam("description","demo code for presenatation")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published",false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_link",false)
                .formParam("video_link",false)
                .when()
                .put("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(200, statusCode);
        Assert.assertEquals("Redirect updated Successfully",message);
    }

    @Test
    public void IfImageRedirectPut_IfIdIncorrect_ShouldReturnStatusCode() throws ParseException {
        File TEST_FILE_PATH = new File("/home/admin142/Downloads/2019-12-12.jpg");
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .multiPart("image",TEST_FILE_PATH)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NzIzNTMsImV4cCI6MTU3Nzg1ODc1M30.yMzV-a5KW9QzG0IaljjEhg7lQ09Gq2ayXvgiHNKMick")
                .formParam("title", "Democode")
                .formParam("_id","5e0adda14d226700")
                .formParam("description","demo code for presenatation")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published",false)
                .formParam("archive", false)
                .formParam("youtube_flag", false)
                .formParam("youtube_link",false)
                .formParam("video_link",false)
                .when()
                .put("https://fundoopush-backend-dev.bridgelabz.com/redirects");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode = response.getStatusCode();
        String message = (String) object.get("message");
        Assert.assertEquals(500, statusCode);
        Assert.assertEquals("Something went wrong",message);
    }

    @Test
    public void givenUser_IfRedirectGet_ShouldReturn200StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NzU2OTksImV4cCI6MTU3Nzg2MjA5OX0.X9rcy7q4JTGGkN0CsfkqexeepzFhRUxSmtn-ybp7bOk")
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects");
       ResponseBody body=response.getBody();
       JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
       int statusCode=response.getStatusCode();
       String message= (String) object.get("message");
       Assert.assertEquals(200,statusCode);
       Assert.assertEquals("All Redirects retrieved Successfully",message);
    }

    @Test
    public void givenUser_IfRedirectDeleteImage_ShouldReturnStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3NzY0NTMsImV4cCI6MTU3Nzg2Mjg1M30.8HNtHM-HsWoFWOCPhNv65sSymQhVyNZGM4vNi_WBYbQ")
                .body("{\"_id\":\"5c128ce5a8e1bc779f20b430\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects/delete");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Redirect deleted Successfully",message);
    }

}
