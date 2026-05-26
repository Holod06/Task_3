package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    // /login:
    // input[0] name='name' type='text'       — поле Email
    // input[1] name='Пароль' type='password' — поле Пароль
    // button text='Войти'
    private final By emailField =
            By.xpath("//input[@name='name']");
    private final By passwordField =
            By.xpath("//input[@name='Пароль']");
    private final By loginButton =
            By.xpath("//button[text()='Войти']");
    private final By registerLink =
            By.xpath("//a[@href='/register']");
    private final By forgotPasswordLink =
            By.xpath("//a[@href='/forgot-password']");

    public LoginPage(WebDriver driver) { super(driver); }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get(BASE_URL + "/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(loginButton));
    }

    @Step("Ввести email")
    public LoginPage enterEmail(String email) {
        waitVisible(emailField).clear();
        waitVisible(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        waitVisible(passwordField).clear();
        waitVisible(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public MainPage clickLoginButton() {
        waitClickable(loginButton).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
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

    @Step("Войти с email и паролем")
    public MainPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step("Проверить, что страница входа открыта")
    public boolean isLoginPageOpened() {
        try {
            wait.until(ExpectedConditions.urlContains("/login"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
