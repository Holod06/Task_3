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

    private String userEmail;
    private String userPassword;
    private PersonalCabinetPage personalCabinetPage;
    private MainPage mainPage;

    @BeforeEach
    @Step("Создать тестового пользователя, выполнить вход и перейти в личный кабинет")
    public void setUpPersonalCabinet() {
        userEmail = TestDataGenerator.generateEmail();
        userPassword = TestDataGenerator.generateValidPassword();
        UserApi.createUser(TestDataGenerator.generateName(), userEmail, userPassword);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(userEmail, userPassword);

        personalCabinetPage = new PersonalCabinetPage(driver);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void tearDownPersonalCabinet() {
        try {
            UserApi.deleteUser(UserApi.getAccessToken(userEmail, userPassword));
        } catch (Exception ignored) {}
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный кабинет»")
    @Description("После авторизации клик на ссылку 'Личный кабинет' в шапке открывает /account")
    public void navigateToPersonalCabinetTest() {
        personalCabinetPage.navigateFromHeader();

        assertTrue(personalCabinetPage.isPersonalCabinetOpened(),
                "Должен открыться личный кабинет (/account)");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    @Description("Из личного кабинета клик на 'Конструктор' в шапке возвращает на главную")
    public void navigateToConstructorFromCabinetTest() {
        personalCabinetPage.navigateFromHeader();
        personalCabinetPage.clickConstructorLink();

        assertTrue(mainPage.isBunsSectionVisible(),
                "После перехода в конструктор должен отображаться раздел 'Булки'");
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную по клику на логотип")
    @Description("Из личного кабинета клик на логотип Stellar Burgers возвращает на главную")
    public void navigateToMainPageViaLogoFromCabinetTest() {
        personalCabinetPage.navigateFromHeader();
        personalCabinetPage.clickLogo();

        assertTrue(mainPage.isBunsSectionVisible(),
                "После клика на логотип должна открыться главная страница с разделом 'Булки'");
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке «Выйти»")
    @Description("В личном кабинете клик на 'Выйти' разлогинивает пользователя и открывает /login")
    public void logoutFromPersonalCabinetTest() {
        personalCabinetPage.navigateFromHeader();
        LoginPage loginPage = personalCabinetPage.clickLogoutButton();

        assertTrue(loginPage.isLoginPageOpened(),
                "После выхода должна открыться страница входа (/login)");
    }
}
