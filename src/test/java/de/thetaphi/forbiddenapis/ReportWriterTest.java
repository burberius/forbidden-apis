package de.thetaphi.forbiddenapis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterTest {
	private static final String[] RESULT = {"<forbidden-apis>", "  <class name=\"lang.Object\" file=\"Object.java\">"
			,"    <violation line=\"42\" description=\"This is a test\" location=\"Somewhere\" />"
			,"  </class>", "</forbidden-apis>"};
	
	@Before
	public void setUp() {
		File report = new File(ReportWriter.FILE_NAME);
		report.delete();
	}
	
	@Test
	public void write() throws FileNotFoundException {
		ReportWriter reportWriter = new ReportWriter(StdIoLogger.INSTANCE);
		reportWriter.start();
		reportWriter.writeFile("lang.Object", "Object.java");
		ForbiddenViolation violation = new ForbiddenViolation(1, "This is a test", "Somewhere", 42);
		reportWriter.writeLine(violation);
		reportWriter.writeFileEnd();
		reportWriter.end();
		
		Scanner scnr = new Scanner(new FileInputStream(ReportWriter.FILE_NAME), "UTF-8");
		int pos = 0;
		while (scnr.hasNextLine()) {
			Assert.assertEquals(RESULT[pos], scnr.nextLine());
			pos++;
		}
		scnr.close();
	}
}
