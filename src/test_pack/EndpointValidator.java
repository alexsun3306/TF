package test_pack;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.io.File;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

import org.apache.http.util.Asserts;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EndpointValidator {


	public	static	final String COMMON_URL = "https://jsonplaceholder.typicode.com";
	public	EndpointName	epn = null;
	public	EndpointData	epd = null;
	public	Object[][]		dp = null;


	public Object[][] DP() {return dp;}
	
	
	public	EndpointValidator()
	{
		this.epn = EndpointName.EP1;
		this.init();
	}
	
	
	public	void	init()
	{
		Object[]	config = this.load_config(this.epn);
		this.epd = (EndpointData) config[0];
		this.dp	= (Object[][]) config[1];
	}
	
	public	Object[]	load_config(EndpointName	epn)
	{
		Object[][]	data_provider = null;
		EnumMap<EndpointName, EndpointData> map = Config.LoadEndpointMap();
		EndpointData	epd = map.get(epn);
		try (Reader reader = Files.newBufferedReader(Paths.get(".\\res\\",epd.getDpPath()))) 
		{
			Gson gson = new Gson();
			Type listType = new TypeToken<List<List<Object>>>() {}.getType();
			List<List<Object>> listOfLists = gson.fromJson(reader, listType);
			
			data_provider = new Object[listOfLists.size()][];
	        for (int i = 0; i < listOfLists.size(); i++) {
	            List<Object> sublist = listOfLists.get(i);
	            data_provider[i] = sublist.toArray(new Object[sublist.size()]);
	        }
	        return	new	Object[]{epd,data_provider};
		}
		catch (IOException e) {
		    e.printStackTrace();
		    return new Object[] {null,null};
		}
	}
	
	  public	Response	call(String url)
	  {
		  String method = this.epd.getMethod();
		  switch(method)
		  {
		  	case "get":
		  		return RestAssured.given().when().get(url);
		  	case "post":
		  		return RestAssured.given().when().post(url);
		  	case "put":
		  		return RestAssured.given().when().put(url);
		  	case "patch":
		  		return RestAssured.given().when().patch(url);
		  	case "delete":
		  		return RestAssured.given().when().delete(url);
		  	default:
		  		return null;
		  }
	  }
	
	
	public	void	validateEp(String s1, String s2,String s3)
	{
		if(s1.equals("positive"))
		{validatePositive(s2,s3);}
		else if (s1.equals("negative"))
		{validateNegative(s2,s3);}
	}
	
	
	
	public	Response	validatePositive(String s2,String s3)
	{
		String url = COMMON_URL+s2;
		//make the restful call invocation
		Response r = call(url);
		//to check the response code is 200
		r.then().assertThat().statusCode(200);
		//to validate if the response format is json
		Assert.assertEquals("application/json; charset=utf-8",r.then().extract().contentType());
		//schema validation...
		r.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\res\\"+this.epd.getSchemaPath())));
		//conduct performance check on response time
		Assert.assertTrue(r.timeIn(TimeUnit.MILLISECONDS)<2000);
		return r;
	}
	
	public	Response	validateNegative(String s2,String s3)
	{
		String url = COMMON_URL+s2;
		//make the restful call invocation
		Response r = call(url);
		//to check the response code is 200
		if (r.statusCode()!=200)
		{
			Assert.assertNotEquals(r.statusCode(),200);
		}
		if (!r.then().extract().contentType().equals("application/json; charset=utf-8")) 
		{
			Assert.assertNotEquals("application/json; charset=utf-8",r.then().extract().contentType());
		}
		return r;
	}
	

	
	
}
