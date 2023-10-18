package test_pack;

import static org.testng.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.response.Response;

public class Ep2Validator extends EndpointValidator {

	@DataProvider
	public Object[][] DP() {return super.DP();}
	
	@Test(dataProvider = "DP")
	public	void	validateEp(String s1, String s2,String s3)
	{
		super.validateEp(s1, s2, s3);
	}
	
	public	Response	validatePositive(String s2,String s3)
	{
		Response r = super.validatePositive(s2, s3);
		LinkedHashMap	obj = (LinkedHashMap)r.then().extract().as(Object.class);
		Assert.assertEquals(obj.get("id"),Integer.valueOf(s3));
		return r;
	}
	
	
	public	Response	validateNegative(String s2,String s3)
	{
		Response r = super.validateNegative(s2, s3);
		return r;
	}
	public	Ep2Validator()
	{
		this.epn = EndpointName.EP2;
		this.init();
	}
	
}
