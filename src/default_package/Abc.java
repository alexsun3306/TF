package default_package;

public class Abc {
	static	private	String hist="";
	public	Abc()
	{
	}
	public	String	test(String s) {System.out.println(hist);hist+="1";return	"prefix-"+s;}
	public	int	calc(int a,int b) {System.out.println(hist);hist+="2";return a+b;}
}
