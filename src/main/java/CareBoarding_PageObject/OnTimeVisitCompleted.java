package CareBoarding_PageObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class OnTimeVisitCompleted {
	
	
	 WebDriver driver;
	 WebDriverWait wait;

	public  OnTimeVisitCompleted(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // Initialize elements with PageFactory if needed
    }

	//=================================Website login==========================================
	    
		 public void login(String username, String password) {
		        try {
		            // Debug: Print page title and URL
		            System.out.println("Page Title: " + driver.getTitle());
		            System.out.println("Current URL: " + driver.getCurrentUrl());

		            WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email' or @name='email']"))); 
		            emailInput.clear();
		            emailInput.click();
		            emailInput.sendKeys(username);

		            WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']")));
		            passwordInput.clear();
		            passwordInput.sendKeys(password);
		            
		            WebElement RememberMe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@for='remember']")));
		            RememberMe.click();

		            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='loginButton']")));
		            loginButton.click();

		            wait.until(d -> !d.getCurrentUrl().contains("login"));
		            System.out.println("Login successful, new URL: " + driver.getCurrentUrl());

		        } catch (NoSuchElementException e) {
		            System.err.println("Element not found: " + e.getMessage());
		        } catch (Exception e) {
		            System.err.println("Login failed: " + e.getMessage());
		            e.printStackTrace();
		        }
		    }
	         
	  //============== Click "Caregivers" Field and Verify Functionality===================== 
	    
	             @FindBy(xpath = "(//*[@class= 'nav-item sidebar-nav-item '])[1]")
	             private WebElement Caregivers;

	             public void Click_caregivers () {
	             Caregivers.click();	
	}
	             
	// ===========================Open a dropdown and Select the Search caregiver ====================

              @FindBy(xpath = "//*[@class='nav nav-treeview ']/li[1]/a")
              private WebElement searchCaregivers;

             public void clickSearchCaregivers() {
             wait.until(ExpectedConditions.elementToBeClickable(searchCaregivers)).click();
        }
	             
	             
	//============================Search the Caregiver name in Search Bar ====================
	             
	             @FindBy(xpath = "//*[@id='SearchInput']/input")
	             private WebElement CaregiversNameSearch;

	             public void Enter_CaregiversNameSearch(String CaregiversName) throws InterruptedException {
	                 // Click on the search input field
	                 CaregiversNameSearch.click();

	                 // Enter the caregiver ID into the search field
	                 CaregiversNameSearch.sendKeys(CaregiversName);
	                 Thread.sleep(3000);

                 // Simulate pressing the down arrow key and then enter
                 CaregiversNameSearch.sendKeys(Keys.ARROW_DOWN);
                 CaregiversNameSearch.sendKeys(Keys.ENTER);
 
				String elementId = CaregiversNameSearch.getAttribute("id");
                 System.out.println("Element ID: TWI-000003 " + elementId);

	             } 
	            
	//======================== caregiver calendar for View Caregiver page.===================	
	             
	             @FindBy(xpath = "//span[normalize-space()='View Calendar']")
	             private WebElement caregiverCalendar;

	             public void selectCaregiverFromCalendar() throws InterruptedException {
	                 // Scroll the caregiverCalendar element into view
	                 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", caregiverCalendar);

	                 // Optional: Adjust scroll position if necessary to avoid fixed headers
	                 ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -100);");

	                 // Click the element
	                 caregiverCalendar.click();
	             }
	             

	    		
	           //============================Select the Today date in caregiver calendar page=========================
	             
	                 
	             public void selectCurrentDatebox() {
	                 try {
	                     // Get today's date
	                     LocalDate today = LocalDate.now();
	                     String isoDate = today.toString(); // Format: YYYY-MM-DD
	                     String dayOfMonth = String.valueOf(today.getDayOfMonth());
	                     
	                     // Array of selectors to try
	                     String[] selectors = {
	                         "//td[contains(@class, 'fc-day-top') and contains(@class, 'fc-today')]",
	                         "//td[contains(@class, 'fc-today')]",
	                         "//td[@data-date='" + isoDate + "']",
	                         "//td[.//span[text()='" + dayOfMonth + "']]",
	                         "//div[contains(@class, 'datepicker')]//td[text()='" + dayOfMonth + "']",
	                         "//td[contains(@class, 'today')]"
	                     };
	                     
	                     // Try each selector
	                     for (String selector : selectors) {
	                         try {
	                             WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
	                             if (dateElement != null && dateElement.isDisplayed()) {
	                                 // Scroll element into view
	                                 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dateElement);
	                                 Thread.sleep(500); // Small delay to stabilize the UI
	                                 
	                                 // Try direct click
	                                 try {
	                                     dateElement.click();
	                                     System.out.println("Successfully clicked current date using: " + selector);
	                                     return; // Success - exit method
	                                 } catch (Exception clickError) {
	                                     // If direct click fails, try JavaScript click
	                                     ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateElement);
	                                     System.out.println("Successfully JavaScript-clicked current date using: " + selector);
	                                     return; // Success - exit method
	                                 }
	                             }
	                         } catch (Exception e) {
	                             // Continue to next selector
	                             System.out.println("Selector failed: " + selector + " - " + e.getMessage());
	                         }
	                     }
	                     
	                     // If we get here, all specific selectors failed, try CSS approach
	                     try {
	                         WebElement currentDate = wait.until(ExpectedConditions.elementToBeClickable(
	                             By.cssSelector("td.fc-day-top.fc-today, td.fc-today, .fc-today")));
	                         currentDate.click();
	                         return;
	                     } catch (Exception e) {
	                         // Last resort - JavaScript approach
	                         try {
	                             JavascriptExecutor js = (JavascriptExecutor) driver;
	                             js.executeScript("document.querySelector('.fc-today, td.fc-today, td.today').click();");
	                             return;
	                         } catch (Exception jsError) {
	                             // Handle fallback approach - look for day number in any cell
	                             List<WebElement> cells = driver.findElements(By.tagName("td"));
	                             for (WebElement cell : cells) {
	                                 if (cell.getText().equals(dayOfMonth)) {
	                                     ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cell);
	                                     Thread.sleep(500);
	                                     cell.click();
	                                     return;
	                                 }
	                             }
	                         }
	                     }
	                     
	                     System.out.println("Failed to select current date with all approaches");
	                 } catch (Exception e) {
	                     System.out.println("Exception in selectCurrentDatebox: " + e.getMessage());
	                     e.printStackTrace();
	                 }
	             }
	          

			//=========​Schedule the visit to occur 15 minutes after the current time.=============  

			 @FindBy(xpath = "//*[@name='in_time']")
			 private WebElement SetInTime1;

			 public void ScheduleInTime() {
			     try {
			    	 
			  Calendar calendar = Calendar.getInstance();
			  calendar.add(Calendar.MINUTE, 10);
			            
			  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			  String newTime = sdf.format(calendar.getTime());

			  WebElement timeInput = driver.findElement(By.xpath("//*[@name='in_time']"));
			  timeInput.sendKeys(newTime); // Enter the time after adding 15 minutes
			  
			  System.out.println("New time after 10 minutes (" + newTime + ") has been entered into the time picker.");
			            
			   } catch (Exception e) {
			     e.printStackTrace();
			   } finally {
	         }
			 }
		 
			 
			//=========== Schedule the visit to coincide with the current visit's in-time ============
				

			 @FindBy(xpath = "//*[@name='out_time']")
			 private WebElement OutTime;

			 public void ScheduleOutTime() {
			     try {
			    	 
			    	 Calendar calendar = Calendar.getInstance();
			            calendar.add(Calendar.MINUTE, 20);
			            
			            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			            String newTime = sdf.format(calendar.getTime());

			            WebElement timeInput = driver.findElement(By.xpath("//*[@name='out_time']"));
			            timeInput.sendKeys(newTime); // Enter the time after adding 30 minutes

			            
			            System.out.println("New time after 20 minutes (" + newTime + ") has been entered into the time picker.");
			            
			        } catch (Exception e) {
			            e.printStackTrace();
			        } finally {
			           
			  
			        }
			    }
			 
 // ===============Patients Name========================
			 
			  public void Select_PatientNameInDropDown() throws InterruptedException {
					    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					    	    WebElement pocDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='selection'])[11]/span")));
					    	    pocDropdown.click();
					    	    pocDropdown.sendKeys("Johnson, Emily G. (TWI-000002)");
					    	    pocDropdown.sendKeys(Keys.ENTER);
					    	
				    	}
			 
			//=====================Pay Rate==================================
			 

			         public void Select_PayRateInDropDown() throws InterruptedException {
			    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			    	 WebElement payRateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='selection'])[12]/span")));
			    	 payRateElement.click();
			    	 payRateElement.sendKeys("Hub Location ");
			    	 payRateElement.sendKeys(Keys.ENTER);
			    	 
			     }
		     
		     //==========================POC Number====================
		     
			            public void SelectPOCNumberInDropDown() {
			    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			    	    WebElement pocDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='selection'])[13]/span")));
			    	    pocDropdown.click();
			    	    pocDropdown.sendKeys("AI Chatbot Integration (431892)");
			    	    pocDropdown.sendKeys(Keys.ENTER);
			    	
		    	}
		     
		     //=========================Service Code====================
			   
		     
			            public void selectServiceCodeNumberInDropDown() {
			    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			    	    WebElement serviceCodeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='col-md-12'])[10]/div/span/span/span")));
			    	    serviceCodeDropdown.click();
			    	    serviceCodeDropdown.sendKeys("S9123");
			    	    serviceCodeDropdown.sendKeys(Keys.ENTER);
			     }
			 
		 
			    
		    //==========================Create Button====================
		    	 
		    	 @FindBy(xpath ="//*[@id='changeStatusFormSubBtn']")
		    	 private WebElement CreateButton;
		    	 
		    	 public void ClickCreatebtn () {
		    		 CreateButton.click();
		    	 }
		    	 



