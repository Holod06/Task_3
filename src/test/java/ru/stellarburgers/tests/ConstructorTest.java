package ru.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stellarburgers.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers")
@Feature("Конструктор")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Клик по вкладке 'Булки' показывает раздел булок")
    @Description("После клика на 'Булки' заголовок раздела виден на странице")
    public void clickBunsTabShowsBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab(); // сначала уйдём от булок
        mainPage.clickBunsTab();
        assertTrue(mainPage.isBunsSectionVisible(),
                "Заголовок 'Булки' должен быть виден");
    }

    @Test
    @DisplayName("Клик по вкладке 'Соусы' показывает раздел соусов")
    @Description("После клика на 'Соусы' заголовок раздела виден на странице")
    public void clickSaucesTabShowsSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isSaucesSectionVisible(),
                "Заголовок 'Соусы' должен быть виден");
    }

    @Test
    @DisplayName("Клик по вкладке 'Начинки' показывает раздел начинок")
    @Description("После клика на 'Начинки' заголовок раздела виден на странице")
    public void clickFillingsTabShowsFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isFillingsSectionVisible(),
                "Заголовок 'Начинки' должен быть виден");
    }
}
