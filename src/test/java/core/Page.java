package core;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

public class Page 
{
  public static WebDriver driver = null;
  public static Connection con = null;
  public static Statement stm = null;
  public static ResultSet rs = null;
  public static Logger log = null;
  public static ExtentReports report = null;
  public static ExtentTest test = null;
  
  int c = 0;
  
  @Parameters({"bfilename"})
  @BeforeMethod // 3rd method -every time before @Test is called
  public void takeScreenShot1(String bfilename) throws Exception
	{
	  c++;
	  
		TakesScreenshot t = (TakesScreenshot)driver;
		File fb =  t.getScreenshotAs(OutputType.FILE); // stores screenshot in temp location
		
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\screenshot\\"+bfilename+"-"+c+".jpg";
	
		FileUtils.copyFile(fb, new File(screenshotpath));
	
	}

  @Parameters({"afilename"})
  @AfterMethod // 4th method - every time after @Test
  public void takeScreenShot2(String afilename) throws Exception
	{
	  c++;
		TakesScreenshot t = (TakesScreenshot)driver;
		File fb =  t.getScreenshotAs(OutputType.FILE); // stores screenshot in temp location
		
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\screenshot\\"+afilename+"-"+c+".jpg";
	
		FileUtils.copyFile(fb, new File(screenshotpath));
	
	}

  @Parameters({"wbname","sheetname"})
  @BeforeClass // 2nd method
  public void makeWBConnection(String wbname,String sheetname) throws Exception
  {
	  System.out.println("2");
	  Class.forName("com.googlecode.sqlsheet.Driver"); // register excel driver 
	  String wbpath = System.getProperty("user.dir")+"//src//test//java//excel//"+wbname;
	  con = DriverManager.getConnection("jdbc:xls:file:"+wbpath);
	  System.out.println("connected to excel WB..");
	  stm=con.createStatement();  // default top to bottom
	  rs=stm.executeQuery("select * from "+sheetname); // Sheet name 
  
	  log.debug("connected to wb "+wbname+" sheet "+sheetname);
      test.log(LogStatus.PASS, "connected to wb "+wbname+" sheet "+sheetname);
  }

  @AfterClass // 5th method
  public void closeWBConnection() throws Exception
  {
	  System.out.println("5");
	  con.commit();
	  con.close();
  }

  @Parameters({"browser","url","reportname","obj"})
  @BeforeTest  // 1st method
  public void openBrowser(String browser,String url,String reportname,String obj) throws Exception
  {
	  System.out.println("1");
	  if(browser.equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
		
	//	driver.get(url); // but cannot go back,forward or referesh
		
		    // or
		driver.navigate().to(url); // go back,forward, refresh
		
	//	log.debug("browser "+browser+" url "+url+" open..");
	//	test.log(LogStatus.PASS, "browser "+browser+" url "+url+" open..");
		
		PageFactory.initElements(driver, this); // compulsory to read external xpath
		
		
		// implicit wait..
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20l));
		
		driver.manage().window().maximize();
		
		// log
		  Properties p = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+reportname+".properties");
		
			p.load(fis);
			p.put("log4j.appender."+obj+".File", System.getProperty("user.dir")+"//src//test//java//logs//"+reportname+".log");
			PropertyConfigurator.configure(p);
			
		log =	Logger.getLogger(reportname);
		
		  // report
		
		report = new ExtentReports(System.getProperty("user.dir")+"//src//test//java//reports//"+reportname+".html");
	    test = report.startTest(reportname);
		

  }

  @AfterTest // 6th method
  public void closeBrowser() throws Exception
  {
	 
	  report.endTest(test);
		report.flush();
	 	  driver.quit();
	  
  }

}
