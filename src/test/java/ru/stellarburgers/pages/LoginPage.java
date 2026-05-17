package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailField =
            By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField =
            By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton =
            By.xpath("//button[text()='Войти']");
    private final By registerLink =
            By.xpath("//a[@href='/register']");
    private final By forgotPasswordLink =
            By.xpath("//a[@href='/forgot-password']");
    private final By pageTitle =
            By.xpath("//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(BASE_URL + "/login");
    }

    @Step("Ввести email: {email}")
    public LoginPage enterEmail(String email) {
        waitVisible(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        waitVisible(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public MainPage clickLoginButton() {
        waitClickable(loginButton).click();
        return new MainPage(driver);
    }

    @Step("Нажать ссылку 'Зарегистрироваться'")
    public RegistrationPage clickRegisterLink() {
        waitClickable(registerLink).click();
        return new RegistrationPage(driver);
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
    public ForgotPasswordPage clickForgotPasswordLink() {
        waitClickable(forgotPasswordLink).click();
        return new ForgotPasswordPage(driver);
    }

    @Step("Войти с email: {email}")
    public MainPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step("Проверить, что открыта страница входа")
    public boolean isLoginPageOpened() {
        return isElementVisible(pageTitle);
    }
}
