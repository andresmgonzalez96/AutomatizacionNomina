package vinculados;


import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ApiTestBase;




/**
 * Clase que valida los servicios de totales de empleado
 * @author agonzalezr
 *
 */
public class VinculadosTest extends ApiTestBase {
	
	String totalVinculados = "10";
	String totalDesvinculados = "0";
	String totalEnProceso = "0";
   
  @Test
  public void getTotalesEmpleados() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/totales"); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).get("contenido.vinculadoProceso").toString(), totalDesvinculados);
	  assertEquals(JsonPath.from(responseString).get("contenido.vinculado").toString(), totalVinculados);
	  assertEquals(JsonPath.from(responseString).get("contenido.desvinculado").toString(), totalEnProceso);
	  
  }
  
  @Test
  public void getAllVinculados() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).getList("contenido").size(), Integer.parseInt(totalVinculados));
	  assertEquals(JsonPath.from(responseString).get("total").toString(), totalVinculados);

	  
  }
  
  @Test
  public void getAllDesvinculados() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/desvinculados"); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).getList("contenido").size(), Integer.parseInt(totalDesvinculados));
	  assertEquals(JsonPath.from(responseString).get("total").toString(), totalDesvinculados);

	  
  }
  
  @Test
  public void getAllEnProceso() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/enproceso"); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).getList("contenido").size(), Integer.parseInt(totalEnProceso));
	  assertEquals(JsonPath.from(responseString).get("total").toString(), totalEnProceso);
	  
  }
  
  @Test
  public void getVinculadoPorUuid() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/enproceso"); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).getList("contenido").size(), Integer.parseInt(totalEnProceso));
	  assertEquals(JsonPath.from(responseString).get("total").toString(), totalEnProceso);
	  
  }
  
  

  @Before
  public void setup() {
	  auth();
	  URL = "/nomina/"+ UUID_TENANT + "/v1/vinculados";
  }

 

}
