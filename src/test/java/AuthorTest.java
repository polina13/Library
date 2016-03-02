import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Author firstAuthor = new Author("Jimmy");
    Author secondAuthor = new Author("Jimmy");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Author myAuthor = new Author("Jimmy");
    myAuthor.save();
    Author savedAuthor = Author.all().get(0);
    assertTrue(savedAuthor.equals(myAuthor));
  }

  @Test
  public void save_assignsIdToObject() {
    Author myAuthor = new Author("Jimmy");
    myAuthor.save();
    Author savedAuthor = Author.all().get(0);
    assertEquals(myAuthor.getId(), savedAuthor.getId());
  }
  @Test
  public void find_findsAuthorInDatabase_true() {
    Author myAuthor = new Author("Jimmy");
    myAuthor.save();
    Author savedAuthor = Author.find(myAuthor.getId());
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void addBook_addsBookToAuthor() {
    Book myBook = new Book("book1");
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myAuthor.addBook(myBook);
    Book savedBook = myAuthor.getBooks().get(0);
    assertTrue(myBook.equals(savedBook));
}

  @Test
  public void getBooks_returnsAllBooks_List() {
    Book myBook = new Book("book1");
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myAuthor.addBook(myBook);
    List savedBooks = myAuthor.getBooks();
    assertEquals(savedBooks.size(), 1);
  }

  @Test
  public void delete_deletesAllAuthorsAndBookAssociations() {
    Book myBook = new Book("book1");
    myBook.save();

    Author myAuthor = new Author("Jimmy");
    myAuthor.save();

    myAuthor.addBook(myBook);
    myAuthor.delete();
    assertEquals(myAuthor.getBooks().size(), 0);
  }
}
