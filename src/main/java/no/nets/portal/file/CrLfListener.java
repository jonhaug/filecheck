package no.nets.portal.file;

public class CrLfListener  {
	public enum EndLine {CR,LF,CRLF,UNKNOWN};
	static double LOAD_FACTOR = 0.9;
	
	StatisticListener cr = new StatisticListener(new byte[]{13});
	StatisticListener lf = new StatisticListener(new byte[]{10});
	StatisticListener crlf = new StatisticListener(new byte[]{13,10});
	public CrLfListener(FilecheckEngine engine) {
		engine.addListener(cr);
		engine.addListener(lf);
		engine.addListener(crlf);
	}
	
	public EndLine getEndLine() {
		int crC = cr.getCount();
		int lfC = lf.getCount();
		int crlfC = crlf.getCount();
		if (crC==0 && lfC==0) return EndLine.UNKNOWN;
		if (within(crlfC,crC) && within(crlfC,lfC)) return EndLine.CRLF;
		if (within(crC,crC+lfC)) return EndLine.CR;
		if (within(lfC,crC+lfC)) return EndLine.LF;
		return EndLine.UNKNOWN;
	}
	
	private boolean within(int a, int b) {
       return ((double) a / (double) b >= LOAD_FACTOR);
	}

	public boolean isFixedLineLength() {
		switch (getEndLine()) {
		case CR:
			return cr.isFixedLineLength();
		case LF:
			return lf.isFixedLineLength();
		case CRLF:
			return crlf.isFixedLineLength();
		default:
			return false;
		}
	}

	

}
