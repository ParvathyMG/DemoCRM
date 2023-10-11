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


public class NotesPage {
	WebDriver driver;

	@FindBy(xpath="//*[text()='Notes']")
	WebElement notelink;
	
	@FindBy(xpath = "//div[@class='page-title clearfix']/h1")
	WebElement notehead;

	@FindBy(className="btn-default")
	WebElement addnotes;

	@FindBy(xpath="//input[@name='title']")
	WebElement notetitle;

	@FindBy(xpath="//textarea[@name='description']")
	WebElement descrpt;
	
	@FindBy(xpath="//input[@id='s2id_autogen2']")
	WebElement label;

	@FindBy(xpath="//button[@class='btn btn-primary']")
	WebElement save;

	@FindBy(xpath="//input[@type='search']")
	WebElement search;

	@FindBy(xpath="//table[@id='note-table']//tbody//tr[1]//td[1]")
	WebElement editrowelement;

	@FindBy(xpath="//a[@title='Edit note']")
	WebElement editbutton;

	@FindBy(xpath="//tr[@class='odd']")
	WebElement deleterow;

	@FindBy(xpath="//a[@title='Delete note']")
	WebElement deletebutton;

	@FindBy(xpath="//button[@id='confirmDeleteButton']")
	WebElement confirmdelete;

	@FindBy(xpath="//table[@id='note-table']//tbody//tr[1]//td[3]")
	WebElement searchtext;

	@FindBy(xpath="//table[@id=\"note-table\"]/tbody/tr")
	WebElement deletetext;
	
	@FindBy(xpath="//button[@class='close' and @type='button']")
	WebElement deleterecord;

	String edittitle;
	String notetitlecolumn="2";

	WaitUtility waitutility;
	ElementUtility elementutility;

	public NotesPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this );
		waitutility=new WaitUtility(driver);
		elementutility=new ElementUtility(driver);
		}

	public String clickNote()
	{
		waitutility.waitForclick(notelink);
		elementutility.click(notelink);
		waitutility.waitforvisible(notehead);
		String actualhead = elementutility.getText(notehead);
		return actualhead;
		
	}

	public void addNote(String titlenote,String description)
	{
		elementutility.click(addnotes);
		elementutility.sendkeys(notetitle, titlenote);
		elementutility.sendkeys(descrpt, description);
		waitutility.waitForclick(notelink);
		elementutility.click(notelink);
		elementutility.click(save);

	}

	public String searchNote(String searchElement)
	{
		waitutility.waitForclick(search);
		elementutility.sendkeys(search, searchElement);

		By locator=By.xpath("//table[@id='note-table']//tbody//tr//td//a[contains(text(),'"+searchElement+"')]");
		waitutility.waitforvisible(locator);
		List<WebElement> notetable=driver.findElements(By.xpath("//table[@id='note-table']//tbody//tr//td//a[contains(text(),'"+searchElement+"')]"));
		waitutility.waitforvisible(notetable);
		int row=elementutility.getTableDataRowCount(notetable, searchElement);

		String actualmsg="";
		if(row!=0) 
		{
			WebElement tableRow=driver.findElement(By.xpath("//table[@id='note-table']//tbody//tr["+row+"]//td["+notetitlecolumn+"]"));
			actualmsg=tableRow.getText();
			System.out.println("Searched Element : " +actualmsg);
		}
		return actualmsg;
	}

	public void editNote(String titlenote)
	{		
		Actions action=new Actions(driver);
		action.moveToElement(editrowelement).perform(); 

		elementutility.click(editbutton);
		elementutility.clear(notetitle);
		elementutility.sendkeys(notetitle, titlenote);
		elementutility.click(save);
		
		waitutility.waitForclick(notelink);
		waitutility.waitforvisible(notelink);
		elementutility.click(notelink);
	}

	public String deleteNote()
	{
		Actions action=new Actions(driver);
		action.moveToElement(deleterow).perform(); 
		elementutility.click(deletebutton);
		elementutility.click(confirmdelete);
		waitutility.waitForclick(deleterecord);
		elementutility.click(deleterecord);
		waitutility.waitforvisible(deletetext);

		String actualmsg=elementutility.getText(deletetext);
		System.out.println("Verify Delete: " +actualmsg);
		return actualmsg;
	}
}
