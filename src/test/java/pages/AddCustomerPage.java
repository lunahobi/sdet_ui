package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static helpers.Wait.waitThenClick;
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

    public AddCustomerPage waitUntilOpen() {
        checkOpenPage();
        clickAddCustomerButton();
        return this;
    }

    @Step("Добавить пользователя")
    public AddCustomerPage addCustomer(String firstName, String lastName, String postCode) {
        waitUntilVisible(driver, firstNameInput);
        waitUntilVisible(driver, lastNameInput);
        waitUntilVisible(driver, postCodeInput);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postCodeInput.sendKeys(postCode);
        waitThenClick(driver, submitButton);
        return this;
    }



}
