package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.elements.MenuElement;

import static helpers.Wait.waitUntilVisible;

public class BasePage {
    protected final WebDriver driver;

    MenuElement menuElement;

    @FindBy(className = "mainHeading")
    private WebElement header;


    public BasePage(final WebDriver webDriver) {
        try {
            PageFactory.initElements(webDriver, this);
            this.driver = webDriver;
            menuElement = new MenuElement(driver);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Ожидание загрузки страницы менеджера банка")
    public void checkOpenPage() {
        waitUntilVisible(driver, header);
    }

    @Step("Отображается ли alert на странице?")
    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    @Step("Получить текст Alert")
    public static String getAlertText(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    @Step("Принять Alert")
    public static void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }


}
