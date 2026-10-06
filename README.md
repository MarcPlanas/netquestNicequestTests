# Nicequest Android UI Tests

This project contains mobile UI tests for the Nicequest Android application. The tests use Appium, Selenium, Cucumber, JUnit, and Maven.

> **Android only:** The test scenarios target an Android emulator or device. They are not configured to run on iOS.

## Requirements

- Java JDK 20 (the Maven compiler is configured for Java 20)
- Apache Maven
- Android Studio with the Android SDK and an Android emulator
- Node.js and npm, to install and run the Appium server
- Appium 2 with the UiAutomator2 driver

Selenium, Cucumber, JUnit, and the Appium Java client are Maven dependencies declared in `pom.xml`; you do not need to install those separately. WebdriverIO is a JavaScript Appium client and is not used by this Java project.

## One-time setup

1. Install the JDK, Maven, Android Studio, and Node.js. Make sure `java`, `mvn`, `adb`, `node`, and `npm` are available from a terminal.
2. In Android Studio, install the SDK platform and create an Android Virtual Device. This project has been used with an API 37 emulator.
3. Install Appium and its Android automation driver:

   ```powershell
   npm install --global appium
   appium driver install uiautomator2
   ```

4. Download the Nicequest Android app as an `.xapk` file. An XAPK is a package archive containing the base APK and configuration split APKs, so extract it before running the tests. Place the base APK and required split APKs together in the project's `apk\` folder (for example, `com.netquest.pokey.apk`, `config.arm64_v8a.apk`, `config.en.apk`, and `config.mdpi.apk`). The tests install these split APKs as a set; do not try to install only the base APK. The paths in `NicequestLoginSteps.java` currently point to `C:\01Dev\netquest\apk\`; update those paths if you store the project elsewhere.
5. The valid-login scenario uses credentials configured in `LoginLocators.java`. Use a dedicated test account and do not commit real account credentials to source control.

## Start the test environment

Before running a scenario:

1. Start the Android emulator from Android Studio's Device Manager, or connect an Android device with USB debugging enabled.
2. Confirm that Android sees the device:

   ```powershell
   adb devices
   ```

3. Start Appium in a separate terminal and leave it running:

   ```powershell
   appium
   ```

   The tests connect to `http://127.0.0.1:4723`.
4. Open another terminal in the project root (the directory containing `pom.xml`).

## Run the Cucumber tests

Run all scenarios in the Cucumber feature:

```powershell
mvn -Dtest=CucumberTest test
```

Run only the valid-login and invalid-login scenarios:

```powershell
mvn -Dtest=CucumberTest '-Dcucumber.filter.tags=@happyPath or @negative' test
```

Run a single tagged scenario:

```powershell
mvn -Dtest=CucumberTest '-Dcucumber.filter.tags=@happyPath' test
mvn -Dtest=CucumberTest '-Dcucumber.filter.tags=@negative' test
```

The feature also contains installation, uninstall, and login scenarios marked with `@skip`. This tag is only a label; Cucumber does not automatically skip scenarios with that tag. To run the `@install` scenario explicitly:

```powershell
mvn -Dtest=CucumberTest '-Dcucumber.filter.tags=@install' test
```

## Run from an IDE

Open the Maven project in IntelliJ IDEA or another Java IDE, allow Maven to import the dependencies, and run `CucumberTest` or use the same Maven commands above from the IDE's terminal. The emulator and Appium server must still be running.
