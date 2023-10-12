package test_pack;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static java.util.Map.entry;

public class SampleRestfulApiTesting {
	/**
	 * this mapping provide a mean to, base on scenario:
	 * - decide whether if it is a get, post, put, patch, delete
	 * - adopt the appropriate json schema file for response schema validation
	 * */
	public static Map<String, String[]> schema_mapping = Map.ofEntries(
			entry("get_posts_id",new String[]{"get",".\\res\\schema_001.json"}),
			entry("get_posts",new String[]{"get",".\\res\\schema_002.json"}),
			entry("get_posts_id_comments",new String[]{"get",".\\res\\schema_003.json"}),
			entry("get_comments_postid_id",new String[]{"get",".\\res\\schema_003.json"}),
			entry("put_posts_id",new String[]{"put",".\\res\\schema_004.json"}),
			entry("patch_posts_id",new String[]{"patch",".\\res\\schema_004.json"}),
			entry("delete_posts_id",new String[]{"delete",".\\res\\empty.json"}),
			entry("post_posts",new String[]{"post",".\\res\\schema_002.json"})
			);
	  
  @DataProvider
  /**
   * this is just a data provider to allow data driven testing
   * */
  public Object[][] overall() {
    return new Object[][] {
    	new Object[] {"get_posts", "/posts" },
    	new Object[] {"get_posts_id", "/posts/3" },
    	new Object[] {"get_posts_id_comments", "/posts/3/comments" },
    	new Object[] {"get_comments_postid_id", "/comments/postId=3" },
    	new Object[] {"post_posts", "/posts" },
    	new Object[] {"put_posts_id", "/posts/3" },
    	new Object[] {"patch_posts_id", "/posts/3" },
    	new Object[] {"delete_posts_id", "/posts/3" },

       
    };
  }
  
  /**
   * this method will actually perform the restAssured call and return a Response object
   * this method provide the response object, which can be used for many subsequent validations
   * */
  public	Response	call(String scenario,String url)
  {
	  String code = schema_mapping.get(scenario)[0];
	  switch(code)
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

  /**
   * this method will load the json schema object based on selected scenario
   * */
  public	JsonSchema	fetch_expected_schema(String scenario) throws FileNotFoundException
  {
	  JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V4);
	  InputStream targetStream = new FileInputStream(schema_mapping.get(scenario)[1]);
	  JsonSchema jsonSchema = factory.getSchema(targetStream);
	  return jsonSchema;
  }
  
  /**
   * this method will extract the response json from response and create a json node object
   * */
  public	JsonNode	fetch_response_json(Response r) throws JsonMappingException, JsonProcessingException
  {
	  String json = r.then().extract().response().asString();
	  ObjectMapper map = new ObjectMapper();  
	  JsonNode jsonNode = map.readTree(json);
	  return jsonNode;
  }

  /**
   * this method conduct schema validation base on scenario & response
   * */
  public	void	schema_validation(String scenario,Response r) throws FileNotFoundException, JsonMappingException, JsonProcessingException
  {
	  JsonSchema js = fetch_expected_schema(scenario);
	  JsonNode jn = fetch_response_json(r);
	  Set<ValidationMessage> errors = js.validate(jn);//this performs the actual validations
	//conduct schema validation, 0 - means no error
	  AssertJUnit.assertEquals(errors.size(),0);
  }
  
  
  
  @Test(dataProvider = "overall")
  public void simple_validation(String s1, String s2) throws JsonMappingException, JsonProcessingException, FileNotFoundException {
	  String url = "https://jsonplaceholder.typicode.com"+s2;
	  System.out.println(url);
	  
	  //make the restful call invocation
	  Response r = call(s1,url);
	  //dump the body any way...
	  r.then().log().body();
	  //to check the response code is 200
	  r.then().assertThat().statusCode(200);
	  //to validate if the response format is json
	  AssertJUnit.assertEquals("application/json; charset=utf-8",r.then().extract().contentType());
	  //then conduct schema validation
	  schema_validation(s1,r);
	  //conduct performance check on response time
	  AssertJUnit.assertTrue(r.timeIn(TimeUnit.MILLISECONDS)<2000);
	  //other type of additional field extractions
	  System.out.println(r.then().extract().headers());
	  //System.out.println((String)r.then().extract().path("title"));
	  
  }
	  
  /*
  @Test
  public static void getResponseBody(){
	  RestAssured
	  .given().when()
	  .get("https://jsonplaceholder.typicode.com/posts?id=4")
	  .then().log()
	  .body();
	}
	*/
}
