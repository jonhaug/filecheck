package no.nets.portal.file;

public interface Listener {
	byte[] getBytes();
	void signal(long position);
	void endSignal(long position);
}
