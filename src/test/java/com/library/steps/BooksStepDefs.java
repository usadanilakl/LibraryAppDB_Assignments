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
import java.util.Map;

public class BooksStepDefs {
    BookPage bookPage = new BookPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    List<String> actualCategoryOptions = new ArrayList<>();
    String searchedBook;
    String mostPopularCategory;
    String newBookName, newIsbn, newYear, newAuthor, newBookCategory;
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

    @When("the librarian click to add book DK")
    public void the_librarian_click_to_add_book_dk() {
        wait.until(ExpectedConditions.elementToBeClickable(bookPage.addBook));
        bookPage.addBook.click();
    }
    @When("the librarian enter book name {string} DK")
    public void the_librarian_enter_book_name_dk(String bookName) {
        wait.until(ExpectedConditions.visibilityOf(bookPage.bookName));
        newBookName = bookName;
        bookPage.bookName.sendKeys(newBookName);
    }
    @When("the librarian enter ISBN {string} DK")
    public void the_librarian_enter_isbn_dk(String ISBN) {
        newIsbn = ISBN;
        bookPage.isbn.sendKeys(newIsbn);
    }
    @When("the librarian enter year {string} DK")
    public void the_librarian_enter_year_dk(String year) {
        newYear = year;
        bookPage.year.sendKeys(newYear);
    }
    @When("the librarian enter author {string} DK")
    public void the_librarian_enter_author_dk(String author) {
        newAuthor = author;
        bookPage.author.sendKeys(newAuthor);
    }
    @When("the librarian choose the book category {string} DK")
    public void the_librarian_choose_the_book_category_dk(String category) {
        newBookCategory = category;
        Select select = new Select(bookPage.categoryDropdown);
        select.selectByVisibleText(newBookCategory);
    }
    @When("the librarian click to save changes DK")
    public void the_librarian_click_to_save_changes_dk() {
        wait.until(ExpectedConditions.elementToBeClickable(bookPage.saveChanges));
        bookPage.saveChanges.click();
    }
    @Then("verify {string} message is displayed DK")
    public void verify_message_is_displayed_dk(String message) {
        wait.until(ExpectedConditions.visibilityOf(bookPage.toastMessage));
        Assert.assertEquals("message failed", message, bookPage.toastMessage.getText());
    }
    @Then("verify {string} information must match with DB DK")
    public void verify_information_must_match_with_db_dk(String string) {
        String query = "select * from books\n" +
                "where name='"+newBookName+"';";
        DB_Util.runQuery(query);
        Map<String, String> bookInfo = DB_Util.getRowMap(1);
        Assert.assertEquals("name failed: ", newBookName, bookInfo.get("name"));
        Assert.assertEquals("isbn failed: ", newIsbn, bookInfo.get("isbn"));
        Assert.assertEquals("year failed: ", newYear, bookInfo.get("year"));
        Assert.assertEquals("author failed: ", newAuthor, bookInfo.get("author"));
    }
}
