package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @Step("Удалить тестового пользователя через API")
    public void deleteTestUser() {
        if (email != null && password != null) {
            try {
                UserApi.deleteUser(UserApi.getAccessToken(email, password));
            } catch (Exception ignored) {}
        }
    }

    @Test
    @DisplayName("Успешная регистрация с валидными данными")
    @Description("После регистрации с корректными данными выполняется редирект на страницу входа")
    public void successfulRegistrationTest() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();

        RegistrationPage page = new RegistrationPage(driver);
        page.open();
        page.register(name, email, password);

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После успешной регистрации должен быть редирект на /login");
    }

    @Test
    @DisplayName("Ошибка при вводе пароля короче 6 символов")
    @Description("При пароле менее 6 символов форма показывает ошибку 'Некорректный пароль'")
    public void registrationWithShortPasswordShowsErrorTest() {
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
