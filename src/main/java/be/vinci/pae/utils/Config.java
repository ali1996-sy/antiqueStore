package be.vinci.pae.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
public class Config {

  /** The prop. */
  private static Properties prop;



  /**
   * Load property file.
   *
   * @param path the path
   */
  public static void load(String path) {
    prop = new Properties();
    try (FileInputStream fis = new FileInputStream(path)) {
      prop.load(fis);
    } catch (FileNotFoundException err) {
      throw new InternalError("fichier inconnu ");
    } catch (IOException err) {
      throw new InternalError("erreur d'ouverture du fichier \"" + path + "\"");
    }
  }


  /**
   * Gets the properties.
   *
   * @param key the key
   * @return the properties
   */
  public static String getProperties(String key) {
    return prop.getProperty(key);
  }

  /**
   * Check if we gets the properties.
   *
   * @param key the key
   * @return the boolean of the check
   */
  public static boolean getBoolProperty(String key) {
    return Boolean.parseBoolean(prop.getProperty(key));
  }
}
