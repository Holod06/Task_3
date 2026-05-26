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
    @DisplayName("Переход к разделу «Булки»")
    @Description("Клик на вкладку 'Булки' делает её активной")
    public void bunsTabBecomesActiveTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();

        assertTrue(mainPage.isBunsTabActive(),
                "Вкладка 'Булки' должна стать активной");
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    @Description("Клик на вкладку 'Соусы' делает её активной")
    public void saucesTabBecomesActiveTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab();

        assertTrue(mainPage.isSaucesTabActive(),
                "Вкладка 'Соусы' должна стать активной");
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    @Description("Клик на вкладку 'Начинки' делает её активной")
    public void fillingsTabBecomesActiveTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickFillingsTab();

        assertTrue(mainPage.isFillingsTabActive(),
                "Вкладка 'Начинки' должна стать активной");
    }
}
