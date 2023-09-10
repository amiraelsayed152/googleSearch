package googleSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import static org.testng.Assert.assertTrue;

public class GoogleSearchPage {

    public static WebDriver driver;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get("https://www.google.com/");
    }

    public void search(String keyword) {
        driver.findElement(By.name("q")).sendKeys(keyword);
        driver.findElement(By.name("q")).submit();
    }

    public void changeLanguage(){
        driver.findElement(By.xpath("//*[@id=\"Rzn5id\"]/div/a[2]")).click();
    }

    public void clearKeyword() {
        driver.findElement(By.name("q")).clear();
    }
// Google changed the pagination UI to infinite scrolling
    public void clickOnLoadMore() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        driver.findElement(By.xpath("//*[@id=\"botstuff\"]/div/div[3]/div[4]/a[1]/h3/div")).click();
    }

    public boolean isNumberOfResultsExistsOnUI() {
         return driver.findElement(By.xpath("//*[@id=\"result-stats\"]")).isDisplayed();
    }

    public boolean areDifferentSearchSuggestionsDisplayed() {
        return driver.findElement(By.xpath("//*[@id=\"bres\"]/div[3]/div/div/div/div[1]/div/div/span")).isDisplayed();
    }

    public static void main(String[] args) {
        //for mac
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");

        //for windows
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/Windows_OS_ChromeDriver");
        WebDriver driver = new ChromeDriver();

        GoogleSearchPage searchPage = new GoogleSearchPage(driver);
        try {
            searchPage.search("foodics");
            searchPage.changeLanguage();
            assertTrue(searchPage.isNumberOfResultsExistsOnUI());

            searchPage.clearKeyword();
            searchPage.search("selenium");
            assertTrue(searchPage.isNumberOfResultsExistsOnUI());
            searchPage.clickOnLoadMore();
            searchPage.areDifferentSearchSuggestionsDisplayed();
            assertTrue(searchPage.areDifferentSearchSuggestionsDisplayed());
        }
        finally {
            driver.close();
            driver.quit();
        }
    }
}