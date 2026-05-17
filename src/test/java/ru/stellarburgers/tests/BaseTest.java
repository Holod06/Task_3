package ru.stellarburgers.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stellarburgers.utils.WebDriverConfig;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL =
            "https://qa-stellarburgers.education-services.ru";

    @BeforeEach
    public void setUp() {
        driver = WebDriverConfig.createDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Скриншот", "image/png",
                        new ByteArrayInputStream(screenshot), "png");
            } catch (Exception ignored) {}
            driver.quit();
        }
    }
}
