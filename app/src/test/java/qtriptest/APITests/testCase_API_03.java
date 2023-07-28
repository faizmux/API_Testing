package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class testCase_API_03 {
    private String email;

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
    }

    @Test(groups = {"API_Tests"}, priority = 3)
    public void TestCase_03() {
        // RestAssured.basePath = "api/v1/register";

        // JSONObject obj = new JSONObject();
        // String email = String.format("faiz%s@gmail.com", UUID.randomUUID());
        // String password = "Faiz7786";
        // String confirmpassword = "Faiz7786";

        // obj.put("email", email);
        // obj.put("password", password);
        // obj.put("confirmpassword", confirmpassword);


        // Response res = RestAssured.given().log().all().header("Content-Type", "application/json")
        //         .body(obj.toString()).when().post().then().log().all().extract().response();

        // System.out.println(res.getStatusCode());
        // // System.out.println(res.getStatusLine());

        // Assert.assertEquals(res.getStatusCode(), 201);
        // Assert.assertTrue(res.jsonPath().getBoolean("success"));
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
        RestAssured.basePath = "api/v1/login";


        JSONObject obj2 = new JSONObject();
        String email = "faiz7525@gmail.com";
        String password = "7786992490";

        obj2.put("email", email);
        obj2.put("password", password);

        RequestSpecification http = RestAssured.given().log().all()
                .header("Content-Type", "application/json").body(obj2.toString());

        Response res_login = http.post().then().log().all().extract().response();

        Assert.assertEquals(res_login.getStatusCode(), 201);

        String token = res_login.jsonPath().getString("data.token");
        String id = res_login.jsonPath().getString("data.id");

        String header_token = "Bearer " + token;

        RestAssured.basePath = "api/v1/reservations/new";

        JSONObject obj_reservation = new JSONObject();

        String userid = id;
        String name = "faiztest";
        String date = "2024-02-14";
        String person = "1";
        String adventure = "2447910730";

        obj_reservation.put("userId", userid);
        obj_reservation.put("name", name);
        obj_reservation.put("date", date);
        obj_reservation.put("person", person);
        obj_reservation.put("adventure", adventure);

        Response res2 = RestAssured.given().log().all().header("Authorization", header_token)
                // .queryParam("userId", userid)
                .header("Content-Type", "application/json").body(obj_reservation.toString()).when()
                .post().then().log().all().extract().response();

        Response res = RestAssured.given().log().all().header("Authorization", header_token)
        .queryParam("id", userid).when().get();

        JsonPath jp = new JsonPath(res2.body().asString());
        System.out.println(res2.getStatusCode());
        Assert.assertEquals(res2.getStatusCode(), 200);
        Assert.assertEquals(jp.getBoolean("success"), true);

        RestAssured.basePath = "api/v1/reservations";

        res = RestAssured.given().log().all().header("Authorization", header_token)
                .queryParam("id", userid).when().get();

        jp = new JsonPath(res.body().asString());
        int index = 0;
        List<String> list_date = jp.getList("date");
        for (int i = 0; i < list_date.size(); i++) {
            if (list_date.get(i).toString().equals(date)) {
                index = i;
                break;
            }
        }

        String path = "[" + index + "]." + "isCancelled";
        Assert.assertFalse(res.jsonPath().getBoolean(path));
    }
}
