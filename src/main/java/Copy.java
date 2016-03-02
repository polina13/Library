import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Copy {
  private int id;
  private int book_id;
  private String checkout_date;
  private String due_date;

  public int getId() {
      return id;
    }

  public int getBookId() {
    return book_id;
  }

  public String getCheckoutDate() {
    return checkout_date;
  }

  public String getDueDate() {
    return due_date;
  }

  public Copy(int book_id, String checkout_date, String due_date) {
    this.book_id = book_id;
    this.checkout_date = checkout_date;
    this.due_date = due_date;
  }

  @Override
  public boolean equals(Object otherCopy){
    if (!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return this.getBookId() == newCopy.getBookId() &&
              this.getCheckoutDate().equals(newCopy.getCheckoutDate()) &&
              this.getDueDate().equals(newCopy.getDueDate()) &&
              this.getId() == newCopy.getId();

    }
  }

  public static List<Copy> all() {
    String sql = "SELECT * FROM copies";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies(book_id, checkout_date, due_date) VALUES (:book_id, :checkout_date, :due_date)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("book_id", book_id)
        .addParameter("checkout_date", checkout_date)
        .addParameter("due_date", due_date)
        .executeUpdate()
        .getKey();
    }
  }

  public static Copy find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies where id=:id";
      Copy copy = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Copy.class);
      return copy;
    }
  }

  public void addPatron(Patron patron) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies_patrons (copy_id, patron_id) VALUES (:copy_id, :patron_id)";
      con.createQuery(sql)
      .addParameter("copy_id",this.getId())
      .addParameter("patron_id", patron.getId())
      .executeUpdate();
    }
  }


  public List<Patron> getPatrons() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT patrons.* FROM copies JOIN copies_patrons ON (copies.id = copies_patrons.copy_id) JOIN patrons ON (copies_patrons.patron_id = patrons.id) WHERE copies.id = :copy_id;";
        return con.createQuery(sql)
        .addParameter("copy_id", this.getId())
        .executeAndFetch(Patron.class);
    }
  }
}
