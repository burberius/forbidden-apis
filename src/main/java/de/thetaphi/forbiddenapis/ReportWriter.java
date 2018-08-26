package de.thetaphi.forbiddenapis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.Locale;

public class ReportWriter {
  public static final String FILE_NAME = "forbidden-apis-report.xml";
  private static final String LINE_START = "<forbidden-apis>\n"; // TODO Add version
  private static final String LINE_START_END = "</forbidden-apis>\n";
  private static final String LINE_CLASS_START = "  <class name=\"%s\" file=\"%s\">\n";
  private static final String LINE_CLASS_END = "  </class>\n";
  private static final String LINE_MESSAGE = "    <violation line=\"%d\" description=\"%s\" location=\"%s\" />\n";

  private Logger logger;

  private FileOutputStream outputStream;
  private OutputStreamWriter writer;

  public ReportWriter(Logger logger) {
    this.logger = logger;
    try {
      outputStream = new FileOutputStream(FILE_NAME);
      writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
    } catch (FileNotFoundException e) {
      logger.error(e.getMessage());
    }
  }

  public void start() {
    try {
      writer.append(LINE_START);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void writeFile(String className, String fileName) {
	Formatter formatter = new Formatter(Locale.ENGLISH);
	String line = formatter.format(LINE_CLASS_START, className, fileName).toString();
	formatter.close();
    try {
      writer.append(line);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void writeFileEnd() {
    try {
      writer.append(LINE_CLASS_END);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void writeLine(ForbiddenViolation violation) {
    Formatter formatter = new Formatter(Locale.ENGLISH);
    String line = formatter.format(LINE_MESSAGE, violation.lineNo, violation.description, violation.locationInfo).toString();
    formatter.close();
    try {
      writer.append(line);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public void end() {
    try {
      writer.append(LINE_START_END);
      writer.flush();
      outputStream.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
