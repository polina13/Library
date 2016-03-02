import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Book {
  private int id;
  private String title;


  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }


  public Book(String title) {
    this.title = title;
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  @Override
  public boolean equals(Object otherBook){
    if(!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books(title) VALUES (:title)";
      this.id= (int) con.createQuery(sql, true).addParameter("title", this.title).executeUpdate().getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void update(String title) {
    this.title = title;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title = :title WHERE id= :id";
      con.createQuery(sql).addParameter("title", title).addParameter("id", id).executeUpdate();
    }
  }

  public void addAuthor(Author author) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
    con.createQuery(sql)
      .addParameter("book_id", this.getId())
      .addParameter("author_id", author.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT DISTINCT author_id FROM books_authors WHERE book_id = :book_id";
      List<Integer> authorIds = con.createQuery(sql)
        .addParameter("book_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Author> authors = new ArrayList<Author>();

      for (Integer authorId : authorIds) {
        String authorQuery = "SELECT * FROM authors WHERE id = :authorId";
        Author author = con.createQuery(authorQuery)
          .addParameter("authorId", authorId)
          .executeAndFetchFirst(Author.class);
          authors.add(author);
      }
    return authors;
    }
  }


    public static List<Book> bookSearch(String bookTitle) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE title LIKE :bookTitle";
          return con.createQuery(sql)
          .addParameter("bookTitle", bookTitle)
          .executeAndFetch(Book.class);
      }
    }


  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM books_authors WHERE book_id = :bookId";
      con.createQuery(joinDeleteQuery)
        .addParameter("bookId", this.getId())
        .executeUpdate();
    }
  }
}
