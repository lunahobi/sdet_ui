package tests;

import helpers.PropertyProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    WebDriver driver;

    @BeforeClass
    public void init(final ITestContext context){
        String browserName = PropertyProvider.getInstance().getProperty("browser.name");
        int pageLoadTimeout = Integer.parseInt(PropertyProvider.getInstance().getProperty("page.load.timeout"));
        WebDriverManager.getInstance(browserName).setup();

        switch (browserName){
            case "chrome" -> driver = new ChromeDriver(new ChromeOptions()
                    .addArguments("--remote-allow-origins=*")
                    .addArguments(" disable gpu")
                    .addArguments("--start-maximized"));
            default -> throw  new IllegalStateException("Unexpected value: " + browserName);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        context.setAttribute("driver", driver);
        String webUrl = PropertyProvider.getInstance().getProperty("web.url");
        driver.get(webUrl);
    }

    @AfterTest
    public final void tearDown(){
        driver.quit();
    }

}
