package test_pack;
import static org.testng.Assert.assertEquals;
import java.util.LinkedHashMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.response.Response;

public class PostsValidatorGet extends	EndpointValidator{
	
	PostsValidatorGet() {
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


	@Test
	public	void	testGet()
	{
		Response response = this.get("");
		validateStatus(response, "2XX");
		validateSchema(response, SchemaName.SN_POSTS);
		validateTimeout(response, 2000);
	}
	
	@Test(dataProvider = "DP_IDS_SUCCESS")
	public	void	testGetIdSuccess(String id)
	{
		Response response = this.get("/"+id);
		validateStatus(response, "2XX");
		validateSchema(response, SchemaName.SN_SINGLE_POST);
		LinkedHashMap	mapFromResponse = (LinkedHashMap) this.fetchResponseData(response);
		assertEquals(mapFromResponse.get("id").toString(),id);
		validateTimeout(response, 2000);
	}
	
	@Test(dataProvider = "DP_IDS_FAILURE")
	public	void	testGetIdFailure(String id)
	{
		Response r = this.get("/"+id);
		validateStatus(r, "4XX");
	}
	

}
