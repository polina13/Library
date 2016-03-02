import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Patron {
  private int id;
  private String name;
  private int copy_id;


  public int getId() {
      return id;
    }

  public String getName() {
    return name;
  }

  public int getCopyId() {
    return copy_id;
  }


  public Patron(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName()) &&
              this.getCopyId() == newPatron.getCopyId() &&
              this.getId() == newPatron.getId();

    }
  }

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons(name, copy_id) VALUES (:name, :copy_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("copy_id", copy_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons where id=:id";
      Patron patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }
}
