
Feature: Books module
  As a librarian, I should be able to add new book into library

  Scenario Outline: Verify added book is matching with DB
    Given the "librarian" on the home page
    And the user navigates to "Books" page
    When the librarian click to add book
    And the librarian enter book name "<Book Name>"
    When the librarian enter ISBN "<ISBN>"
    And the librarian enter year "<Year>"
    When the librarian enter author "<Author>"
    And the librarian choose the book category "<Book Category>"
    And the librarian click to save changes
    Then verify "The book has been created" message is displayed
    And verify "<Book Name>" information must match with DB
    Examples:
      | Book Name             | ISBN     | Year | Author          | Book Category        |
      | Head First Java       | 10112021 | 2021 | Kathy Sierra    | Action and Adventure |
      | The Scrum Field Guide | 11112021 | 2006 | Mitch Lacey     | Short Story          |

  @DK @db @ui @us5
  Scenario Outline: Verify added book is matching with DB DK
    Given the "librarian" on the home page DK
    And the user navigates to "Books" page DK
    When the librarian click to add book DK
    And the librarian enter book name "<Book Name>" DK
    When the librarian enter ISBN "<ISBN>" DK
    And the librarian enter year "<Year>" DK
    When the librarian enter author "<Author>" DK
    And the librarian choose the book category "<Book Category>" DK
    And the librarian click to save changes DK
    Then verify "The book has been created" message is displayed DK
    And verify "<Book Name>" information must match with DB DK
    Examples:
      | Book Name             | ISBN     | Year | Author          | Book Category        |
      | Head First Java DK       | 10112021 | 2021 | Kathy Sierra    | Action and Adventure |
      | The Scrum Field Guide DK | 11112021 | 2006 | Mitch Lacey     | Short Story          |