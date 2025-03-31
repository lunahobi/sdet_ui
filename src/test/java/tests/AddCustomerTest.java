package tests;

import helpers.DataGenerator;
import helpers.PropertyProvider;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.elements.MenuElement;

import static helpers.Endpoint.ADD_CUSTOMER;
import static helpers.Endpoint.CUSTOMERS;
import static pages.BasePage.*;

public class AddCustomerTest extends BaseTest{

    @DataProvider(name = "Customer information")
    public Object[][] dpMethod() {
        String firstPostCode = DataGenerator.generatePostCode();
        String firstFirstName = DataGenerator.generateFirstName(firstPostCode);
        String secondPostCode = DataGenerator.generatePostCode();
        String secondFirstName = DataGenerator.generateFirstName(secondPostCode);
        String thirdPostCode = DataGenerator.generatePostCode();
        String thirdFirstName = DataGenerator.generateFirstName(thirdPostCode);
        return new Object[][] {
                {firstFirstName, PropertyProvider.getInstance().getProperty("last_name.first_example"), firstPostCode},
                {secondFirstName, PropertyProvider.getInstance().getProperty("last_name.second_example"), secondPostCode},
                {thirdFirstName, PropertyProvider.getInstance().getProperty("last_name.third_example"), thirdPostCode}
        };
    }

    @Test(description = "Add customer with generated post code and first name", dataProvider = "Customer information")
    public final void addCustomer(String firstName, String lastName, String postCode){
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
        addCustomerPage.waitUntilOpen().
                addCustomer(firstName, lastName, postCode);

        Assert.assertTrue(isAlertPresent(driver), "Alert is not present");

        String alertText = getAlertText(driver);
        Assert.assertTrue(alertText.contains("Customer added successfully"),
                "Alert text is not expected. Actually: " + alertText);
        acceptAlert(driver);

        Assert.assertEquals(driver.getCurrentUrl(), ADD_CUSTOMER.getUrl(), "Incorrect URL");

        Assert.assertTrue(MenuElement.isAddCustomerPageOpen(), "Add Customer page is not open");
        CustomersPage customersPage = new CustomersPage(driver);
        customersPage.waitUntilOpen();

        Assert.assertEquals(driver.getCurrentUrl(), CUSTOMERS.getUrl(), "Incorrect URL");
        Assert.assertTrue(MenuElement.isCustomersPageOpen(), "Customers page is not open");

        Assert.assertTrue(customersPage.isCustomerPresent(firstName, lastName, postCode),
                "Customer not found in the table: " + firstName + " " + lastName);
    }
}
