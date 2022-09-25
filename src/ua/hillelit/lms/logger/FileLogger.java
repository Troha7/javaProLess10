package ua.hillelit.lms.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ua.hillelit.lms.exceptions.FileMaxSizeReachedException;

public class FileLogger {

  private long fileSize;
  private int countLog;

  private final FileLoggerConfiguration config;


  public FileLogger(FileLoggerConfiguration config) {
    this.config = config;
    createLogFile();
  }

  private String getFileName() {

    String fileName = countLog + "_" + currentDate("d.M.y-HH:mm");

    return config.getFile().replace(".", fileName + ".");
  }

  private void createLogFile() {

    try {

      File log = new File(getFileName());
      if (log.createNewFile()) {
        System.out.println("Log File created");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private String logMassage(String level, String massage) {

    String logFormat = config.getFormat();
    String date = currentDate("d.M.y-HH:mm:ss");

    return String.format(logFormat, date, level, massage) + "\n";
  }

  private void writeToLog(String level, String massage) {

    fileSize += logMassage(level, massage).length();

    if (fileSize >= config.getMaxSize()) {

      try {
        throw new FileMaxSizeReachedException(config.getMaxSize(), fileSize, config.getFile());
      } catch (FileMaxSizeReachedException e) {
        System.out.println("Log File size is reached!\nCreate NEW Log File!");
      }

      countLog++;
      createLogFile();
      fileSize = 0;

    }

    try (Writer writer = new FileWriter(getFileName(), true)) {
      writer.write(logMassage(level, massage));
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void debug(String massage) {

    String levelName = LoggingLevel.DEBUG.name();

    if (config.getLevel() == LoggingLevel.DEBUG) {
      writeToLog(levelName, massage);
    }

  }

  public void info(String massage) {

    String levelName = LoggingLevel.INFO.name();

    writeToLog(levelName, massage);

  }

  private String currentDate(String pattern) {

    DateFormat dateFormat = new SimpleDateFormat(pattern);
    Date date = new Date();

    return dateFormat.format(date);
  }

}
