package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static helpers.Wait.waitUntilVisible;

public class CustomersPage extends BasePage{

    @FindBy(xpath = "//a[contains(@ng-click, 'fName')]")
    WebElement sortByFirstName;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr")
    private List<WebElement> customerRows;

    public CustomersPage(final WebDriver webDriver) {
        super(webDriver);
    }

    public CustomersPage waitUntilOpen() {
        checkOpenPage();
        clickCustomersButton();
        waitUntilVisible(driver, sortByFirstName);
        return this;
    }

    public boolean isCustomerPresent(String firstName, String lastName, String postCode) {
        return customerRows.stream()
                .anyMatch(row -> {
                    String rowText = row.getText();
                    return rowText.contains(firstName) &&
                            rowText.contains(lastName) &&
                            rowText.contains(postCode);
                });
    }
}
