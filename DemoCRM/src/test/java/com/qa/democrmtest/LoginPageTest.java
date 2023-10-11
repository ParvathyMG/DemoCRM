package com.qa.democrmtest;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.constants.Constants;
import com.crm.democrm.LoginPage;
import com.crm.utility.ExcelRead;

public class LoginPageTest extends BaseTest {

	@Test(dataProvider="userData")
	public void verifylogin(String username,String password) {
		LoginPage lp=new LoginPage(driver);
		
		boolean status=lp.doLogin("admin@admin.com", "12345678");
		Assert.assertTrue(status);
	}

	@DataProvider

	public Object[][] userData() throws InvalidFormatException, IOException 
	{
		Object[][] data=ExcelRead.getDataFromExcel(Constants.testdata, 
				"LoginDetails");
		return data;
	}
}