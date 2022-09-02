package utilities;


import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiTestBase {
	
	public static String  BEARER;
	public static String  UUID_TENANT;
	public static String  URL;
	public static String  CREDENCIALES = "{\"usuario\": \"agonzalezrtest@loggro.com\", \"pass\": \"423423\"}";
	

	@BeforeAll
	public static void configRequestSecurity() {
		RestAssured.baseURI = "https://apiprb.loggro.com";
		RestAssured.basePath = "/api";
		  
		  RestAssured.requestSpecification = new RequestSpecBuilder()
				  .setContentType(ContentType.JSON)
				  .build();
     
  }

  
  /**
	 * Metodo que realiza el login en la app
	 */
	public static void auth() {
		Response response = RestAssured
		   	.given()
		   	.body(CREDENCIALES)
		   	.post("/auth");
	   	
		BEARER = response.getHeaders().get("Authorization").toString();
		String[] testBearer = BEARER.split("=");
		BEARER = testBearer[1];
		
		String responseString = response.getBody().asString();
		UUID_TENANT = (String) JsonPath.from(responseString).getList("uuidTenant").get(1);
	   	
	}
	
}
