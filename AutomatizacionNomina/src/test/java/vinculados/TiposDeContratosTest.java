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
 * Clase que valida los servicios relacionados al tipo de contrato
 * @author agonzalezr
 *
 */
public class TiposDeContratosTest extends ApiTestBase {
   
  @Test
  public void getTiposDeContrato() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).getList("contenido.descripcion").get(0), "Término Indefinido");
	  assertEquals(JsonPath.from(responseString).getList("contenido.codigo").get(0), "TI");
	  assertEquals(JsonPath.from(responseString).getList("contenido.descripcion").get(1), "Término Fijo");
	  assertEquals(JsonPath.from(responseString).getList("contenido.codigo").get(1), "TF");
	  assertEquals(JsonPath.from(responseString).getList("contenido.descripcion").get(2), "Duración de Obra o Labor");
	  assertEquals(JsonPath.from(responseString).getList("contenido.codigo").get(2), "OB");
	  
  }
  
  

  @Before
  public void setup() {
	  auth();
	  URL = "/nomina/"+ UUID_TENANT + "/v1/vinculados/tiposContrato";
  }

 

}
