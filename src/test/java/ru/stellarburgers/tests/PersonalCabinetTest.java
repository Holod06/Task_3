package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    @Step("Создать пользователя через API и войти в аккаунт")
    public void createAndLoginUser() {
        String name = TestDataGenerator.generateName();
        email = TestDataGenerator.generateEmail();
        password = TestDataGenerator.generateValidPassword();
        UserApi.createUser(name, email, password);
        accessToken = UserApi.getAccessToken(email, password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(email, password);
        // Ждём завершения входа
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void deleteTestUser() {
        UserApi.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на ссылку 'Личный кабинет'")
    @Description("После входа кликаем на 'Личный кабинет' → открывается /account/profile")
    public void navigateToPersonalCabinet() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalCabinetLink();
        wait.until(ExpectedConditions.urlContains("/account"));
        assertTrue(driver.getCurrentUrl().contains("/account"),
                "URL должен содержать /account");
    }

    @Test
    @DisplayName("Из личного кабинета в конструктор по клику 'Конструктор'")
    @Description("Кликаем 'Конструктор' в шапке из кабинета → открывается главная")
    public void navigateFromCabinetToConstructorViaLink() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        wait.until(ExpectedConditions.urlContains("/account"));
        cabinetPage.clickConstructorLink();
        wait.until(ExpectedConditions.urlMatches(".*/($|\\?).*|.*/"));
        assertTrue(!driver.getCurrentUrl().contains("/account"),
                "После перехода URL не должен содержать /account");
    }

    @Test
    @DisplayName("Из личного кабинета на главную по клику на логотип")
    @Description("Кликаем на логотип из кабинета → открывается главная страница")
    public void navigateFromCabinetToMainViaLogo() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        wait.until(ExpectedConditions.urlContains("/account"));
        cabinetPage.clickLogo();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/account")));
        assertTrue(!driver.getCurrentUrl().contains("/account"),
                "После клика на логотип URL не должен содержать /account");
    }

    @Test
    @DisplayName("Выход из аккаунта по кнопке 'Выйти'")
    @Description("Нажимаем 'Выйти' в личном кабинете → открывается страница входа")
    public void logoutFromPersonalCabinet() {
        PersonalCabinetPage cabinetPage = new PersonalCabinetPage(driver);
        cabinetPage.open();
        wait.until(ExpectedConditions.urlContains("/account"));
        cabinetPage.clickLogoutButton();
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После выхода URL должен содержать /login");
    }
}
