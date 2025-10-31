package CareBoarding_BaseClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class InvokeDriver_WebAndApp {

    protected WebDriver webDriver;
    protected AndroidDriver mobileDriver;

    // ======== WEB SETUP ========
    
    @BeforeTest(alwaysRun = true)
    public void setupWeb() {
        EdgeOptions options = new EdgeOptions();
        webDriver = new EdgeDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.get("https://app.careboarding.com/");
        System.out.println("✅ Web browser launched successfully.");
    }
    

    // ======== MOBILE SETUP ========
    
    @SuppressWarnings("deprecation")
	protected AndroidDriver initMobileDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("MyAppiumEmulator1")
                .setAutomationName("UiAutomator2")
                .setApp(System.getProperty("user.dir") + "\\src\\test\\java\\CareBoarding_Resource\\83.apk")
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(180))
                .setNoReset(false);

        try {
            System.out.println("📱 Trying to connect to Appium server at http://127.0.0.1:4723");
            mobileDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            System.out.println("✅ Mobile driver initialized successfully.");
        } catch (Exception e) {
            System.out.println("❌ Connection failed. Retrying with legacy Appium URL...");
            mobileDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        }
        return mobileDriver;
    }

    // ======== CLEANUP ========
	@AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            //webDriver.close(); // Uncomment this to close browser after test
            System.out.println("🧹 WebDriver session maintained for debugging.");
        }
        if (mobileDriver != null) {
            //mobileDriver.quit(); // Uncomment this to close app after test
            System.out.println("🧹 Mobile driver session maintained for debugging.");
        }
    }
}