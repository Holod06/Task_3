package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PersonalCabinetPage extends BasePage {

    // Кнопка выхода — ищем любую кнопку с текстом «Выйти» или «Выход»
    private final By logoutButton =
            By.xpath("//button[contains(text(),'Выйти') or contains(text(),'Выход')]");

    // Поле имени в профиле
    private final By nameField =
            By.xpath("//input[@name='name' or @placeholder='Имя']");

    // Ссылка «Конструктор» в шапке (по href)
    private final By constructorLink =
            By.xpath("//a[@href='/']");

    // Логотип
    private final By logoLink =
            By.xpath("//*[contains(@class,'logo')]");

    // Ссылка «Профиль» в левом меню кабинета
    private final By profileMenuLink =
            By.xpath("//a[contains(@href,'/account/profile')]");

    public PersonalCabinetPage(WebDriver driver) { super(driver); }

    @Step("Открыть страницу личного кабинета напрямую")
    public void open() { driver.get(BASE_URL + "/account/profile"); }

    @Step("Нажать кнопку 'Выйти'")
    public LoginPage clickLogoutButton() {
        jsClick(logoutButton);
        return new LoginPage(driver);
    }

    @Step("Нажать 'Конструктор' в шапке")
    public MainPage clickConstructorLink() {
        jsClick(constructorLink);
        return new MainPage(driver);
    }

    @Step("Нажать на логотип")
    public MainPage clickLogo() {
        jsClick(logoLink);
        return new MainPage(driver);
    }

    private void jsClick(By locator) {
        WebElement el = waitVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    @Step("Проверить, что личный кабинет открыт")
    public boolean isPersonalCabinetOpened() {
        // Проверяем по URL — надёжнее чем локатор
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        return driver.getCurrentUrl().contains("/account");
    }
}
