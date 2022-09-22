package ua.hillelit.lms;

import ua.hillelit.lms.logger.FileLogger;
import ua.hillelit.lms.logger.FileLoggerConfiguration;

public class Main {

  public static void main(String[] args) {

    FileLogger logger = new FileLogger(new FileLoggerConfiguration().load());

    for (int i = 0; i < 30; i++) {

      logger.info("Test Log!");
      logger.debug("Test Debug!");

    }

  }

}
