package no.nets.portal.file;

import java.util.ArrayList;
import java.util.List;

public class GuessCharSetListener {

	List<CharSetListener> cListeners = new ArrayList<CharSetListener>();
	public GuessCharSetListener(FilecheckEngine engine) {
		cListeners.add(new CharSetListener("UTF-8",engine));
		cListeners.add(new CharSetListener("ISO-8859-1",engine));
		cListeners.add(new CharSetListener("ISO-8859-15",engine));
	}
	public String getCharSet() {
		StringBuffer charSet = new StringBuffer();
		for (CharSetListener csl: cListeners) {
			int c = csl.getCount();
			if (c>0) {
				if (charSet.length() > 0) charSet.append('/');
				charSet.append(csl.getCharSet()).append(':').append(c);
			}
		}
		return charSet.toString();
	}
}
