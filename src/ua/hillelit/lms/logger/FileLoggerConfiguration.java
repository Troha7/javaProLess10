package ua.hillelit.lms.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import ua.hillelit.lms.exceptions.LoggerConfigFormatException;

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

    validateConfigFormat(strArr);

    return strArr;
  }

  private void validateConfigFormat(String[] strArr) {
    String time = "CURRENT_TIME";
    if (!strArr[3].contains(time)) {
      throw new LoggerConfigFormatException("FORMAT: " + strArr[3] +
          " wrong keyword [" + time + "]");
    }

    String level = "LEVEL";
    if (!strArr[3].contains(level)) {
      throw new LoggerConfigFormatException("FORMAT: " + strArr[3] +
          " wrong keyword [" + level + "]");
    }

    String message = "STRING-MESSAGE";
    if (!strArr[3].contains(message)) {
      throw new LoggerConfigFormatException("FORMAT: " + strArr[3] +
          " wrong keyword [" + message + "]");
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

  public FileLoggerConfiguration load() {

    try {

      String[] configArr = parsingConfigStr(readConfigFile());

      String file = configArr[0];
      LoggingLevel level = levelConfig(configArr[1]);
      long maxSize = Long.parseLong(configArr[2]);
      String format = configArr[3];

      return new FileLoggerConfiguration(file, level, maxSize, format);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
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
