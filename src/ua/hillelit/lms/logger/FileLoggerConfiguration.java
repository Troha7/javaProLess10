package ua.hillelit.lms.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileLoggerConfiguration {

  private String file;
  private LoggingLevel level;
  private long maxSize;
  private String format;

  public FileLoggerConfiguration(String file, LoggingLevel level, long maxSize, String format) {
    this.file = file;
    this.level = level;
    this.maxSize = maxSize;
    this.format = format;
  }

  public FileLoggerConfiguration() {
  }

  public String readConfigFile() throws FileNotFoundException {

    File loggerConfig = new File("LoggerConfig.txt");

    StringBuilder configStr = new StringBuilder();

    try (Reader reader = new FileReader(loggerConfig)) {
      int s;
      while ((s = reader.read()) != -1) {
        configStr.append((char) s);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return configStr.toString();
  }

  private String[] parsingConfigStr(String str) {

    str = str.replaceAll("[\\w-]+:", "");

    String[] strArr = str.split("\n");

    for (int i = 0; i < strArr.length; i++) {
      strArr[i] = strArr[i].trim();
    }

    return strArr;
  }

  public FileLoggerConfiguration load() {

    try {
      String[] configArr = parsingConfigStr(readConfigFile());

      String file = configArr[0];
      LoggingLevel level = levelConfig(configArr[1].toUpperCase());
      long maxSize = Long.parseLong(configArr[2]);
      String format = configArr[3].toUpperCase();

      return new FileLoggerConfiguration(file, level, maxSize, format);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

  }

  private LoggingLevel levelConfig(String config) {

    switch (LoggingLevel.valueOf(config)) {
      case INFO:
        return LoggingLevel.INFO;
      case DEBUG:
        return LoggingLevel.DEBUG;
      default:
        return null;
    }

  }

  public String getFile() {
    return file;
  }

  public LoggingLevel getLevel() {
    return level;
  }

  public long getMaxSize() {
    return maxSize;
  }

  public String getFormat() {
    return format;
  }
}
