package ru.stellarburgers.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfig {

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        // Путь к yandexdriver задаётся через -Dyandex.driver.path=...
        String driverPath = System.getProperty("yandex.driver.path",
                "/usr/local/bin/yandexdriver");
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        // Путь к бинарю Яндекс.Браузера задаётся через -Dyandex.browser.path=...
        String browserPath = System.getProperty("yandex.browser.path",
                "/usr/bin/yandex-browser");
        options.setBinary(browserPath);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }
}
