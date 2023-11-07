package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;


public class PostsValidationPost extends EndpointValidator{
	PostsValidationPost() {
		super(EndpointName.EP_POSTS);
	}

	
	@DataProvider
	public Object[][] DP_POST_SUCCESS() 
	{
		return new Object[][] {
		      new Object[] {new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	}}},
		      new Object[] {new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	    put("userId", "aaaaa");
		    	}}},
		    };
	}
	
	@DataProvider
	public Object[][] DP_POST_FAILURE() 
	{
		return new Object[][] {
		      new Object[] {new HashMap<String, String>() {{
		    	    put("body", "body_1");
		    	    put("userId", "1");
		    	}}},
		      new Object[] {new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("userId", "1");
		    	}}},
		      new Object[] {new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	    put("body", "body_1");
		    	}}},
		      new Object[] {new HashMap<String, String>() {{
		    	}}},
		      new Object[] {new HashMap<String, String>() {{
		    	    put("abc", "title_1");
		    	    put("def", "body_1");
		    	    put("ghi", "aaaaa");
		    	}}},
		    };
	}
	
	
	
	@Test(dataProvider = "DP_POST_SUCCESS")
	public	void	testPostSuccess(Object obj)
	{
		Response response = this.post(obj);
		validateStatus(response, "2XX");
		validateSchema(response, SchemaName.SN_SINGLE_POST);
		HashMap	mapFromObject = (HashMap) obj;
		LinkedHashMap	mapFromResponse = (LinkedHashMap) this.fetchResponseData(response);
		assertEquals(mapFromResponse.get("title"),mapFromObject.get("title"));
		assertEquals(mapFromResponse.get("body"),mapFromObject.get("body"));
		assertEquals(mapFromResponse.get("userId"),mapFromObject.get("userId"));
		validateTimeout(response, 2000);
	}
	
	@Test(dataProvider = "DP_POST_FAILURE")
	public	void	testPostFailure(Object obj)
	{
		Response response = this.post(obj);
		validateStatus(response, "5XX");
	}
	
	
	
}



