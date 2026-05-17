package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.RegistrationPage;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTest extends BaseTest {

    private String email;
    private String password;

    @AfterEach
    public void deleteTestUser() {
        if (email != null && password != null) {
            try {
                String token = UserApi.getAccessToken(email, password);
                UserApi.deleteUser(token);
            } catch (Exception ignored) {}
        }
    }

    @Test
    @DisplayName("Успешная регистрация перенаправляет на страницу входа")
    @Description("После регистрации с валидными данными должна открыться страница входа")
    public void successfulRegistrationRedirectsToLogin() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        LoginPage loginPage = registrationPage.register(name, email, password);

        assertTrue(loginPage.isLoginPageOpened(),
                "После успешной регистрации должна открыться страница входа");
    }

    @Test
    @DisplayName("Пароль короче 6 символов вызывает ошибку 'Некорректный пароль'")
    @Description("При вводе пароля < 6 символов и попытке регистрации отображается ошибка")
    public void registrationWithShortPasswordShowsError() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateShortPassword();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.clickRegisterButtonExpectError();

        assertTrue(registrationPage.isPasswordErrorVisible(),
                "Должна отображаться ошибка 'Некорректный пароль'");
    }
}
