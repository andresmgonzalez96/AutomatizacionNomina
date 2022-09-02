package com.loggro.api.cuentas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import utilities.ApiTestBase;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Tag("api")
public class CuentasApiTest extends ApiTestBase {

	public final static String ESTRCTURA_ID = "contenido.id";
	private final static String ESTRCTURA_CODIGO = "contenido.codigo";
	private final static String ESTRCTURA_NOMBRE = "contenido.nombre";
	private final static String ESTRCTURA_CODIGO_COMPLETO = "contenido.codigoCompleto";
	private final static String ESTRCTURA_TIPO_ELEMENTO_CONTABLE = "contenido.tipoElementoContable";
	private final static String ESTRCTURA_TIPO_CUENTA = "contenido.tipoCuenta";
	private final static String ESTRCTURA_ESTADO = "contenido.estado";


	/**
	 * Metodo que valida el codigo de la respuesta exitosa.
	 */
	@Test
	public void validarRespuestaExitosa() {
		Response response = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL); 
	  
	  assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
	}

	/**
	 * Test para validar el endpoint que devuelve una cuenta mayor
	 */	
	@Test
	public void validarCuentaMayor() {
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/523010"); 
		String json = res.asString();
		JsonPath jp = new JsonPath(json);

		assertEquals(HttpStatus.SC_OK, res.getStatusCode());
		assertEquals("523010", jp.get(ESTRCTURA_CODIGO));
		assertEquals("Seguros Cumplimiento Ventas", jp.get(ESTRCTURA_NOMBRE));
		assertEquals("5525230523010", jp.get(ESTRCTURA_CODIGO_COMPLETO));
		assertEquals("MAYOR", jp.get(ESTRCTURA_TIPO_ELEMENTO_CONTABLE));
		assertEquals("GASTO", jp.get(ESTRCTURA_TIPO_CUENTA));
		assertEquals("ACTIVO", jp.get(ESTRCTURA_ESTADO));

	}
	
	/**
	 * Test para validar el endpoint que devuelve la cuenta imputable por el codigo
	 */	
	@Test
	public void validarCuentaImputableCodigo() {
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/imputable/25050501");
		String json = res.asString();
		JsonPath jp = new JsonPath(json);

		assertEquals(HttpStatus.SC_OK, res.getStatusCode());
		assertEquals("25050501", jp.get(ESTRCTURA_CODIGO));
		assertEquals("Salarios por Pagar", jp.get(ESTRCTURA_NOMBRE));
		assertEquals("225250525050525050501", jp.get(ESTRCTURA_CODIGO_COMPLETO));
		assertEquals("IMPUTABLE", jp.get(ESTRCTURA_TIPO_ELEMENTO_CONTABLE));
		assertEquals("PASIVO_CORRIENTE", jp.get(ESTRCTURA_TIPO_CUENTA));
		assertEquals("ACTIVO", jp.get(ESTRCTURA_ESTADO));

	}
	
	
	
	/**
	 * Test para validar el endpoint que devuelve una cuenta imputable
	 */	
	@Test
	public void validarCuentaImputable() {
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/11050501");
		String json = res.asString();
		JsonPath jp = new JsonPath(json);

		assertEquals(HttpStatus.SC_OK, res.getStatusCode());
		assertEquals("11050501", jp.get(ESTRCTURA_CODIGO));
		assertEquals("Caja General", jp.get(ESTRCTURA_NOMBRE));
		assertEquals("111110511050511050501", jp.get(ESTRCTURA_CODIGO_COMPLETO));
		assertEquals("IMPUTABLE", jp.get(ESTRCTURA_TIPO_ELEMENTO_CONTABLE));
		assertEquals("ACTIVO_CORRIENTE", jp.get(ESTRCTURA_TIPO_CUENTA));
		assertEquals("ACTIVO", jp.get(ESTRCTURA_ESTADO));

	}
	
	/**
	 * Test para respuesta con cuenta inexistente.
	 */	
	@Test
	public void validarCuentaInvalida() {		
		String cuentaInvalida = "30303045";
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL + "/" + cuentaInvalida);
		String json = res.asString();
		JsonPath jp = new JsonPath(json);

		assertEquals(HttpStatus.SC_NOT_FOUND, res.getStatusCode());		
		assertEquals("[No existe Cuenta con identificador "+ cuentaInvalida + "]", jp.get("errores.mensaje").toString());

	}
	
	/**
	 * Test para validar seguridad del servicio exitoso
	 */	
	@Test
	public void validarPermisoExitosos() {		
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER)
			   	.get(URL);

		assertEquals(HttpStatus.SC_OK, res.getStatusCode());		

	}
	
	/**
	 * Test para validar seguridad del servicio con token invalido
	 */	
	@Test
	public void validarPermisoInvalido() {		
		Response res = RestAssured
			   	.given()
			   	.headers("Authorization",
			               BEARER+"78")
			   	.get(URL);

		assertEquals(HttpStatus.SC_UNAUTHORIZED, res.getStatusCode());		

	}

	
	@BeforeAll
	  public static void setup() {
		  auth();
		  URL = "/contabilidad/" + UUID_TENANT + "/v1/cuentas";
	  }

}
