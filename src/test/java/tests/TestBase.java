package tests;

import com.codeborne.selenide.Configuration;
import config.MobileConfig;
import drivers.BrowserstackMobileDriver;
import drivers.MobileDriver;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.Attach.getSessionId;

public class TestBase {
    static final MobileConfig CFG = ConfigFactory.create(MobileConfig.class);

    @BeforeAll
    public static void setup() {
        addListener("AllureSelenide", new AllureSelenide());


        switch (CFG.device()) {
            case "browserstack":
                Configuration.browser = BrowserstackMobileDriver.class.getName();
                break;
            case "selenoid":
            case "emulation":
            case "real":
                Configuration.browser = MobileDriver.class.getName();
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown device name=%s. " +
                                "Please run with parameter " +
                                "-Ddevice.name=[Browserstack/Selenoid/Emulation/Real]", CFG.device()));
        }
    }

    @BeforeEach
    public void startDriver() {
        open();
    }

    @AfterEach
    public void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        closeWebDriver();

        switch (CFG.device()) {
            case "browserstack":
                Attach.videoBrowserstack(getSessionId());
                break;
            case "selenoid":
                Attach.videoSelenoid();
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown device name=%s. " +
                                "Please run with parameter " +
                                "-Ddevice.name=[Browserstack/Selenoid/Emulation/Real]", CFG.device()));
        }
    }

}