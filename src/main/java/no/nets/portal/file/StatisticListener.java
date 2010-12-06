package no.nets.portal.file;

import java.util.Arrays;

public class StatisticListener extends ConstListener implements Listener {
	long prevPos = -1;
	static int LIMIT=200;
	int[] counts = new int[LIMIT+1];

	public StatisticListener(byte[] bytes) {
		super(bytes);
		Arrays.fill(counts, 0);
	}
	
	@Override
	public void signal(long position) {
		super.signal(position);
		update(position);
	}
	
	protected void update(long position) {
		int sz = (int) (position - prevPos);
		if (sz > LIMIT) sz = LIMIT;
		counts[sz] ++;
		prevPos = position;
	}
	
	@Override
	public void endSignal(long position) {
		super.endSignal(position);
		position--;
		if (position > prevPos) update(position);
	}
	
	public boolean isFixedLineLength() {
		int lineLength = -1;
		for (int sz: counts) {
			if (sz > 0) {
				if (lineLength != -1) return false;
				lineLength = sz;
			}
		}
		return lineLength != LIMIT;
	}

}
