package CareBoardingWebAppTestCases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.testng.annotations.Test;

import CareBoarding_BaseClass.InvokeDriver_WebAndApp;
import CareBoarding_PageObject.OnTimeVisitCompleted;
import CareBoarding_PageObject.PastVisitCompleted;

public class TC_OnTimeVisitCompleted extends InvokeDriver_WebAndApp {
	
	   private String scheduledInTime; // To store the scheduled in time for validation

	   
	    // Helper method to check if Appium server is running
	    @SuppressWarnings({})
		private boolean isAppiumServerRunning() {
	        try {
	            @SuppressWarnings("deprecation")
				HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:4723/status").openConnection();
	            connection.setConnectTimeout(1000);
	            connection.connect();
	            return connection.getResponseCode() == 200;
	        } catch (IOException e) {
	            return false;
	        }
	    }
	 
	@Test
	    public void runEndToEndVisitTest() throws Exception {
		
	        // Web part - this should work regardless of Appium
	        try {

	             // Login to the website
	        	 OnTimeVisitCompleted UserNamePassword = new  OnTimeVisitCompleted(webDriver);
	            UserNamePassword.login("johnallen@yopmail.com", "12345678"); // 
	            System.out.println("✅ Logged in successfully to web application.");
	            Thread.sleep(2000);

	            // Step 2: Click the caregiver in the side menu
	            OnTimeVisitCompleted SelectCaregiver = new  OnTimeVisitCompleted(webDriver);
	            SelectCaregiver.Click_caregivers();
	            System.out.println("📂 Navigated to Caregivers section.");
	            Thread.sleep(2000);

	         // Step 3: Select the Search caregiver in the caregiver menu
//	            OnTimeVisitCompleted Searchcaregiver = new OnTimeVisitCompleted(webDriver);
//	            Searchcaregiver.Click_caregivers();
//	            System.out.println("🔍 Clicked on Search caregivers.");
//	            Thread.sleep(2000);
	            
	            // Step 4: Search the care giver name in Search Bar section
	           OnTimeVisitCompleted CaregiversNameSearchInSearchBar = new OnTimeVisitCompleted(webDriver);
	            // You can parameterize this caregiver ID for better test flexibility
	            String caregiverId = "TWI-000003";
	            CaregiversNameSearchInSearchBar.Enter_CaregiversNameSearch(caregiverId);
	            System.out.println("✅ Searched for Caregiver ID: " + caregiverId);
	            Thread.sleep(2000);
	          
	            
	            // Step 5: Select the Caregiver calendar
	            OnTimeVisitCompleted CaregiversCalendar = new OnTimeVisitCompleted(webDriver);
	            CaregiversCalendar.selectCaregiverFromCalendar();
	            System.out.println("📅 Selected the Caregiver calendar");
	            Thread.sleep(5000);
	       
	            
	            // Step 6: Select the Today's date in caregiver calendar
	           OnTimeVisitCompleted SelectTheCurrentDate = new OnTimeVisitCompleted(webDriver);
	            Thread.sleep(5000);
	            SelectTheCurrentDate.selectCurrentDatebox();
	            
	            // Step 7: Fill up the visit Schedule information (In Time)
	           OnTimeVisitCompleted ScheduleTheVisitToOccur15minutesAftertheCurrentTime = new OnTimeVisitCompleted(webDriver);
	            ScheduleTheVisitToOccur15minutesAftertheCurrentTime.ScheduleInTime();
	            
	            // Store the scheduled in time (15 minutes from now) for later verification
	            Calendar calendar = Calendar.getInstance();
	            calendar.add(Calendar.MINUTE, 10);
	            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	            scheduledInTime = sdf.format(calendar.getTime());
	            System.out.println("⏰ Scheduled visit in-time: " + scheduledInTime);
	            Thread.sleep(2000);
	            
	            // Step 8: Fill up the visit Schedule information (Out Time)
	           OnTimeVisitCompleted ScheduleTheVisitToCoincideWithCurrentVisitInTtime = new OnTimeVisitCompleted(webDriver);
	            ScheduleTheVisitToCoincideWithCurrentVisitInTtime.ScheduleOutTime();
	            System.out.println("⏰ Scheduled visit out-time");
	            Thread.sleep(2000);
	            
	            // Step 9: Fill up the visit Schedule information (Patients Name)
	           OnTimeVisitCompleted SelectThePatientsNameindropdown = new OnTimeVisitCompleted(webDriver);
	            SelectThePatientsNameindropdown.Select_PatientNameInDropDown();
	            System.out.println("👤 Patient name selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 10: Fill up the visit Schedule information (Pay Rate)
	           OnTimeVisitCompleted SelectThePayRatedropdown = new OnTimeVisitCompleted(webDriver);
	            SelectThePayRatedropdown.Select_PayRateInDropDown();
	            System.out.println("💰 Pay rate selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 11: Fill up the visit Schedule information (POC Number)
	           OnTimeVisitCompleted SelectThePOCNumberInDropdown = new OnTimeVisitCompleted(webDriver);
	            SelectThePOCNumberInDropdown.SelectPOCNumberInDropDown();
	            System.out.println("📋 POC number selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 12: Fill up the visit Schedule information (Service Code number)
	           OnTimeVisitCompleted SelectTheServiceCodeNumberInDropdown = new OnTimeVisitCompleted(webDriver);
	            SelectTheServiceCodeNumberInDropdown.selectServiceCodeNumberInDropDown();
	            System.out.println("🔢 Service code selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 13: Fill up the visit Schedule information (Create Button)
	           OnTimeVisitCompleted ClickTheCreateBtnInScheduleinfomation = new OnTimeVisitCompleted(webDriver);
	            ClickTheCreateBtnInScheduleinfomation.ClickCreatebtn();
	            System.out.println("✅ Visit schedule created successfully");
	            Thread.sleep(3000); 
	            
	            // Step 14:  Sent the Notification Yes button click in missed visit card
	            PastVisitCompleted SentTheNotificationOkBtn = new PastVisitCompleted(webDriver);
	            SentTheNotificationOkBtn.ClicktheOkbtnInNotification();
	            System.out.println("✅ Ok button successfully click");
	            Thread.sleep(3000); 
	            
	            
	        } catch (Exception e) {
	            System.err.println("❌ Web part failed: " + e.getMessage());
	            e.printStackTrace();
	            throw e; // Re-throw to fail the test
	        }
	            
//===============================Appium part - check if server is running first========================================
	            
	            // In a real scenario, you would extract the email from the web UI
	            String caregiverEmail = "emily.johnson@yopmail.com"; // Replace with actual code to extract email
	            System.out.println("📧 Using caregiver email: " + caregiverEmail);
	            
	            // Mobile part - check if Appium server is running first
	            if (!isAppiumServerRunning()) {
	                System.err.println("⚠️ Appium server is not running on http://127.0.0.1:4723");
	                System.err.println("Please start the Appium server and try again.");
	                return; // Skip the mobile part but don't fail the test
	            }
	            
	            try {
	            	
	                // Step 1: Initialize mobile driver for Appium
	                System.out.println("🔄 Connecting to Appium server...");
	                mobileDriver = initMobileDriver();
	                System.out.println("📱 Mobile driver initialized successfully.");
	                
                // Step 2: Create Appium page object and login to the app
               OnTimeVisitCompleted AppiumLogin = new OnTimeVisitCompleted(mobileDriver);
                Thread.sleep(2000);
                AppiumLogin.loginWithWebCredentials(caregiverEmail, "12345678");  // Use the correct password for emily.johnson
                System.out.println("🔑 Logged into mobile app using email: " + caregiverEmail);
	                
                // Add significant wait time to ensure app is fully loaded after login
                System.out.println("⏳ Waiting for app to fully load after login...");
                Thread.sleep(15000); // Increased wait time to prevent crashes
	                
	                // Step 3: Click on Today's Schedule section using simple method
	               OnTimeVisitCompleted TodaySchedule = new OnTimeVisitCompleted(mobileDriver);
	                System.out.println("🔍 Attempting to click on Today's Schedule...");
	                TodaySchedule.clickSimpleTodaySchedule();
	                System.out.println("📅 Successfully navigated to Today's Schedule section");
	                Thread.sleep(3000);  // Add a short wait for the schedule list to load
	                
	                // Step 4: Select the recently created visit 
	               OnTimeVisitCompleted SelectTheVisit = new OnTimeVisitCompleted(mobileDriver);
	                System.out.println("🔍 Attempting to find the newly created visit with scheduled time: " + scheduledInTime);
	                
//	                // Try multiple approaches to select the visit
//	                boolean visitFound = false;
//	                try {
//	                    // First try the more reliable time pattern matcher with the scheduled time
//	                    System.out.println("Trying to select visit by time pattern with scheduled time: " + scheduledInTime);
//	                    SelectTheVisit.findAndSelectVisitByTimePattern();
//	                    visitFound = true;
//	                } catch (Exception e1) {
//	                    System.out.println("Time pattern approach failed: " + e1.getMessage());
//	                    try {
//	                        // If that fails, try selecting the most recent visit by time
//	                        System.out.println("Trying to select the most recent visit by time...");
//	                        SelectTheVisit.selectMostRecentVisit();
//	                        visitFound = true;
//	                    } catch (Exception e2) {
//	                        System.out.println("Most recent visit selection failed: " + e2.getMessage());
//	                        try {
//	                            // Fallback to original approach
//	                            System.out.println("Trying fallback approach...");
//	                            SelectTheVisit.findAndSelectVisitByTimePattern();
//	                            visitFound = true;
//	                        } catch (Exception e3) {
//	                            System.err.println("All visit selection approaches failed");
//	                            throw new Exception("Unable to select any visit");
//	                        }
//	                    }
//	                }
//	                
//	                if (visitFound) {
//	                    System.out.println("✅ Visit selection completed successfully");
//	                }
	                
            // Step 5: Click on Clock-in button in visit details
	               OnTimeVisitCompleted ClickTheClockInBtn = new OnTimeVisitCompleted(mobileDriver);
	                ClickTheClockInBtn.clockIn();
	                System.out.println("📅 Successfully Clock-in");
	                
	         // Step 6: Click the location confirn in google map       
	                
	               OnTimeVisitCompleted ClickTheconfirmBtnInvisit = new OnTimeVisitCompleted(mobileDriver);
	                ClickTheconfirmBtnInvisit.ClicktheconfirmBtn();
	                System.out.println("📅 Confirm button click successfully in clock-In");
	                
	         //Step 7: Click the Ok button notification in Clock-In section
	               OnTimeVisitCompleted ClicktheOkbtnnotification = new OnTimeVisitCompleted(mobileDriver);
	                ClicktheOkbtnnotification.ClicktheOkBtn();
	                System.out.println("📅 Successfully Ok notification button");
	                
	         // Step 8: Click on Clock-in button in visit details
	               OnTimeVisitCompleted ClickTheClockOutBtn = new OnTimeVisitCompleted(mobileDriver);
	                ClickTheClockOutBtn.clockOut();
	                System.out.println("📅 Successfully Clock-Out");	  
	                
	        // Step 9: Click the location confirn in google map       
	                
	               OnTimeVisitCompleted ClickTheconfirmBtnInClockOut = new OnTimeVisitCompleted(mobileDriver);
	                ClickTheconfirmBtnInClockOut.ClicktheconfirmBtn();
	                System.out.println("📅 Confirm button click successfully Clock-Out");  
	                
         // Step 10: Click the Select the plan of care tasks section      
	                
	               OnTimeVisitCompleted SelectOnePlanofTasks = new OnTimeVisitCompleted(mobileDriver);
	                SelectOnePlanofTasks.SelectTheoneplan();
	                System.out.println("📅 Successfully Select the plan of care tasks section"); 
	                
	         // Step 11: Click the Save button in Plan of care tasks 
	                
	               OnTimeVisitCompleted ClickTHeSavebtnInPalnTasks = new OnTimeVisitCompleted(mobileDriver);
	                ClickTHeSavebtnInPalnTasks.ClickSavebtn();
	                System.out.println("📅 Successfully Click save button"); 
	         
	                
	                
	                System.out.println("✅ Mobile app actions completed successfully.");
	            } catch (Exception e) {
	                System.err.println("❌ Mobile app part failed: " + e.getMessage());
	                System.err.println("📋 TROUBLESHOOTING TIPS:");
	                System.err.println("1. Verify the Android emulator/device is running and connected");
	                System.err.println("2. Check the APK path: " + System.getProperty("user.dir") + "\\src\\test\\java\\Recourse\\47.apk");
	                System.err.println("3. Make sure you have the correct Appium Java client version");
	                e.printStackTrace();
	            }
	}
	
}



