package no.nets.portal.file;

public class ConstListener implements Listener {

	protected byte[] bytes;
	protected int count=0;
	protected long last;
	String name = null;

	public ConstListener(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public ConstListener(String s, byte[] barr) {
		this.bytes = barr;
		this.name=s;
	}

	@Override
	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public void signal(long position) {
		count++;
		last = position;
	}
	public int getCount() {
		return count;
	}

	public long getSize() {
		return last;
	}

	@Override
	public void endSignal(long position) {
		last=position;
	}

}
