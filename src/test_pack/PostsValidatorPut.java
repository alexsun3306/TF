package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class PostsValidatorPut extends EndpointValidator{

	PostsValidatorPut() {
		super(EndpointName.EP_POSTS);
	}


	
	@DataProvider
	public Object[][] DP_PUT_SUCCESS() 
	{
		return new Object[][] {
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	    put("id", "1");
		    	}}},
		      new Object[] {"2",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "aaaaa");
		    	    put("id", "2");
		    	}}},
		    };
	}
	
	
	@DataProvider
	public Object[][] DP_PUT_FAILURE() 
	{
		return new Object[][] {
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	    put("id", "a");
		    	}}},
		      new Object[] {"b",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "aaaaa");
		    	    put("id", "2");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	    put("id", "1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("userId", "1");
		    	    put("id", "1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("id", "1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("hello", "title_1");
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	    put("id", "1");
		    	}}},
		    };
	}
	
	
	
	@Test(dataProvider = "DP_PUT_SUCCESS")
	public	void	testPutSuccess(String	id,Object obj)
	{
		Response response = this.put(id,obj);
		validateSchema(response, SchemaName.SN_SINGLE_POST);
		validateStatus(response, "2XX");
		LinkedHashMap	mapFromResponse = (LinkedHashMap) this.fetchResponseData(response);
		HashMap	mapFromInput = (HashMap) obj;
		assertEquals(mapFromResponse.get("title").toString(),mapFromInput.get("title"));
		assertEquals(mapFromResponse.get("id").toString(),mapFromInput.get("id"));
		assertEquals(mapFromResponse.get("body").toString(),mapFromInput.get("body"));
		assertEquals(mapFromResponse.get("userId").toString(),mapFromInput.get("userId"));
		validateTimeout(response, 2000);
	}
	
	
	@Test(dataProvider = "DP_PUT_FAILURE")
	public	void	testPutFailure(String	id,Object obj)
	{
		Response response = this.put(id,obj);
		validateStatus(response, "5XX");
	}
	
	
}
