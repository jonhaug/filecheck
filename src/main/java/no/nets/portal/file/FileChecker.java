package no.nets.portal.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import no.nets.portal.file.CrLfListener.EndLine;

public class FileChecker {

	InputStream stream;
	
	public FileChecker(File file) {
		try {
			stream = new FileInputStream(file);
		} catch (IOException ioe) {
			throw new RuntimeException("Could not open " + file, ioe);
		}
		
	}
	public Result check() {
		FilecheckEngine engine = new FilecheckEngine();
		CrLfListener crLfListener = new CrLfListener(engine);
		BinaryListener binaryListener = new BinaryListener(engine);
		GuessCharSetListener gcsListener = new GuessCharSetListener(engine);
		try {
			engine.work(stream);
			Result result = new Result(crLfListener.getEndLine(),binaryListener.isBinary(), crLfListener.isFixedLineLength(),
					gcsListener.getCharSet());
			return result;
		} catch (IOException ioe) {
			throw new RuntimeException("Read error", ioe);
		} finally {
			try { stream.close(); } catch (IOException ioe2) {}
		}
	}
	
	public static class Result {
		public final EndLine endLine;
		public final boolean binary;
		public final boolean sameLength;
		public final String charSet;
		public Result(EndLine endLine, boolean binary, boolean sameLength, String charSet) {
			this.endLine = endLine;
			this.binary = binary;
			this.sameLength = sameLength;
			this.charSet = charSet;
		}
		public String toString() {
			return "newLine=" + endLine + " sameLength=" + sameLength + " binary=" + binary +
			" charSet=" + charSet;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (binary ? 1231 : 1237);
			result = prime * result
					+ ((endLine == null) ? 0 : endLine.hashCode());
			result = prime * result + (sameLength ? 1231 : 1237);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Result other = (Result) obj;
			if (binary != other.binary)
				return false;
			if (endLine == null) {
				if (other.endLine != null)
					return false;
			} else if (!endLine.equals(other.endLine))
				return false;
			if (sameLength != other.sameLength)
				return false;
			return true;
		}
		
	}
	public static void main(String[] args) {
		File file = new File("testfile");
		FileChecker fileChecker = new FileChecker(file);
		Result result = fileChecker.check();
		System.out.println(result);
	}
}
