import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCopiesAreEqual() {
    Copy firstCopy = new Copy(1, "01/01/2016", "01/01/2017");
    Copy secondCopy = new Copy(1,"01/01/2016", "01/01/2017" );
    assertTrue(firstCopy.equals(secondCopy));
  }

  @Test
  public void save_savesIntoDatabase() {
    Copy myCopy = new Copy(1,"01/01/2016", "01/01/2017");
    myCopy.save();
    assertTrue(Copy.all().get(0).equals(myCopy));
  }

  @Test
  public void find_findCopyInDatabase_true() {
    Copy myCopy = new Copy(1,"01/01/2016", "01/01/2017");
    myCopy.save();
    Copy savedCopy = Copy.find(myCopy.getId());
    assertTrue(myCopy.equals(savedCopy));
  }

  @Test
  public void addPatron_addPatronToCopy() {
    Copy myCopy = new Copy(1,"01/01/2016", "01/01/2017");
    myCopy.save();

    Patron myPatron = new Patron("Jimmy");
    myPatron.save();
    myCopy.addPatron(myPatron);
    List savedPatrons = myCopy.getPatron();
    assertEquals(savedPatrons.size(), 1);
  }

  @Test
  public void getPatrons_returnsAllPatrons_ArrayList() {
    Copy myCopy = new Copy(1,"01/01/2016", "01/01/2017");
    myCopy.save();

    Patron myPatron = new Patron("Jimmy");
    myPatron.save();

    myCopy.addPatron(myPatron);
    assertEquals(myCopy.getPatron().size(), 1);
  }
}
