package auth;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import groovy.transform.stc.FromString;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ApiTestBase;




/**
 * Valida la seguridad del api.
 * @author agonzalez
 *
 */
public class ExampleTest extends ApiTestBase {

  @Test
  public void authTest() {
	  RestAssured
			   	.given()
			   	.body("{\r\n"
			   			+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
			   			+ "    \"password\": \"cityslicka\"\r\n"
			   			+ "}")
			   	.post("/login")
			   	.then()
			   	.statusCode(HttpStatus.SC_OK) //codigo de respuesta esperado
			   	.body("token", notNullValue());
	    
  }
  
  @Test
  public void getSingleUser() {
	  RestAssured
			   	.given()
			   	.get("/users/2")
			   	.then()
			   	.statusCode(HttpStatus.SC_OK) //codigo de respuesta esperado
	  			.body("data.id", equalTo(2));
  }
  
  @Test
  public void getAllUsers() {
	  Response response = RestAssured
			   	.given()
			   	.get("/users?page=2");
	  
	  Headers headers = response.getHeaders();
	  int statusCode = response.getStatusCode();
	  String body = response.getBody().asString();
	  
	  assertEquals(statusCode, HttpStatus.SC_OK);
	  headers.get("Content-Type"); //Acceder con llave valor a cualquier elemento

  }
  
  @Test
  public void deleteUser() {
	  RestAssured
			   	.given()
			   	.delete("/users/2")
			   	.then()
			   	.statusCode(HttpStatus.SC_NO_CONTENT); //codigo de respuesta esperado
	  			
  }
  
  @Test
  public void patchUserTest() {
	  String nameUpdate = RestAssured
						   	.given()
						   	.when()
						   	.body("{\r\n"
						   			+ "    \"name\": \"morpheus\",\r\n"
						   			+ "    \"job\": \"zion resident\"\r\n"
						   			+ "}")
						   	.patch("/users/2")
						   	.then()
						   	.statusCode(HttpStatus.SC_OK)
						   	.extract()
						   	.jsonPath().get("name");
	  assertThat(nameUpdate, equalTo("morpheus"));
	  			
  }
  
  @Test
  public void puthUserTest() {
	  String nameUpdate = RestAssured
						   	.given()
						   	.when()
						   	.body("{\r\n"
						   			+ "    \"name\": \"morpheus\",\r\n"
						   			+ "    \"job\": \"zion1 resident\"\r\n"
						   			+ "}")
						   	.patch("/users/2")
						   	.then()
						   	.statusCode(HttpStatus.SC_OK)
						   	.extract()
						   	.jsonPath().get("job");
	  assertThat(nameUpdate, equalTo("zion1 resident"));
	  			
  }
  
  
  @Test
  public void getAllUsersJson() {
	  String response = RestAssured
			   	.given()
			   	.when()
			   	.get("/users?page=2")
			   	.then()
			   	.extract()
			   	.body()
			   	.asString();

	  int totalPages = JsonPath.from(response).get("total_pages");
	  System.out.println(totalPages);
	  
	  List<String> user = JsonPath.from(response).getList("data");
	  System.out.println(user);
	  
	  int idUser0 = JsonPath.from(response).get("data[0].id");
	  System.out.println(idUser0);
  }

  @Before
  public void setup() {

	  
  }

 

}
