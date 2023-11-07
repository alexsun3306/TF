package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class CommentsValidatorGet extends EndpointValidator {

	CommentsValidatorGet() {
		super(EndpointName.EP_COMMENTS);
		// TODO Auto-generated constructor stub
	}

	@DataProvider
	public Object[][] DP_POSTIDS_SUCCESS() 
	{
		return new Object[][] {
		      new Object[] { "1"},
		      new Object[] { "2"},
		      new Object[] { "3"},
		    };
	}
	@DataProvider
	public Object[][] DP_POSTIDS_FAILURE() 
	{
		return new Object[][] {
		      new Object[] { "0"},
		      new Object[] { "1000"},
		      new Object[] { "a"},
		    };
	}
	

	@Test
	public	void	testGet()
	{
		Response response = this.get("");
		validateStatus(response, "2XX");
		validateSchema(response, SchemaName.SN_COMMENTS);
		validateTimeout(response, 2000);
	}
	
	@Test(dataProvider = "DP_POSTIDS_SUCCESS")
	public	void	testGetPostIdSuccess(String id)
	{
		Response response = this.get("?postId="+id);
		validateStatus(response, "2XX");
		validateSchema(response, SchemaName.SN_COMMENTS);
		ArrayList	arrayInResponse = (ArrayList) this.fetchResponseData(response);
		for(Object objectInArray:(ArrayList)arrayInResponse)
		{
			LinkedHashMap	objectAsMap =(LinkedHashMap) objectInArray;
			assertEquals(objectAsMap.get("postId").toString(),id);
		}
		validateTimeout(response, 2000);
	}
	
	@Test(dataProvider = "DP_POSTIDS_FAILURE")
	public	void	testGetPostIdFailure(String id)
	{
		Response response = this.get("?postId="+id);
		validateStatus(response, "4XX");
	}
	
}
