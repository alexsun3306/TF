package default_package;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class AbcTest {
  @Test(dataProvider = "dp1")
  public void f1(Integer n1, Integer n2, Integer n3) {
	  Assert.assertEquals(new Abc().calc(n1,n2),n3);
  }
  

  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("\t\t\tbefire method");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("\t\t\tafter method");
  }


  @DataProvider
  public Object[][] dp1() {
    return new Object[][] {
      new Object[] { 1, 2 ,3},
      new Object[] { 2, 3 ,5},
      new Object[] { 1, 2 ,3},
      new Object[] { 2, 3 ,5},
      new Object[] { 1, 2 ,3},
      new Object[] { 2, 3 ,5},
      new Object[] { 1, 2 ,3},
      new Object[] { 2, 3 ,5},
      new Object[] { 1, 2 ,3},
      new Object[] { 2, 3 ,5},
    };
  }
  
  @DataProvider
  public Object[][] dp2() {
    return new Object[][] {
      new Object[] { "aaa","prefix-aaa" },
      new Object[] { "aaa","prefix-aaa" },
      new Object[] { "aaa","prefix-aaa" },
      new Object[] { "aaa","prefix-aaa" },
      new Object[] { "aaa","prefix-aaa" },
      new Object[] { "bbb","prefix-bbb" },
      new Object[] { "bbb","prefix-bbb" },
      new Object[] { "bbb","prefix-bbb" },
      new Object[] { "bbb","prefix-bbb" },
      new Object[] { "bbb","prefix-bbb" },
    };
  }
  @Test(dataProvider = "dp2")
  public void f2(String s1, String s2) {
	  Assert.assertEquals(new Abc().test(s1),s2);
  }
  
  
  
  @BeforeClass
  public void beforeClass() {
	  System.out.println("\t\tbefire class");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("\t\ttafter class");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("\tbefire test");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("\tafter test");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("before suite");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("before suite");
  }


  @Test
  public void calcTest() {
    Abc abc = new Abc();
    Assert.assertEquals(new Abc().calc(12, 13),25);
  }

  @Test
  public void testTest() {
    Assert.assertEquals(new Abc().test("alex"),"prefix-alex");
  }
}
