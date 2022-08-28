package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.util.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//input[@placeholder='Город']").setValue(validUser.getCity()).pressTab().pressEscape();
        $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(firstMeetingDate);
        $x("//input[@name='name']").setValue(validUser.getName());
        $x("//input[@name='phone']").setValue(validUser.getPhone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[contains(text(),'Запланировать')]").click();
        $x("//div[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[@data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), visible);
        $("[data-test-id='date'] .input__control").pressEscape().doubleClick().sendKeys(secondMeetingDate);
        $x("//span[contains(text(),'Запланировать')]").click();
        $x("//div[contains(text(),'Необходимо подтверждение')]").shouldHave(visible, text("Необходимо подтверждение"));
        $x("//button[@class='button button_view_extra button_size_s button_theme_alfa-on-white']").click();
        $x("//div[contains(text(),'Успешно')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[@data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate), visible);

    }

}
