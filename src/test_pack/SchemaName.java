package test_pack;

public enum SchemaName {
	SN_SINGLE_POST(".\\res\\schema_single_post.json"),
	SN_POSTS(".\\res\\schema_posts.json"),
	SN_COMMENTS(".\\res\\schema_comments.json");
	private	String	jsonPath;
	private	SchemaName(String jsonPath)
	{
		this.jsonPath = jsonPath;
	}
	public	String	getJsonPath()
	{
		return this.jsonPath;
	}
}
