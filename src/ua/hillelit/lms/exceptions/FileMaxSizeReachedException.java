package ua.hillelit.lms.exceptions;

import java.io.IOException;

public class FileMaxSizeReachedException extends IOException {

  public FileMaxSizeReachedException(long maxSize, long fileSize, String filePath) {

    super(
        "File Size [" + fileSize + " byte] is Reached Max Size [" + maxSize + " byte]\nFile Path ["
            + filePath + "]");

  }
}
