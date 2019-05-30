package com.bae.apache.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import constants.Constants;

public class DemoSiteApacheTest {

	public static WebDriver driver;

	@Before
	public void setup() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Administrator\\Downloads\\chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@After
	/*
	 * public void teardown() { driver.quit(); }
	 */

	@Test
	public void test1() throws InterruptedException {

		FileInputStream file = null;
		try {
			file = new FileInputStream(Constants.FILE);
		} catch (FileNotFoundException e) {
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
		} catch (IOException e) {
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		for (int i = 1; i < 5; i++) {
			XSSFCell cellUser = sheet.getRow(i).getCell(0);
			XSSFCell cellPassword = sheet.getRow(i).getCell(1);

			driver.get(Constants.URL1);

			WebElement setUp = driver.findElement(By
					.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[3]"));
			setUp.click();
			WebElement setUser = driver.findElement(By.name("username"));
			setUser.sendKeys(cellUser.getStringCellValue());
			WebElement setPassword = driver.findElement(By.name("password"));
			setPassword.sendKeys(cellPassword.getStringCellValue());
			setPassword.submit();
			WebElement goLogin = driver.findElement(By
					.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[4]"));
			goLogin.click();
			WebElement enterUser = driver.findElement(By.name("username"));
			enterUser.sendKeys(cellUser.getStringCellValue());
			WebElement enterPassword = driver.findElement(By.name("password"));
			enterPassword.sendKeys(cellPassword.getStringCellValue());
			enterPassword.submit();

			WebElement checkLogin = driver
					.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b"));
			assertTrue(checkLogin.isDisplayed());

			XSSFCell cellResult = sheet.getRow(i).createCell(2);
			WebElement successfulLogin = checkLogin;
			if (successfulLogin.isDisplayed()) {
				cellResult.setCellValue("Login Successful");
			} else {
				cellResult.setCellValue("Login Unsuccessful");
			}
			try {
				FileOutputStream out = new FileOutputStream(new File(Constants.FILE));
				workbook.write(out);
				out.close();
				System.out.println("File saved without issue");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
