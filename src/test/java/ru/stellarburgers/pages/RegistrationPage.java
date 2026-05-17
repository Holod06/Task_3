package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    private final By nameField =
            By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField =
            By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField =
            By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton =
            By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink =
            By.xpath("//a[@href='/login']");
    private final By passwordError =
            By.xpath("//p[contains(@class,'input__error') and text()='Некорректный пароль']");
    private final By pageTitle =
            By.xpath("//h2[text()='Регистрация']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(BASE_URL + "/register");
    }

    @Step("Ввести имя: {name}")
    public RegistrationPage enterName(String name) {
        waitVisible(nameField).sendKeys(name);
        return this;
    }

    @Step("Ввести email: {email}")
    public RegistrationPage enterEmail(String email) {
        waitVisible(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public RegistrationPage enterPassword(String password) {
        waitVisible(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public LoginPage clickRegisterButton() {
        waitClickable(registerButton).click();
        return new LoginPage(driver);
    }

    @Step("Нажать кнопку 'Зарегистрироваться' (ожидаем ошибку)")
    public RegistrationPage clickRegisterButtonExpectError() {
        waitClickable(registerButton).click();
        return this;
    }

    @Step("Нажать ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        waitClickable(loginLink).click();
        return new LoginPage(driver);
    }

    @Step("Зарегистрироваться: имя={name}, email={email}")
    public LoginPage register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        return clickRegisterButton();
    }

    @Step("Проверить, что отображается ошибка 'Некорректный пароль'")
    public boolean isPasswordErrorVisible() {
        return isElementVisible(passwordError);
    }

    @Step("Проверить, что открыта страница регистрации")
    public boolean isRegistrationPageOpened() {
        return isElementVisible(pageTitle);
    }
}
