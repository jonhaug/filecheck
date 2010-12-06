package no.nets.portal.file;

public class ConstListener implements Listener {

	protected byte[] bytes;
	protected int count=0;
	protected long last;

	public ConstListener(byte[] bytes) {
		this.bytes = bytes;
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
