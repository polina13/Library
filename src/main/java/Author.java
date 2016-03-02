import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Author {

  private int id;
  private String name;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Author(String name) {
  this.name = name;
}

// public List<Author> getAuthors() {
//   try(Connection con = DB.sql2o.open()) {
//     String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) WHERE books.id = :book_id;";
//       return con.createQuery(sql)
//       .addParameter("book_id", this.getId())
//       .executeAndFetch(Author.class);
//   }
// }


@Override
public boolean equals(Object otherAuthor){
  if (!(otherAuthor instanceof Author)) {
    return false;
  } else {
    Author newAuthor = (Author) otherAuthor;
    return this.getName().equals(newAuthor.getName()) &&
           this.getId() == newAuthor.getId();
  }
}

  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors where id=:id";
      Author author = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return author;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
      .addParameter("author_id",this.getId())
      .addParameter("book_id", book.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT DISTINCT book_id FROM books_authors WHERE author_id = :author_id";
      List<Integer> bookIds = con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Book> books = new ArrayList<Book>();

      for (Integer bookId : bookIds) {
          String authorQuery = "Select * From books WHERE id = :bookId";
          Book book = con.createQuery(authorQuery)
            .addParameter("bookId", bookId)
            .executeAndFetchFirst(Book.class);
          books.add(book);
      }
      return books;
    }
  }

  public static List<Author> authorSearch(String name) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM authors WHERE name LIKE :name";
        return con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetch(Author.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM authors WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM books_authors WHERE author_id = :authorId";
      con.createQuery(joinDeleteQuery)
        .addParameter("authorId", this.getId())
        .executeUpdate();
    }
  }
}
