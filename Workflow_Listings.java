package Workflow_Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.demo.base.BaseClass;
import Page_Component.PageLogin;
import Page_Component.PageHome;
import Page_Component.PageListings;

public class Workflow_Listings extends BaseClass {

	// WebDriver driver;
	PageLogin PageLogin = new PageLogin(driver);
	PageHome PageHome = new PageHome(driver);
	PageListings PageListings = new PageListings(driver);

	// Goto the Listings Page
	public String LaunchListing(String listingUrl) {
		driver.get(listingUrl);
		return driver.getTitle();

	}

	public String SelectCity(String city, String desiredcity, String alldesiredCity) throws InterruptedException {
		PageListings.click_Locations();
		Thread.sleep(7000);
		PageListings.select_city(city);
		Thread.sleep(7000);
		PageListings.select_city(desiredcity);
		Thread.sleep(7000);
		PageListings.select_desired_city(alldesiredCity);
		PageListings.click_price();
		Thread.sleep(7000);
		PageListings.click_price();
		Thread.sleep(7000);
		return PageListings.search_city();
	}

	/*
	 * public LinkedHashMap<String, String> SelectTopListings(int countListings) {
	 * //Create a LinkedHashMap which stores the Key as Neighborhood and value as
	 * Building Name(Url) LinkedHashMap<String,String>hMap = new
	 * LinkedHashMap<String,String>(); //Now get the number of items displayed per
	 * page.
	 * 
	 * //PagesIteration do { boolean found = false; //Items in each PageIteration
	 * List<WebElement> get_Items = PageListings.get_Items(); for(WebElement webele
	 * :get_Items) { if(hMap.size() == countListings) { found = true; break;
	 * 
	 * }else { try { String row = webele.getAttribute("row"); //Getting the
	 * neighborhood value from the selected Webelement String neighborhood =
	 * PageListings.get_Neighborhood_Name(row); System.out.println(neighborhood);
	 * if(!(hMap.containsKey(neighborhood))) { hMap.put(neighborhood,
	 * PageListings.get_Building_Url(row)); }//inner if loop }catch(Exception e) {
	 * System.out.
	 * println("We couldn't find the Top 5 Listings of different neighborhood from the total list we got"
	 * ); }
	 * 
	 * }//outer if loop
	 * 
	 * }//for loop if(found) { break; } }while(PageListings.click_Next());
	 * 
	 * return hMap; }
	 */

	public LinkedHashMap<String, String> SelectTopListings(int countListings)
			throws NumberFormatException, InterruptedException {
		// Create a LinkedHashMap which stores the Key as Neighborhood and value as
		// Building Name(Url)
		LinkedHashMap<String, String> hMap = new LinkedHashMap<String, String>();
		// This do while loop does the Page iteration
		do {
			boolean found = false;
			// get the initial rownumber by getting the attribute value of first row
			int initialrow = Integer.parseInt(PageListings.each_Row.getAttribute("row")); // page 1 = 0 and page 2 = 120
			int listingsperPage = Integer.parseInt(PageListings.get_ItemsPerPage()); // 120 in this case
			int maxlistingsperpage = initialrow + (listingsperPage - 1); // 0+119 or 120+119
			String neighborhood;
			// for loop will do Listings traverse per page
			for (int i = initialrow; i <= maxlistingsperpage; i++) {
				String str1 = Integer.toString(i);
				// check if the hashMap already reached countListings number
				if (hMap.size() == countListings) {
					found = true;
					break;
				} else {
					// Read the neighborhood of a row
					try {
						neighborhood = PageListings.get_Neighborhood_Name(str1);

					} catch (Exception e) {
						log.error("The element is not found and the listings is ended here"
								+ "The listings so far we did are returned");
						System.out.println("The element is not found and the listings is ended here"
								+ "The listings so far we did are returned");
						found = true;
						break;
					}

					// add to hashmap only if the neighbothood doesn't exist
					if (!(hMap.containsKey(neighborhood))) {
						try {
							hMap.put(neighborhood, PageListings.get_Building_Url(str1));
						} catch (Exception e) {
							log.error("The Url is not found for row " + str1 + " Please check on the website for that Row"
									+ "We continue to find the top listings to  add in the list until the desired number is met");
							System.out.println("The Url is not found for row " + str1 + " Please check on the website for that Row"
									+ "We continue to find the top listings to  add in the list until the desired number is met");
							continue;
						}

					} // inner if loop

				} // outer if loop (

			} // for loop

			if (found) {
				break;
			}
		} while (PageListings.click_Next());

		return hMap;
	}

}
