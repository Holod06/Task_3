package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stellarburgers.pages.LoginPage;
import ru.stellarburgers.pages.PersonalCabinetPage;
import ru.stellarburgers.utils.TestDataGenerator;
import ru.stellarburgers.utils.UserApi;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

        new LoginPage(driver).open();
        new LoginPage(driver).login(email, password);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/account']")));
    }

    @AfterEach
    @Step("Удалить тестового пользователя через API")
    public void deleteTestUser() {
        UserApi.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Клик на 'Личный кабинет' в шапке открывает страницу профиля")
    public void navigateToPersonalCabinet() {
        new PersonalCabinetPage(driver).navigateFromHeader();

        assertTrue(driver.getCurrentUrl().contains("/account"),
                "URL должен содержать /account");
    }

    @Test
    @DisplayName("Переход из кабинета в конструктор")
    @Description("Клик на 'Конструктор' в шапке из кабинета возвращает на главную")
    public void navigateFromCabinetToConstructor() {
        PersonalCabinetPage cabinet = new PersonalCabinetPage(driver);
        cabinet.navigateFromHeader();
        cabinet.clickConstructorLink();

        assertFalse(driver.getCurrentUrl().contains("/account"),
                "URL не должен содержать /account");
    }

    @Test
    @DisplayName("Переход из кабинета на главную через логотип")
    @Description("Клик на логотип из кабинета возвращает на главную страницу")
    public void navigateFromCabinetViaLogo() {
        PersonalCabinetPage cabinet = new PersonalCabinetPage(driver);
        cabinet.navigateFromHeader();
        cabinet.clickLogo();

        assertFalse(driver.getCurrentUrl().contains("/account"),
                "URL не должен содержать /account");
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Кнопка 'Выход' в кабинете разлогинивает и перенаправляет на /login")
    public void logout() {
        PersonalCabinetPage cabinet = new PersonalCabinetPage(driver);
        cabinet.navigateFromHeader();
        cabinet.clickLogoutButton();

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После выхода URL должен содержать /login");
    }
}
