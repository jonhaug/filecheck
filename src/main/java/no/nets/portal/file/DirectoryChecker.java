package no.nets.portal.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import no.nets.portal.file.FileChecker.Result;

public class DirectoryChecker {
	File[] allFiles;
	public DirectoryChecker(File directory) {
		if (! directory.isDirectory()) {
			throw new IllegalArgumentException("This is not a directory");
		}
		allFiles = directory.listFiles();
	}
	public List<FileResult> check() {
		List<FileResult> fileResults = new ArrayList<FileResult>();
		for (File file: allFiles) {
			FileResult fileResult;
			try {
				if (! file.isDirectory()) {
				FileChecker fileChecker = new FileChecker(file);
				Result result = fileChecker.check();
				fileResult = new FileResult(file,result);
				} else {
					fileResult = null;
				}
			} catch (Exception e) {
				fileResult = new FileResult(file,e);
			}
			if (fileResult != null) fileResults.add(fileResult);
		}
		return fileResults;
	}
	public static class FileResult {
		public final Result result;
		public final File file;
		public final Exception exception;

		public FileResult(File file, Exception e) {
			this.file = file;
			this.exception = e;
			this.result = null;
		}
		public FileResult(File file, Result result) {
			this.result = result;
			this.file = file;
			this.exception = null;
		}
		public String toString() {
			return file.getName() + ": " + (result==null ? exception.toString() : result.toString());
		}
	}
	public static void main(String[] args) {
		File dir = new File(".");
		DirectoryChecker directoryChecker = new DirectoryChecker(dir);
		List<FileResult> fileResults = directoryChecker.check();
		for (FileResult fileResult: fileResults) {
			System.out.println(fileResult.toString());
		}
	}

}
