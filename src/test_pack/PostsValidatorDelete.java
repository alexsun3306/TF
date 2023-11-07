package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class PostsValidatorDelete extends EndpointValidator {
	
	
	

	PostsValidatorDelete() {
		super(EndpointName.EP_POSTS);
	}

	@DataProvider
	public Object[][] DP_IDS_SUCCESS() 
	{
		return new Object[][] {
		      new Object[] { "1"},
		      new Object[] { "2"},
		      new Object[] { "3"},
		    };
	}
	@DataProvider
	public Object[][] DP_IDS_FAILURE() 
	{
		return new Object[][] {
		      new Object[] { "0"},
		      new Object[] { "1000"},
		      new Object[] { "a"},
		    };
	}

	
	@Test(dataProvider = "DP_IDS_SUCCESS")
	public	void	testDeleteIdSuccess(String id)
	{
		Response response = this.get("/"+id);
		validateStatus(response, "2XX");
	}
	
	@Test(dataProvider = "DP_IDS_FAILURE")
	public	void	testDeleteIdFailure(String id)
	{
		Response response = this.get("/"+id);
		validateStatus(response, "4XX");
	}
	

	
	
	
	

	
}
