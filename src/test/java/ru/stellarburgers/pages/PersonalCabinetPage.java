package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalCabinetPage extends BasePage {

    private final By logoutButton =
            By.xpath("//button[text()='Выход']");
    private final By nameField =
            By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By constructorLink =
            By.xpath("//p[text()='Конструктор']");
    private final By logoLink =
            By.xpath("//div[contains(@class,'AppHeader_header__logo')]");

    public PersonalCabinetPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу личного кабинета")
    public void open() {
        driver.get(BASE_URL + "/account/profile");
    }

    @Step("Нажать кнопку 'Выйти'")
    public LoginPage clickLogoutButton() {
        waitClickable(logoutButton).click();
        return new LoginPage(driver);
    }

    @Step("Нажать ссылку 'Конструктор' в шапке")
    public MainPage clickConstructorLink() {
        waitClickable(constructorLink).click();
        return new MainPage(driver);
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickLogo() {
        waitClickable(logoLink).click();
        return new MainPage(driver);
    }

    @Step("Проверить, что личный кабинет открыт (видно поле 'Имя')")
    public boolean isPersonalCabinetOpened() {
        return isElementVisible(nameField);
    }
}
