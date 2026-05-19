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
    @DisplayName("Переход к разделу 'Булки'")
    @Description("После клика вкладка 'Булки' становится активной на UI")
    public void bunsTabBecomesActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab(); // уходим от булок
        mainPage.clickBunsTab();  // метод сам ждёт активации вкладки

        assertTrue(mainPage.isBunsTabActive(),
                "Вкладка 'Булки' должна быть активной");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("После клика вкладка 'Соусы' становится активной на UI")
    public void saucesTabBecomesActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickSaucesTab();

        assertTrue(mainPage.isSaucesTabActive(),
                "Вкладка 'Соусы' должна быть активной");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("После клика вкладка 'Начинки' становится активной на UI")
    public void fillingsTabBecomesActive() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickFillingsTab();

        assertTrue(mainPage.isFillingsTabActive(),
                "Вкладка 'Начинки' должна быть активной");
    }
}
