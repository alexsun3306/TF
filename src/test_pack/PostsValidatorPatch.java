package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class PostsValidatorPatch extends EndpointValidator{

	PostsValidatorPatch() {
		super(EndpointName.EP_POSTS);
	}


	
	@DataProvider
	public Object[][] DP_PATCH_SUCCESS() 
	{
		return new Object[][] {
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("title", "title_1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("body", "body_1");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("userId", "1");
		    	}}},
		      new Object[] {"2",new HashMap<String, String>() {{
		    	    put("id", "2");
		    	}}},
		    };
	}
	
	
	@DataProvider
	public Object[][] DP_PATCH_FAILURE() 
	{
		return new Object[][] {
		      new Object[] {"b",new HashMap<String, String>() {{
		    	    put("id", "2");
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	}}},
		      new Object[] {"1",new HashMap<String, String>() {{
		    	    put("abc", "def");
		    	}}},
		    };
	}
	
	
	
	@Test(dataProvider = "DP_PATCH_SUCCESS")
	public	void	testPatchSuccess(String	id,Object obj)
	{
		Response r = this.patch(id,obj);
		validateStatus(r, "2XX");
		validateSchema(r, SchemaName.SN_SINGLE_POST);
		HashMap	mapFromObject = (HashMap) obj;
		LinkedHashMap	mapFromResponse = (LinkedHashMap) this.fetchResponseData(r);
		for (Object key : mapFromObject.keySet()) 
		{
			String nodeName = (String) key;
			assertEquals(mapFromResponse.get(nodeName).toString(),mapFromObject.get(nodeName).toString());
		}
		validateTimeout(r, 2000);
	}
	
	
	@Test(dataProvider = "DP_PATCH_FAILURE")
	public	void	testPatchFailure(String	id,Object obj)
	{
		Response response = this.patch(id,obj);
		validateStatus(response, "5XX");
	}
	
	
}
