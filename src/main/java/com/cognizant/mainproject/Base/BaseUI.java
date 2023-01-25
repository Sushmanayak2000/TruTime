package com.cognizant.mainproject.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cognizant.mainproject.Utils.ExtentReportManager;
import com.google.common.io.Files;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	JavascriptExecutor js;
	public static WebDriverWait wait;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public File file;
	public XSSFWorkbook workbook;
	public XSSFSheet sh;
	public FileOutputStream fos;

	// Method to invoke different browsers
	public void invokeBrowser() {
		prop = new Properties();

		try {
			prop.load(
					new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// To open chrome browser
		if (prop.getProperty("browserName").matches("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		// To open firefox browser
		if (prop.getProperty("browserName").matches("firefox")) {
			System.setProperty("webdriver.edge.driver", "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		// To maximize the Browser Window
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	// Method to open the Main Page URL
	public void openURL() {
		driver.get(prop.getProperty("URL"));
	}

	// Method to show the failed test cases in the report
	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
	}

	// Method to show the passed test cases in the report
	public void reportPass(String report) {
		logger.log(Status.PASS, report);
	}

	// Method to take screenshot
	public void Screenshot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir") + "/Screenshot/" + fileName + ".png");
		Files.copy(srcFile, destFile);
	}

	// Method to apply dynamic wait
	public void wait(int sec, By locator) {
		wait = new WebDriverWait(driver, sec);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Method to input all data to the report
	public void endReport() {
		report.flush();
	}

	// Method to close the Browser
	public void quitBrowser() throws IOException {
		fos = new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();
		driver.quit();
	}

}
