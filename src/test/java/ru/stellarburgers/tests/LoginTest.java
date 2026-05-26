package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.MainPage;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Вход в аккаунт")
public class LoginTest extends BaseTest {

    private String userEmail;
    private String userPassword;
    private MainPage mainPage;
    private LoginPage loginPage;

    @BeforeEach
    @Step("Создать тестового пользователя через API")
    public void setUpLogin() {
        userEmail = TestDataGenerator.generateEmail();
        userPassword = TestDataGenerator.generateValidPassword();
        UserApi.createUser(TestDataGenerator.generateName(), userEmail, userPassword);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void tearDownLogin() {
        try {
            UserApi.deleteUser(UserApi.getAccessToken(userEmail, userPassword));
        } catch (Exception ignored) {}
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    @Description("Клик на кнопку 'Войти в аккаунт' на главной → форма логина → успешный вход")
    public void loginViaMainPageButtonTest() {
        mainPage.open();
        mainPage.clickLoginToAccountButton().login(userEmail, userPassword);

        assertTrue(mainPage.isPersonalCabinetLinkVisible(),
                "После входа должна быть видна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через ссылку «Личный кабинет» в шапке главной страницы")
    @Description("Клик на 'Личный кабинет' в шапке → форма логина → успешный вход")
    public void loginViaPersonalCabinetLinkTest() {
        mainPage.open();
        mainPage.clickPersonalCabinetLink().login(userEmail, userPassword);

        assertTrue(mainPage.isPersonalCabinetLinkVisible(),
                "После входа должна быть видна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через кнопку «Войти» в форме регистрации")
    @Description("Страница регистрации → ссылка 'Войти' → форма логина → успешный вход")
    public void loginViaRegistrationFormTest() {
        loginPage.open();
        loginPage.clickRegisterLink().clickLoginLink().login(userEmail, userPassword);

        assertTrue(mainPage.isPersonalCabinetLinkVisible(),
                "После входа должна быть видна ссылка 'Личный кабинет'");
    }

    @Test
    @DisplayName("Вход через кнопку «Войти» в форме восстановления пароля")
    @Description("Страница восстановления пароля → ссылка 'Войти' → форма логина → успешный вход")
    public void loginViaForgotPasswordFormTest() {
        loginPage.open();
        loginPage.clickForgotPasswordLink().clickLoginLink().login(userEmail, userPassword);

        assertTrue(mainPage.isPersonalCabinetLinkVisible(),
                "После входа должна быть видна ссылка 'Личный кабинет'");
    }
}
