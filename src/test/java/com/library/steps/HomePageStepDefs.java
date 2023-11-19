package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageStepDefs {
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    String actualAmountOfBorrowedBooks;
//    @Test
//    public void test(){
//        Driver.getDriver().get(ConfigurationReader.getProperty("library_url"));
//        loginPage.login("librarian");
//    }

    @Given("the {string} on the home page DK")
    public void the_on_the_home_page_dk(String userType) {
        loginPage.login(userType);
    }
    @When("the librarian gets borrowed books number DK")
    public void the_librarian_gets_borrowed_books_number_dk() {
        wait.until(ExpectedConditions.visibilityOf(dashBoardPage.borrowedBooksNumber));
        actualAmountOfBorrowedBooks = dashBoardPage.borrowedBooksNumber.getText();
    }

    @Then("borrowed books number information must match with DB DK")
    public void borrowed_books_number_information_must_match_with_db_dk() {
        String query = "select count(*) from book_borrow where is_returned=0";
        DB_Util.runQuery(query);
        String expectedAmountOfBorrowedBooks = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals("Verification of borrowed books failed: ", expectedAmountOfBorrowedBooks, actualAmountOfBorrowedBooks);
    }
}
