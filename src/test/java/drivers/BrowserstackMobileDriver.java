package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.MobileConfig;
import io.appium.java_client.android.AndroidDriver;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BrowserstackMobileDriver implements WebDriverProvider {
    static final MobileConfig CFG = ConfigFactory.create(MobileConfig.class);
    private static final String APP_URL = uploadAPK();

    public static URL getBrowserstackUrl() {
        try {
            return new URL(CFG.url());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    BrowserstackMobileDriver() {
        Assertions.assertNotNull(CFG.url(), "Url not found");
        Assertions.assertNotNull(CFG.curl(), "Curl not found");
        Assertions.assertNotNull(CFG.user(), "User not found");
        Assertions.assertNotNull(CFG.key(), "Key not found");
    }

    @Nonnull
    private static String uploadAPK() {
      return given()
                .multiPart("file", new File("src/test/resources/apk/app-alpha-universal-release.apk"))
                .when()
                .post(CFG.curl())
                .then()
                .statusCode(200)
                .body("app_url", is(notNullValue()))
                .extract().path("app_url").toString();
    }

    @Nonnull
    @Override
    public WebDriver createDriver(Capabilities capabilities) {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);
        mutableCapabilities.setCapability("browserstack.appium_version", "1.22.0");
        mutableCapabilities.setCapability("browserstack.user", CFG.user());
        mutableCapabilities.setCapability("browserstack.key",  CFG.key());
        mutableCapabilities.setCapability("app", APP_URL);
        mutableCapabilities.setCapability("device", "Google Pixel 3");
        mutableCapabilities.setCapability("os_version", "9.0");
        mutableCapabilities.setCapability("project", "First Java Project");
        mutableCapabilities.setCapability("build", "browserstack-build-1");
        mutableCapabilities.setCapability("name", "first_test");

        return new RemoteWebDriver(getBrowserstackUrl(), mutableCapabilities);
    }

    @Nonnull
    @Deprecated
    public WebDriver createDriver(DesiredCapabilities caps) {

        // Set your access credentials
        caps.setCapability("browserstack.user", CFG.user());
        caps.setCapability("browserstack.key", CFG.key());

        // Set URL of the application under test
        caps.setCapability("app", APP_URL);

        // Specify device and os_version for testing
        caps.setCapability("device", "Google Pixel 3");
        caps.setCapability("os_version", "9.0");

        // Set other BrowserStack capabilities
        caps.setCapability("project", "First Java Project");
        caps.setCapability("build", "browserstack-build-1");
        caps.setCapability("name", "first_test");


        return new AndroidDriver(getBrowserstackUrl(), caps);
    }
}