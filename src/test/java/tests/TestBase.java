package tests;

import com.codeborne.selenide.Configuration;
import config.MobileConfig;
import drivers.BrowserstackMobileDriver;
import drivers.EmulatorMobileDriver;
import drivers.MobileDriver;
import drivers.SelenoidMobileDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.Attachments.*;

public class TestBase {
    static final MobileConfig CFG = ConfigFactory.create(MobileConfig.class);

    @BeforeAll
    public static void setup() {
        addListener("AllureSelenide", new AllureSelenide());

        switch (CFG.device().toLowerCase()) {
            case "browserstack":
                Configuration.browser = BrowserstackMobileDriver.class.getName();
                break;
            case "selenoid":
                Configuration.browser = SelenoidMobileDriver.class.getName();
                break;
            case "emulation":
                Configuration.browser = EmulatorMobileDriver.class.getName();
                break;
            case "real":
                Configuration.browser = MobileDriver.class.getName();
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown device name=%s. " +
                                "-Ddevice.name=[Browserstack/Selenoid/Emulation/Real]", CFG.device()));
        }
        Configuration.browserSize = null;
    }

    @BeforeEach
    public void beforeEach() {
        open();
    }

    @AfterEach
    public void afterEach() {
        String sessionId = getSessionId();
        screenshotAs("Last screenshot");
        pageSource();

        closeWebDriver();
        switch (CFG.device().toLowerCase()) {
            case "browserstack":
                videoBrowserstack(sessionId);
                break;
            case "selenoid":
                videoSelenoid(sessionId);
                break;
        }
    }

}
