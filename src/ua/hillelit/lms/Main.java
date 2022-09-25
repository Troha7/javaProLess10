package ua.hillelit.lms;

import ua.hillelit.lms.api.Loggable;
import ua.hillelit.lms.logger.StdoutLogger;
import ua.hillelit.lms.logger.StdoutLoggerConfiguration;

public class Main {

  public static void main(String[] args) {

    //Loggable fileLogger = new FileLogger(new FileLoggerConfiguration());
    Loggable stdoutLogger = new StdoutLogger(new StdoutLoggerConfiguration());

    for (int i = 0; i < 30; i++) {

      stdoutLogger.info("Test Log!");
      stdoutLogger.debug("Test Debug!");

    }

  }

}
