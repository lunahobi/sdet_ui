package pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static helpers.Wait.waitThenClick;

public class MenuElement {

    WebDriver driver;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    static WebElement addCustomerButton;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    static WebElement customersButton;

    public MenuElement(WebDriver webDriver) {
        try {
            PageFactory.initElements(webDriver, this);
            this.driver = webDriver;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickAddCustomerButton() {
        waitThenClick(driver, addCustomerButton);
    }

    public void clickCustomersButton() {
        waitThenClick(driver, customersButton);
    }

    public static boolean isAddCustomerPageOpen() {
        return addCustomerButton.getAttribute("class").contains("btn-primary");
    }

    public static boolean isCustomersPageOpen() {
        return customersButton.getAttribute("class").contains("btn-primary");
    }

}
