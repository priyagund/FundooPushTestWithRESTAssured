package com.fundoo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
                .headers("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTQyNTMsImV4cCI6MTU3Nzg4MDY1M30.SUC2qa7lp6zrjHjXAHY-H5Yrq2Rd2LwAhq9oqSb19js")
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
                .headers("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTQyNTMsImV4cCI6MTU3Nzg4MDY1M30." +
                        "SUC2qa7lp6zrjHjXAHY-H5Yrq2Rd2LwAhq9oqSb19js")
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
    public void givenUser_IfRedirectDeleteImage_ShouldReturn200StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTI1NjksImV4cCI6MTU3Nzg3ODk2OX0.o7HbNfOKO2vcXAc-jVzwJbKvOGBQmGGIZ1YCig2y9bI")
                .body("{\"_id\":\"5e0adda14d2267003253102e\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/redirects/delete");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Redirect deleted Successfully",message);
    }

    @Test
    public void givenUser_IfRedirctRetriveData_ShouldReturn200StatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/bl-redirects");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("All Redirects retrieved Successfully",message);
    }

    @Test
    public void givenUser_IfHashTageEdit_ShouldReturn200SuccessCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3ODIxNDksImV4cCI6MTU3Nzg2ODU0OX0.VMKbMO5jzOOzSd9jqzaywfqjXdJmoh5e8Pq5VCW23VQ")
                .body("{\"redirect_id\":\"5e0adda14d2267003253102e\",\"hashtag\":\"#fundoopushapp\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/hashtag/edit");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Hashtag edit done Successfully",message);
    }

    @Test
    public void givenUser_IfGetRedirectHashtag_ShouldReturn200successCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3ODIxNDksImV4cCI6MTU3Nzg2ODU0OX0.VMKbMO5jzOOzSd9jqzaywfqjXdJmoh5e8Pq5VCW23VQ")
                .body("{\"hashtag\":\"#fundoopushapp\"}")
                .when()
                .get("https://fundoopush-backend-dev.bridgelabz.com/redirects/hashtag/%23fundoopushapp");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Hashtag sent Successfully",message);
    }

    @Test
    public void givenUser_IfPostScreapping_ShouldReturn200SuccessCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3ODIxNDksImV4cCI6MTU3Nzg2ODU0OX0.VMKbMO5jzOOzSd9jqzaywfqjXdJmoh5e8Pq5VCW23VQ")
                .body("{\"url\":\"https://www.deccanchronicle.com/technology/in-other-news/270319/companies-that-are-changing-the-way-education-is-being-delivered-to-st.html\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/web-scraping");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Successfully scrapped data",message);
    }

    @Test
    public void givenUser_IfSerarchashTag_ShouldReturn200SuccessCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTY3OTUsImV4cCI6MTU3Nzg4MzE5NX0.NE2NzvO7aE7AHOcIsoTfgB4Z1DX9t6hQ8FH2il7vCr4")
                .body("{\"hashtag\": \"#bridgelabz\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/search/hashtag");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Successfully searched data",message);
    }

    @Test
    public void givenUser_IfSearchHashTagNotFound_ShouldReturnSuccessCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTY3OTUsImV4cCI6MTU3Nzg4MzE5NX0.NE2NzvO7aE7AHOcIsoTfgB4Z1DX9t6hQ8FH2il7vCr4")
                .body("{\"hashtag\": \"\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/search/hashtag");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(400,statusCode);
        Assert.assertEquals("hashtag is required",message);

    }

    @Test
    public void givenUser_IfSerarchHashTagIfNotFound_ShouldReturnNullResult() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVlMDllMmUxNGQyMjY3MDAzMjUzMGZkOCJ9LCJpYXQiOjE1Nzc3OTY3OTUsImV4cCI6MTU3Nzg4MzE5NX0.NE2NzvO7aE7AHOcIsoTfgB4Z1DX9t6hQ8FH2il7vCr4")
                .body("{\"hashtag\": \"#abc\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/search/hashtag");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        String result= (String) object.get("result");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Successfully searched data",message);
        MatcherAssert.assertThat(result,Matchers.anything(null));
    }

    @Test
    public void givenUser_JobAdded_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVjODY1YTc0OWZkNTkxNWFjNjQwZDZhNyJ9LCJpYXQiOjE1Nzc3OTk3MTcsImV4cCI6MTU3Nzg4NjExN30.GTtAlZp8yG1IlQPgngHpGqWhYq0Azeku99THCQXqcPU")
                .body("{\"redirect_id\":\"5e0b4281fe754c00321c8631\",\"years_of_experience\":\"1\",\"salary\":\"3.6\",\"location\":\"Mumbai\",\"company_profile\":\"Ideation\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Jobs added successfully",message);
    }

    @Test
    public void givenUser_JobHashtagAdded_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVjODY1YTc0OWZkNTkxNWFjNjQwZDZhNyJ9LCJpYXQiOjE1Nzc3OTk3MTcsImV4cCI6MTU3Nzg4NjExN30.GTtAlZp8yG1IlQPgngHpGqWhYq0Azeku99THCQXqcPU")
                .body("{\"job_id\": \"5e0b506bf24f8605182dea46\",\"hashtag\": \"#bridgelabz\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/add");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Jobs hashtag added successfully",message);
    }

    @Test
    public void givenUser_JobHashtagRemove_ShouldReturnCorrectStatusCode() throws ParseException {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVjODY1YTc0OWZkNTkxNWFjNjQwZDZhNyJ9LCJpYXQiOjE1Nzc3OTk3MTcsImV4cCI6MTU3Nzg4NjExN30.GTtAlZp8yG1IlQPgngHpGqWhYq0Azeku99THCQXqcPU")
                .body("{ \"job_id\": \"5e0b506bf24f8605182dea46\",\"hashtag_id\": \"5d39926ab19c56004f263df6\"}")
                .when()
                .post("https://fundoopush-backend-dev.bridgelabz.com/jobs/hashtag/remove");
        ResponseBody body=response.getBody();
        JSONObject object= (JSONObject) new JSONParser().parse(body.prettyPrint());
        int statusCode=response.getStatusCode();
        String message= (String) object.get("message");
        Assert.assertEquals(200,statusCode);
        Assert.assertEquals("Jobs hashtag removed successfully",message);
    }

}
