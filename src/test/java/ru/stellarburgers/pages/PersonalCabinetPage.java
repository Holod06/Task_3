package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PersonalCabinetPage extends BasePage {

    // Из реального DOM (шапка одинакова на всех страницах после логина):
    // <a href="/account" class="AppHeader_header__link__3D_hX">Личный Кабинет</a>
    private final By personalCabinetHeaderLink =
            By.xpath("//a[@href='/account']");

    // <a href="/"><p class="AppHeader_header__linkText__3q_va">Конструктор</p></a>
    private final By constructorLink =
            By.xpath("//a[@href='/']//p[contains(@class,'AppHeader_header__linkText')]");

    // <div class="AppHeader_header__logo__2D0X2"><a class="active" href="/">
    private final By logoLink =
            By.xpath("//div[contains(@class,'AppHeader_header__logo')]//a[@href='/']");

    // Кнопка «Выход» — из реального DOM: button text='Выход'
    private final By logoutButton =
            By.xpath("//button[text()='Выход']");

    public PersonalCabinetPage(WebDriver driver) { super(driver); }

    /**
     * Переходит в кабинет кликом по ссылке в шапке.
     * НЕ использует driver.get() — страница /account/profile требует
     * SPA-навигации с живой сессией, иначе React отдаёт пустой root.
     * Вызывать только когда пользователь уже на главной после входа.
     */
    @Step("Перейти в личный кабинет кликом на ссылку в шапке")
    public PersonalCabinetPage navigateFromHeader() {
        waitClickable(personalCabinetHeaderLink).click();
        wait.until(ExpectedConditions.urlContains("/account"));
        // Ждём рендера содержимого кабинета
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
