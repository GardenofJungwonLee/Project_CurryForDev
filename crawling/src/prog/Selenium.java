package prog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {

   private WebDriver driver;
   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
   public static final String WEB_DRIVER_PATH = "src/chromedriver.exe";

   public Selenium() {
      System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
      driver = new ChromeDriver();
   }
   
   public WebDriver getDriver() {
      return driver;
   }
}