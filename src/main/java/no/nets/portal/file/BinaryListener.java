package no.nets.portal.file;

public class BinaryListener  {

	int count;
	long size;
	ConstListener[] blisten = new ConstListener[32];

	public BinaryListener(FilecheckEngine engine) {
		for (int i=0;i<32;i++) {
			blisten[i] = new ConstListener(new byte[]{(byte) i});
			engine.addListener(blisten[i]);
		}
	}
	
	public void count() {
		count = 0; size=0;
		for (int i=0;i<32;i++) {
			if (i != 10 && i != 13) {
				count += blisten[i].getCount();
			}
			if (blisten[i].getSize() > size) size=blisten[i].getSize();
		}
	}
	public boolean isBinary() {
		count();
		return size>2 && count*100/size > 3;
	}

}
