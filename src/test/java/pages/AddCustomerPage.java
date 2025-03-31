package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static helpers.Wait.waitUntilVisible;

public class AddCustomerPage extends BasePage{

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCodeInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public AddCustomerPage(final WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Перейти на страницу Add Customer")
    public AddCustomerPage waitUntilOpen() {
        checkOpenPage();
        clickAddCustomerButton();
        waitUntilVisible(driver, firstNameInput); //ожидание загрузки страницы
        return this;
    }

    @Step("Добавить пользователя {firstName}")
    public AddCustomerPage addCustomer(String firstName, String lastName, String postCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postCodeInput.sendKeys(postCode);
        submitButton.click();
        return this;
    }

}
