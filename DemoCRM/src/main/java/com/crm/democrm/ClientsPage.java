package com.crm.democrm;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.utility.ElementUtility;
import com.crm.utility.WaitUtility;

public class ClientsPage {
	WebDriver driver;

	@FindBy(xpath = "//a[@href='https://qalegend.com/crm/index.php/clients']")
	WebElement clientlink;

	@FindBy(className = "btn-default")
	WebElement addclient;

	@FindBy(xpath = "//input[@name='company_name']")
	WebElement companynamefield;

	@FindBy(xpath = "//textarea[@id='address']")
	WebElement addressfield;

	@FindBy(xpath = "//input[@name='city']")
	WebElement cityfield;

	@FindBy(xpath = "//input[@name='state']")
	WebElement statefield;

	@FindBy(xpath = "//input[@name='zip']")
	WebElement zipfield;

	@FindBy(xpath = "//input[@name='country']")
	WebElement countryfield;

	@FindBy(xpath = "//input[@name='phone']")
	WebElement phonefield;

	@FindBy(xpath = "//input[@name='website']")
	WebElement web;

	@FindBy(xpath = "//input[@name='vat_number']")
	WebElement vatfield;

	@FindBy(xpath = "//input[@id='currency_symbol']")
	WebElement currency;

	@FindBy(xpath = "//button[@class='btn btn-primary']")
	WebElement save;

	@FindBy(xpath = "//h1[text()]")
	WebElement clienttext;

	@FindBy(xpath = "//input[@type='search']")
	WebElement search;

	@FindBy(xpath = "//table[@id='client-table']//tbody//tr[1]//td[2]")
	WebElement searchelement;

	@FindBy(xpath = "//tr[@class='odd']")
	WebElement delClientRow;

	@FindBy(xpath = "//a[@title='Delete client']")
	WebElement delClientbtn;

	@FindBy(xpath = "//button[@id='confirmDeleteButton']")
	WebElement confirmdel;

	@FindBy(xpath = "//table[@id=\"client-table\"]/tbody/tr/td")
	WebElement delmessage;

	@FindBy(xpath = "//button[@class='close' and @type='button']")
	WebElement deleterecord;

	String clientCmpName = "2";

	WaitUtility waitutility;
	ElementUtility elementUtility;

	public ClientsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutility = new WaitUtility(driver);
		elementUtility = new ElementUtility(driver);
	}

	public void clickClient() {
		waitutility.waitForclick(clientlink);
		elementUtility.click(clientlink);
	}

	public void addClient(String companyname, String address, String city, String state, String zip, String country,
			String phone, String website, String vatnumber, String currencysymbol) {
		elementUtility.click(clientlink);
		elementUtility.click(addclient);
		elementUtility.sendkeys(companynamefield, companyname);
		elementUtility.sendkeys(addressfield, address);
		elementUtility.sendkeys(cityfield, city);
		elementUtility.sendkeys(statefield, state);
		elementUtility.sendkeys(zipfield, zip);
		elementUtility.sendkeys(countryfield, country);
		elementUtility.sendkeys(phonefield, phone);
		elementUtility.sendkeys(web, website);
		elementUtility.scrollintoview(vatfield);
		elementUtility.sendkeys(vatfield, vatnumber);
		elementUtility.sendkeys(currency, currencysymbol);
		elementUtility.click(save);
		
	}

	public String searchClient(String searchname) {
		waitutility.waitForclick(search);
		elementUtility.sendkeys(search, searchname);

		By locator = By.xpath("//table[@id='client-table']//tbody//tr//td//a[contains(text(),'" + searchname + "')]");
		waitutility.waitforvisible(locator);
		List<WebElement> notetable = driver.findElements(
				By.xpath("//table[@id='client-table']//tbody//tr//td//a[contains(text(),'" + searchname + "')]"));
		waitutility.waitforvisible(notetable);
		int row = elementUtility.getTableDataRowCount(notetable, searchname);

		String actualmsg = "";
		if (row != 0) {
			WebElement tableRow = driver.findElement(
					By.xpath("//table[@id='client-table']//tbody//tr[" + row + "]//td[" + clientCmpName + "]"));
			actualmsg = tableRow.getText();
			System.out.println("VerifySearch " + actualmsg);
		}
		return actualmsg;
	}

	public String deleteClient() {
		Actions action = new Actions(driver);
		action.moveToElement(delClientRow).perform();
		elementUtility.click(delClientbtn);
		elementUtility.click(confirmdel);
		waitutility.waitForclick(deleterecord);
		elementUtility.click(deleterecord);
		waitutility.waitforvisible(delmessage);

		String actualmsg = elementUtility.getText(delmessage);
		System.out.println("Verify DeleteItem : " + actualmsg);
		return actualmsg;

	}
}
