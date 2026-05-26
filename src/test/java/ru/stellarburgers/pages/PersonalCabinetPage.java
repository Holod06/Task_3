package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalCabinetPage extends BasePage {

    // Ссылка на личный кабинет в шапке
    private final By personalCabinetHeaderLink =
            By.xpath("//a[@href='/account']");

    // Ссылка на конструктор в шапке
    private final By constructorLink =
            By.xpath("//a[@href='/']//p[contains(@class,'AppHeader_header__linkText')]");

    // Логотип
    private final By logoLink =
            By.xpath("//div[contains(@class,'AppHeader_header__logo')]//a[@href='/']");

    // Кнопка «Выход»
    private final By logoutButton =
            By.xpath("//button[text()='Выход']");

    public PersonalCabinetPage(WebDriver driver) { super(driver); }

    @Step("Перейти в личный кабинет кликом на ссылку в шапке")
    public PersonalCabinetPage navigateFromHeader() {
        waitClickable(personalCabinetHeaderLink).click();
        wait.until(ExpectedConditions.urlContains("/account"));
        waitVisible(logoutButton);
        return this;
    }

    @Step("Нажать кнопку 'Выйти'")
    public LoginPage clickLogoutButton() {
        waitVisible(logoutButton).click();
        wait.until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    @Step("Нажать 'Конструктор' в шапке")
    public MainPage clickConstructorLink() {
        waitClickable(constructorLink).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/account")));
        return new MainPage(driver);
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickLogo() {
        waitClickable(logoLink).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/account")));
        return new MainPage(driver);
    }

    @Step("Проверить, что личный кабинет открыт")
    public boolean isPersonalCabinetOpened() {
        return driver.getCurrentUrl().contains("/account");
    }
}
