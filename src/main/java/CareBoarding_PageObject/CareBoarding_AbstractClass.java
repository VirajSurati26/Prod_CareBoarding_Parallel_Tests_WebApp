package CareBoarding_PageObject;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;

public class CareBoarding_AbstractClass {
	
	
	public WebDriver driver;
    public WebDriverWait wait;
    public static String email;
    public static String password = "Viraj@123456";

//====================Constructor initializing WebDriver and WebDriverWait===========================
    
    public CareBoarding_AbstractClass(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    public static String getEmail() {
        if (email == null) {
            email = "user" + java.util.UUID.randomUUID().toString() + "@example.com";
        }
        return email;
    }

    public static String getPassword() {
        return password;
    }

//===================== Utility function to wait for elements to be visible==========================
    public void waitForElement(WebElement ele) {
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    //Sleep utility for pauses (use sparingly)
    
    public void hold() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Perform an Enter key action using Actions class
    public void actions() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).build().perform();
    }
    
 //==========================LogOut===============================
	
	   @FindBy(xpath ="//*[@class='main-header navbar navbar-expand navbar-white navbar-light']/div[2]")
      private WebElement Log_Out_Icon;
	   
	   public void Click_Logout () {
		   Log_Out_Icon.click();   	
		   
	   }
	   
	 //================= Thread sleep ============================

		public void pauserun1() {
					try {
				Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
		public void pauserun2() {
			try {
		Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
		public void pauserun3() {
			try {
		Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		
		public void pauserun4() {
			try {
		Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}

//======================Scroll down===================================   		   
    		   
    		public void Vertical_ScrollDown () {
    			
    			JavascriptExecutor js = (JavascriptExecutor) driver;
    			js.executeScript("window.scrollBy(0,800)");
    		}
    		
	public void Vertical_ScrollDown1 () {
    			
    			JavascriptExecutor js = (JavascriptExecutor) driver;
    			js.executeScript("window.scrollBy(0,1200)");
    		}
	
		
public void Calendar_ScrollDown() {
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,700)");
		}

	
	
	public void Vertical_PopUpScrollDown () {
		
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollBy(0,300)");
	}	
		
	//Close button in pop-up dialog
	
	 @FindBy(xpath = "(//*[@class='modal-content'])[3]/div[1]/button/span")
	    private WebElement CloseBtn;

	    public void ClickTheCloseBtnPopUp() {
	        CloseBtn.click();
	    }
	    
	 //Scroll
	    
	    public void scrollAction(WebElement element) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(true);", element);
	    }
	    
	    public void VisitVertical_ScrollDown(By elementLocator) {
	        if (elementLocator == null) {
	            throw new IllegalArgumentException("Locator must be set");
	        }

	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        // Use WebDriverWait to wait for the element to be visible
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust timeout if needed
	        while (true) {
	            // Scroll down by 3000px
	            js.executeScript("window.scrollBy(0,3000)");

	            // Wait for the element to be visible
	            try {
	                wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
	                WebElement element = driver.findElement(elementLocator);
	                if (element.isDisplayed()) {
	                    break; // Exit the loop when the element is found
	                }
	            } catch (Exception e) {
	                // Log exception or handle it
	                System.out.println("Element not visible yet, scrolling more...");
	            }

	            // Optionally, add a small delay
	            try {
	                Thread.sleep(500); // Sleep for half a second
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		
		public void ChromeNotification () {
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.querySelector('div[role=\"dialog\"]').remove();");
		}
	    
	    
	
	    	    

public void drawRandomSignature() {
    try {
        // Using selenium's PointerInput for touch actions
        org.openqa.selenium.interactions.PointerInput finger = 
            new org.openqa.selenium.interactions.PointerInput(
                org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
        
        org.openqa.selenium.interactions.Sequence sequence = 
            new org.openqa.selenium.interactions.Sequence(finger, 0);

        WebElement signaturePadElement = driver.findElement(
                AppiumBy.xpath("//*[contains(@content-desc, 'reason') or contains(@text, 'reason') or contains(@resource-id, 'reason')]"));
        
        // Get element dimensions
        int elementX = signaturePadElement.getLocation().getX();
        int elementY = signaturePadElement.getLocation().getY();
        int elementWidth = signaturePadElement.getSize().getWidth();
        int elementHeight = signaturePadElement.getSize().getHeight();

        // Define starting point
        int startX = elementX + 50;
        int startY = elementY + (elementHeight / 2);

        // Start the touch sequence
        sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), 
                org.openqa.selenium.interactions.PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(finger.createPointerDown(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofMillis(50)));

        // Create movement points for signature
        // Example: simple left-to-right line with slight variation
        for (int i = 1; i <= 20; i++) {
            int moveX = startX + (i * (elementWidth / 20));
            int moveY = startY + (int)(Math.sin(i) * 10); // Small wave pattern
            
            sequence.addAction(finger.createPointerMove(Duration.ofMillis(5), 
                    org.openqa.selenium.interactions.PointerInput.Origin.viewport(), moveX, moveY));
            sequence.addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofMillis(5)));
        }

        // End the touch sequence
        sequence.addAction(finger.createPointerUp(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));

        // Perform the action
        ((RemoteWebDriver) driver).perform(Collections.singletonList(sequence));
    } catch (Exception e) {
        System.out.println("Error drawing signature: " + e.getMessage());
        e.printStackTrace();
    }
}

}