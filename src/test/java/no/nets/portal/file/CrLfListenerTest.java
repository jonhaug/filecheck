package no.nets.portal.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import no.nets.portal.file.CrLfListener.EndLine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class CrLfListenerTest {
	private FilecheckEngine engine;
	private CrLfListener crLfListener;
	static String CR_S = "\r"; 
	static String LF_S = "\n"; 
	static String CRLF_S = "\r\n";
	static String L = "Dette er en linje";
	static String STR = "mange tilfeldige tegn og bokstaver OG STORE BOKSTAVER cxz)(/&/&";
	Random random = new Random();

	@Before
	public void setUp() throws Exception {
		engine = new FilecheckEngine();
		crLfListener = new CrLfListener(engine);
	}
	
	@Test
	public void testCr() throws Exception {
		String s = L + CR_S + L + CR_S;
		engine.work(getIS(s));
		assertEquals(EndLine.CR,crLfListener.getEndLine());
	}
	
	@Test
	public void testLf() throws Exception {
		String s = L + LF_S + L + LF_S;
		engine.work(getIS(s));
		assertEquals(EndLine.LF,crLfListener.getEndLine());
	}
	@Test
	public void testCrLf() throws Exception {
		String s = L + CRLF_S + L + CRLF_S;
		engine.work(getIS(s));
		assertEquals(EndLine.CRLF,crLfListener.getEndLine());
	}
	@Test
	public void testUnknown() throws Exception {
		String s = L + L ;
		engine.work(getIS(s));
		assertEquals(EndLine.UNKNOWN,crLfListener.getEndLine());
	}
	@Test
	public void testUnknown2() throws Exception {
		String s = L + CR_S + L + LF_S + L + CR_S + L ;
		engine.work(getIS(s));
		assertEquals(EndLine.UNKNOWN,crLfListener.getEndLine());
	}
	
	@Test
	public void testSameLineLength() throws Exception {
		StringBuffer sb = new StringBuffer(1000);
		sb.append(gRndString(80)).append('\n');
		sb.append(gRndString(80)).append('\n');
		sb.append(gRndString(80)).append('\n');
		engine.work(getIS(sb.toString()));
		assertTrue(crLfListener.isFixedLineLength());
	}

	@Test
	public void testNotSameLineLength() throws Exception {
		StringBuffer sb = new StringBuffer(1000);
		sb.append(gRndString(84)).append('\n');
		sb.append(gRndString(80)).append('\n');
		sb.append(gRndString(80)).append('\n');
		engine.work(getIS(sb.toString()));
		assertFalse(crLfListener.isFixedLineLength());
	}

	private InputStream getIS(String s) {
		return new ByteArrayInputStream(gb(s));
	}
	private byte[] gb(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("xxx",e);
		}
	}
	private String gRndString(int length) {
		StringBuffer sb = new StringBuffer(length);
		for (int i=0;i<length;i++) {
			sb.append(STR.charAt(random.nextInt(STR.length())));
		}
		return sb.toString();
	}
}
