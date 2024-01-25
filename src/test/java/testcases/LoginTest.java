package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Hook;
import core.Page;

import java.sql.ResultSetMetaData;

import org.testng.annotations.DataProvider;

public class LoginTest extends Hook
{
	public static boolean flag = true;
	
  @Test(dataProvider = "loginData")
  public void login(String id, String pd) 
  {
	  log.debug("login test called ");
	  test.log(LogStatus.PASS, "login called");
	  
	  signin.click();
		uid.sendKeys(id);
		ctnbtn.click();
		   try {
		String msg1 = err1.getText();
		System.out.println(msg1);
		flag = false;
		   }
		   catch(Exception e)
		   {
			   System.out.println(" userid is valid.. ");
			   pwd.sendKeys(pd);
			   submit.click();
			     try {
			String msg2 =   err2.getText();
			System.out.println(msg2);
			flag = false;
			     }
			     catch(Exception ee)
			     {
			    	 System.out.println("password is right..");
			    	 flag = true;
			     }
		   }
	
  }

  @DataProvider
  public Object[][] loginData() throws Exception
  {
	  Object data[][]= {};  // empty array
	  if(rs != null)
	  {
	  ResultSetMetaData rsmt=rs.getMetaData();
	  int columncount=rsmt.getColumnCount();

	  rs.last(); // place record pointer on last record
	  int rowcount=rs.getRow(); // fetch position of last record

	  System.out.println(columncount+" , "+rowcount);
	  rs.beforeFirst(); // reset

	  data = new Object[rowcount][columncount]; //-> size of array 
	  			
	  for(int rowNum = 1 ; rowNum <= rowcount ; rowNum++)
	     { 
	  				
	  for(int colNum=1 ; colNum<= columncount; colNum++)
	        {
	                   rs.absolute(rowNum); // point to row  
	  	Object data1= rs.getObject(colNum); // getting values from excel
	  	
	  		data[rowNum-1][colNum-1]= data1 ; //adding table data in  array , array starts from 0
	  				}
	  			}
	  }
	  
	 
	  return data;

  }
  
}
