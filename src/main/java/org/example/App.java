package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://traceability.apeda.gov.in/Organic/TraceNet/ErrorPage/authorizationfailed.html");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement text = driver.findElement(By.id("myform"));
        //driver.quit();
        //System.out.println("------------------------" + text.findElement(By.linkText("TraceNet Login")));
        //System.out.println("------------------------" + text.findElement(By.partialLinkText("TraceNet Login")));
        List<WebElement> webButtonList = text.findElements(By.tagName("a"));
        webButtonList.stream().forEach(webElement -> {
            if ("TraceNet Login".equals(webElement.getText())) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].click();", webElement);
                // Actions actions = new Actions(driver); actions.moveToElement(webElement).moveToElement(webElement).click().build().perform();
                //System.out.println("------------------" + webElement.getText());
            }
        });
        // driver.quit();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        //System.out.println(driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtUserID")).getAttribute("maxlength"));
        //System.out.println(driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword")).getAttribute("maxlength"));
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtUserID")).sendKeys("XNJAMH");
        driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtPassword")).sendKeys("Grv#753482");
        Thread.sleep(15000);

        driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit")).click();

        Thread.sleep(5000);
        List<WebElement> webUlList = driver.findElements(By.tagName("ul"));

        // Locating the Main Menu (Parent element)
        WebElement mainMenu = driver.findElement(By.xpath("//*[@id='ctl00_HorizontalMenu1_tdmenu']/div/ul/li[3]/a"));

//Instantiating Actions class
        Actions actions = new Actions(driver);

//Hovering on main menu
        actions.moveToElement(mainMenu);

// Locating the element from Sub Menu
        WebElement subMenu = driver.findElement(By.xpath("//*[@id='ctl00_HorizontalMenu1_tdmenu']/div/ul/li[3]/ul/li[1]/a"));

//To mouseover on sub menu
        actions.moveToElement(subMenu);

//build()- used to compile all the actions into a single step
        actions.click().build().perform();

        Thread.sleep(4000);

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = new FileInputStream("D:\\spring\\spring authorization server\\spring-boot-angular-oauth2-social-login-demo\\untitled second\\src\\main\\java\\org\\example\\farmerinspectionentry.json");
        TypeReference<List<FarmerInternalInspectionsEntry>> typeReference = new TypeReference<List<FarmerInternalInspectionsEntry>>() {
        };

        List<FarmerInternalInspectionsEntry> farmerInternalInspectionsEntriesList = objectMapper.readValue(is, typeReference);

        AtomicReference<Boolean> isFormSubmited = new AtomicReference<>(false);
        List<String> espectedfarmerRegNo = new ArrayList<>();
        //System.out.println("--------" + tableRow.size());
        for (FarmerInternalInspectionsEntry farmerInternalInspectionsEntry :
                farmerInternalInspectionsEntriesList) {
            //put inside because when page refreshed webelement state  When DOM element is
            //destroyed by the browser, WebElement is marked "stale" and can't be used anymore.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> tableRow = driver.findElements(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_DataListInspCompelted']/tbody/tr/td/table/tbody/tr"));
            isFormSubmited.set(true);
            tableRow.stream().forEach(webElement -> {
                        //System.out.println("--------" + farmerInternalInspectionsEntry + "-------------" + webElement.getText());
                        if (isFormSubmited.get() && !"".equals(webElement.getText())) {
                            if (webElement.getText().contains(farmerInternalInspectionsEntry.getFarmerRegistrationNo())) {
                                //System.out.println("---------111111text vaue" + webElement.getText().split(" ").length);
                                String sNo = webElement.findElements(By.tagName("td")).get(0).getText();
                                if(!"".equals(sNo) && Integer.parseInt(sNo) <=9 )
                                {
                                    sNo ="0"+sNo;
                                }
                                String actionBtnId = "ctl00_ContentPlaceHolder1_DataListInspCompelted_ctl" + sNo + "_lnkbtnCompletedprocess";
                                WebElement actionBtnWebElement = webElement.findElements(By.tagName("td")).get(6).findElement(By.id(actionBtnId));
                                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                                javascriptExecutor.executeScript("arguments[0].click();", actionBtnWebElement);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                WebElement inspectedby = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtInspectedby"));
                                inspectedby.sendKeys(farmerInternalInspectionsEntry.getInspectedBy());
                                WebElement mobileno = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtmobileno"));
                                mobileno.sendKeys(farmerInternalInspectionsEntry.getMobileNo());


                                //start Date
                                //String fromDate = "2023-08-13";
                                getCurrentDayMonthAndYear();
                                getTargetDayMonthAndYear(farmerInternalInspectionsEntry.getInspectionFromDate());
                                calRequiredMonthToJump();


                                manageCalender("ctl00_ContentPlaceHolder1_txtInspectedFromDate", driver);
                                manageCalender("ctl00_ContentPlaceHolder1_txtInspectedToDate", driver);

                                WebElement rdNo = driver.findElement(By.id("ctl00_ContentPlaceHolder1_rdNo"));
                                javascriptExecutor.executeScript("arguments[0].click();", rdNo);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                WebElement btnSubmit = driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit"));
                                javascriptExecutor.executeScript("arguments[0].click();", btnSubmit);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                isFormSubmited.set(false);

                            }
                        }

                    } // end of table loop
            );
            System.out.println("inspection entry done for :- "+farmerInternalInspectionsEntry.getFarmerRegistrationNo());
        }
        ; //end of Data Loop

    }

    private static void getTargetDayMonthAndYear(String date) {
        LocalDate targetDate = LocalDate.parse(date);
        _Calender.targetDay = targetDate.getDayOfMonth();
        _Calender.targetMonth = targetDate.getMonthValue();
        _Calender.targetYear = targetDate.getYear();
    }

    private static void getCurrentDayMonthAndYear() {
        LocalDate currentDate = LocalDate.now();
        _Calender.currentDay = currentDate.getDayOfMonth();
        _Calender.currentMonth = currentDate.getMonthValue();
        _Calender.currentYear = currentDate.getYear();
    }

    private static void calRequiredMonthToJump() {
        if ((_Calender.targetMonth - _Calender.currentMonth) > 0) {
            _Calender.jumpMonth = _Calender.targetMonth - _Calender.currentMonth;
        } else {
            _Calender.jumpMonth = _Calender.currentMonth - _Calender.targetMonth;
            _Calender.frdOrBack = false;
        }
    }

    private static void manageCalender(String calenderId, WebDriver webDriver) {
        WebElement inspectedFromDate = webDriver.findElement(By.id(calenderId));
        inspectedFromDate.click();
        for (int i = 0; i < _Calender.jumpMonth; i++) {
            if (_Calender.frdOrBack) {
                inspectedFromDate.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")).click();
            } else {

                inspectedFromDate.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[1]")).click();
            }
        }
        webDriver.findElement(By.linkText("" + _Calender.targetDay)).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class _Calender {

    public static int targetMonth = 0, targetYear = 0, targetDay = 0;
    public static int currentMonth = 0, currentYear = 0, currentDay = 0;
    public static int jumpMonth = 0;

    public static boolean frdOrBack = true;

}

