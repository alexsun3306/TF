package test_pack;

public enum EndpointName {
	EP_POSTS("https://jsonplaceholder.typicode.com/posts"),
	EP_COMMENTS("https://jsonplaceholder.typicode.com/comments");
	private	String	url;
	private	EndpointName(String url)
	{
		this.url = url;
	}
	public	String	getUrl()
	{
		return this.url;
	}
}
