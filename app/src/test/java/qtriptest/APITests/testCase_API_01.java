package qtriptest.APITests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.asynchttpclient.Request;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.UUID;



public class testCase_API_01 {

    private String email;

    // @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
    }

    @Test(groups = {"API_Tests"}, priority = 1)
    public void TestCase_01() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
        RestAssured.basePath = "api/v1/register";

        JSONObject obj = new JSONObject();
        String email = String.format("faiz%s.crio.in", UUID.randomUUID());
        String password = "Faiz7786";
        String confirmpassword = "Faiz7786";

        obj.put("email", email);
        obj.put("password", password);
        obj.put("confirmpassword", confirmpassword);


        Response res = RestAssured.given().log().all().header("Content-Type", "application/json")
                .body(obj.toString()).when().post().then().log().all().extract().response();

        System.out.println(res.getStatusCode());
        // System.out.println(res.getStatusLine());

        Assert.assertEquals(res.getStatusCode(), 201);
        Assert.assertTrue(res.jsonPath().getBoolean("success"));

        this.email = email;

        RestAssured.basePath = "api/v1/login";

        JSONObject obj2 = new JSONObject();
        String email2 = this.email;
        String password2 = "Faiz7786";

        obj2.put("email", email2);
        obj2.put("password", password2);

        RequestSpecification http = RestAssured.given().log().all()
                .header("Content-Type", "application/json").body(obj.toString());

        Response res2 = http.when().post().then().log().all().assertThat().statusCode(201).extract()
                .response();

        Assert.assertEquals(res2.getStatusCode(), 201);
        Assert.assertTrue(res2.jsonPath().getBoolean("success"));

        Assert.assertNotNull(res2.jsonPath().getString("data.token"));
        Assert.assertNotNull(res2.jsonPath().getString("data.id"));
    }

    /* 

    @Test(dependsOnMethods = {"register"})
    public void login() {
        RestAssured.basePath = "api/v1/login";

        JSONObject obj = new JSONObject();
        String email = this.email;
        String password = "Faiz7786";

        obj.put("email", email);
        obj.put("password", password);

        RequestSpecification http = RestAssured.given().log().all()
                .header("Content-Type", "application/json").body(obj.toString());

        Response res = http.when().post().then().log().all().assertThat().statusCode(201).extract()
                .response();

        Assert.assertEquals(res.getStatusCode(), 201);
        Assert.assertTrue(res.jsonPath().getBoolean("success"));

        Assert.assertNotNull(res.jsonPath().getString("data.token"));
        Assert.assertNotNull(res.jsonPath().getString("data.id"));

    }

    */
}
