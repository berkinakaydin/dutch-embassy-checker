package configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
  public static Properties loadProperties() {
    try (InputStream input = new FileInputStream(".env")) {
      Properties prop = new Properties();

      // load a properties file
      prop.load(input);

      return prop;
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
