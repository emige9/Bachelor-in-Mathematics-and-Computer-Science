
//--------------------------------------------------------------------------
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
//--------------------------------------------------------------------------

import prLibreria.*;

//--------------------------------------------------------------------------

public class JUnitTestRunnerLibreriaOferta {
	//----------------------------------------------------------------------
	//--JUnitTest-----------------------------------------------------------
	//----------------------------------------------------------------------
	public static class JUnitTestLibreriaOferta {
		private static final String[] autoresOferta = { "george orwell", "isaac asimov" };
		private LibreriaOferta lro1;
		@BeforeClass
		public static void beforeClass() {
			// Code executed before the first test method
			System.out.println("Start of LibreriaOferta JUnit Test");
		}
		@AfterClass
		public static void  afterClass() {
			// Code executed after the last test method
			System.out.println("End of LibreriaOferta JUnit Test");
		}
		@Before
		public void setUp() {
			// Code executed before each test
			lro1 = new LibreriaOferta(2, 20.0, autoresOferta);
		}
		@After
		public void tearDown() {
			// Code executed after each test
		}
		@Test(timeout = 1000)
		public void libreriaOfertaCtorTest1() {
			assertTrue("\n> Error: LibreriaOferta extends Libreria:", ((Object)lro1 instanceof Libreria));
		}
		@Test(timeout = 1000)
		public void libreriaOfertaCtorTest2() {
			LibreriaOferta lro2 = new LibreriaOferta(20.0, autoresOferta);
			assertTrue("\n> Error: LibreriaOferta extends Libreria:", ((Object)lro2 instanceof Libreria));
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetDescuentoTest1() {
			assertEquals("\n> Error: getDescuento():", 20.00, lro1.getDescuento(), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetOfertaTest1() {
			assertArrayEquals("\n> Error: getOferta():", autoresOferta, lro1.getOferta());
		}
		@Test(timeout = 1000)
		public void libreriaOfertaSetOfertaTest1() {
			String[] autoresOferta2 = { "Aldous Huxley", "William Gibson" };
			lro1.setOferta(35.00, autoresOferta2);
			assertEquals("\n> Error: getDescuento():", 35.00, lro1.getDescuento(), 1e-6);
			assertArrayEquals("\n> Error: getOferta():", autoresOferta2, lro1.getOferta());
		}
		@Test(timeout = 1000)
		public void libreriaOfertaAddLibroTest1() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.30, lro1.getPrecioBase("Isaac Asimov", "La Fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.50, lro1.getPrecioBase("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 8.30, lro1.getPrecioBase("William Gibson", "Neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.20, lro1.getPrecioBase("George Orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.40, lro1.getPrecioBase("Ray Bradbury", "Fahrenheit 451"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaAddLibroTest2() {
			lro1.addLibro("isaac asimov", "la fundacion", 5.30);
			lro1.addLibro("aldous huxley", "un mundo feliz", 4.50);
			lro1.addLibro("william gibson", "neuromante", 6.30);
			lro1.addLibro("george orwell", "1984", 4.20);
			lro1.addLibro("ray bradbury", "fahrenheit 451", 5.40);
			//------------------------
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.30, lro1.getPrecioBase("Isaac Asimov", "La Fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.50, lro1.getPrecioBase("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 8.30, lro1.getPrecioBase("William Gibson", "Neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.20, lro1.getPrecioBase("George Orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.40, lro1.getPrecioBase("Ray Bradbury", "Fahrenheit 451"), 1e-6);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.30, lro1.getPrecioBase("isaac asimov", "la fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.50, lro1.getPrecioBase("aldous huxley", "un mundo feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 8.30, lro1.getPrecioBase("william gibson", "neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.20, lro1.getPrecioBase("george orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.40, lro1.getPrecioBase("ray bradbury", "fahrenheit 451"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetPrecioBaseTest1() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.30, lro1.getPrecioBase("Isaac Asimov", "La Fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.50, lro1.getPrecioBase("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 8.30, lro1.getPrecioBase("William Gibson", "Neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.20, lro1.getPrecioBase("George Orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.40, lro1.getPrecioBase("Ray Bradbury", "Fahrenheit 451"), 1e-6);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("XXX", "XXX"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetPrecioBaseTest2() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.30, lro1.getPrecioBase("isaac asimov", "la fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.50, lro1.getPrecioBase("aldous huxley", "un mundo feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 8.30, lro1.getPrecioBase("william gibson", "neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 6.20, lro1.getPrecioBase("george orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 7.40, lro1.getPrecioBase("ray bradbury", "fahrenheit 451"), 1e-6);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("xxx", "xxx"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetPrecioFinalTest1() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 6.424, lro1.getPrecioFinal("Isaac Asimov", "La Fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 7.15, lro1.getPrecioFinal("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 9.13, lro1.getPrecioFinal("William Gibson", "Neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 5.456, lro1.getPrecioFinal("George Orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 8.14, lro1.getPrecioFinal("Ray Bradbury", "Fahrenheit 451"), 1e-6);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 0.00, lro1.getPrecioFinal("XXX", "XXX"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaGetPrecioFinalTest2() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 6.424, lro1.getPrecioFinal("isaac asimov", "la fundacion"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 7.15, lro1.getPrecioFinal("aldous huxley", "un mundo feliz"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 9.13, lro1.getPrecioFinal("william gibson", "neuromante"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 5.456, lro1.getPrecioFinal("george orwell", "1984"), 1e-6);
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 8.14, lro1.getPrecioFinal("ray bradbury", "fahrenheit 451"), 1e-6);
			//------------------------
			assertEquals("\n> Error: addLibro(): getPrecioFinal():", 0.00, lro1.getPrecioFinal("xxx", "xxx"), 1e-6);
		}
		@Test(timeout = 1000)
		public void libreriaOfertaRemLibroTest1() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			precond(7.30, lro1.getPrecioBase("Isaac Asimov", "La Fundacion"), 1e-6);
			precond(6.50, lro1.getPrecioBase("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			precond(8.30, lro1.getPrecioBase("William Gibson", "Neuromante"), 1e-6);
			precond(6.20, lro1.getPrecioBase("George Orwell", "1984"), 1e-6);
			precond(7.40, lro1.getPrecioBase("Ray Bradbury", "Fahrenheit 451"), 1e-6);
			//------------------------
			lro1.remLibro("Isaac Asimov", "La Fundacion");
			lro1.remLibro("Aldous Huxley", "Un Mundo Feliz");
			lro1.remLibro("William Gibson", "Neuromante");
			lro1.remLibro("George Orwell", "1984");
			lro1.remLibro("Ray Bradbury", "Fahrenheit 451");
			//------------------------
			assertEquals("\n> Error: remLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("Isaac Asimov", "La Fundacion"), 1e-6);
			assertEquals("\n> Error: remLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("Aldous Huxley", "Un Mundo Feliz"), 1e-6);
			assertEquals("\n> Error: remLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("William Gibson", "Neuromante"), 1e-6);
			assertEquals("\n> Error: remLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("George Orwell", "1984"), 1e-6);
			assertEquals("\n> Error: remLibro(): getPrecioBase():", 0.00, lro1.getPrecioBase("Ray Bradbury", "Fahrenheit 451"), 1e-6);
			//------------------------
			lro1.remLibro("xxx", "xxx");
		}
		@Test(timeout = 1000)
		public void libreriaOfertaToStringTest1() {
			assertEquals("\n> Error: lro1.toString():",
						 normalize("20.0%[george orwell, isaac asimov][]"),
						 normalize(lro1.toString()));
		}
		@Test(timeout = 1000)
		public void libreriaOfertaToStringTest2() {
			lro1.addLibro("Isaac Asimov", "La Fundacion", 7.30);
			lro1.addLibro("Aldous Huxley", "Un Mundo Feliz", 6.50);
			lro1.addLibro("William Gibson", "Neuromante", 8.30);
			lro1.addLibro("George Orwell", "1984", 6.20);
			lro1.addLibro("Ray Bradbury", "Fahrenheit 451", 7.40);
			//------------------------
			assertEquals("\n> Error: lro1.toString():",
						 normalize("20.0%[george orwell, isaac asimov][(Isaac Asimov; La Fundacion; 7.3; 20.0%; 5.84; 10.0%; 6.4239999999999995), (Aldous Huxley; Un Mundo Feliz; 6.5; 10.0%; 7.15), (William Gibson; Neuromante; 8.3; 10.0%; 9.13), (George Orwell; 1984; 6.2; 20.0 % ; 4.96 ; 10.0 % ; 5.4559999999999995), (Ray Bradbury; Fahrenheit 451; 7.4; 10.0%; 8.14)]"),
						 normalize(lro1.toString()));
		}
		//------------------------------------------------------------------
	}
	//----------------------------------------------------------------------
	//--JUnitTestSuite------------------------------------------------------
	//----------------------------------------------------------------------
	@RunWith(Suite.class)
	@Suite.SuiteClasses({ JUnitTestLibreriaOferta.class
				})
				public static class JUnitTestSuite { /*empty*/ }
	//----------------------------------------------------------------------
	//--JUnitTestRunner-----------------------------------------------------
	//----------------------------------------------------------------------
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JUnitTestSuite.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		if (result.wasSuccessful()) {
			System.out.println("JUnit Test Succeeded");
		} else {
			System.out.println("> Error: JUnit Test Failed");
		}
	}
	//----------------------------------------------------------------------
	//-- Utils -------------------------------------------------------------
	//----------------------------------------------------------------------
	private static char normalizeUnicode(char ch) {
		switch (ch) {
		case '\n':
		case '\r':
		case '\t':
		case '\f':
			ch = ' ';
			break;
		case '\u00C0':
		case '\u00C1':
		case '\u00C2':
		case '\u00C3':
		case '\u00C4':
		case '\u00C5':
		case '\u00C6':
			ch = 'A';
			break;
		case '\u00C7':
			ch = 'C';
			break;
		case '\u00C8':
		case '\u00C9':
		case '\u00CA':
		case '\u00CB':
			ch = 'E';
			break;
		case '\u00CC':
		case '\u00CD':
		case '\u00CE':
		case '\u00CF':
			ch = 'I';
			break;
		case '\u00D0':
			ch = 'D';
			break;
		case '\u00D1':
			ch = 'N';
			break;
		case '\u00D2':
		case '\u00D3':
		case '\u00D4':
		case '\u00D5':
		case '\u00D6':
			ch = 'O';
			break;
		case '\u00D9':
		case '\u00DA':
		case '\u00DB':
		case '\u00DC':
			ch = 'U';
			break;
		case '\u00DD':
			ch = 'Y';
			break;
		case '\u00E0':
		case '\u00E1':
		case '\u00E2':
		case '\u00E3':
		case '\u00E4':
		case '\u00E5':
		case '\u00E6':
			ch = 'a';
			break;
		case '\u00E7':
			ch = 'c';
			break;
		case '\u00E8':
		case '\u00E9':
		case '\u00EA':
		case '\u00EB':
			ch = 'e';
			break;
		case '\u00EC':
		case '\u00ED':
		case '\u00EE':
		case '\u00EF':
			ch = 'i';
			break;
		case '\u00F0':
			ch = 'd';
			break;
		case '\u00F1':
			ch = 'n';
			break;
		case '\u00F2':
		case '\u00F3':
		case '\u00F4':
		case '\u00F5':
		case '\u00F6':
			ch = 'o';
			break;
		case '\u00F9':
		case '\u00FA':
		case '\u00FB':
		case '\u00FC':
			ch = 'u';
			break;
		case '\u00FD':
		case '\u00FF':
			ch = 'y';
			break;
		}
		return ch;
	}
	//----------------------------------------------------------------------
	private static String normalize(String s1) {
		int sz = s1 == null ? 16 : s1.length() == 0 ? 16 : 2*s1.length();
		StringBuilder sb = new StringBuilder(sz);
		sb.append(' ');
		if (s1 != null) {
			for (int i = 0; i < s1.length(); ++i) {
				char ch = normalizeUnicode(s1.charAt(i));
				char sbLastChar = sb.charAt(sb.length()-1);
				if (Character.isLetter(ch)) {
					if ( ! Character.isLetter(sbLastChar)) {
						if ( ! Character.isSpaceChar(sbLastChar)) {
							sb.append(' ');
						}
					}
					sb.append(ch);
				} else if (Character.isDigit(ch)) {
					if ((i >= 2)
						&& (s1.charAt(i-1) == '.')
						&& Character.isDigit(s1.charAt(i-2))) {
						sb.setLength(sb.length()-2); // "9 ."
						sb.append('.');
					} else if ((i >= 2)
							   && ((s1.charAt(i-1) == 'e')||(s1.charAt(i-1) == 'E'))
							   && Character.isDigit(s1.charAt(i-2))) {
						sb.setLength(sb.length()-2); // "9 e"
						sb.append('e');
					} else if ((i >= 3)
							   && (s1.charAt(i-1) == '+')
							   && ((s1.charAt(i-2) == 'e')||(s1.charAt(i-2) == 'E'))
							   && Character.isDigit(s1.charAt(i-3))) {
						sb.setLength(sb.length()-4); // "9 e +"
						sb.append('e');
					} else if ((i >= 3)
							   && (s1.charAt(i-1) == '-')
							   && ((s1.charAt(i-2) == 'e')||(s1.charAt(i-2) == 'E'))
							   && Character.isDigit(s1.charAt(i-3))) {
						sb.setLength(sb.length()-4); // "9 e -"
						sb.append("e-");
					} else if ( (sbLastChar != '-') && ! Character.isDigit(sbLastChar)) {
						if ( ! Character.isSpaceChar(sbLastChar)) {
							sb.append(' ');
						}
					}
					sb.append(ch);
				} else if (Character.isSpaceChar(ch)) {
					if ( ! Character.isSpaceChar(sbLastChar)) {
						sb.append(' ');
					}
				} else {
					if ( ! Character.isSpaceChar(sbLastChar)) {
						sb.append(' ');
					}
					sb.append(ch);
				}
			}
		}
		if (Character.isSpaceChar(sb.charAt(sb.length()-1))) {
			sb.setLength(sb.length()-1);
		}
		if ((sb.length() > 0) && Character.isSpaceChar(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}
	//----------------------------------------------------------------------
	private static final String precondMessage = "\n> Aviso: No se pudo realizar el test debido a errores en otros metodos";
	//----------------------------------------------------------------------
	private static void precond(boolean expectedValue, boolean currValue) {
		if (expectedValue != currValue) {
			fail(precondMessage);
		}
	}
	private static void precond(char expectedValue, char currValue) {
		if (expectedValue != currValue) {
			fail(precondMessage);
		}
	}
	private static void precond(short expectedValue, short currValue) {
		if (expectedValue != currValue) {
			fail(precondMessage);
		}
	}
	private static void precond(int expectedValue, int currValue) {
		if (expectedValue != currValue) {
			fail(precondMessage);
		}
	}
	private static void precond(long expectedValue, long currValue) {
		if (expectedValue != currValue) {
			fail(precondMessage);
		}
	}
	private static void precond(float expectedValue, float currValue, float delta) {
		if (Math.abs(expectedValue - currValue) > delta) {
			fail(precondMessage);
		}
	}
	private static void precond(double expectedValue, double currValue, double delta) {
		if (Math.abs(expectedValue - currValue) > delta) {
			fail(precondMessage);
		}
	}
	private static void precond(Object expectedValue, Object currValue) {
		if ((expectedValue == null)||(currValue == null)) {
			if (expectedValue != currValue) {
				fail(precondMessage);
			}
		} else if ( ! expectedValue.equals(currValue)) {
			fail(precondMessage);
		}
	}
	//----------------------------------------------------------------------
}
//--------------------------------------------------------------------------
