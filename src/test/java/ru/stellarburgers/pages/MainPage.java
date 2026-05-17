package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    // Шапка
    private final By loginToAccountButton =
            By.xpath("//button[contains(text(),'Войти')]");
    private final By personalCabinetLink =
            By.xpath("//a[contains(@href,'/account')]");
    private final By logoLink =
            By.xpath("//a[contains(@href,'/') and .//*[contains(@class,'logo')] or contains(@class,'logo')]");
    private final By constructorHeaderLink =
            By.xpath("//a[@href='/']");

    // Вкладки конструктора
    private final By bunsTab =
            By.xpath("//div[contains(@class,'tab_tab')]//span[text()='Булки']");
    private final By saucesTab =
            By.xpath("//span[text()='Соусы']");
    private final By fillingsTab =
            By.xpath("//span[text()='Начинки']");

    // Заголовки разделов
    private final By bunsHeader   = By.xpath("//h2[contains(text(),'Булки')]");
    private final By saucesHeader = By.xpath("//h2[contains(text(),'Соусы')]");
    private final By fillingsHeader = By.xpath("//h2[contains(text(),'Начинки')]");

    public MainPage(WebDriver driver) { super(driver); }

    @Step("Открыть главную страницу")
    public void open() { driver.get(BASE_URL); }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной")
    public LoginPage clickLoginToAccountButton() {
        jsClick(loginToAccountButton);
        return new LoginPage(driver);
    }

    @Step("Нажать ссылку 'Личный кабинет' в шапке")
    public LoginPage clickPersonalCabinetLink() {
        jsClick(personalCabinetLink);
        return new LoginPage(driver);
    }

    @Step("Нажать на логотип")
    public MainPage clickLogo() {
        // Логотип — картинка внутри ссылки или div; кликаем JS
        By logo = By.xpath("//*[contains(@class,'logo')]");
        jsClick(logo);
        return this;
    }

    @Step("Нажать 'Конструктор' в шапке")
    public MainPage clickConstructorHeaderLink() {
        jsClick(constructorHeaderLink);
        return this;
    }

    @Step("Нажать вкладку 'Булки'")
    public void clickBunsTab() { jsClick(bunsTab); }

    @Step("Нажать вкладку 'Соусы'")
    public void clickSaucesTab() { jsClick(saucesTab); }

    @Step("Нажать вкладку 'Начинки'")
    public void clickFillingsTab() { jsClick(fillingsTab); }

    private void jsClick(By locator) {
        WebElement el = waitVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    @Step("Проверить, что заголовок 'Булки' виден")
    public boolean isBunsSectionVisible() { return isElementVisible(bunsHeader); }

    @Step("Проверить, что заголовок 'Соусы' виден")
    public boolean isSaucesSectionVisible() { return isElementVisible(saucesHeader); }

    @Step("Проверить, что заголовок 'Начинки' виден")
    public boolean isFillingsSectionVisible() { return isElementVisible(fillingsHeader); }

    @Step("Проверить, что ссылка 'Личный кабинет' доступна (пользователь вошёл)")
    public boolean isPersonalCabinetLinkVisible() {
        // После входа в шапке появляется ссылка на /account
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(personalCabinetLink));
            return true;
        } catch (Exception e) { return false; }
    }

    @Step("Проверить, что кнопка 'Войти в аккаунт' отображается")
    public boolean isLoginToAccountButtonVisible() {
        return isElementVisible(loginToAccountButton);
    }
}
