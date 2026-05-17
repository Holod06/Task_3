package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
                UserApi.deleteUser(UserApi.getAccessToken(email, password));
            } catch (Exception ignored) {}
        }
    }

    @Test
    @DisplayName("Успешная регистрация — редирект на страницу входа")
    @Description("После регистрации с валидными данными открывается /login")
    public void successfulRegistrationRedirectsToLogin() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();

        RegistrationPage page = new RegistrationPage(driver);
        page.open();
        page.register(name, email, password);

        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После регистрации должен быть редирект на /login");
    }

    @Test
    @DisplayName("Пароль < 6 символов показывает ошибку 'Некорректный пароль'")
    @Description("При вводе короткого пароля и отправке формы отображается ошибка")
    public void shortPasswordShowsError() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateShortPassword();

        RegistrationPage page = new RegistrationPage(driver);
        page.open();
        page.enterName(name);
        page.enterEmail(email);
        page.enterPassword(password);
        page.clickRegisterButtonExpectError();

        assertTrue(page.isPasswordErrorVisible(),
                "Должна отображаться ошибка 'Некорректный пароль'");
    }
}
