package ua.hillelit.lms.api;

import ua.hillelit.lms.logger.LoggingLevel;

public interface Configurable {

  LoggingLevel getLevel();
  String getFormat();

}
