package com.qa.democrmtest;

import org.testng.annotations.Test;

import com.crm.democrm.LoginPage;
import com.crm.democrm.NotesPage;
import com.crm.utility.ElementUtility;
import com.crm.utility.FakerUtility;

import org.testng.Assert;

public class NotesTest extends BaseTest {
	String notetitle = FakerUtility.getName();
	String descrpt = FakerUtility.getRandomName();

	@Test(priority = 1, groups = { "smoke" })
	public void verifyAddNotes() {

		LoginPage lp = new LoginPage(driver);
		lp.doLogin(ElementUtility.getPropertyValue("username"), ElementUtility.getPropertyValue("password"));

		NotesPage notepg = new NotesPage(driver);
		notepg.clickNote();
		notepg.addNote(notetitle, descrpt);
		String actualmsg = notepg.searchNote(notetitle);
		String expectedmsg = notetitle;
		Assert.assertEquals(actualmsg, expectedmsg);
	}

	@Test(priority = 2, groups = { "smoke" })
	public void verifySearch() {
		LoginPage lp = new LoginPage(driver);
		lp.doLogin("admin@admin.com", "12345678");
		NotesPage notepg = new NotesPage(driver);
		notepg.clickNote();
		String actualmsg = notepg.searchNote(notetitle);
		String expectedmsg = notetitle;
		Assert.assertEquals(actualmsg, expectedmsg);
	}

	@Test(priority = 3, groups = { "regression" })
	public void verifyEditNote() {
		LoginPage lp = new LoginPage(driver);
		lp.doLogin("admin@admin.com", "12345678");

		NotesPage notepg = new NotesPage(driver);
		notepg.clickNote();
		notepg.searchNote(notetitle);

		notepg.editNote("editedName");
		String actualmsg = notepg.searchNote("editedName");
		String expectedmsg = "editedName";
		Assert.assertEquals(actualmsg, expectedmsg);
	}

	@Test(priority = 4, groups = { "smoke" })
	public void verifyDelNote() {
		LoginPage lp = new LoginPage(driver);
		lp.doLogin("admin@admin.com", "12345678");

		NotesPage notepg = new NotesPage(driver);
		notepg.clickNote();
		notepg.searchNote(descrpt);
		String actualmsg = notepg.deleteNote();
		String expectedmsg = "No record found.";
		Assert.assertEquals(actualmsg, expectedmsg);
	}
}
