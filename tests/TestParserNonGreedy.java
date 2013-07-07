import org.junit.*;
import junit.framework.Assert;


public class TestParserNonGreedy extends Assert{
	static Lexer l;
	static 	Parser p;
	
	@Before
	public void setUp() {
		l = new Lexer(true, true);
		
		p = new Parser(true, false);
		p.reset(Parser.what.ALL);
	}
	
	@After
	public void tearDown() throws Exception {
		if (p.getCurrTok()==Names.RF) Parser.error("Неправильный выход из expr_list, возможно лишняя RF }");
		assertTrue(Parser.getErrors()==0);
	}
	
	
	@Test
	public void testPrint2As3() throws Exception {
		// http://automated-testing.info/forum/kak-poluchit-imya-metoda-vo-vremya-vypolneniya-testa#comment-961
		System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
		l.scan("sin(-pi/2)");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(-1.0, p.lastResult);
	}
	
	@Test
	public void test3FactorialFactorial() throws Exception {
		l.scan("3!!");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(720.0, p.lastResult);
	}
	
	@Test
	public void test3FactorialAdd4Factorial() throws Exception {
		l.scan("3!+4!");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(30.0, p.lastResult);
	}
	
	@Test
	public void test1minus3FactoriAladd4Factorial() throws Exception {
		l.scan("1-3!+4!");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(19.0, p.lastResult);
	}
	
	@Test
	public void test1plus3FactoriAladd4Factorial() throws Exception {
		l.scan("1+3!+4!");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(31.0, p.lastResult);
	}
	
	@Test
	public void test3FactorialMul4Factorial() throws Exception {
		l.scan("3!*4!");
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(144.0, p.lastResult);
	}
	
	@Test
	public void test2pow3Factorial() throws Exception {
		l.scan("2^3!"); // 2^(3!)
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(64.0, p.lastResult);
	}
	
	@Test
	public void test2pow3FactorialPlus1() throws Exception {
		l.scan("2^3!+1"); // 2^(3!)+1
		p.addTokens(l.getTokens());
		p.exprList();
		assertEquals(65.0, p.lastResult);
	}
}