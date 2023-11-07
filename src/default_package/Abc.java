package default_package;

public class Abc {
	static	private	String hist="";
	public	Abc()
	{
	}
	public	String	test(String s) {System.out.println(hist);hist+="1";return	"prefix-"+s;}
	public	int	calc(int a,int b) {System.out.println(hist);hist+="2";return a+b;}
	public void main(String[] aa)
	{
		 Integer a=12;
		 Integer b=13;
		 System.out.println(a+b+"dfs");
	}
}
