package be.vinci.pae.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class Loggers.
 */
public class Loggers {

  /** The logger. */
  private static Logger logger = Logger.getLogger(Logger.class.getName());


  /**
   * Inits the.
   *
   * @param fichier the fichier
   */
  public static void init(String fichier) {
    logger.setLevel(Level.ALL);
    logger.setUseParentHandlers(false);
    try {
      FileHandler fileHandler = new FileHandler(fichier, true);
      logger.addHandler(fileHandler);
    } catch (SecurityException | IOException exception) {
      exception.printStackTrace();
    }
    String strInit = "\n\n-------------------Nouveau cycle de vie de l'application ("
        + LocalDateTime.now() + ")-------------------\n";
    try {
      byte[] strInitBytes = strInit.getBytes(Charset.forName("UTF-8"));
      Files.write(Paths.get(fichier), strInitBytes, StandardOpenOption.APPEND);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }


  /**
   * Gets the logger.
   *
   * @return the logger
   */
  public static Logger getLogger() {
    return logger;
  }


  /**
   * Log exception.
   *
   * @param level the level
   * @param exception the exception
   */
  public static void logException(Level level, Exception exception) {
    String stackTraceString = Arrays.toString(exception.getStackTrace()).replaceAll(",", "\n\t");
    String stackTraceFormatee = stackTraceString.substring(1, stackTraceString.length() - 1);
    logger.log(level,
        LocalDateTime.now() + "\n\t" + exception.getMessage() + "\n\t" + stackTraceFormatee);

  }



}
