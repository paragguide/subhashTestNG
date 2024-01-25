package testcases;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Hook;

public class ShopTest extends Hook
{
  @Test(dataProvider = "product")
  public void search(String s) 
  {
	    if(LoginTest.flag == true)
	    {
	    	log.debug("search "+s);
	    	test.log(LogStatus.PASS,"search "+s);
	  Hook.searchbox.clear();  	
	  Hook.searchbox.sendKeys(s);
	  Actions a = new Actions(Hook.driver);
	  a.sendKeys(Keys.ENTER).perform();
	    }
	    else
	    {
	    	Assert.fail("not logged in");
	    	System.err.println("not logged in");
	    }
  }

  @DataProvider
  public String[][] product() 
  {
    String p[][] = {{"sun glasses"},{"mobile"}};
    return p;
  }
}
