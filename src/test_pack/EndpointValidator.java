package test_pack;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public abstract	class EndpointValidator {
	
	private	EndpointName	endpointName;
	EndpointValidator(EndpointName	endpointName)
	{
		this.endpointName = endpointName;
	}
	public EndpointName getEndpointName() {
		return endpointName;
	}

	
	
	private	String	getFullPath(String appendix)
	{
		return this.getEndpointName().getUrl()+appendix;
	}
	
	
	public	Response	get(String appendix)
	{
		return RestAssured.given().when().get(getFullPath(appendix));
	}
	public	Response	post(Object obj)
	{
		return RestAssured.given().header("Content-type", "application/json")
				.and().body(obj)
				.when().post(getFullPath(""));
	}
	public	Response	put(String id,Object obj)
	{
		return RestAssured.given().header("Content-type", "application/json")
				.and().body(obj)
				.when().put(getFullPath("/"+id));
	}
	public	Response	patch(String id,Object obj)
	{
		return RestAssured.given().header("Content-type", "application/json")
				.and().body(obj)
				.when().patch(getFullPath("/"+id));
	}
	public	Response	delete(String	appendix)
	{
		return RestAssured.given().when().delete(getFullPath(appendix));
	}
	
	public	void	validateStatus(Response	response,String codeGroup)
	{
		int code = response.getStatusCode();
		//System.out.println(code);
		switch(codeGroup)
		{
		case "2XX":
			assertTrue(200 <= code && code <= 299, "Expected a success status code in the range 200-299, but got: " + code);
			break;
		case "4XX":
			assertTrue(400 <= code && code <= 499, "Expected a success status code in the range 400-499, but got: " + code);
			break;
		case "5XX":
			assertTrue(500 <= code && code <= 599, "Expected a success status code in the range 500-599, but got: " + code);
			break;
		}
	}
	
	public	void	validateSchema(Response response,SchemaName sn)
	{
		response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(sn.getJsonPath())));	
	}

	public	void	validateTimeout(Response response,int milliSeconds)
	{
		assertTrue(response.timeIn(TimeUnit.MILLISECONDS)<milliSeconds);
	}

	public	Object	fetchResponseData(Response response)
	{
		return	response.then().extract().as(Object.class);
	}

	
	

	
	
}
