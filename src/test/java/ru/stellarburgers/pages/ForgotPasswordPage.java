package ru.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    private final By loginLink =
            By.xpath("//a[@href='/login']");
    private final By pageTitle =
            By.xpath("//h2[text()='Восстановление пароля']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу восстановления пароля")
    public void open() {
        driver.get(BASE_URL + "/forgot-password");
    }

    @Step("Нажать ссылку 'Войти' на форме восстановления пароля")
    public LoginPage clickLoginLink() {
        waitClickable(loginLink).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что открыта страница восстановления пароля")
    public boolean isForgotPasswordPageOpened() {
        return isElementVisible(pageTitle);
    }
}
