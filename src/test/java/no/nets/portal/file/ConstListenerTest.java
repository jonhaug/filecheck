package no.nets.portal.file;


import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ConstListenerTest {

	private FilecheckEngine engine;

	@Before
	public void setUp() throws Exception {
		engine = new FilecheckEngine();
	}
	
	@Test
	public void testString() throws Exception {
		byte[] bytes = "abc".getBytes("UTF-8");
		byte[] fileSim = "A text with abc and abc.".getBytes("UTF-8");
		ConstListener listener = new ConstListener(bytes);
		engine.addListener(listener);
		ByteArrayInputStream bais = new ByteArrayInputStream(fileSim);
		engine.work(bais);
		assertEquals(2,listener.getCount());
	}
	@Test
	public void testSingle() throws Exception {
		ConstListener a1 = new ConstListener(gb("a"));
		ConstListener a2 = new ConstListener(gb("a"));
		ConstListener b1 = new ConstListener(gb("b"));
		engine.addListener(a1);
		engine.addListener(a2);
		engine.addListener(b1);
		ByteArrayInputStream stream = new ByteArrayInputStream(gb("aaabbbaaa"));
		engine.work(stream);
		assertEquals(6,a1.getCount());
		assertEquals(6,a2.getCount());
		assertEquals(3,b1.getCount());
	}
	@Test
	public void testOverlap() throws Exception {
		ConstListener a1 = new ConstListener(gb("ab"));
		ConstListener a2 = new ConstListener(gb("baa"));
		ConstListener b1 = new ConstListener(gb("bb"));
		engine.addListener(a1);
		engine.addListener(a2);
		engine.addListener(b1);
		ByteArrayInputStream stream = new ByteArrayInputStream(gb("aaabbbaaa"));
		engine.work(stream);
		assertEquals(1,a1.getCount());
		assertEquals(1,a2.getCount());
		assertEquals(2,b1.getCount());
		
	}
	
	private byte[] gb(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("xxx",e);
		}
	}

}
