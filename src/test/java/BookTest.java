import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBookesAreEqual() {
    Book firstBook = new Book("book1", 1);
    Book secondBook = new Book("book1", 1);
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_savesIntoDatabase() {
    Book myBook = new Book("book1", 1);
    myBook.save();
    assertTrue(Book.all().get(0).equals(myBook));
  }

  @Test
  public void find_findBookInDatabase_true() {
    Book myBook = new Book("book1", 1);
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void addAuthor_addsAuthorToBook() {
    Book myBook = new Book("book1", 1);
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myBook.addAuthor(myAuthor);
    Author savedAuthor = myAuthor.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void getAuthors_returnsAllAuthors_ArrayList() {
    Book myBook = new Book("book1", 1);
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);
  }

  @Test
  public void delete_deletesAllTasksAndListsAssoicationes() {
    Book myBook = new Book("book1", 1);
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myBook.addAuthor(myAuthor);
    myBook.delete();
    assertEquals(myAuthor.getBooks().size(), 0);
  }

  @Test
  public void testsTitleSearch() {
    Book myBook = new Book("book1", 1);
    myBook.save();

    Book myBook2 = new Book("book2", 1);
    myBook2.save();

    String bookName = "book1";
    List titlesFound = Book.bookSearch(bookName);

    assertEquals(titlesFound.size(), 1);
    assertEquals(titlesFound.get(0), myBook);
  }

  @Test
  public void testsAuthorSearch() {
    Book myBook21 = new Book("book21", 1);
    myBook21.save();

    Book myBook4 = new Book("book4", 1);
    myBook4.save();

    String authorName = "Joe";
    List authorsFound = Book.authorSearch(authorName);

    assertEquals(authorsFound.size(), 1);
    assertEquals(authorsFound.get(0), myBook21);
  }
}
