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

public class ItemsPage {

	WebDriver driver;

	@FindBy(xpath = "//*[text()='Items']")
	WebElement itemlink;

	@FindBy(xpath = "//a[@title='Add item']")
	WebElement addItem;

	@FindBy(xpath = "//input[@id='title']")
	WebElement itemtitleName;

	@FindBy(xpath = "//textarea[@id='description']")
	WebElement description;

	@FindBy(xpath = "//input[@id='unit_type']")
	WebElement unittype;

	@FindBy(xpath = "//input[@id='item_rate']")
	WebElement rate;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement save;

	@FindBy(xpath = "//tr[@class='odd']")
	WebElement editItemRow;

	@FindBy(xpath = "//a[@title='Edit item']")
	WebElement editItemBtn;

	@FindBy(xpath = "//input[@type='search']")
	WebElement searchItem;

	@FindBy(xpath = "//tr[@class='odd']")
	WebElement delItemRow;

	@FindBy(xpath = "//a[@title='Delete']")
	WebElement delItembtn;

	@FindBy(xpath = "//table[@id='item-table']//tbody//tr//td[1]")
	List<WebElement> itemtable;

	@FindBy(xpath = "//table[@id='item-table']//tbody//tr[1]//td[1]")
	WebElement edititemtext;

	@FindBy(xpath = "//table[@id=\"item-table\"]/tbody/tr/td")
	WebElement delmessage;

	@FindBy(xpath = "//button[@class='close' and @type='button']")
	WebElement deleterecord;

	String itemtitlecolumn = "1";
	String editcolumn = "2";

	ElementUtility elementutility;
	WaitUtility waitutility;

	String searchElement;

	public ItemsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementutility = new ElementUtility(driver);
		waitutility = new WaitUtility(driver);
	}

	public void clickItem() {
		waitutility.waitForclick(itemlink);
		elementutility.click(itemlink);
	}

	public void addItem(String itemtitle, String itemdescription, String itemunit, String itemrate) {
		elementutility.click(addItem);
		elementutility.sendkeys(itemtitleName, itemtitle);
		elementutility.sendkeys(description, itemdescription);
		elementutility.sendkeys(unittype, itemunit);
		elementutility.sendkeys(rate, itemrate);
		elementutility.click(save);

		waitutility.waitForclick(itemlink);
		elementutility.click(itemlink);
	}

	public String editItem(String edititem) {
		Actions action = new Actions(driver);
		action.moveToElement(editItemRow).perform();

		elementutility.click(editItemBtn);
		elementutility.clear(description);
		waitutility.waitforvisible(description);
		elementutility.sendkeys(description, edititem);
		elementutility.click(save);

		By locator = By.xpath("//table[@id='item-table']//tbody//tr//td[contains(text(),'" + edititem + "')]");
		waitutility.waitforvisible(locator);
		List<WebElement> itemtable = driver
				.findElements(By.xpath("//table[@id='item-table']//tbody//tr//td[" + editcolumn + "]"));
		waitutility.waitforvisible(itemtable);
		int row = elementutility.getTableDataRowCount(itemtable, edititem);
		String actualmsg = "";

		if (row != 0) {
			WebElement tableRow = driver
					.findElement(By.xpath("//table[@id='item-table']//tbody//tr[" + row + "]//td[" + editcolumn + "]"));
			actualmsg = tableRow.getText();
			System.out.println("Verify EditItem Description :  " + actualmsg);
		}
		return actualmsg;
	}

	public String searchItem(String searchElement) {
		waitutility.waitForclick(searchItem);
		elementutility.sendkeys(searchItem, searchElement);

		By locator = By.xpath("//table[@id='item-table']//tbody//tr//td[contains(text(),'" + searchElement + "')]");
		waitutility.waitforvisible(locator);
		List<WebElement> itemtable = driver
				.findElements(By.xpath("//table[@id='item-table']//tbody//tr//td[" + itemtitlecolumn + "]"));
		waitutility.waitforvisible(itemtable);
		int row = elementutility.getTableDataRowCount(itemtable, searchElement);
		String actualmsg = "";

		if (row != 0) {
			WebElement tableRow = driver.findElement(
					By.xpath("//table[@id='item-table']//tbody//tr[" + row + "]//td[" + itemtitlecolumn + "]"));
			actualmsg = tableRow.getText();
			System.out.println("Verify SearchItem : " + actualmsg);
		}
		return actualmsg;
	}

	public String delItem() {
		Actions action = new Actions(driver);
		action.moveToElement(delItemRow).perform();
		elementutility.click(delItembtn);
		waitutility.waitForclick(deleterecord);
		elementutility.click(deleterecord);
		waitutility.waitforvisible(delmessage);

		String actualmsg = elementutility.getText(delmessage);
		System.out.println("Verify DeleteItem : " + actualmsg);
		return actualmsg;

	}

}
