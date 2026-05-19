package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stellarburgers.pages.ForgotPasswordPage;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.RegistrationPage;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Stellar Burgers")
@Feature("Вход в аккаунт")
public class LoginTest extends BaseTest {

    private String email;
    private String password;
    private String accessToken;

    @BeforeEach
    @Step("Создать тестового пользователя через API")
    public void createTestUser() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();
        UserApi.createUser(name, email, password);
        accessToken = UserApi.getAccessToken(email, password);
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void deleteTestUser() {
        UserApi.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Кнопка 'Войти в аккаунт' ведёт на форму входа и авторизует пользователя")
    public void loginViaMainPageButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLoginToAccountButton();
        new LoginPage(driver).login(email, password);

        assertFalse(driver.getCurrentUrl().contains("/login"),
                "После входа URL не должен содержать /login");
    }

    @Test
    @DisplayName("Вход через ссылку 'Личный кабинет' в шапке")
    @Description("Клик на 'Личный кабинет' без авторизации ведёт на форму входа")
    public void loginViaPersonalCabinetLink() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(email, password);

        assertFalse(driver.getCurrentUrl().contains("/login"),
                "После входа URL не должен содержать /login");
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме регистрации")
    @Description("Со страницы регистрации можно перейти к форме входа и авторизоваться")
    public void loginViaRegistrationForm() {
        new RegistrationPage(driver).open();
        new RegistrationPage(driver).clickLoginLink();
        new LoginPage(driver).login(email, password);

        assertFalse(driver.getCurrentUrl().contains("/login"),
                "После входа URL не должен содержать /login");
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме восстановления пароля")
    @Description("Со страницы восстановления пароля можно перейти к форме входа")
    public void loginViaForgotPasswordForm() {
        new ForgotPasswordPage(driver).open();
        new ForgotPasswordPage(driver).clickLoginLink();
        new LoginPage(driver).login(email, password);

        assertFalse(driver.getCurrentUrl().contains("/login"),
                "После входа URL не должен содержать /login");
    }
}
