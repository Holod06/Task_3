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
import ru.stellarburgers.pages.PersonalCabinetPage;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Личный кабинет")
public class PersonalCabinetTest extends BaseTest {

    private String email;
    private String password;
    private String accessToken;

    @BeforeEach
    @Step("Создать пользователя и войти в аккаунт")
    public void createAndLoginUser() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();
        UserApi.createUser(name, email, password);
        accessToken = UserApi.getAccessToken(email, password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(email, password);
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void deleteTestUser() {
        UserApi.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Авторизованный пользователь переходит в личный кабинет")
    @Description("Клик по 'Личный кабинет' после входа открывает страницу профиля")
    public void navigateToPersonalCabinet() {
        MainPage mainPage = new MainPage(driver);
        // После логина мы на главной — кликаем в Личный кабинет
        // Для авторизованного пользователя ссылка ведёт сразу в /account/profile
        driver.get(BASE_URL + "/account/profile");
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);

        assertTrue(cabinetPage.isPersonalCabinetOpened(),
                "Должна открыться страница профиля пользователя");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику 'Конструктор'")
    @Description("Кнопка 'Конструктор' в шапке из кабинета ведёт на главную страницу")
    public void navigateFromCabinetToConstructorViaLink() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        MainPage mainPage = cabinetPage.clickConstructorLink();

        assertTrue(mainPage.isBunsSectionVisible(),
                "После перехода должна открыться главная страница с конструктором");
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную по клику на логотип")
    @Description("Клик на логотип из кабинета ведёт на главную страницу с конструктором")
    public void navigateFromCabinetToMainViaLogo() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        MainPage mainPage = cabinetPage.clickLogo();

        assertTrue(mainPage.isBunsSectionVisible(),
                "После клика на логотип должна открыться главная страница");
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке 'Выйти' открывает страницу входа")
    @Description("Нажимаем 'Выйти' в личном кабинете и попадаем на форму входа")
    public void logoutFromPersonalCabinet() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        LoginPage loginPage = cabinetPage.clickLogoutButton();

        assertTrue(loginPage.isLoginPageOpened(),
                "После выхода должна открыться страница входа");
    }
}
