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

import static helpers.Wait.waitUntilVisible;

public class CustomersPage extends BasePage{

    @FindBy(xpath = "//a[contains(@ng-click, 'fName')]")
    private WebElement sortByFirstName;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> customerRows;

    public CustomersPage(final WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Перейти на страницу Customers")
    public CustomersPage waitUntilOpen() {
        checkOpenPage();
        menuElement.clickCustomersButton();
        waitUntilVisible(driver, sortByFirstName);
        return this;
    }

    @Step("Отображается ли клиент в таблице Customers?")
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
        sortByFirstName.click();
        return this;
    }

    private List<String> getAllFirstNames() {
        return customerRows.stream()
                .map(row -> row.findElement(By.xpath("./td[1]")).getText())
                .collect(Collectors.toList());
    }

    @Step("Сортировка по возрастанию?")
    public boolean isSortedAscending() {
        List<String> names = getAllFirstNames();
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).compareTo(names.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    @Step("Сортировка по убыванию?")
    public boolean isSortedDescending() {
        List<String> names = getAllFirstNames();
        for (int i = 0; i < names.size() - 1; i++) {
            if (names.get(i).compareTo(names.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    @Step("Найти имя с длиной, ближайшей к средней")
    public Optional<String> findNameWithClosestToAverageLength() {
        List<String> names = getAllFirstNames();
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

    @Step("Клиент {name} отсутствует в таблице?")
    public boolean isCustomerDeleted(String name) {
        return getAllFirstNames().stream()
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
