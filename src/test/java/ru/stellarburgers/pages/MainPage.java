package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    // --- Шапка ---
    private final By loginToAccountButton =
            By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalCabinetLink =
            By.xpath("//p[text()='Личный кабинет']");
    private final By logoLink =
            By.xpath("//*[contains(@class,'AppHeader_header__logo')]");
    private final By constructorLink =
            By.xpath("//p[text()='Конструктор']");

    // --- Вкладки конструктора (li или div с текстом) ---
    private final By bunsTab =
            By.xpath("//span[text()='Булки']/ancestor::div[contains(@class,'tab')]");
    private final By saucesTab =
            By.xpath("//span[text()='Соусы']/ancestor::div[contains(@class,'tab')]");
    private final By fillingsTab =
            By.xpath("//span[text()='Начинки']/ancestor::div[contains(@class,'tab')]");

    // --- Заголовки разделов в скролл-списке ---
    private final By bunsHeader =
            By.xpath("//h2[text()='Булки']");
    private final By saucesHeader =
            By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeader =
            By.xpath("//h2[text()='Начинки']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(BASE_URL);
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
    public MainPage clickConstructorLink() {
        waitClickable(constructorLink).click();
        return this;
    }

    @Step("Нажать на вкладку 'Булки'")
    public void clickBunsTab() {
        scrollToElementAndClick(bunsTab);
    }

    @Step("Нажать на вкладку 'Соусы'")
    public void clickSaucesTab() {
        scrollToElementAndClick(saucesTab);
    }

    @Step("Нажать на вкладку 'Начинки'")
    public void clickFillingsTab() {
        scrollToElementAndClick(fillingsTab);
    }

    private void scrollToElementAndClick(By locator) {
        WebElement el = waitVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    @Step("Проверить, что вкладка 'Булки' активна")
    public boolean isBunsTabActive() {
        try {
            WebElement tab = waitVisible(bunsTab);
            String cls = tab.getAttribute("class");
            return cls != null && cls.contains("current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что заголовок 'Соусы' виден в списке")
    public boolean isSaucesSectionVisible() {
        return isElementVisible(saucesHeader);
    }

    @Step("Проверить, что заголовок 'Начинки' виден в списке")
    public boolean isFillingsSectionVisible() {
        return isElementVisible(fillingsHeader);
    }

    @Step("Проверить, что заголовок 'Булки' виден в списке")
    public boolean isBunsSectionVisible() {
        return isElementVisible(bunsHeader);
    }

    @Step("Проверить, что ссылка 'Личный кабинет' доступна")
    public boolean isPersonalCabinetLinkVisible() {
        return isElementVisible(personalCabinetLink);
    }

    @Step("Проверить, что кнопка 'Войти в аккаунт' отображается")
    public boolean isLoginToAccountButtonVisible() {
        return isElementVisible(loginToAccountButton);
    }
}
