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
    @DisplayName("Клик по вкладке 'Булки' — раздел булок виден")
    @Description("После клика на вкладку 'Булки' заголовок раздела должен отображаться")
    public void clickBunsTabShowsBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        // Сначала перейдём к другому разделу, потом вернёмся к булкам
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();

        assertTrue(mainPage.isBunsSectionVisible(),
                "Заголовок раздела 'Булки' должен быть виден");
    }

    @Test
    @DisplayName("Клик по вкладке 'Соусы' — раздел соусов виден")
    @Description("После клика на вкладку 'Соусы' заголовок раздела должен отображаться")
    public void clickSaucesTabShowsSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab();

        assertTrue(mainPage.isSaucesSectionVisible(),
                "Заголовок раздела 'Соусы' должен быть виден");
    }

    @Test
    @DisplayName("Клик по вкладке 'Начинки' — раздел начинок виден")
    @Description("После клика на вкладку 'Начинки' заголовок раздела должен отображаться")
    public void clickFillingsTabShowsFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickFillingsTab();

        assertTrue(mainPage.isFillingsSectionVisible(),
                "Заголовок раздела 'Начинки' должен быть виден");
    }
}
