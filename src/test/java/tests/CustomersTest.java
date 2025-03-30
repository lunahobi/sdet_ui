package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CustomersPage;
import pages.elements.MenuElement;

import static helpers.Endpoint.CUSTOMERS;

public class CustomersTest extends BaseTest{

    @Test(description = "Sort by First Name")
    public void sortByFirstName() {
        CustomersPage customersPage = new CustomersPage(driver);
        customersPage.waitUntilOpen();

        Assert.assertEquals(driver.getCurrentUrl(), CUSTOMERS.getUrl(), "Incorrect URL");
        Assert.assertTrue(MenuElement.isCustomersPageOpen(), "Customers page is not open");

        customersPage.clickFirstName();

        Assert.assertTrue(customersPage.isSortedDescending(),
                "Клиенты не отсортированы по убыванию");

        customersPage.clickFirstName();

        Assert.assertTrue(customersPage.isSortedAscending(),
                "Клиенты не отсортированы по возрастанию");
    }

    @Test(description = "Delete Client")
    public void deleteClient() {
        CustomersPage customersPage = new CustomersPage(driver);
        customersPage.waitUntilOpen();

        Assert.assertEquals(driver.getCurrentUrl(), CUSTOMERS.getUrl(), "Incorrect URL");
        Assert.assertTrue(MenuElement.isCustomersPageOpen(), "Customers page is not open");

        String customerToDelete = customersPage.findNameWithClosestToAverageLength()
                .orElseThrow(() -> new AssertionError("Нет клиентов для удаления"));

        customersPage.deleteCustomer(customerToDelete);

        Assert.assertTrue(customersPage.isCustomerDeleted(customerToDelete),
                "Клиент не был удален из таблицы");
    }
}
