package com.crm.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportUtility {
	static ExtentReports extent;
	public static ExtentReports createExtentReports()
	{
		String path =System.getProperty("user.dir")+"\\Extend_Report\\extent-report.html";
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		extent =new ExtentReports();
		extent.attachReporter(reporter);

		return extent;
	}

}


