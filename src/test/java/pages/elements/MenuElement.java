package pages.elements;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuElement {

    WebDriver driver;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private static WebElement addCustomerButton;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    private static WebElement customersButton;

    public MenuElement(WebDriver webDriver) {
        try {
            PageFactory.initElements(webDriver, this);
            this.driver = webDriver;
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Нажать на кнопку 'Add Customer'")
    public void clickAddCustomerButton() {
        addCustomerButton.click();
    }

    @Step("Нажать на кнопку 'Customers'")
    public void clickCustomersButton() {
        customersButton.click();
    }

    @Step("Открыта ли страница Add Customer?")
    public static boolean isAddCustomerPageOpen() {
        return addCustomerButton.getAttribute("class").contains("btn-primary");
    }

    @Step("Открыта ли страница Customers?")
    public static boolean isCustomersPageOpen() {
        return customersButton.getAttribute("class").contains("btn-primary");
    }

}
