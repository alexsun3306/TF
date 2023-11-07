package retired;


public	class EndpointData {
	private String method;
	private String partial_url;
	private String schema_path;
	private String dp_path;

	
	
	public String getMethod() {
		return method;
	}

	public String getParialUrl() {
		return partial_url;
	}

	public String getSchemaPath() {
		return schema_path;
	}
	
	
	public String getDpPath() {
		return dp_path;
	}
	

public EndpointData(String method, String path,String schema_path,String dp_path) {
    this.method = method;
    this.partial_url = partial_url;
    this.schema_path = schema_path;
    this.dp_path = dp_path;
}
}