package br.anderson.tasks.api.test;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://localhost:8010/tasks-backend";
	}
	
	@Test
	public void deveRetornarTasks() {
		
		RestAssured.given()
			.contentType(ContentType.JSON)
		.when()
			.get("/todo")
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTaskComSucesso() {
		
		RestAssured.given()
			.body("{ \"task\": \"Teste Via API\", \"dueDate\": \"2021-04-01\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveAddTaskInvalida() {
		
		RestAssured.given()
			.body("{ \"task\": \"Teste Via API\", \"dueDate\": \"2010-04-01\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
