
import org.junit.*;
import junit.framework.Assert;

public class TestParserGreedyMost extends Assert {
	static Lexer l;
	static Buffer b;
	static Parser p;
	String inputString = null;

	@Before
	public void setUp() throws MyException {
		// StringReader in2 = new StringReader(inputString);
		// BufferedReader stdio = new BufferedReader(in2);
		OutputSystem out = new OutputSystem();
		l = new Lexer();
		Options o = new Options(out);
		o.set(Id.AUTO_END, true);
		o.set(Id.GREEDY_FUNC, true);
		// Старый конструктор Buffer: опции lexerAutoEnd, lexerPrintTokens :
		// true, false
		b = new Buffer(l, null, null, o, out);
		// Старый конструктор Parser: опции autoPrint, greedyFunc : true, true
		p = new Parser(b, o, out);
	}

	@After
	public void tearDown() throws Exception {
		// if (p.getCurrTok().name==Terminal.RF)
		// p.error("Неправильный выход из expr_list, возможно лишняя RF }");
		if (p.getErrors() > 0)
			System.err.println("Ошибка на " + b.getLineNum());
		assertTrue(p.getErrors() == 0);
	}

	@Test
	public void testPrint2As3() throws Exception {
		// http://automated-testing.info/forum/kak-poluchit-imya-metoda-vo-vremya-vypolneniya-testa#comment-961
		System.out.println(new Object() {
		}.getClass().getEnclosingMethod().getName());
		b.setArgs(new String[] { "print print2=as3=-321.694;\nas3=-as3" }); // не
																			// смотря
																			// на
																			// '\n',
																			// лексер
																			// считает
																			// это
																			// как
																			// одну
																			// строку
		p.program();
		assertEquals(321.694, p.lastResult); // работает
	}

	@Test
	public void testPrint_() throws Exception {
		b.setArgs(new String[] { "print print_ = as3=321.694", "", "" });
		p.program();
		assertEquals(321.694, p.lastResult); // работает
	}

	@Test
	public void testPrintCosPiDiv2() throws Exception {
		b.setArgs(new String[] { "print cos pi/2" });
		p.program();
		assertEquals(0.0, p.lastResult); // работает
	}

	@Test
	public void testPrint_1() throws Exception {
		b.setArgs(new String[] { "print -1" });
		p.program();
		assertEquals(-1.0, p.lastResult); // работает
	}

	@Test
	public void testPrintSinPiDiv2() throws Exception {
		b.setArgs(new String[] { "print sin pi/2" });
		p.program();
		assertEquals(1.0, p.lastResult); // работает
	}

	@Test
	public void testPrintCosSinPiDiv2() throws Exception {
		b.setArgs(new String[] { "print cos sin pi/2" });
		p.program();
		assertEquals(Math.cos(Math.sin(Math.PI / 2.0)), p.lastResult); // работает
	}

	@Test
	public void testPrintZero() throws Exception {
		b.setArgs(new String[] { "print 0.0" });
		p.program();
		assertEquals(0.0, p.lastResult); // работает
	}

	@Test
	public void testIf_false_firstAfterIf() throws Exception {
		b.setArgs(new String[] { "if(sin pi) {print 2+ 2*2;}  print e" });
		p.program();
		assertEquals(Math.E, p.lastResult);
	}

	@Test
	public void testIf_true_() throws Exception {
		b.setArgs(new String[] { "if(sin pi+3) {print 2 + 2*2;}" });
		p.program();
		assertEquals(6.0, p.lastResult); // работает
	}

	@Test
	public void testIf_false_El() throws Exception {
		b.setArgs(new String[] { "if(sin pi){print 2 + 2*2;} else {print printMe;}" });
		p.program();
		assertEquals(0.0, p.lastResult); // работает
	}

	@Test
	public void testInsertedIfEl1() throws Exception {
		b.setArgs(new String[] { "if(1){ if(2){print 2+2*2;}else{print err2;} }else{print err1;}" });
		p.program();
		assertEquals(6.0, p.lastResult); // работает
	}

	@Test
	public void testInsertedIfEl2() throws Exception {
		b.setArgs(new String[] { "if(1){ if(2){print 2+2*20;}}else{print err1;}" });
		p.program();
		assertEquals(42.0, p.lastResult); // работает
	}

	@Test
	public void testInsertedIfEl3() throws Exception {
		b.setArgs(new String[] { "if(1){ if(2){print -10+2*2;}else{print err2;} }" });
		p.program();
		assertEquals(-6.0, p.lastResult); // работает
	}

	@Test
	public void testaab() throws Exception {
		b.setArgs(new String[] { "a = 1; b = a+2; print b;" });
		p.program();
		assertEquals(3.0, p.lastResult); // работает
	}

	@Test
	public void testPow1() throws Exception {
		b.setArgs(new String[] { "aaa=2^3^4; bb=( 2 ^ 3 ) ^ 4; if(a-b){}else{print 2; } print 3" });
		p.program();
		assertEquals(3.0, p.lastResult); // работает
	}

	@Test
	public void testIf1() throws Exception {
		b.setArgs(new String[] { "if(-e){print 2 + 3;} print" });
		p.program();
		assertEquals(5.0, p.lastResult); // работает
	}

	@Test
	public void testIf2() throws Exception {
		b.setArgs(new String[] { "if(-e){print 2 + 3;}; print" });
		p.program();
		assertEquals(5.0, p.lastResult); // работает
	}

	@Test
	public void testIf3() throws Exception {
		b.setArgs(new String[] { "if(-e+e){print 2 + 3; ;}else{ print 14; ;} print;" });
		p.program();
		assertEquals(14.0, p.lastResult); // работает
	}

	@Test
	public void testAns() throws Exception {
		b.setArgs(new String[] { "2; (5+3)+ans" });
		p.program();
		assertEquals(10.0, p.lastResult); // работает
	}

	@Test
	public void testTemplateForFutureVector() throws Exception {
		b.setArgs(new String[] { "p=4; (3*p^2 + 6*p - 4)/2" }); // TODO Убрать
																// p=8 и
																// использовать
																// для проверки
																// деления
																// вектора на
																// ЧИСЛО
		p.program();
		assertEquals(34.0, p.lastResult); // работает
	}
}
