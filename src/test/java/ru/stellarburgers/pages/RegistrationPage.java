package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {

    // register:
    // input[0] name='name' type='text'       — поле Имя
    // input[1] name='name' type='text'       — поле Email (оба name='name'!)
    // input[2] name='Пароль' type='password'
    // button text='Зарегистрироваться'
    private final By nameField =
            By.xpath("(//input[@name='name'])[1]");
    private final By emailField =
            By.xpath("(//input[@name='name'])[2]");
    private final By passwordField =
            By.xpath("//input[@name='Пароль']");
    private final By registerButton =
            By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink =
            By.xpath("//a[@href='/login']");
    private final By passwordError =
            By.xpath("//*[contains(@class,'input__error') and contains(text(),'Некорректный пароль')]");

    public RegistrationPage(WebDriver driver) { super(driver); }

    @Step("Открыть страницу регистрации")
    public void open() {
        driver.get(BASE_URL + "/register");
        wait.until(ExpectedConditions.presenceOfElementLocated(registerButton));
    }

    @Step("Ввести имя")
    public RegistrationPage enterName(String name) {
        waitVisible(nameField).sendKeys(name);
        return this;
    }

    @Step("Ввести email")
    public RegistrationPage enterEmail(String email) {
        waitVisible(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public RegistrationPage enterPassword(String password) {
        waitVisible(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать 'Зарегистрироваться' (ожидаем редирект на /login)")
    public LoginPage clickRegisterButton() {
        waitClickable(registerButton).click();
        wait.until(ExpectedConditions.urlContains("/login"));
        return new LoginPage(driver);
    }

    @Step("Нажать 'Зарегистрироваться' (ожидаем ошибку)")
    public RegistrationPage clickRegisterButtonExpectError() {
        waitClickable(registerButton).click();
        return this;
    }

    @Step("Нажать ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        waitClickable(loginLink).click();
        return new LoginPage(driver);
    }

    @Step("Зарегистрировать пользователя")
    public LoginPage register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        return clickRegisterButton();
    }

    @Step("Проверить, что страница регистрации открыта")
    public boolean isRegistrationPageOpened() {
        try {
            wait.until(ExpectedConditions.urlContains("/register"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить ошибку 'Некорректный пароль'")
    public boolean isPasswordErrorVisible() {
        return isElementVisible(passwordError);
    }
}
