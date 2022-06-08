import configs.PropertiesLoader;
import utils.SendMail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Scenarios {

  public static WebDriver driver = Main.getDriver();
  private static int checkCount = 0;

  public void navigateTo(String url) {
    driver.get(url);
  }

  public void clickLink(String link) {
    if (!driver.findElements(By.linkText(link)).isEmpty())
      driver.findElement(By.linkText(link)).click();
  }

  public void clickButton(String button) {
    driver.findElement(By.id(button)).click();
  }

  public void pickItemByName(String item, String dropdown) {
    Select drpCity = new Select(driver.findElement(By.id(dropdown)));
    drpCity.selectByVisibleText(item);
  }

  public void pickItemByValue(String value, String dropdown) {
    Select drpCity = new Select(driver.findElement(By.id(dropdown)));
    drpCity.selectByValue(value);
  }

  public boolean checkAvailability() {
    String labelId = "plhMain_lblMsg";
    String matchFail = "No date(s) available for appointment.";
    String matchFail2 = "No date(s) available for current month.";
    String matchErr = "Error in the application, please contact admin.";
    WebElement lbl = driver.findElement(By.id(labelId));

    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      checkCount++;
      FileUtils.copyFile(scrFile, new File("tmp/screenshot" + checkCount + ".png"));

      String content;

      if ((checkCount == 1 || checkCount == 3)
          && (matchFail.equals(lbl.getText()) || matchFail2.equals(lbl.getText()) || matchErr.equals(lbl.getText()))) {
        return false;
      } else if ((checkCount == 2 || checkCount == 3)
          && !(matchFail.equals(lbl.getText()) || matchFail2.equals(lbl.getText()) || matchErr.equals(lbl.getText()))) {
        content = "RUN";
        SendMail.sendMail(content, checkCount);
       
        return true;
      }

    } catch (MessagingException | IOException e) {
      System.err.println(e);
      e.printStackTrace();
    }

    return true;
  }

  public void fillInput() {
    Properties conf = PropertiesLoader.loadProperties();
    String title = conf.getProperty("TITLE");
    String name = conf.getProperty("NAME");
    String surname = conf.getProperty("SURNAME");
    String phone = conf.getProperty("PHONE");
    String email = conf.getProperty("EMAIL");

    String titleId = "plhMain_repAppVisaDetails_cboTitle_0";
    String nameId = "plhMain_repAppVisaDetails_tbxFName_0";
    String surnameId = "plhMain_repAppVisaDetails_tbxLName_0";
    String phoneId = "plhMain_repAppVisaDetails_tbxContactNumber_0";
    String emailId = "plhMain_repAppVisaDetails_tbxEmailAddress_0";
    String confirmId = "plhMain_cboConfirmation";

    Select drpTitle = new Select(driver.findElement(By.id(titleId)));
    drpTitle.selectByVisibleText(title);

    WebElement nameInput = driver.findElement(By.id(nameId));
    nameInput.sendKeys(name);

    WebElement surnameInput = driver.findElement(By.id(surnameId));
    surnameInput.sendKeys(surname);

    WebElement phoneInput = driver.findElement(By.id(phoneId));
    phoneInput.sendKeys(phone);

    WebElement emailInput = driver.findElement(By.id(emailId));
    emailInput.sendKeys(email);

    Select drpConfirm = new Select(driver.findElement(By.id(confirmId)));
    drpConfirm.selectByValue("1");
  }

  public void waitFor(String seconds) {
    try {
      Thread.sleep(1000 * Integer.parseInt(seconds));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
