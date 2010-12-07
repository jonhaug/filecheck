package no.nets.portal.file;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharSetListener {
	public static String CHARS = "€©æøåÆØÅ½¾¡¦£$½¥{[]}¶¹²³¼‰¾‰⅝÷«»";
	List<ConstListener> listeners = new ArrayList<ConstListener>();
	String charSet;
	public CharSetListener(String charSet, FilecheckEngine engine) {
		this.charSet=charSet;
		for (char ch: CHARS.toCharArray()) {
			String s = "" + ch;	
			try {
				byte[] barr = s.getBytes(charSet);
				ConstListener cl = new ConstListener(s,barr);
				listeners.add(cl);
				engine.addListener(cl);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("No such encoding: ", e);
			}
		}
	}
	public String toString() {
		StringBuffer sb = new StringBuffer(charSet + "\n");
		for (ConstListener listener: listeners) {
			sb.append('(').append(listener.name).append(':').
			append(UnsignedByteArrayToString(listener.getBytes())).
			append(")\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) throws Exception {
		FilecheckEngine engine = new FilecheckEngine();
		CharSetListener utf8List = new CharSetListener("UTF-8",engine);
		System.out.println(utf8List.toString());
		CharSetListener iso1List = new CharSetListener("ISO-8859-1",engine);
		System.out.println(iso1List.toString());
		CharSetListener iso15List = new CharSetListener("ISO-8859-15",engine);
		System.out.println(iso15List.toString());
		
		byte[] barr = {35,63,35};
		System.out.println(new String(barr,"ISO-8859-1"));
		
	}
	public static String UnsignedByteArrayToString(byte[] barr) {
		int[] iarr = new int[barr.length];
		int i=0;
		for (byte b: barr) {
			iarr[i++] = (int) 0xFF & b;
		}
		return Arrays.toString(iarr);
	}
	
}
