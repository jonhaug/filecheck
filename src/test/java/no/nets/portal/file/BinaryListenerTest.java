package no.nets.portal.file;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


public class BinaryListenerTest {

	private FilecheckEngine engine;
	private BinaryListener listener;

	@Before
	public void setUp() throws Exception {
		engine = new FilecheckEngine();
		listener = new BinaryListener(engine);
	}
	
	@Test
	public void testString() throws Exception {
		byte[] fileSim = gb("A text with abc \nand abc.\n\n\n\n\n\n\n");
		ByteArrayInputStream bais = new ByteArrayInputStream(fileSim);
		engine.work(bais);
		assertFalse(listener.isBinary());
	}
	@Test
	public void testBinary() throws Exception {
		byte[] fileSim = getRandom(100);
		ByteArrayInputStream bais = new ByteArrayInputStream(fileSim);
		engine.work(bais);
		assertTrue(listener.isBinary());
	}
	@Test
	public void testSomeBinary() throws Exception {
		byte[] fileSim = getRandom(100);
		for (int i=5;i<15;i++) fileSim[i]=100;
		ByteArrayInputStream bais = new ByteArrayInputStream(fileSim);
		engine.work(bais);
		assertTrue(listener.isBinary());
	}
	
	private byte[] getRandom(int n) {
		byte[] bs = new byte[n];
		Random random = new Random();
		random.nextBytes(bs);
		return bs;
	}

	private byte[] gb(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("xxx",e);
		}
	}

}
