package qtriptest.APITests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class testCase_API_02 {

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
        RestAssured.basePath = "api/v1/cities";
    }

    @Test(groups = {"API_Tests"}, priority = 2)
    public void TestCase_02() {
        // Response res = RestAssured.given().log().all().queryParam("q", "beng").when().get()
        //         .then().log().all().extract().response();
        // JSONArray arr = new JSONArray(res.body().asString());

        // JsonPath jp = new JsonPath(res.body().asString());

        // System.out.println(jp.getString("description"));

        // List<String> list = jp.getList("id");
        // System.out.println(arr.length());

        // Assert.assertEquals(list.size(), 1);
        // Assert.assertEquals(jp.getString("[0].description"), "100+ Places");

        RequestSpecification http = RestAssured.given().queryParam("cities", "beng");

        Response resp = http.request(Method.GET);

        int respCode = resp.getStatusCode();

        Assert.assertEquals(respCode, 200);

        File schemaFile = new File(
                "/home/crio-user/workspace/faizm77869-ME_API_TESTING_PROJECT/app/src/test/resources/schema.json");

        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchema(schemaFile);

        resp.then().assertThat().body(validator);
    }
}
