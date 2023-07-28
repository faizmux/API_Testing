package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {

    @Test(groups = {"API_Tests"}, priority = 4)
    public void TestCase_04() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net/";
        RestAssured.basePath = "api/v1/register";

        JSONObject obj = new JSONObject();
        String email = String.format("faiz%s.crio.in", UUID.randomUUID());
        String password = "Faiz7786";
        String confirmpassword = "Faiz7786";

        obj.put("email", email);
        obj.put("password", password);
        obj.put("confirmpassword", confirmpassword);

        RequestSpecification http = RestAssured.given().log().all()
                .header("Content-Type", "application/json").body(obj.toString());

        Response res = http.when().post().then().log().all().extract().response();

        System.out.println(res.getStatusCode());

        res = http.when().post();

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertFalse(res.jsonPath().getBoolean("success"));
        Assert.assertEquals(res.jsonPath().getString("message"), "Email already exists");
    }

}


