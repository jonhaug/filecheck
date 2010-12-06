package no.nets.portal.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class FilecheckEngine {

	private LBox staticListeners = new LBox();
	private LBox dynListeners = new LBox();
	public void addListener(Listener listener) {
		EListener el = new EListener(listener);
		staticListeners.add(el);
	}
	
	public void work(InputStream stream) throws IOException {
		int b;
		long pos = 0;
		while ((b=stream.read()) != -1) {
			push(b,pos);
			pos++;
		}
		staticListeners.endSignal(pos);
	}


	private void push(int b, long pos) {
		LBox newDyn = new LBox();
		for (EListener el: dynListeners.get(b)) {
			if (! el.signalIfEnd(pos)) {
				el.next();
				newDyn.add(el);
			}
		}
		for (EListener el: staticListeners.get(b)) {
			if (! el.signalIfEnd(pos)) {
				EListener newEl = new EListener(el.listener);
				newEl.next();
				newDyn.add(newEl);
			}
		}
		dynListeners = newDyn;
	}
	private static class EListener  {
		Listener listener;
		byte[] bytes;
		int index = 0;
		public EListener(Listener listener) {
			this.listener = listener;
			bytes = listener.getBytes();
		}
		public boolean signalIfEnd(long pos) {
			if (index+1 == bytes.length) {
				listener.signal(pos);
				return true;
			}
			return false;
		}
		public int next() {
			return bytes[index++];
		}
		public int top() {
			return bytes[index];
		}
	}
	@SuppressWarnings("unchecked")
	private static class LBox {
		LinkedList<EListener>[] listeners = (LinkedList<EListener>[]) new LinkedList[256];
		public void add(EListener el) {
			int b = el.top();
			if (listeners[b] == null) listeners[b] = new LinkedList<EListener>();
			listeners[b].add(el);
		}
		public void endSignal(long pos) {
			for (int i=0;i<256;i++) {
				if (listeners[i] != null) {
					for (EListener el:listeners[i]) {
						el.listener.endSignal(pos);
					}
				}
			}
			
		}
		public LinkedList<EListener> get(int b) {
			if (listeners[b] == null) {
				listeners[b] = new LinkedList<EListener>();
			}
			return listeners[b];
		}
	}
}
