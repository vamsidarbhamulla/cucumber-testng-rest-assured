package com.bodhivruksha.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class UserValidationSteps {
	
	private Response response;
	private RequestSpecification request;
	private static final String BASE_PATH = "/users";
	
	@Given("^The endpoint is already configured$")
	public void the_endpoint_is_already_configured() {
		request = RestAssured.given();
	}
	
	@When("^I input a valid userId \"([^\"]*)\"$")
	public void i_input_a_valid_userId(String userId) {
		response = request.basePath(BASE_PATH).when().get("/" + userId);
		response.then().log().all();
	}

	@Then("^I should have the status code \"([^\"]*)\"$")
	public void i_should_have_the_status_code(String statusCode) {
		response.then().statusCode(Integer.parseInt(statusCode));
	}

	@Then("^content type should be in \"([^\"]*)\" format$")
	public void content_type_should_be_in_format(String format) {
		
		if(format.equals("JSON")){
			response.then().assertThat().contentType(ContentType.JSON)
					.and()
			.body(matchesJsonSchemaInClasspath("user-schema.json"));
		}
	}
	
	@Then("^the body response content should be matched$")
	public void the_body_response_content_should_be_matched(DataTable table) {

		List<List<String>> data = table.asLists();
		List<Map<String, String>> mapData =  table.asMaps();

		response.then().body("id", equalTo(Integer.parseInt(data.get(1).get(1))));

		for(int i = 2; i < data.size(); i++) {
			response.then().assertThat().body(data.get(i).get(0), equalTo(data.get(i).get(1)));
		}
	}
}
