
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import configs.PropertiesLoader;

public class Main {
  private static WebDriver driver;

  public static WebDriver getDriver() {
    return driver;
  }

  public static void main(String[] args) {
    Properties conf = PropertiesLoader.loadProperties();

    System.setProperty("webdriver.chrome.driver", "app/bin/chromedriver");
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments(
        "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.5005.61 Safari/537.36");
    chromeOptions.addArguments("--window-size=1920,1080");
    if (Boolean.parseBoolean(conf.getProperty("HEADLESS")))
      chromeOptions.addArguments("--headless");

    driver = new ChromeDriver(chromeOptions);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
    driver.manage().window().maximize();

    String filePath = "tmp";
    File file = new File(filePath);
    try {
      FileUtils.deleteDirectory(file);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    new Story();

    quit();
  }

  public static void quit() {
    if (!driver.toString().contains("(null)")) {
      driver.quit();
      System.exit(0);
    }
  }
}