// ------------------------------Click the Ok button in Notification pop-up----------------------------

		    		@FindBy(xpath = "//button[normalize-space()='OK']")
		    		private WebElement ClickOkBtn;

		    		public void ClicktheOkbtnInNotification() {
		    			ClickOkBtn.click();

		    		}
		    	 
	//=========================Appium code========================================  
		 
			    	 
		    	    private String caregiverEmail = "sarah.thompson@yopmail.com";
		    	    private String caregiverPassword = "12345678"; // Default password

		    	    public void AppiumClass(AppiumDriver mobileDriver) {
		    	        
		    	        this.driver = mobileDriver;
		    	        PageFactory.initElements(mobileDriver, this);
		    	    }
		    	    
		    	    public void setCaregiverEmail(String email) {
		    	        this.caregiverEmail = email;
		    	    }
		    	    
		    	    public void setCaregiverPassword(String password) {
		    	        this.caregiverPassword = password;
		    	    }

		    	    public void ClicktheTodaySchedulebtn() throws InterruptedException {
		    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    	        // Step 1: Click "Continue"
		    	        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Continue"))).click();

		    	    
	    	        // Step 3: Enter Email
                     WebElement emailField = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)"));
	    	        emailField.click();
		    	        
		    	        // Use the stored caregiver email if available, otherwise use default
		    	        emailField.sendKeys(caregiverEmail != null ? caregiverEmail : "automation@yopmail.com");
		    	      
	    	        // Step 4: Enter Password
	    	        WebElement passwordField = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)"));
	    	        passwordField.click();
	    	        passwordField.sendKeys(caregiverPassword);

		    	        // Step 5: Click "Sign In"
		    	        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Sign In"))).click();
		    	       
		    	    }
		    	    
		    	    //Step: 6  We are refreshed our look! pop-up "Got it" button click
	  
		    	    public void ClickTheGotItbtn() {
		    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    	        WebElement gotItBtn = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().description(\"Got it!\")")));
		    	        gotItBtn.click();
		    	    }
		    	        
		    	       // Step 7: Click the today's schedule section
		    	    
		    	    public void clickTodayScheduleButton() {
		    	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		    	        // Define a list of locator strategies
		    	        List<By> locators = Arrays.asList(
		    	            AppiumBy.accessibilityId("Today's Schedule"),
		    	            AppiumBy.xpath("//android.view.View[contains(@content-desc, 'Today')]"),
		    	            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Today\")"),
		    	            AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Today\")"),
		    	            AppiumBy.xpath("//android.view.View[@clickable='true' and (contains(@content-desc, 'Today') or contains(@text, 'Today'))]"),
		    	            AppiumBy.xpath("//android.widget.TextView[contains(@text, 'Today')]")
		    	        );

		    	        // Try each locator strategy
		    	        for (By locator : locators) {
		    	            try {
		    	                WebElement scheduleElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
		    	                scheduleElement.click();
		    	                System.out.println("Clicked Today's Schedule using: " + locator);
		    	                return; // Exit after the first successful click
		    	            } catch (Exception e) {
		    	                System.out.println("Failed to click using locator: " + locator + " - " + e.getMessage());
		    	            }
		    	        }

		    	        // If no strategy succeeds
		    	        System.out.println("Could not find Today's Schedule element using any method");
		    	    }
		    	    // New method to login with email and password from web app
		    	    public void loginWithWebCredentials(String email, String password) throws InterruptedException {
		    	        // Set the email and password for login
		    	        setCaregiverEmail(email);
		    	        setCaregiverPassword(password);
		    	        
		    	        // Proceed with regular login flow
		    	        ClicktheTodaySchedulebtn();
		    	    }
		    	    
		    	    // Keep existing method for backward compatibility
		    	    public void loginWithWebEmail(String email) throws InterruptedException {
		    	        // Set the email for login using default password
		    	        setCaregiverEmail(email);
		    	        
		    	    }
		    	  
		    	    public void clickSimpleTodaySchedule() throws InterruptedException {
		    	        // Wait for app to stabilize
		    	        Thread.sleep(5000);
		    	        
		    	        try {
		    	            // Try to find any element with "Today" in the description
		    	            System.out.println("Looking for any element with 'Today' in description");
		    	            List<WebElement> elements = driver.findElements(AppiumBy.xpath("//*[contains(@content-desc, 'Today')]"));
		    	            
		    	            if (elements.size() > 0) {
		    	                elements.get(0).click();
		    	                System.out.println("Clicked element with 'Today' in description");
		    	                return;
		    	            }
		    	            
		    	            // Try direct UI Automator selector
		    	            System.out.println("Trying UI Automator selector");
		    	            driver.findElement(AppiumBy.androidUIAutomator( "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("  + "new UiSelector().textContains(\"Today\"))")).click();
		    	            System.out.println("Clicked element with UI Automator");
		    	            
		    	        } catch (Exception e) {
		    	            System.out.println("First attempts failed. Trying direct tap on screen areas...");
		    	            
		    	            // Get screen dimensions
		    	            int width = driver.manage().window().getSize().width;
		    	            int height = driver.manage().window().getSize().height;
		    	            
		    	            // Tap middle top area of screen (common location for menu items)
		    	            new Actions(driver)
		    	                .moveToLocation(width/2, height/4)
		    	                .click()
		    	                .perform();
		    	            
		    	            System.out.println("Tapped screen area where Today's Schedule might be");
		    	        }
		    	    }
		    	    
		    	    public void selectVisitByTime(String targetTime) {
		    	        try {
		    	            // Try exact match with XPath
		    	            driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,'" + targetTime + "')]")).click();
		    	        } catch (Exception e1) {
		    	            try {
		    	                // Fallback: Try using UiAutomator
		    	                String selector = "new UiSelector().textContains(\"" + targetTime + "\")";
		    	                driver.findElement(AppiumBy.androidUIAutomator(selector)).click();
		    	            } catch (Exception e2) {
		    	                try {
		    	                    // Fallback: Loop through elements and find partial match
		    	                    List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
		    	                    for (WebElement el : elements) {
		    	                        if (el.getText().contains(targetTime)) {
		    	                            el.click();
		    	                            return;
		    	                        }
		    	                    }
		    	                    System.out.println("Time not found: " + targetTime);
		    	                } catch (Exception e3) {
		    	                    System.out.println("Error selecting time: " + e3.getMessage());
		    	                }
		    	            }
		    	        }
		    	    }

	    // Add this new method to select the most recent visit
		    	    public void selectVisitByText(String visitText) {
		    	        try {
		    	            // Wait for visit list to load
		    	            Thread.sleep(2000);

		    	            // Find all text elements in the visit list
		    	            List<WebElement> visitElements = driver.findElements(AppiumBy.className("android.widget.TextView"));

		    	            for (WebElement visit : visitElements) {
		    	                if (visit.getText() != null && visit.getText().contains(visitText)) {
		    	                    visit.click();
		    	                    System.out.println("Clicked visit: " + visit.getText());
		    	                    return;
		    	                }
		    	            }

		    	            System.out.println("Visit not found: " + visitText);

		    	        } catch (Exception e) {
		    	            e.printStackTrace();
		    	        }
		    	    }
		    	    
	    
	 // Simple method to click Clock-In button
	    public void clockIn() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement clockInButton = wait.until(
	            ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("CLOCK IN"))   );
	        clockInButton.click();
	    }
	    
	    //  Location confirm button click In Clock-In section
	    public void ClicktheconfirmBtn() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement Confirmbtn = wait.until(
	            ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Confirm")));
	        Confirmbtn.click();
	    }
	    
	    //Click ok button in Clock-In section
	    public void ClicktheOkBtn() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement OKbtn = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc='Ok']")));
	        OKbtn.click();
	    }
	    
	    
	 // Simple method to click Clock-Out button
	    public void clockOut() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement clockOutButton = wait.until(
	            ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("CLOCK OUT")));
	        clockOutButton.click();
	    }
	    
	    //  Location confirm button click In Clock-Out section
	    public void ClicktheconfirmBtnclocckOut() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement ConfirmbtnInnclockOut = wait.until( ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Confirm")));
	        ConfirmbtnInnclockOut.click();
	    }
	    
	    //Select the plan of care tasks section
	    
	    public void SelectTheoneplan() {
	   	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));   
	   	WebElement SelectTheOnePlan= wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.view.View[@content-desc='Personal Care'])[1]/android.view.View[2]")));
	   	SelectTheOnePlan.click();
	    }
	    
	    //Draw patients Signaturw
	    
	    public void drawSimpleSignature() {
	        try {
	            System.out.println("Drawing simple signature...");

	            // Get screen center
	            Dimension size = driver.manage().window().getSize();
	            int x = size.getWidth() / 2;
	            int y = size.getHeight() / 2;

	            // Create a simple zigzag signature
	            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	            Sequence sign = new Sequence(finger, 0);
	            sign.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x - 50, y));
	            sign.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	            sign.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), x, y + 40));
	            sign.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), x + 50, y - 40));
	            sign.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

	            ((RemoteWebDriver) driver).perform(Arrays.asList(sign));
	            System.out.println("Signature complete.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
 
	    
	    //Click The Save button
	    
	    public void ClickSavebtn() {
	   	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));   
	   	WebElement ClickTheSavebtn= wait.until( ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[@content-desc='Save']")));
	   	ClickTheSavebtn.click();
	    
	}
	    
	    // The Streak Missed! pop message click OK button
	    
	    
	    public void ClickStreakMissedbtn() {
	      	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));   
	      	WebElement ClickTheOkbtn= wait.until( ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.view.View[@content-desc=\"Ok\"]")));
	      	ClickTheOkbtn.click();
	       
	   }
	    
	    
	}    

