package amazonautomation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AmazonAutomation {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.automationexercise.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        //verify that logo and the home page is visible successfully
	    //find the logo element
		WebElement logoElement =  driver.findElement(By.xpath("//img[@src='/static/images/home/logo.png']"));
		// Check if the logo is displayed
		boolean logo = logoElement.isDisplayed();
		// Highlight the logo if it is displayed
		if (logo) {
		   
		    js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border:3px solid red;');", logoElement);
		    Assert.assertTrue(logo); // Verify that the logo is displayed
		} else {
		    // You can add an assertion here or handle the case when the logo is not displayed
		    Assert.fail("Logo is not displayed.");
		}
		//find the 'Home' text
				WebElement hometext = driver.findElement(By.xpath("//a[@href='/' and contains(text(),' Home')]"));
				boolean homepage = hometext.isDisplayed();
				if(homepage) 
				{
					//JavascriptExecutor jse = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].setAttribute('style','background:yellow; border: 3px solid red;');", hometext);
					Assert.assertTrue(homepage);
				}
				else 
				{
					Assert.fail("homepage is not displayed");
				}
				System.out.println("Home page and the logo displayed properly");
				Thread.sleep(2000);
           // Find the sign-up element
     		WebElement signUp = driver.findElement(By.xpath("//a[@href='/login' or text()='Signup / Login']"));

     		// Highlight the sign-up link
     		//JavascriptExecutor JS = (JavascriptExecutor) driver;
     		js.executeScript("arguments[0].setAttribute('style', 'background:yellow; border:2px solid red;');", signUp);

     		// Click on the sign-up link
     		signUp.click();

        // Load Excel file
        File excelfile = new File("./src/test/resources/excel.xlsx");
        FileInputStream fip = new FileInputStream(excelfile);
        XSSFWorkbook workbook = new XSSFWorkbook(fip);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        int rowcount = sheet.getPhysicalNumberOfRows();
        int colcount = sheet.getRow(0).getPhysicalNumberOfCells(); // Corrected column count logic

        System.out.println("Row count: " + rowcount + ", Column count: " + colcount);

        // Loop through rows and columns
        for (int i = 1; i < rowcount; i++) {
            XSSFRow celldata = sheet.getRow(i);

            // Get the email and password from the Excel sheet
            String mail = "";
            String password = "";

            // Handling possible cell types (string or numeric)
            if (celldata.getCell(0).getCellType() == CellType.STRING) {
                mail = celldata.getCell(0).getStringCellValue();
            } else if (celldata.getCell(0).getCellType() == CellType.NUMERIC) {
                mail = String.valueOf(celldata.getCell(0).getNumericCellValue());
            }

            if (celldata.getCell(1).getCellType() == CellType.STRING) {
                password = celldata.getCell(1).getStringCellValue();
            } else if (celldata.getCell(1).getCellType() == CellType.NUMERIC) {
                password = String.valueOf(celldata.getCell(1).getNumericCellValue());
            }

            // Fill the email and password into the form
            driver.findElement(By.name("email")).clear();
            driver.findElement(By.name("email")).sendKeys(mail);
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys(password);

            System.out.println(mail + " || " + password);
        }

        workbook.close();
        fip.close();
        
        //Click on login
        WebElement login = driver.findElement(By.xpath("//button[@data-qa='login-button']"));
        
        login.click();
        Thread.sleep(2000);
        
        //Verify 'Products' button is visible
        WebElement products = driver.findElement(By.xpath("//a[@href='/products']"));
        boolean productsButton = products.isDisplayed();
        //Highlight the element
        if(productsButton) {
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('style','background : yellow;border:2px solid red;');", products);
        Assert.assertTrue(productsButton);
        }
        else 
        {
        	Assert.assertFalse(productsButton);
        }
        //Click on 'Products'button
        products.click();
        
        //Verify user is navigated to ALL PRODUCTS page successfully
        WebElement allProducts = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        boolean allproducts = allProducts.isDisplayed();
        if(allproducts) 
        {
        	//JavascriptExecutor JS1 = (JavascriptExecutor)driver;
        	js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');", allProducts);
        	Assert.assertTrue(allproducts);
        }
        else 
        {
        	Assert.assertFalse(allproducts);
        }
        //Enter product name in search input and click search button
        //Locate the element
        WebElement search = driver.findElement(By.id("search_product"));
        // Scroll the page until the element is visible
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", search);
        search.sendKeys("crop tops");
        driver.findElement(By.id("submit_search")).click();
        
        //Verify 'SEARCHED PRODUCTS' is visible
        WebElement searchedproducts = driver.findElement(By.xpath("//h2[@class='title text-center']"));
        boolean searchedProducts = searchedproducts.isDisplayed();
        if(searchedProducts) 
        {
        	js.executeScript("arguments[0].setAttribute('style','background:yellow;border:2px solid red;');", searchedproducts);
        	Assert.assertTrue(searchedProducts);
        }else 
        {
        	Assert.assertFalse(searchedProducts);
        }
        //Click on kids
        driver.findElement(By.xpath("//a[@href=\"#Kids\"]")).click();
        driver.findElement(By.xpath("//a[@href=\"/category_products/4\"]")).click();
        //Hover over first product and click 'Add to cart'
       // Wait for the product to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='/get_product_picture/16']")));
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[2]/div/div[1]/div[1]/a")));
        /*Actions action = new Actions(driver);
        action.moveToElement(firstproduct).moveToElement(firstproduct).moveToElement(addToCart).click().perform();
        addToCart.click();*/
        // Scroll the page to bring the Add to Cart button into view
        //js.executeScript("arguments[0].scrollIntoView(true);", addToCart);
        
        // Wait for the button to be clickable
        //wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        addToCart.click();
        
        
        //Click on view cart
        WebElement viewCart = driver.findElement(By.xpath("//u[normalize-space()='View Cart']"));
        viewCart.click();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//a[@class=\"btn btn-default check_out\"]")).click();
        
        
    }
}
        
        
        
        
        
    

        
        

        

    
  
		
		
		
        
        
        
		
		

		
		
		
		
		
		
		


	


