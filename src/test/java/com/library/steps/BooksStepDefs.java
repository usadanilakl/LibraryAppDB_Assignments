package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BooksStepDefs {
    BookPage bookPage = new BookPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    List<String> actualCategoryOptions = new ArrayList<>();
    String searchedBook;
    String mostPopularCategory;
    @When("the user navigates to {string} page DK")
    public void the_user_navigates_to_page_dk(String module) {
        bookPage.navigateModule(module);
    }
    @When("the user clicks book categories DK")
    public void the_user_clicks_book_categories_dk() {
        wait.until(ExpectedConditions.visibilityOf(bookPage.bookCategoriesDropdown));
        Select select = new Select(bookPage.bookCategoriesDropdown);
        List<WebElement> actualCategoryOptionsWebElem = select.getOptions();
        actualCategoryOptionsWebElem.forEach( c->actualCategoryOptions.add(c.getText()) );
        actualCategoryOptions.remove("ALL");

    }
    @Then("verify book categories must match book_categories table from db DK")
    public void verify_book_categories_must_match_book_categories_table_from_db_dk() {
        String query = "select name from book_categories";
        DB_Util.runQuery(query);
        List<String> expectedCategoryOptions = DB_Util.getColumnDataAsList(1);
        Assert.assertEquals(expectedCategoryOptions,actualCategoryOptions);
    }

    @When("the user searches for {string} book DK")
    public void the_user_searches_for_book_dk(String string) {
        searchedBook = string;
        bookPage.search.sendKeys(string, Keys.ENTER);
    }
    @When("the user clicks edit book button DK")
    public void the_user_clicks_edit_book_button_dk() {
        bookPage.editBook(searchedBook).click();
    }
    @Then("book information must match the Database DK")
    public void book_information_must_match_the_database_dk() {
        //String bookName = bookPage.bookNameFromEditPopUp.getAttribute("value");
        wait.until(ExpectedConditions.visibilityOf(bookPage.bookAuthorFromEditPopUp));
        String author = bookPage.bookAuthorFromEditPopUp.getAttribute("value");
        String isbn = bookPage.bookISBNFromEditPopUp.getAttribute("value");
        String year = bookPage.bookYearFromEditPopUp.getAttribute("value");
        String description = bookPage.description.getText();
        String category = new Select(bookPage.categoryDropdown).getFirstSelectedOption().getText();
        bookPage.closeEditWindow.click();

        String query = "select b.name, b.author, b.year, b.isbn, c.name, b.description  from books b join book_categories c on c.id=b.book_category_id\n" +
                "         where b.name = '"+searchedBook+"' and\n" +
                "               author = '"+author+"' and\n" +
                "               isbn = "+isbn+" and\n" +
                "               year = "+year+" and\n" +
                "               c.name = '"+category+"' and\n" +
                "               b.description = '"+description+"'";
        DB_Util.runQuery(query);
        System.out.println("DB_Util.getRowMap(1) = " + DB_Util.getRowMap(1));
        System.out.println(DB_Util.getFirstRowFirstColumn());
        Assert.assertTrue(DB_Util.getRowCount()>0);

    }

    @When("I execute query to find most popular book genre DK")
    public void i_execute_query_to_find_most_popular_book_genre_dk() {
        String query = "select c.name as category, count(*) as popularity\n" +
                "from book_borrow borr\n" +
                "join books b on borr.book_id = b.id\n" +
                "join book_categories c on b.book_category_id = c.id\n" +
                "group by category\n" +
                "order by popularity DESC";
        DB_Util.runQuery(query);
        mostPopularCategory = DB_Util.getFirstRowFirstColumn();
    }
    @Then("verify {string} is the most popular book genre DK.")
    public void verify_is_the_most_popular_book_genre_dk(String string) {
        Assert.assertEquals("Verification of the most popular category failed", string, mostPopularCategory);
    }
}
