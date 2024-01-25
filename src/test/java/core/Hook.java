package core;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Hook extends Page
{
    // login page
	@FindBy(xpath = externalXpath.AllXpath.signin)
	public static WebElement signin;

	@FindBy(xpath = externalXpath.AllXpath.uid)
	public static WebElement uid;
	
	@FindBy(xpath = externalXpath.AllXpath.ctnbtn)
	public static WebElement ctnbtn;
	
	@FindBy(xpath = externalXpath.AllXpath.err1)
	public static WebElement err1;
	
	@FindBy(xpath = externalXpath.AllXpath.pwd)
	public static WebElement pwd;
	
	@FindBy(xpath = externalXpath.AllXpath.submit)
	public static WebElement submit;
	
	@FindBy(xpath = externalXpath.AllXpath.err2)
	public static WebElement err2;

	  // search result
		@FindBy(xpath = externalXpath.AllXpath.searchbox)
		public static WebElement searchbox;

}
