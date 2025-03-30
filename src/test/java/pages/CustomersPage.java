package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static helpers.Wait.waitThenClick;
import static helpers.Wait.waitUntilVisible;

public class CustomersPage extends BasePage{

    @FindBy(xpath = "//a[contains(@ng-click, 'fName')]")
    WebElement sortByFirstName;

    @FindBy(xpath = "//tbody/tr")
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

    @Step("Нажать на First Name")
    public CustomersPage clickFirstName() {
        waitThenClick(driver, sortByFirstName);
        return this;
    }

    public List<String> getAllFirstNames() {
        return customerRows.stream()
                .map(row -> row.findElement(By.xpath("./td[1]")).getText())
                .collect(Collectors.toList());
    }

    public boolean isSortedAscending() {
        List<String> names = getAllFirstNames();
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).compareTo(names.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isSortedDescending() {
        List<String> names = getAllFirstNames();
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).compareTo(names.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    public List<String> getCustomerNames() {
        return customerRows.stream()
                .map(row -> row.findElement(By.xpath("./td[1]")).getText())
                .toList();
    }

    @Step("Найти имя с длиной, ближайшей к средней")
    public Optional<String> findNameWithClosestToAverageLength() {
        List<String> names = getCustomerNames();
        if (names.isEmpty()) {
            return Optional.empty();
        }

        double averageLength = calculateAverageNameLength(names);
        System.out.println(averageLength);
        return findClosestToAverage(names, averageLength);
    }

    @Step("Удалить клиента с именем: {nameToDelete}")
    public void deleteCustomer(String nameToDelete) {
        customerRows.stream()
                .filter(row -> row.findElement(By.xpath("./td[1]")).getText().equals(nameToDelete))
                .findFirst()
                .ifPresent(row -> row.findElement(By.xpath("./td[5]/button")).click());
    }

    @Step("Проверить, что клиент {name} отсутствует в таблице")
    public boolean isCustomerDeleted(String name) {
        return getCustomerNames().stream()
                .noneMatch(n -> n.equals(name));
    }

    private double calculateAverageNameLength(List<String> names) {
        return names.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);
    }

    private Optional<String> findClosestToAverage(List<String> names, double average) {
        return names.stream()
                .min(Comparator.comparingDouble(
                        name -> Math.abs(name.length() - average)));
    }
}
