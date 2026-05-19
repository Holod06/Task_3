package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    // Шапка
    private final By personalCabinetLink =
            By.xpath("//a[@href='/account']");
    private final By constructorHeaderLink =
            By.xpath("//a[@href='/']//p[contains(@class,'AppHeader_header__linkText')]");
    private final By logoLink =
            By.xpath("//div[contains(@class,'AppHeader_header__logo')]//a[@href='/']");
    private final By loginToAccountButton =
            By.xpath("//button[text()='Войти в аккаунт']");

    // Заголовки разделов
    private final By bunsHeader   = By.xpath("//h2[text()='Булки']");
    private final By saucesHeader  = By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeader = By.xpath("//h2[text()='Начинки']");

    // Вкладки :
    private final By bunsTab =
            By.xpath("//div[contains(@class,'tab_tab__') and .//span[text()='Булки']]");
    private final By saucesTab =
            By.xpath("//div[contains(@class,'tab_tab__') and .//span[text()='Соусы']]");
    private final By fillingsTab =
            By.xpath("//div[contains(@class,'tab_tab__') and .//span[text()='Начинки']]");

    // Активные вкладки
    private final By activeBunsTab =
            By.xpath("//div[contains(@class,'tab_tab_type_current') and .//span[text()='Булки']]");
    private final By activeSaucesTab =
            By.xpath("//div[contains(@class,'tab_tab_type_current') and .//span[text()='Соусы']]");
    private final By activeFillingsTab =
            By.xpath("//div[contains(@class,'tab_tab_type_current') and .//span[text()='Начинки']]");

    public MainPage(WebDriver driver) { super(driver); }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(personalCabinetLink));
    }

    @Step("Нажать кнопку 'Войти в аккаунт' на главной")
    public LoginPage clickLoginToAccountButton() {
        waitClickable(loginToAccountButton).click();
        return new LoginPage(driver);
    }

    @Step("Нажать ссылку 'Личный кабинет' в шапке")
    public LoginPage clickPersonalCabinetLink() {
        waitClickable(personalCabinetLink).click();
        return new LoginPage(driver);
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickLogo() {
        waitClickable(logoLink).click();
        return this;
    }

    @Step("Нажать на 'Конструктор' в шапке")
    public MainPage clickConstructorHeaderLink() {
        waitClickable(constructorHeaderLink).click();
        return this;
    }

    @Step("Нажать вкладку 'Булки' и дождаться её активации на UI")
    public void clickBunsTab() {
        WebElement el = waitVisible(bunsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        // Ждём пока вкладка визуально станет активной (класс tab_tab_type_current появится в DOM)
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeBunsTab));
    }

    @Step("Нажать вкладку 'Соусы' и дождаться её активации на UI")
    public void clickSaucesTab() {
        WebElement el = waitVisible(saucesTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        // Ждём пока вкладка визуально станет активной
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeSaucesTab));
    }

    @Step("Нажать вкладку 'Начинки' и дождаться её активации на UI")
    public void clickFillingsTab() {
        WebElement el = waitVisible(fillingsTab);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        // Ждём пока вкладка визуально станет активной
        wait.until(ExpectedConditions.visibilityOfElementLocated(activeFillingsTab));
    }

    @Step("Проверить, что вкладка 'Булки' активна на UI")
    public boolean isBunsTabActive() {
        return isElementVisible(activeBunsTab);
    }

    @Step("Проверить, что вкладка 'Соусы' активна на UI")
    public boolean isSaucesTabActive() {
        return isElementVisible(activeSaucesTab);
    }

    @Step("Проверить, что вкладка 'Начинки' активна на UI")
    public boolean isFillingsTabActive() {
        return isElementVisible(activeFillingsTab);
    }

    @Step("Проверить, что ссылка 'Личный кабинет' есть в шапке")
    public boolean isPersonalCabinetLinkVisible() {
        return isElementVisible(personalCabinetLink);
    }

    @Step("Проверить, что кнопка 'Войти в аккаунт' отображается")
    public boolean isLoginToAccountButtonVisible() {
        return isElementVisible(loginToAccountButton);
    }

    @Step("Проверить, что раздел 'Булки' виден")
    public boolean isBunsSectionVisible() {
        return isElementVisible(bunsHeader);
    }

    @Step("Проверить, что раздел 'Соусы' виден")
    public boolean isSaucesSectionVisible() {
        return isElementVisible(saucesHeader);
    }

    @Step("Проверить, что раздел 'Начинки' виден")
    public boolean isFillingsSectionVisible() {
        return isElementVisible(fillingsHeader);
    }
}
