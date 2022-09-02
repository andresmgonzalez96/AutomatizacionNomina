package organizacion;


import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.ApiTestBase;




/**
 * Clase que valida los servicios de la Organizacion
 * @author agonzalezr
 *
 */
public class OrganizacionTest extends ApiTestBase {
   
  @Test
  public void getOrganizacion() {
	  Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL); 
	  
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	  
	  String responseString = response.getBody().asString();
	  assertEquals(JsonPath.from(responseString).get("contenido.id"), "80002020");
	  assertEquals(JsonPath.from(responseString).get("contenido.nombre"), "Viva Natural S.A");
	  assertEquals(JsonPath.from(responseString).get("contenido.nombreVisibleCompania"), "Viva Natural S.A");
	  assertEquals(JsonPath.from(responseString).get("contenido.onboarding").toString(), "52");
	  assertEquals(JsonPath.from(responseString).get("contenido.periodicidadPago").toString(), "MES");
	  
  }
  
  

  @Before
  public void setup() {
	  auth();
	  URL = "/nomina/"+ UUID_TENANT + "/v1/organizacion";
  }

 

}
