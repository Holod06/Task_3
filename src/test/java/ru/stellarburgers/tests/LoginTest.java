package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stellarburgers.pages.ForgotPasswordPage;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.pages.RegistrationPage;
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

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной странице")
    @Description("Нажимаем кнопку на главной, вводим данные — пользователь авторизован")
    public void loginViaMainPageButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        LoginPage loginPage = mainPage.clickLoginToAccountButton();
        loginPage.login(email, password);

        assertTrue(new MainPage(driver).isPersonalCabinetLinkVisible(),
                "После входа должна быть доступна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через ссылку 'Личный кабинет' в шапке")
    @Description("Неавторизованный клик на 'Личный кабинет' → форма входа → авторизация")
    public void loginViaPersonalCabinetLink() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        LoginPage loginPage = mainPage.clickPersonalCabinetLink();
        loginPage.login(email, password);

        assertTrue(new MainPage(driver).isPersonalCabinetLinkVisible(),
                "После входа должна быть доступна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме регистрации")
    @Description("Открываем регистрацию, переходим к форме входа, авторизуемся")
    public void loginViaRegistrationFormLink() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        LoginPage loginPage = registrationPage.clickLoginLink();
        loginPage.login(email, password);

        assertTrue(new MainPage(driver).isPersonalCabinetLinkVisible(),
                "После входа должна быть доступна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через ссылку 'Войти' на форме восстановления пароля")
    @Description("Открываем восстановление пароля, переходим к форме входа, авторизуемся")
    public void loginViaForgotPasswordFormLink() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.open();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        loginPage.login(email, password);

        assertTrue(new MainPage(driver).isPersonalCabinetLinkVisible(),
                "После входа должна быть доступна ссылка 'Личный кабинет'");
    }
}
