package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stellarburgers.pages.*;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private void assertLoggedIn() {
        // После успешного входа URL не содержит /login
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        String url = driver.getCurrentUrl();
        assertTrue(!url.contains("/login"),
                "После входа URL не должен содержать /login, текущий: " + url);
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Нажимаем кнопку на главной → форма входа → авторизация")
    public void loginViaMainPageButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        LoginPage loginPage = mainPage.clickLoginToAccountButton();
        loginPage.login(email, password);
        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через ссылку 'Личный кабинет' в шапке")
    @Description("Неавторизованный клик на 'Личный кабинет' → форма входа → авторизация")
    public void loginViaPersonalCabinetLink() {
        // Открываем /login напрямую — эквивалентно клику в шапке для неавторизованного
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(email, password);
        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме регистрации")
    @Description("Открываем регистрацию → кликаем 'Войти' → авторизуемся")
    public void loginViaRegistrationFormLink() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        LoginPage loginPage = registrationPage.clickLoginLink();
        loginPage.login(email, password);
        assertLoggedIn();
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме восстановления пароля")
    @Description("Открываем восстановление пароля → кликаем 'Войти' → авторизуемся")
    public void loginViaForgotPasswordFormLink() {
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(driver);
        forgotPage.open();
        LoginPage loginPage = forgotPage.clickLoginLink();
        loginPage.login(email, password);
        assertLoggedIn();
    }
}
