import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NicequestLoginSteps {

    private static final String APP_PACKAGE = "com.netquest.pokey";
    private static final List<String> APK_FILES = List.of(
            "C:\\01Dev\\netquest\\apk\\com.netquest.pokey.apk",
            "C:\\01Dev\\netquest\\apk\\config.arm64_v8a.apk",
            "C:\\01Dev\\netquest\\apk\\config.en.apk",
            "C:\\01Dev\\netquest\\apk\\config.mdpi.apk"
    );

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Android Emulator");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @Given("I install the application")
    public void installApplication() {
        driver.executeScript("mobile: installMultipleApks", Map.of("apks", APK_FILES));
    }

    @Given("I uninstall the application")
    public void uninstallApplication() {
        if (driver.isAppInstalled(APP_PACKAGE)) {
            driver.removeApp(APP_PACKAGE);
        }
    }

    @When("I open the app")
    public void openApp() {
        driver.activateApp(APP_PACKAGE);
        clickIfPresent("Don't Show Again", Duration.ofSeconds(5));
    }

    @When("I skip the options")
    public void skipOptions() {
        clickIfPresent("Skip", Duration.ofSeconds(30));
        clickIfPresent("Later", Duration.ofSeconds(3));
        clickIfPresent("I already have an account", Duration.ofSeconds(3));
    }

    @When("I log in with valid email and password")
    public void loginWithValidCredentials() {
        enterCredentials(LoginLocators.NETQUEST_EMAIL,
                LoginLocators.NETQUEST_PASSWORD);
        submitLogin();
    }

    @When("I log in with invalid email and password")
    public void loginWithInvalidCredentials() {
        enterCredentials("invalid-login@example.invalid", "InvalidPassword123!");
        submitLogin();
    }

    @Then("the login should succeed")
    public void verifyLoginSucceeded() {
        assertTrue(new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        LoginLocators.START)).isDisplayed(),
            "The sign-in screen should be visible after a successful login.");
    }

    @Then("the login should be rejected")
    public void verifyLoginRejected() {
        assertTrue(new WebDriverWait(driver, Duration.ofSeconds(15))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                LoginLocators.EMAIL)).isDisplayed(),
                "The sign-in screen should remain visible after invalid credentials.");
    }

    private void enterCredentials(String email, String password) {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        LoginLocators.EMAIL))
                .sendKeys(email);
        driver.findElement(LoginLocators.PASSWORD).sendKeys(password);
    }

    private void submitLogin() {
        clickIfPresent("Log in", Duration.ofSeconds(3));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void clickIfPresent(String text, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.elementToBeClickable(
                            AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")")))
                    .click();
        } catch (TimeoutException ignored) {
            // These onboarding and compatibility dialogs may not appear on every launch.
        }
    }
}
