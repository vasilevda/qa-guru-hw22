package helpers;

import config.MobileConfig;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class Browserstack {
    static final MobileConfig CFG = ConfigFactory.create(MobileConfig.class);

    public static String videoUrl(String sessionId) {
        return given()
                .auth().basic(CFG.user(), CFG.key())
                .when()
                .get("https://api-cloud.browserstack.com/app-automate/sessions/" + sessionId +".json")
                .then()
                .statusCode(200)
                .extract()
                .path("automation_session.video_url");
    }
}
