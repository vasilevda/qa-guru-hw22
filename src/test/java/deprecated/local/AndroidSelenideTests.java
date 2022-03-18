package deprecated.local;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

@Deprecated
public class AndroidSelenideTests extends TestBase {

    void searchTest() {
        step("Skip onboarding page", () -> {
            for (int i = 0; i < 3; i++) {
                $(AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_forward_button"))
                        .click();
            }
            $(AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_done_button"))
                    .click();
        });

        step("Type search", () -> {
            $(AppiumBy.accessibilityId("Search Wikipedia")).click();
            $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"))
                    .setValue("BrowserStack");
        });
        step("Verify content found", () ->
                $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }
}

