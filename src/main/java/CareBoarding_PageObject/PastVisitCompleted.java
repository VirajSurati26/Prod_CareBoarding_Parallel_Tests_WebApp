package CareBoarding_PageObject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class PastVisitCompleted {
	

	 WebDriver driver;
	    WebDriverWait wait;
	    
	    public PastVisitCompleted(WebDriver driver) {
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
                 System.out.println("Element ID: TSA-000098 " + elementId);

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

	             //Past In Time 
		     	   	
		   		 @FindBy(xpath = "//*[@name='in_time']")
		   		 private WebElement PastSetInTime;
		   		 
		   		 public void EnterPastTheInTime() {
		   			PastSetInTime.click();	
		   		 }
		   		 
		   		//Enter the past visit Schedule In time  

		   		@FindBy(xpath = "//*[@name='in_time']")
		   		private WebElement PastscheduleInTime;

		   		public void After1hourSchedulePastInTime() {
		   		    try {
		   		        // Get the current time and subtract one minute
		   		        Calendar calendar = Calendar.getInstance();
		   		        calendar.add(Calendar.HOUR, -2);  // Subtract 1 minute

		   		        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		   		        String newTime = sdf.format(calendar.getTime());

		   		        // Use the WebElement PastscheduleInTime to set the time
		   		        PastscheduleInTime.clear();  // Clear any existing value
		   		        PastscheduleInTime.sendKeys(newTime);  // Set the new time value

		   		        // Optionally, print the new time for confirmation
		   		        System.out.println("New time after subtracting -2 hour (" + newTime + ") has been entered into the time picker.");
		   		    } catch (Exception e) {
		   		        e.printStackTrace();  // Log any exceptions
		   		    }
		   		}
		   		
		   	 //Past Out Time
		   		 
		   		 @FindBy(xpath = "//*[@name='out_time']")
		   		 private WebElement SetPatOutTime;
		   		 
		   		 public void EnterThePastOutTime() {
		   			SetPatOutTime.click();	
		   		 }
		   	 
		   		 
		   		//Enter the Past Schedule OutTime
		   			
		   		@FindBy(xpath = "//*[@name='out_time']")
				 private WebElement PastOutTime;

				 public void VisitCreatedWithin1HourAfterScheduledInTime() {
				     try {
				    	 
				    	 Calendar calendar = Calendar.getInstance();
				            calendar.add(Calendar.HOUR, -1);
				            
				            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				            String newTime = sdf.format(calendar.getTime());

				            WebElement timeInput = driver.findElement(By.xpath("//*[@name='out_time']"));
				            timeInput.sendKeys(newTime); // Enter the time after adding -4 hour

				            // You can also perform other actions or validations after setting the time.
				            System.out.println("New time after -1 hour (" + newTime + ") has been entered into the time picker.");
				            
				        } catch (Exception e) {
				            e.printStackTrace();
				        } finally {

				        }
				    }
				 // ===============Patients Name========================
				 
				  public void Select_PatientNameInDropDown() {
					    
					    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

					    // Wait and click on the dropdown
					    WebElement Patients = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-patientIdVal-container")));
					    Patients.click();

					    // Wait for the dropdown options to be visible
					    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='select2-patientIdVal-results']/li")));

					    // Get all patient options in the dropdown
					    List<WebElement> options = driver.findElements(By.xpath("//*[@id='select2-patientIdVal-results']/li"));

					    // Select the 32nd option (index 31 in 0-based list)
					    if (options.size() >= 0) {
					        WebElement selectedOption = options.get(0);
					        System.out.println("Selected option: " + selectedOption.getText());
					        selectedOption.click();
					    } else {
					        System.out.println("Less than 33 options available. Selecting the first one.");
					        if (!options.isEmpty()) {
					            System.out.println("Selected option: " + options.get(0).getText());
					            options.get(0).click();
					        }
					    }
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
		    	
		    	 
		    	//========================== OK Button ==========================

		    	 @FindBy(xpath = "//button[normalize-space()='OK']")
		    	 private WebElement visitOkButton;

		    	 public void clickVisitOkButton() {
		    	     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    	     wait.until(ExpectedConditions.elementToBeClickable(visitOkButton));
		    	     visitOkButton.click();
		    	 }
		    	    
		    	//=========================Visit section=================================

		    		// -----------Verify the Click "VIsits" Field and Verify Function

		    		@FindBy(xpath = "//*[@class='nav nav-pills nav-sidebar nav-sidebar-custom flex-column']/li[5]")
		    		private WebElement Visits;

		    		public void Click_VisitsInsidemenu() {
		    			Visits.click();
		    		}

		    		// ---------Verify opening the "VIsits" section in the side menu, then open the
		    		// drop-down and select the "Visit review" option.

		    		@FindBy(xpath = "//*[@class='nav-item sidebar-nav-item menu-is-opening menu-open']/ul/li[1]/a")
		    		private WebElement Visit_Review;

		    		public void Click_Visit_Review() {
		    			Visit_Review.click();

		    		}

		    		// -------------------Missed card--------------------------------

		    		@FindBy(xpath = "//*[@class='row sales-card']/div[3]/div")
		    		private WebElement MissedCard;

		    		public void ClickTheMissCard() {
		    			MissedCard.click();
		    		}

		    		// ------------------select the today in calendar drop-down----------------------------

		    		public void selectTodayFromDropdown() throws InterruptedException {
		    			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    			WebElement dropdownIcon = wait.until(ExpectedConditions
		    					.elementToBeClickable(By.xpath("//*[@class='col-sm-6 text-right header-btns']/div/div[2]")));

		    			dropdownIcon.click();

		    			// Wait for dropdown options to appear
		    			WebElement todayOption = wait.until(ExpectedConditions.elementToBeClickable(
		    					By.xpath("//*[@class='daterangepicker ltr show-ranges opensright']/div[1]/ul/li[1]")));
		    			todayOption.click();
		    		}
		    	// -----------------------Select the caregiver name and sent notification--------------------
		    		

		    		public void clickTimesheetNotificationIcon(String caregiverName) {
		    		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    		    List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));

		    		    for (WebElement row : rows) {
		    		        if (row.getText().contains(caregiverName)) {
		    		            js.executeScript("arguments[0].scrollIntoView({block:'center'});", row);

		    		            WebElement button = row.findElement(By.xpath(".//button[contains(@title,'Notification') or contains(@class,'notification')]"));
		    		            wait.until(ExpectedConditions.elementToBeClickable(button));

		    		            js.executeScript("arguments[0].click();", button);
		    		            System.out.println("Clicked notification for: " + caregiverName);
		    		            return;
		    		        }
		    		    }

		    		    System.out.println("Caregiver not found: " + caregiverName);
		    		}
		    		
		    		
		    		// ---------------------------Click Yes button--------------------------

		    		@FindBy(xpath = "//*[@class='swal2-actions']/button[1]")
		    		private WebElement YesBtn;

		    		public void ClicktheYesbtn() {
		    			YesBtn.click();
		    		}

		    		// ------------------------------Click the Ok button in Notification
		    		// pop-up----------------------------

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
	    public void selectMostRecentVisit() {
	        try {
	            // Wait for visit list to load
	            Thread.sleep(3000);
	            
	            // Find all TextView elements (which likely contain visit information)
	            List<WebElement> textViews = driver.findElements(AppiumBy.className("android.widget.TextView"));
	            System.out.println("Found " + textViews.size() + " TextView elements");
	            
	            // Try to find elements that look like visit times (e.g., containing "AM" or "PM")
	            List<WebElement> potentialVisits = new ArrayList<>();
	            for (WebElement element : textViews) {
	                try {
	                    String text = element.getText();
	                    if (text != null && (text.contains("AM") || text.contains("PM") || text.contains(":"))) {
	                        potentialVisits.add(element);
	                        System.out.println("Potential visit element: " + text);
	                    }
	                } catch (Exception e) {
	                    // If we can't get text, just continue
	                    continue;
	                }
	            }
	            
	            // If we found elements that look like visits, click the first one
	            if (!potentialVisits.isEmpty()) {
	                System.out.println("Clicking on first potential visit element: " + potentialVisits.get(0).getText());
	                potentialVisits.get(0).click();
	                return;
	            }
	            
	            // If we haven't found anything that looks like a visit time, try clicking the first TextView
	            if (!textViews.isEmpty()) {
	                System.out.println("No visit times found. Clicking first TextView element");
	                textViews.get(0).click();
	                return;
	            }
	            
	            // Last resort: Look for clickable elements
	            List<WebElement> clickableElements = driver.findElements(
	                AppiumBy.xpath("//android.view.View[@clickable='true']"));
	            if (!clickableElements.isEmpty()) {
	                System.out.println("Trying to click first clickable element as last resort");
	                clickableElements.get(0).click();
	                return;
	            }
	            
	            System.out.println("Could not find any suitable elements to click");
	            
	        } catch (Exception e) {
	            System.out.println("Error in selectMostRecentVisit: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Select the very first visit in the list regardless of content
	     */
	    public void selectFirstVisit() {
	        try {
	            // Wait for the screen to fully load
	            Thread.sleep(3000);
	            System.out.println("Attempting to select the first visit in the list");
	            
	            // First approach: Try to find all elements with visit-like content in a list
	            try {
	                List<WebElement> visitItems = driver.findElements(AppiumBy.xpath("//android.widget.ListView/android.view.View"));
	                if (!visitItems.isEmpty()) {
	                    System.out.println("Found " + visitItems.size() + " visit items in the list");
	                    visitItems.get(0).click();
	                    System.out.println("Clicked the first visit item");
	                    return;
	                }
	            } catch (Exception e) {
	                System.out.println("First approach failed: " + e.getMessage());
	            }
	            
	            // Second approach: Try to find any recycler view items
	            try {
	                List<WebElement> recyclerItems = driver.findElements(AppiumBy.xpath("//android.widget.RecyclerView/android.view.View"));
	                if (!recyclerItems.isEmpty()) {
	                    System.out.println("Found " + recyclerItems.size() + " recycler items");
	                    recyclerItems.get(0).click();
	                    System.out.println("Clicked the first recycler item");
	                    return;
	                }
	            } catch (Exception e) {
	                System.out.println("Second approach failed: " + e.getMessage());
	            }
	            
	            // Third approach: Look for any view that might be clickable and appears in a list-like format
	            try {
	                List<WebElement> allViews = driver.findElements(AppiumBy.xpath("//android.view.View"));
	                // Filter for views that are likely list items (not too large, not too small)
	                List<WebElement> potentialListItems = new ArrayList<>();
	                
	                for (WebElement view : allViews) {
	                    try {
	                        if (view.isDisplayed() && view.isEnabled()) {
	                            potentialListItems.add(view);
	                        }
	                    } catch (Exception e) {
	                        // Skip elements we can't interact with
	                    }
	                }
	                
	                if (!potentialListItems.isEmpty()) {
	                    System.out.println("Found " + potentialListItems.size() + " potential list items");
	                    potentialListItems.get(1).click();
	                    System.out.println("Clicked the first potential list item");
	                    return;
	                }
	            } catch (Exception e) {
	                System.out.println("Third approach failed: " + e.getMessage());
	            }
	            
	            // Last resort: Just try to click something in the top part of the screen
	            try {
	                int width = driver.manage().window().getSize().width;
	                int height = driver.manage().window().getSize().height;
	                
	                // Click in the upper third of the screen, which often contains the first item
	                new Actions(driver)
	                    .moveToLocation(width/2, height/4)
	                    .click()
	                    .perform();
	                
	                System.out.println("Performed direct tap on the upper part of the screen");
	                return;
	            } catch (Exception e) {
	                System.out.println("Last resort approach failed: " + e.getMessage());
	            }
	            
	            System.out.println("Could not find any element to click as first visit");
	        } catch (Exception e) {
	            System.out.println("Error in selectFirstVisit: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    /**
	     * A more reliable method to find and select visit by the time pattern
	     */
	    public void findAndSelectVisitByTimePattern() {
	        try {
	            System.out.println("Searching for visit with time pattern (HH:MM AM/PM format)");
	            Thread.sleep(2000);
	            
	            // Take screenshot to help with debugging (comment out if not needed)
	            // File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	            // FileUtils.copyFile(scrFile, new File("visit_screen.png"));
	            
	            // Try to find elements with common time patterns
	            // This will look for patterns like "08:30 AM", "8:30 AM", "08:30 AM To 09:00 AM" etc.
	            List<String> timePatterns = Arrays.asList(
	                "//android.widget.TextView[contains(@text, ':') and (contains(@text, 'AM') or contains(@text, 'PM'))]",
	                "//android.view.View[contains(@content-desc, ':') and (contains(@content-desc, 'AM') or contains(@content-desc, 'PM'))]"
	            );
	            
	            for (String pattern : timePatterns) {
	                try {
	                    List<WebElement> elements = driver.findElements(AppiumBy.xpath(pattern));
	                    if (!elements.isEmpty()) {
	                        System.out.println("Found " + elements.size() + " elements matching time pattern: " + pattern);
	                        for (WebElement element : elements) {
	                            System.out.println("Time element text: " + (element.getText() != null ? element.getText() : "null") +
	                                               ", Content-desc: " + (element.getAttribute("content-desc") != null ? 
	                                                                   element.getAttribute("content-desc") : "null"));
	                        }
	                        
	                        // Click the first element that matches our time pattern
	                        elements.get(0).click();
	                        System.out.println("Clicked the first element with time pattern");
	                        return;
	                    }
	                } catch (Exception e) {
	                    System.out.println("Failed with pattern " + pattern + ": " + e.getMessage());
	                }
	            }
	            
	            // Fallback: Look for any clickable elements that might be visits
	            List<WebElement> clickableViews = driver.findElements(
	                AppiumBy.xpath("//android.view.View[@clickable='true']"));
	            
	            if (!clickableViews.isEmpty()) {
	                // Often the first few elements are headers, so try to click something a bit down the list
	                int indexToClick = Math.min(2, clickableViews.size() - 1);
	                clickableViews.get(indexToClick).click();
	                System.out.println("Clicked a clickable view as fallback");
	                return;
	            }
	            
	            System.out.println("Could not find any elements matching time patterns");
	        } catch (Exception e) {
	            System.out.println("Error in findAndSelectVisitByTimePattern: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	//Clidk the Manually Timesheet     
	
	    public void clickTimesheetbtn() {
	        try {
	            System.out.println("Attempting to click 'Submit Manual Timesheet' button...");
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	            
	            // Define all locator strategies to try
	            List<By> locators = Arrays.asList(
	                AppiumBy.xpath("//android.view.View[@content-desc='Submit Manual Timesheet']"),
	                AppiumBy.accessibilityId("Submit Manual Timesheet"),
	                AppiumBy.xpath("//*[contains(@content-desc, 'Manual Timesheet')]"),
	                AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Manual Timesheet\")")
	            );
	            
	            // Try each locator strategy
	            boolean buttonClicked = false;
	            for (By locator : locators) {
	                try {
	                    WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
	                    button.click();
	                    buttonClicked = true;
	                    System.out.println("'Submit Manual Timesheet' button clicked successfully using: " + locator);
	                    break; // Exit the loop if successful
	                } catch (Exception e) {
	                    System.out.println("Failed to click using: " + locator + " - " + e.getMessage());
	                }
	            }
	            
	            // If all locator strategies failed, try direct W3C action
	            if (!buttonClicked) {
	                try {
	                    System.out.println("Trying W3C actions as last resort...");
	                    // Find any element that might be related to timesheet
	                    List<WebElement> elements = driver.findElements(AppiumBy.xpath("//*[contains(@content-desc, 'Timesheet') or contains(@text, 'Timesheet')]"));
	                    
	                    if (!elements.isEmpty()) {
	                        System.out.println("Found " + elements.size() + " potential timesheet elements");
	                        elements.get(0).click();
	                        System.out.println("Clicked first potential timesheet element");
	                    } else {
	                        // If no element found, try tapping in center-bottom of screen
	                        System.out.println("No timesheet elements found, trying direct tap");
	                        Dimension size = driver.manage().window().getSize();
	                        int centerX = size.getWidth() / 2;
	                        int bottomY = (int)(size.getHeight() * 0.75); // Try lower part of screen
	                        
	                        // Use W3C action to tap
	                        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	                        Sequence tap = new Sequence(finger, 0);
	                        tap.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), centerX, bottomY));
	                        tap.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
	                        tap.addAction(new Pause(finger, Duration.ofMillis(50)));
	                        tap.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
	                        
	                        ((AndroidDriver)driver).perform(Collections.singletonList(tap));
	                        System.out.println("Performed direct tap in center-bottom of screen");
	                    }
	                } catch (Exception e) {
	                    throw new NoSuchElementException("Button not found with any strategy: " + e.getMessage());
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Failed to click 'Submit Manual Timesheet' button: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } 
	    
	    
	    //Select the plan of care tasks section
	    
	    public void SelectTheoneplan() {
	   	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));   
	   	WebElement SelectTheOnePlan= wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath( "(//android.view.View[@content-desc=\"Personal Care\"])[1]/android.widget.CheckBox")));
	   	SelectTheOnePlan.click();
	    }
	    
	    //Select the time sheet reason
	    
	    public void SelectTheTimesheetReason() {
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));   
	            
	            System.out.println("Attempting to click on the reason dropdown...");
	            
	            // Define multiple locator strategies for the dropdown
	            List<By> dropdownLocators = Arrays.asList(
	                AppiumBy.xpath("//android.widget.ImageView[@content-desc='Select Reason']"),
	                AppiumBy.accessibilityId("Select Reason"),
	                AppiumBy.xpath("//*[contains(@content-desc, 'Select Reason')]"),
	                AppiumBy.xpath("//android.widget.ImageView[contains(@content-desc, 'Reason')]"),
	                AppiumBy.xpath("//*[contains(@content-desc, 'Reason') and contains(@class, 'android.widget.ImageView')]"),
	                AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Reason\")")
	            );
	            
	            // Try each dropdown locator strategy
	            boolean dropdownClicked = false;
	            for (By locator : dropdownLocators) {
	                try {
	                    WebElement selectReasonDropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
	                    selectReasonDropdown.click();
	                    dropdownClicked = true;
	                    System.out.println("Dropdown clicked successfully using: " + locator);
	                    break; // Exit the loop if successful
	                } catch (Exception e) {
	                    System.out.println("Failed to click dropdown using: " + locator);
	                }
	            }
	            
	            if (!dropdownClicked) {
	                System.out.println("Attempting fallback methods for dropdown...");
	                
	                // Try to find any element with 'reason' in its attributes
	                List<WebElement> reasonElements = driver.findElements(
	                    AppiumBy.xpath("//*[contains(@content-desc, 'reason') or contains(@text, 'reason') or contains(@resource-id, 'reason')]"));
	                
	                if (!reasonElements.isEmpty()) {
	                    System.out.println("Found " + reasonElements.size() + " potential reason elements, clicking first one");
	                    reasonElements.get(0).click();
	                    dropdownClicked = true;
	                } else {
	                    // Last resort: try to find by coordinate or other visible elements
	                    System.out.println("No reason-related elements found, attempting to locate by UI context");
	                    
	                    // Try to find elements that might be near the dropdown
	                    List<WebElement> potentialContextElements = driver.findElements(
	                        AppiumBy.xpath("//android.widget.ScrollView/android.view.View"));
	                    
	                    if (potentialContextElements.size() > 4) {
	                        // Assuming the dropdown might be the 5th element or similar position
	                        System.out.println("Attempting to click potential dropdown element by position");
	                        potentialContextElements.get(4).click();
	                        dropdownClicked = true;
	                    }
	                }
	            }
	            
	            if (!dropdownClicked) {
	                throw new NoSuchElementException("Could not find or click the reason dropdown with any strategy");
	            }
	            
	            // Add a small delay after clicking the dropdown
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            
	            // Try to find options using multiple strategies
	            System.out.println("Searching for dropdown options...");
	            
	            // Define locator strategies for dropdown options
	            List<By> optionLocators = Arrays.asList(
	                AppiumBy.xpath("//android.widget.ListView/android.view.View"),
	                AppiumBy.xpath("//android.widget.Button"),
	                AppiumBy.xpath("//*[@content-desc='Phone Not Working or Technical Issues' or contains(@text, 'Phone Not Working')]"),
	                AppiumBy.xpath("//android.widget.ListView/*"),
	                AppiumBy.xpath("//*[contains(@content-desc, 'Technical') or contains(@text, 'Technical')]"),
	                AppiumBy.xpath("//*[contains(@content-desc, 'Phone') or contains(@text, 'Phone')]")
	            );
	            
	            // Try each option locator strategy
	            boolean optionSelected = false;
	            for (By locator : optionLocators) {
	                try {
	                    List<WebElement> options = driver.findElements(locator);
	                    System.out.println("Found " + options.size() + " options with: " + locator);
	                    
	                    if (options.size() > 0) {
	                        options.get(0).click();
	                        optionSelected = true;
	                        System.out.println("Selected first option using: " + locator);
	                        break;
	                    }
	                } catch (Exception e) {
	                    System.out.println("Failed to find or select options using: " + locator);
	                }
	            }
	            
	            if (!optionSelected) {
	                System.out.println("Could not select any option with standard methods, trying fallback approaches");
	                
	                // Try to perform a tap at likely locations for dropdown options
	                try {
	                    // Get screen dimensions for tap positioning
	                    Dimension size = driver.manage().window().getSize();
	                    int screenWidth = size.getWidth();
	                    int screenHeight = size.getHeight();
	                    
	                    // Create W3C pointer action for tapping dropdown option
	                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	                    Sequence sequence = new Sequence(finger, 0);
	                    
	                    // Tap in the upper quarter of the screen where dropdown might appear
	                    sequence.addAction(finger.createPointerMove(Duration.ZERO, 
	                        PointerInput.Origin.viewport(), screenWidth / 2, screenHeight / 4));
	                    sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	                    sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
	                    
	                    // Perform the action
	                    ((AndroidDriver) driver).perform(Collections.singletonList(sequence));
	                    System.out.println("Performed tap in likely dropdown area");
	                    optionSelected = true;
	                } catch (Exception e) {
	                    System.out.println("Failed to perform fallback tap action: " + e.getMessage());
	                }
	            }
	            
	            if (!optionSelected) {
	                System.out.println("Warning: Could not select any dropdown option.");
	            }
	            
	        } catch (Exception e) {
	            System.out.println("Exception in SelectTheTimesheetReason: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    
//======================Draw Signature with XPath============================
	    
	    // Ultra-simple signature drawing using only basic W3C Actions
	    public void drawSimpleSignature() {
	        try {
	            System.out.println("Drawing patient signature using only W3C Actions API...");
	            
	            // Get screen dimensions
	            Dimension size = driver.manage().window().getSize();
	            int width = size.getWidth();
	            int height = size.getHeight();
	            
	            // Default signature position
	            int centerX = width / 2;
	            int centerY = height / 2 - 100; // Adjusted to be more centered
	            
	            // Try to locate signature area if possible
	            boolean signatureAreaFound = false;
	            try {
	                // Look for signature area elements with common attributes
	                List<WebElement> possibleSignatureAreas = driver.findElements(
	                    AppiumBy.xpath("//*[contains(@content-desc, 'signature') or contains(@text, 'signature') or contains(@resource-id, 'signature')]"));
	                
	                if (!possibleSignatureAreas.isEmpty()) {
	                    WebElement signatureArea = possibleSignatureAreas.get(0);
	                    Point location = signatureArea.getLocation();
	                    Dimension elementSize = signatureArea.getSize();
	                    
	                    // Update coordinates with found element's center point
	                    centerX = location.getX() + elementSize.getWidth() / 2;
	                    centerY = location.getY() + elementSize.getHeight() / 2;
	                    signatureAreaFound = true;
	                    System.out.println("Found signature area at: (" + centerX + ", " + centerY + ")");
	                }
	            } catch (Exception e) {
	                System.out.println("Could not locate signature area by elements: " + e.getMessage());
	            }
	            
	            // Scroll down first using only W3C Actions if we didn't find a signature area
	            if (!signatureAreaFound) {
	                scrollDownUsingW3C();
	                Thread.sleep(1000);
	                
	                System.out.println("Using default signature position at: (" + centerX + ", " + centerY + ")");
	                
	                // Draw simple signature with individual taps
	                drawPatternUsingTaps(centerX, centerY);
	            } else {
	                // If we found a signature area, draw on it
	                drawPatternUsingTaps(centerX, centerY);
	            }
	            
	            // Delay before button tap
	            Thread.sleep(1000);
	            
	            // Try to click Next button
	            tapBottomButton();
	            
	        } catch (Exception e) {
	            System.out.println("Exception in drawSimpleSignature: " + e.getMessage());
	            try {
	                // Emergency fallback - try to draw anywhere on screen lower half
	                Dimension size = driver.manage().window().getSize();
	                int centerX = size.getWidth() / 2;
	                int centerY = (int)(size.getHeight() * 0.6);
	                
	                System.out.println("Trying emergency signature at: (" + centerX + ", " + centerY + ")");
	                drawPatternUsingTaps(centerX, centerY);
	                
	                tapBottomButton();
	            } catch (Exception fallbackEx) {
	                System.out.println("Emergency fallback also failed: " + fallbackEx.getMessage());
	            }
	        }
	    }
	    
	    // Ultra-simple employee signature with only W3C Actions
	    public void drawEmployeeSignature() {
	        try {
	            System.out.println("Drawing employee signature using only W3C Actions API...");
	            
	            // Get screen dimensions
	            Dimension size = driver.manage().window().getSize();
	            int width = size.getWidth();
	            int height = size.getHeight();
	            
	            // Try to locate signature area if possible
	            boolean signatureAreaFound = false;
	            int centerX = width / 2;
	            int centerY = (int)(height * 0.7);
	            
	            try {
	                // Look for signature area elements that might be for employee
	                List<WebElement> possibleSignatureAreas = driver.findElements(
	                    AppiumBy.xpath("//*[contains(@content-desc, 'signature') or contains(@text, 'signature') or contains(@resource-id, 'signature')]"));
	                
	                // If more than one signature area is found, use the second one (likely employee)
	                if (possibleSignatureAreas.size() > 1) {
	                    WebElement signatureArea = possibleSignatureAreas.get(1);
	                    Point location = signatureArea.getLocation();
	                    Dimension elementSize = signatureArea.getSize();
	                    
	                    // Use found element's center point
	                    centerX = location.getX() + elementSize.getWidth() / 2;
	                    centerY = location.getY() + elementSize.getHeight() / 2;
	                    signatureAreaFound = true;
	                    System.out.println("Found employee signature area at: (" + centerX + ", " + centerY + ")");
	                }
	            } catch (Exception e) {
	                System.out.println("Could not locate employee signature area by elements: " + e.getMessage());
	            }
	            
	            // Scroll down twice to reach employee signature if we didn't find an area
	            if (!signatureAreaFound) {
	                // First scroll
	                scrollDownUsingW3C();
	                Thread.sleep(1000);
	                
	                // Second scroll
	                scrollDownUsingW3C();
	                Thread.sleep(1000);
	            }
	            
	            System.out.println("Drawing employee signature at coordinates: (" + centerX + ", " + centerY + ")");
	            
	            // Draw simple signature with individual taps
	            drawPatternUsingTaps(centerX, centerY);
	            
	            // Delay before button tap
	            Thread.sleep(1000);
	            
	            // Try to click Submit button
	            tapBottomButton();
	            
	        } catch (Exception e) {
	            System.out.println("Exception in drawEmployeeSignature: " + e.getMessage());
	            try {
	                // Emergency fallback - try to draw near bottom of screen
	                Dimension size = driver.manage().window().getSize();
	                int centerX = size.getWidth() / 2;
	                int centerY = (int)(size.getHeight() * 0.8);
	                
	                System.out.println("Trying emergency employee signature at: (" + centerX + ", " + centerY + ")");
	                drawPatternUsingTaps(centerX, centerY);
	                
	                tapBottomButton();
	            } catch (Exception fallbackEx) {
	                System.out.println("Emergency fallback also failed: " + fallbackEx.getMessage());
	            }
	        }
	    }
	    
	    // Reliable scroll using only W3C Actions
	    private void scrollDownUsingW3C() {
	        try {
	            System.out.println("Scrolling using W3C Actions only...");
	            
	            // Get screen dimensions
	            Dimension size = driver.manage().window().getSize();
	            int width = size.getWidth();
	            int height = size.getHeight();
	            
	            // Define scroll gesture with safe values (middle of screen)
	            int startX = width / 2;
	            int startY = (int)(height * 0.7);  // Start from lower middle
	            int endY = (int)(height * 0.3);    // End at upper middle
	            
	            // Create W3C pointer input
	            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	            
	            // Create W3C sequence
	            Sequence scroll = new Sequence(finger, 0);
	            scroll.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), startX, startY));
	            scroll.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
	            scroll.addAction(new Pause(finger, Duration.ofMillis(100)));
	            scroll.addAction(finger.createPointerMove(Duration.ofMillis(600), Origin.viewport(), startX, endY));
	            scroll.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
           
	            // Perform the gesture
	            ((AndroidDriver)driver).perform(Collections.singletonList(scroll));
           
	            System.out.println("Scrolled using W3C Actions");
           
	        } catch (Exception e) {
	            System.out.println("W3C scroll failed: " + e.getMessage());
	            
	            // Fallback method 1: Try alternative W3C actions with different parameters
	            try {
	                System.out.println("Trying alternative W3C scroll...");
	                
	                Dimension size = driver.manage().window().getSize();
	                int centerX = size.getWidth() / 2;
	                
	                // Use different start/end positions - scroll less distance
	                int start_y = (int) (size.getHeight() * 0.6);
	                int end_y = (int) (size.getHeight() * 0.4);
	                
	                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	                Sequence swipe = new Sequence(finger, 0);
	                swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), Origin.viewport(), centerX, start_y));
	                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	                swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), Origin.viewport(), centerX, end_y));
	                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
               
	                ((AndroidDriver)driver).perform(Arrays.asList(swipe));
	                System.out.println("Alternative W3C scroll successful");
	            } catch (Exception e2) {
	                System.out.println("Alternative W3C scroll failed: " + e2.getMessage());
               
	                // Fallback method 2: Try using Android-specific scrollGesture with CORRECT parameters
	                try {
	                    System.out.println("Trying Android scrollGesture...");
                   
	                    Dimension size = driver.manage().window().getSize();
	                    int centerX = size.getWidth() / 2;
	                    int startY = (int)(size.getHeight() * 0.7);
                   
	                    // Make sure to use POSITIVE height value
	                    Map<String, Object> scrollParams = new HashMap<>();
	                    scrollParams.put("left", centerX);
	                    scrollParams.put("top", startY);
	                    scrollParams.put("width", 10);
	                    scrollParams.put("height", 600);  // POSITIVE height
	                    scrollParams.put("direction", "down");
	                    scrollParams.put("percent", 0.7);
                   
	                    ((AndroidDriver)driver).executeScript("mobile: scrollGesture", scrollParams);
	                    System.out.println("Android scrollGesture successful");
	                } catch (Exception e3) {
	                    System.out.println("Android scrollGesture failed: " + e3.getMessage());
                   
	                    // Last fallback: Try simple swipe command
	                    try {
	                        System.out.println("Trying simple swipe command...");
	                        Map<String, Object> swipeParams = new HashMap<>();
	                        swipeParams.put("direction", "up");  // Scroll direction
	                        swipeParams.put("percent", 0.5);     // Scroll amount
	                        ((AndroidDriver)driver).executeScript("mobile: swipe", swipeParams);
	                        System.out.println("Simple swipe successful");
	                    } catch (Exception e4) {
	                        System.out.println("All scroll methods failed: " + e4.getMessage());
	                    }
	                }
	            }
	        }
	    }
	    
	    // Draw signature pattern using only individual taps (no continuous touch)
	    private void drawPatternUsingTaps(int centerX, int centerY) {
	        try {
	            System.out.println("Drawing signature using individual taps pattern...");
	            
	            // List of coordinates for individual taps to form signature pattern
	            int[][] tapPoints = {
	                {centerX - 100, centerY},        // Left
	                {centerX - 50, centerY - 20},    // Left up
	                {centerX, centerY},              // Center
	                {centerX + 50, centerY + 20},    // Right down
	                {centerX + 100, centerY}         // Right
	            };
	            
	            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
           
	            // Perform each tap individually
	            for (int[] point : tapPoints) {
	                // Create a separate sequence for each tap
	                Sequence tap = new Sequence(finger, 0);
	                tap.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), point[0], point[1]));
	                tap.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
	                tap.addAction(new Pause(finger, Duration.ofMillis(100)));
	                tap.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
               
	                // Perform the tap
	                ((AndroidDriver)driver).perform(Collections.singletonList(tap));
               
	                // Small pause between taps
	                Thread.sleep(200);
	            }
           
	            // Add one continuous signature motion as fallback if taps don't register well
	            try {
	                System.out.println("Adding continuous signature motion as backup...");
	                PointerInput continuousFinger = new PointerInput(PointerInput.Kind.TOUCH, "continuousFinger");
	                Sequence continuousSign = new Sequence(continuousFinger, 0);
               
	                // Start at leftmost point
	                continuousSign.addAction(continuousFinger.createPointerMove(Duration.ZERO, 
	                    Origin.viewport(), centerX - 100, centerY));
	                continuousSign.addAction(continuousFinger.createPointerDown(MouseButton.LEFT.asArg()));
               
	                // Add small pause
	                continuousSign.addAction(new Pause(continuousFinger, Duration.ofMillis(50)));
               
	                // Create smooth movements across signature points
	                for (int i = 1; i < tapPoints.length; i++) {
	                    continuousSign.addAction(continuousFinger.createPointerMove(
	                        Duration.ofMillis(150), Origin.viewport(), tapPoints[i][0], tapPoints[i][1]));
	                    continuousSign.addAction(new Pause(continuousFinger, Duration.ofMillis(50)));
	                }
               
	                // Release at the end
	                continuousSign.addAction(continuousFinger.createPointerUp(MouseButton.LEFT.asArg()));
               
	                // Perform the continuous signature
	                ((AndroidDriver)driver).perform(Collections.singletonList(continuousSign));
               
	                Thread.sleep(300);
	            } catch (Exception e) {
	                System.out.println("Continuous signature failed: " + e.getMessage());
	            }
           
	            System.out.println("Successfully drew signature using W3C Actions");
           
	        } catch (Exception e) {
	            System.out.println("Drawing signature pattern failed: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    // Simple method to tap on bottom of screen where submit buttons are typically located
	    private void tapBottomButton() {
	        try {
	            System.out.println("Attempting to find and tap bottom button...");
	            
	            // First approach: Try to find common bottom button elements
	            try {
	                List<WebElement> bottomButtons = driver.findElements(
	                    AppiumBy.xpath("//*[(contains(@content-desc, 'Next') or contains(@content-desc, 'Submit') or " +
	                                  "contains(@text, 'Next') or contains(@text, 'Submit') or " +
	                                  "contains(@content-desc, 'Continue') or contains(@text, 'Continue')) and " +
	                                  "(@clickable='true')]"));
	                
	                if (!bottomButtons.isEmpty()) {
	                    WebElement button = bottomButtons.get(0);
	                    System.out.println("Found button with text: " + 
	                        (button.getAttribute("text") != null ? button.getAttribute("text") : 
	                        (button.getAttribute("content-desc") != null ? button.getAttribute("content-desc") : "unknown")));
	                    button.click();
	                    System.out.println("Successfully clicked found button");
	                    return;
	                } else {
	                    System.out.println("No navigation buttons found, trying coordinates approach");
	                }
	            } catch (Exception e) {
	                System.out.println("Button element search failed: " + e.getMessage());
	            }
	            
	            // Second approach: Try coordinate-based tapping at common locations
	            // Get screen dimensions
	            Dimension size = driver.manage().window().getSize();
	            int width = size.getWidth();
	            int height = size.getHeight();
	            
	            // Define common button positions to try
	            int[][] buttonPositions = {
	                {width / 2, (int)(height * 0.9)},        // Center bottom
	                {width - 100, (int)(height * 0.9)},      // Right bottom (often Next/Submit)
	                {width - 150, (int)(height * 0.85)},     // Slightly higher right
	                {width / 2, (int)(height * 0.85)}        // Slightly higher center
	            };
	            
	            // Try each position
	            for (int[] position : buttonPositions) {
	                try {
	                    System.out.println("Attempting tap at: (" + position[0] + ", " + position[1] + ")");
	                    tapAtPoint(position[0], position[1]);
	                    Thread.sleep(500); // Wait to see if tap had effect
	                } catch (Exception e) {
	                    System.out.println("Tap failed at " + position[0] + "," + position[1] + ": " + e.getMessage());
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("All button tap attempts failed: " + e.getMessage());
	        }
	    }
	    
	    // Helper method to tap at specific coordinates
	    
	    private void tapAtPoint(int x, int y) {
	        try {
	            System.out.println("Tapping at coordinates: (" + x + ", " + y + ")");
	            
	            // First approach: Use W3C Actions
	            try {
	                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	                
	                Sequence tap = new Sequence(finger, 0);
	                tap.addAction(finger.createPointerMove(Duration.ZERO, Origin.viewport(), x, y));
	                tap.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
	                tap.addAction(new Pause(finger, Duration.ofMillis(50)));
	                tap.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
	                
	                ((AndroidDriver)driver).perform(Collections.singletonList(tap));
	                System.out.println("W3C tap successful");
	                return;
	            } catch (Exception e) {
	                System.out.println("W3C tap failed: " + e.getMessage() + ", trying alternative method");
	            }
	            
	            // Second approach: Try using JavaScript executor for tap
	            try {
	                // Use proper tap parameters with x and y as proper parameters
	                Map<String, Object> args = new HashMap<>();
	                args.put("x", x);
	                args.put("y", y);
	                // Do NOT use "element" parameter as we're tapping by coordinates
	                ((AndroidDriver) driver).executeScript("mobile: tap", args);
	                System.out.println("JavaScript tap successful");
	                return;
	            } catch (Exception e) {
	                System.out.println("JavaScript tap failed: " + e.getMessage() + ", trying final approach");
	            }
	            
	            // Third approach: Use Actions class
	            try {
	                new Actions(driver)
	                    .moveToLocation(x, y)
	                    .click()
	                    .perform();
	                System.out.println("Actions tap successful");
	                return;
	            } catch (Exception e) {
	                System.out.println("Actions tap failed: " + e.getMessage());
	                
	                // Fourth approach: Try with the basic JavaScript executor click at point
	                try {
	                    String jsScript = "document.elementFromPoint(" + x + "," + y + ").click();";
	                    ((JavascriptExecutor) driver).executeScript(jsScript);
	                    System.out.println("JavaScript elementFromPoint tap successful");
	                    return;
	                } catch (Exception e2) {
	                    System.out.println("JavaScript elementFromPoint tap failed: " + e2.getMessage());
	                    throw e; // Re-throw to be caught by caller
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("All tap methods failed at " + x + "," + y + ": " + e.getMessage());
	        }
	    }

	    
//=========================Approve the timeSheets===============================

		@FindBy(xpath = "//*[@class='nav nav-pills nav-sidebar nav-sidebar-custom flex-column']/li[5]")
       private WebElement VisitForsideMenu;

       public void ClickVisitsSIdeMenu () {
    	 VisitForsideMenu.click();	
}

//Verify opening the "VIsits" section in the side menu, then open the drop-down and select the "Visit review" option.

       @FindBy(xpath = "//*[@class='nav-item sidebar-nav-item menu-is-opening menu-open']/ul/li[1]/a")
    private WebElement VisitReview;

    public void ClickVisitReview() {
   	 VisitReview.click();		
    
    }
    
//==================== Missed card=============================
    
    @FindBy(xpath = "//*[@class='row sales-card']/div[3]/div")
    private WebElement MissedCardTimesheet;
    
    public void Click_The_MissCard () {
    	MissedCardTimesheet.click();
    }
    

    
    //select the today in calender dropdown 
   
       public void SelectTodayFromDropdown() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dropdownIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='col-sm-6 text-right header-btns']/div/div[2]")));

        dropdownIcon.click();

      // Wait for dropdown options to appear
		  WebElement todayOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='daterangepicker ltr show-ranges opensright']/div[1]/ul/li[1]")));
		  todayOption.click();
    
}

// -----------------------Select the caregiver name and sent notification--------------------
	
       public void clickTimesheetEditBtn(String caregiverName) {
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	    JavascriptExecutor js = (JavascriptExecutor) driver;

    	    try {
    	        // Wait for table and rows
    	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr")));
    	        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
    	        System.out.println("Found " + rows.size() + " rows.");

    	        for (WebElement row : rows) {
    	            String rowText = row.getText().trim();
    	            System.out.println("Row text: " + rowText);

    	            if (rowText.toLowerCase().contains(caregiverName.toLowerCase())) {
    	                js.executeScript("arguments[0].scrollIntoView({block:'center'});", row);
    	                wait.until(ExpectedConditions.visibilityOf(row));

    	                WebElement button = null;
    	                try {
    	                    button = row.findElement(By.xpath(".//button[contains(@title,'Edit')]"));
    	                } catch (Exception e1) {
    	                    try {
    	                        button = row.findElement(By.xpath(".//*[contains(@class,'fa-edit')]"));
    	                    } catch (Exception e2) {
    	                        System.out.println("Edit button not found in this row.");
    	                        continue;
    	                    }
    	                }

    	                wait.until(ExpectedConditions.elementToBeClickable(button));
    	                js.executeScript("arguments[0].click();", button);
    	                System.out.println("✅ Clicked Edit for: " + caregiverName);
    	                return;
    	            }
    	        }

    	        System.out.println("❌ Caregiver not found: " + caregiverName);

    	    } catch (Exception e) {
    	        System.out.println("❌ Error: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	}


    
//=================Visit Information in select the reason code==========================

       public void SelectReasonCode() {
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	    JavascriptExecutor js = (JavascriptExecutor) driver;
    	    
    	    try {
    	        // Click Select2 container
    	        WebElement select2Container = wait.until(ExpectedConditions.elementToBeClickable(
    	            By.id("select2-mdl_mis_vis_reason_type-container")));
    	        js.executeScript("arguments[0].click();", select2Container);
    	        Thread.sleep(700);
    	        
    	        // Select option
    	        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
    	            By.xpath("//ul[@id='select2-mdl_mis_vis_reason_type-results']/li[2]")));
    	        option.click();
    	        
    	        System.out.println("✅ Selected Reason Code using Select2");
    	    } catch (Exception e) {
    	        System.out.println("❌ Error: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	}
	    
	   //Visit Information in select the Action taker
	    
       public void SelecttheActionApprove() {
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	    JavascriptExecutor js = (JavascriptExecutor) driver;
    	    
    	    try {
    	        // Scroll dropdown into view
    	        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
    	            By.xpath("//*[@class='document_list']/tr/td[2]/select")));
    	        
    	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
    	        Thread.sleep(500);
    	        
    	        // Use JavaScript to select (more reliable)
    	        js.executeScript("arguments[0].selectedIndex = 0; arguments[0].dispatchEvent(new Event('change'));", dropdown);
    	        
    	        System.out.println("✅ Selected Action Approve");
    	    } catch (Exception e) {
    	        System.out.println("❌ Error: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	}
	   
	   //save button
	   
	 	 @FindBy(xpath= "(//*[@class='text-center'])[2]/button[1]")
 	 private WebElement SaveBtn;
 	 
 	  public void ClickSaveBtn() { 
 		 SaveBtn.click();
	   
 	  }
 	  
 	//Ok Button
 	  
 	 @FindBy(xpath= "//*[@class='swal2-actions']/button[1]")
 	 private WebElement OkBtn;
 	 
 	  public void ClickOkBtn() { 
 		 OkBtn.click();
 		 
 	  }
 	  
  //Complter card
 		 
 		 @FindBy(xpath = "//h3[@class='completeCount']")
         private WebElement CompltedCardTimesheet;
         
         public void ClickTheCompleted () {
        	 CompltedCardTimesheet.click();
         } 
	   
 	  }
 	  
 	  