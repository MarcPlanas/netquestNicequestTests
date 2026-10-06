import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public final class LoginLocators {

    public static final By EMAIL = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"sign-in-email-input\")");
    public static final By PASSWORD = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"sign-in-password-input\")");
    public static final String NETQUEST_EMAIL = "your_email_here"; // Replace with the actual email
    public static final String NETQUEST_PASSWORD = "your_password_here"; // Replace with the actual password
    public static final By START = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"tab-button-Start\")");

    private LoginLocators() {
    }
}
