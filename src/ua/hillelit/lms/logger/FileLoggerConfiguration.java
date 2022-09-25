package ua.hillelit.lms.logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Properties;

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

  public FileLoggerConfiguration load() {
    try (InputStream input = Files.newInputStream(Paths.get("resources/loggerConfig.properties"))) {

      Properties prop = new Properties();

      // load a properties file
      prop.load(input);

      // get the property value
      file = prop.getProperty("logger.file");
      level = levelConfig(prop.getProperty("logger.level"));
      maxSize = Long.parseLong(prop.getProperty("logger.max_size"));
      format = prop.getProperty("logger.format");

    } catch (NoSuchFileException ex) {
      System.out.println("*File [loggerConfig.properties] is empty");
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return new FileLoggerConfiguration(file, level, maxSize, format);
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
