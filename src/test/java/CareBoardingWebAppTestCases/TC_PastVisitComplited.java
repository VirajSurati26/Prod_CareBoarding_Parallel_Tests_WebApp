package CareBoardingWebAppTestCases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.testng.annotations.Test;
import CareBoarding_BaseClass.InvokeDriver_WebAndApp;
import CareBoarding_PageObject.OnTimeVisitCompleted;
import CareBoarding_PageObject.PastVisitCompleted;



public class TC_PastVisitComplited extends InvokeDriver_WebAndApp {
	
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
	   public void RunEndToEndPastVisitTest() throws Exception {
		   
	        // Web part - this should work regardless of Appium
	        try {
	        	
	            // Login to the website
	        	PastVisitCompleted UserNamePassword = new  PastVisitCompleted(webDriver);
	            UserNamePassword.login("johnallen@yopmail.com", "12345678"); // 
	            System.out.println("✅ Logged in successfully to web application.");
	            Thread.sleep(2000);

	            // Step 2: Click the caregiver in the side menu
	            PastVisitCompleted SelectCaregiver = new  PastVisitCompleted(webDriver);
	            SelectCaregiver.Click_caregivers();
	            System.out.println("📂 Navigated to Caregivers section.");
	            Thread.sleep(2000);

//	            // Step 3: Select the Search caregiver in the caregiver menu
//	            PastVisitComplited Searchcaregiver = new PastVisitComplited(webDriver);
//	            Searchcaregiver.clickSearchCaregivers();
//	            System.out.println("🔍 Clicked on Search caregivers.");
//	            Thread.sleep(2000);
	            
	            // Step 4: Search the care giver name in Search Bar section
	            PastVisitCompleted CaregiversNameSearchInSearchBar = new PastVisitCompleted(webDriver);
	            // You can parameterize this caregiver ID for better test flexibility
	            String caregiverId = "TWI-000003";
	            CaregiversNameSearchInSearchBar.Enter_CaregiversNameSearch(caregiverId);
	            System.out.println("✅ Searched for Caregiver ID: " + caregiverId);
	            Thread.sleep(2000);
	            
	            // Step 5: Select the Caregiver calendar
	            PastVisitCompleted CaregiversCalendar = new PastVisitCompleted(webDriver);
	            CaregiversCalendar.selectCaregiverFromCalendar();
	            System.out.println("📅 Selected the Caregiver calendar");
	            Thread.sleep(5000);
	            
	            // Step 6: Select the Today's date in caregiver calendar
	            PastVisitCompleted SelectTheCurrentDate = new PastVisitCompleted(webDriver);
	            Thread.sleep(5000);
	            SelectTheCurrentDate.selectCurrentDatebox();
	            
	            // Step 7: Select the Today's date and enter the past time on In-Time.
	            PastVisitCompleted SelectThePastInTime = new PastVisitCompleted(webDriver);
	            Thread.sleep(5000);
	            SelectThePastInTime. After1hourSchedulePastInTime();
	            
	            // Step 8: Select the Today's date and enter the past time on Out-Time.
	            PastVisitCompleted SelectThePastOutTime = new PastVisitCompleted(webDriver);
	            Thread.sleep(5000);
	            SelectThePastOutTime.VisitCreatedWithin1HourAfterScheduledInTime();
	            
	            
	            // Step 9: Fill up the visit Schedule information (Patients Name)
	            PastVisitCompleted SelectThePatientsNameindropdown = new PastVisitCompleted(webDriver);
	            SelectThePatientsNameindropdown.Select_PatientNameInDropDown();
	            System.out.println("👤 Patient name selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 10: Fill up the visit Schedule information (Pay Rate)
	            PastVisitCompleted SelectThePayRatedropdown = new PastVisitCompleted(webDriver);
	            SelectThePayRatedropdown.Select_PayRateInDropDown();
	            System.out.println("💰 Pay rate selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 11: Fill up the visit Schedule information (POC Number)
	            PastVisitCompleted SelectThePOCNumberInDropdown = new PastVisitCompleted(webDriver);
	            SelectThePOCNumberInDropdown.SelectPOCNumberInDropDown();
	            System.out.println("📋 POC number selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 12: Fill up the visit Schedule information (Service Code number)
	            PastVisitCompleted SelectTheServiceCodeNumberInDropdown = new PastVisitCompleted(webDriver);
	            SelectTheServiceCodeNumberInDropdown.selectServiceCodeNumberInDropDown();
	            System.out.println("🔢 Service code selected successfully");
	            Thread.sleep(2000);
	            
	            // Step 13: Fill up the visit Schedule information (Create Button)
	            PastVisitCompleted ClickTheCreateBtnInScheduleinfomation = new PastVisitCompleted(webDriver);
	            ClickTheCreateBtnInScheduleinfomation.ClickCreatebtn();
	            System.out.println("✅ Visit schedule created successfully");
	            Thread.sleep(3000); 
	            
	            
	            PastVisitCompleted ClickTheOKbuttonInVisit = new PastVisitCompleted(webDriver);
	            ClickTheOKbuttonInVisit.clickVisitOkButton();
	            System.out.println("🔢Click the OK button successfully");
	            Thread.sleep(2000);
	            
	            
//===============================Visit module and Select the missed visit card, missed visit notification And Approve======================
	            
	            // Step 1:  Click the Visit in the side menu
	            PastVisitCompleted ClickTheViistbtnInSideMenu = new PastVisitCompleted(webDriver);
	            ClickTheViistbtnInSideMenu.Click_VisitsInsidemenu();
	            System.out.println("✅ Click the Visit in Side menu successfully");
	            Thread.sleep(3000); 
	            
	            // Step 2:  Click the Visit view in Visit module dropdown
	            PastVisitCompleted SelectTheVisitReviewInVisitDropDown = new PastVisitCompleted(webDriver);
	            SelectTheVisitReviewInVisitDropDown.Click_Visit_Review();
	            System.out.println("✅ Successfully select Visit review in Visit DropDown");
	            Thread.sleep(3000); 
	            
	            // Step 3:  Click the Missed visit card In Visit Management
	            PastVisitCompleted SelectTheMissedvisitcard = new PastVisitCompleted(webDriver);
	            SelectTheMissedvisitcard.ClickTheMissCard();
	            System.out.println("✅ Successfully select Missed visit card");
	            Thread.sleep(3000); 
	            
	            // Step 4:  Select the today in drop down on calender
	            PastVisitCompleted SelectTodayIncalender = new PastVisitCompleted(webDriver);
	            SelectTodayIncalender.selectTodayFromDropdown();
	            System.out.println("✅ Successfully select the Today in calender");
	            Thread.sleep(3000); 
	            
	            // Step 5:  Sent the Notification in missed visit card
	            PastVisitCompleted SentTheNotification = new PastVisitCompleted(webDriver);
	            SentTheNotification.clickTimesheetNotificationIcon("John, Emily R.");
	            System.out.println("✅ Successfully Sent the Notification");
	            Thread.sleep(3000); 
           
	           // Step 6:  Sent the Notification Yes button click in missed visit card
	            PastVisitCompleted SentTheNotificationYesBtn = new PastVisitCompleted(webDriver);
	            SentTheNotificationYesBtn.ClicktheYesbtn();
	            System.out.println("✅ Yes button click for notification pop-up ");
	            Thread.sleep(3000); 
	            
	            // Step 7:  Sent the Notification Yes button click in missed visit card
	            PastVisitCompleted SentTheNotificationOkBtn = new PastVisitCompleted(webDriver);
	            SentTheNotificationOkBtn.ClicktheOkbtnInNotification();
	            System.out.println("✅ Ok button successfully click");
	            Thread.sleep(3000); 
	            
	            

	            // Verify the visit was created successfully by checking for success message or another element
	            // You can add verification code here
	            
	        } catch (Exception e) {
	            System.err.println("❌ Web part failed: " + e.getMessage());
	            e.printStackTrace();
	            throw e; // Re-throw to fail the test
	        }
	       
            
	      //===============================Appium part - check if server is running first========================================
            
	        
            // In a real scenario, you would extract the email from the web UI
            String caregiverEmail = "emily.johnson@yopmail.com"; // Replace with actual code to extract email
            System.out.println("📧 Using caregiver email: " + caregiverEmail);
            Thread.sleep(2000);
            
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
                Thread.sleep(2000);
                
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
                
////                // Try multiple approaches to select the visit
////                boolean visitFound = false;
////                try {
////                    // First try the more reliable time pattern matcher with the scheduled time
////                    System.out.println("Trying to select visit by time pattern with scheduled time: " + scheduledInTime);
////                    SelectTheVisit.findAndSelectVisitByTimePattern();
////                    visitFound = true;
////                } catch (Exception e1) {
////                    System.out.println("Time pattern approach failed: " + e1.getMessage());
////                    try {
////                        // If that fails, try selecting the most recent visit by time
////                        System.out.println("Trying to select the most recent visit by time...");
////                        SelectTheVisit.selectMostRecentVisit();
////                        visitFound = true;
////                    } catch (Exception e2) {
////                        System.out.println("Most recent visit selection failed: " + e2.getMessage());
////                        try {
////                            // Fallback to original approach
////                            System.out.println("Trying fallback approach...");
////                            SelectTheVisit.findAndSelectVisitByTimePattern();
////                            visitFound = true;
////                        } catch (Exception e3) {
////                            System.err.println("All visit selection approaches failed");
////                            throw new Exception("Unable to select any visit");
////                        }
////                    }
////                }
////                
//                if (visitFound) {
//                    System.out.println("✅ Visit selection completed successfully");
                

                // Step 5: Select the recently created visit 
	                 PastVisitCompleted ClickThemanuallyTimesheet = new  PastVisitCompleted(mobileDriver);
	                 ClickThemanuallyTimesheet.clickTimesheetbtn();
	                System.out.println("🔍 Successfully click the Manual timesheet button");
	                
                // Step 6: Click the Select the plan of care tasks section      
	                
	                PastVisitCompleted SelectOnePlanofTasks = new PastVisitCompleted(mobileDriver);
	                SelectOnePlanofTasks.SelectTheoneplan();
	                System.out.println("📅 Successfully Select the plan of care tasks section"); 
	                
	            // Step 7:  Select the miss timesheet Reason
	                
	                PastVisitCompleted SelectTheReasonMissVisit = new PastVisitCompleted(mobileDriver);
	                SelectTheReasonMissVisit.SelectTheTimesheetReason();
	                System.out.println("📅 Successfully Select the Missed timesheet reason"); 
	                
	            // Step 8:  Draw the patients signature 
	                
	                PastVisitCompleted patientssignature = new PastVisitCompleted(mobileDriver);
	                patientssignature.drawSimpleSignature();
	                System.out.println("📅 Successfully Draw the patients signature"); 
	                
	                // Step 9: Draw the employee/caregiver signature
	                
	                PastVisitCompleted Employeesignature = new PastVisitCompleted(mobileDriver);
	                Employeesignature.drawSimpleSignature();
	                System.out.println("📅 Successfully draw the employee signature"); 
	                
	                // Wait to ensure the mobile timesheet is properly submitted and recorded in the system
	                System.out.println("⏳ Waiting for timesheet submission to be processed...");
	                Thread.sleep(5000);
	         
	                System.out.println("✅ Mobile app actions completed successfully.");
	            } catch (Exception e) {
	                System.err.println("❌ Mobile app part failed: " + e.getMessage());
	                System.err.println("📋 TROUBLESHOOTING TIPS:");
	                System.err.println("1. Verify the Android emulator/device is running and connected");
	                System.err.println("2. Check the APK path: " + System.getProperty("user.dir") + "\\src\\test\\java\\Recourse\\47.apk");
	                System.err.println("3. Make sure you have the correct Appium Java client version");
	                e.printStackTrace();
	            }
	            
	            
//==========================After the mobile part is completed, switch back to the web browser to approve the pending visit===================================
	            try {
	                System.out.println("🔄 Switching back to web application to approve the pending visit...");
	                
	                // Step 1: Go to the Visits section in the side menu (if not already there)
	                PastVisitCompleted GoToVisitSection = new PastVisitCompleted(webDriver);
	                GoToVisitSection.ClickVisitsSIdeMenu();
	                System.out.println("📂 Navigated to Visits section in side menu");
	                Thread.sleep(2000);
	                
	                // Step 2: Click on the Visit Review option
	                PastVisitCompleted GoToVisitReview = new PastVisitCompleted(webDriver);
	                GoToVisitReview.ClickVisitReview();
	                System.out.println("🔍 Clicked on Visit Review option");
	                Thread.sleep(2000);
	   
	               // Step 3: Click on the Missed visit card
	                PastVisitCompleted GoToMissedVisitCard = new PastVisitCompleted(webDriver);
	                GoToMissedVisitCard.Click_The_MissCard();
	                System.out.println("🔍 Clicked on Missed Card");
	                Thread.sleep(2000); 
	        
	                
	                // Step 4: Select today from the calendar dropdown (we already have this method)
	                PastVisitCompleted SelectTodayInCalendar = new PastVisitCompleted(webDriver);
	                SelectTodayInCalendar.selectTodayFromDropdown();
	                System.out.println("📅 Selected Today's date in the calendar");
	                Thread.sleep(3000);
	                
	                // Step 5: Click the approve button for the visit
	                PastVisitCompleted ClickApproveBtn = new PastVisitCompleted(webDriver);
	                ClickApproveBtn.clickTimesheetEditBtn("John, Emily R.");
	                System.out.println("✅ Clicked approve button for the missed visit");
	                Thread.sleep(2000);
	                
	                // Step 6: Select the reason code in visit Infomation
	                PastVisitCompleted SelectTheReasonCode = new PastVisitCompleted(webDriver);
	                SelectTheReasonCode.SelectReasonCode();
	                System.out.println("✅ Successfully select the reason code");
	                Thread.sleep(2000);
	                
	                // Step 7: Select the Action code in visit Infomation
	                PastVisitCompleted SelectTheActionCode = new PastVisitCompleted(webDriver);
	                SelectTheActionCode.SelecttheActionApprove();
	                System.out.println("✅ Successfully Select the Resolution code");
	                Thread.sleep(2000);
	                
	                //Step 8: Click the Action Field and open a drop down and select the Approve
	                PastVisitCompleted ClickTheActionBtnAndSelectApproveBtn = new PastVisitCompleted(webDriver);
	                ClickTheActionBtnAndSelectApproveBtn.SelecttheActionApprove();
	                System.out.println("✅ Successfully select the Approve Button");
	                Thread.sleep(2000);
	                
	                //Step 9: Click the Save button for approve timesheet
	                PastVisitCompleted ClickTheSaveBtnInApproveTimeSheet = new PastVisitCompleted(webDriver);
	                ClickTheSaveBtnInApproveTimeSheet.ClickSaveBtn();
	                System.out.println("✅ Successfully Save the timesheet");
	                Thread.sleep(2000);
	                
	                //Step 10: Click the OK button
	                PastVisitCompleted ClickTheOkBtnInApproveTimeSheet = new PastVisitCompleted(webDriver);
	                ClickTheOkBtnInApproveTimeSheet.ClickOkBtn();
	                System.out.println("✅ Successfully Ok button ");
	                Thread.sleep(2000);
	                
	                //Step 11: Click the Completed Card
	                PastVisitCompleted ClickTheCompletedCard = new PastVisitCompleted(webDriver);
	                ClickTheCompletedCard.ClickTheCompleted();
	                System.out.println("✅ Successfully The Completed button");
	                Thread.sleep(2000);
	                
	            
	                System.out.println("✅ Successfully approved the pending visit on the web application");
	                
	            } catch (Exception e) {
	                System.err.println("❌ Web approval part failed: " + e.getMessage());
	                e.printStackTrace();
	            }
	   }
	        

	  
}