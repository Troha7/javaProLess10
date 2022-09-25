package ua.hillelit.lms.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ua.hillelit.lms.api.Configurable;
import ua.hillelit.lms.api.Loggable;

public class StdoutLogger implements Loggable {

  private final Configurable config;

  public StdoutLogger(Configurable config) {
    this.config = config;
  }

  private String logMassage(String level, String massage) {

    String logFormat = config.getFormat();
    String date = currentDate();

    return String.format(logFormat, date, level, massage) + "\n";
  }

  public void debug(String massage) {

    String levelName = LoggingLevel.DEBUG.name();

    if (config.getLevel() == LoggingLevel.DEBUG) {
      System.out.print(logMassage(levelName, massage));
    }

  }

  public void info(String massage) {

    String levelName = LoggingLevel.INFO.name();

    System.out.print(logMassage(levelName, massage));

  }

  private String currentDate() {

    DateFormat dateFormat = new SimpleDateFormat("d.M.y-HH:mm:ss");
    Date date = new Date();

    return dateFormat.format(date);
  }

}
