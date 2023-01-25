package TestSuites;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cognizant.mainproject.Base.BaseUI;
import com.cognizant.mainproject.Pages.TruTime;

public class AppTest extends BaseUI {
	TruTime tr = new TruTime();

	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("Executing Test Cases");

		tr.invokeBrowser();
		reportPass("Browser is invoked");
	}

	@Test(priority = 1)
	public void testCases() throws Exception {

		tr.openURL();
		tr.login();
		tr.getData();
		reportPass("All steps passed successfuly");
	}

	@AfterTest
	public void closeBrowser() throws IOException {
		reportPass("Browser is closed successfuly");
		tr.endReport();
		tr.quitBrowser();
	}
}
