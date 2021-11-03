package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.*;

/**
 * A physical object that contains written or printed literary work.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames =
        {"title", "publisher_name"}),
        @UniqueConstraint(columnNames =
                {"title", "authoring_entity_name"})})

@NamedNativeQuery(
        name = "ReturnAllBooks",
        query = "SELECT * FROM BOOKS",
        resultClass = Books.class
)
@NamedNativeQuery(
        name = "ReturnAllBookISBN",
        query = "SELECT ISBN " +
                "FROM BOOKS " +
                "WHERE ISBN = ?",
        resultClass = Books.class
)
public class Books {
    /**
     * A unique ID that helps identify a book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 17, nullable = false)
    private String ISBN;

    /**
     * The title of the book.
     */
    @Column(length = 80, nullable = false)
    private String title;

    /**
     * The year that the book was published.
     */
    @Column(length = 4, nullable = false)
    private int year_published;

    /**
     * The name of the publisher that published the book.
     */
    @ManyToOne
    @JoinColumn(name = "publisher_name", referencedColumnName = "name")
    private Publishers publisher;

    /**
     * The name of the authoring entity that wrote the book.
     */
    @OneToOne
    @JoinColumn(name = "authoring_entity_name", referencedColumnName = "name")
    private Authoring_Entities authoringEntity;

    /**
     * The constructor of the Books class. Creates a book object.
     * @param isbn              The ID of the book;
     * @param title             The title of the book
     * @param yearPublished     The year that the book was published.
     * @param publisher         The publisher of the book.
     * @param authoringEntity   The authoring entity that wrote the book.
     */
    public Books(String isbn, String title, int yearPublished, Publishers publisher, Authoring_Entities authoringEntity) {
        this.setISBN(isbn);
        this.setTitle(title);
        this.setYear_published(yearPublished);
        this.setPublisher(publisher);
        this.setAuthoringEntity(authoringEntity);
    }

    /**
     * The default constructor of the Books class.
     */
    public Books(){
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public Publishers getPublisher() {
        return publisher;
    }

    public void setPublisher(Publishers publisher) {
        this.publisher = publisher;
    }

    public Authoring_Entities getAuthoringEntity() {
        return authoringEntity;
    }

    public void setAuthoringEntity(Authoring_Entities authoringEntity) {
        this.authoringEntity = authoringEntity;
    }

    @Override
    public String toString() {
        return "ISBN: " + this.getISBN() +
                ", title: " + this.getTitle() +
                ", year_published: " + this.getYear_published() +
                ", publisher: " + this.getPublisher() +
                ", authoringEntity: " + this.getAuthoringEntity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return ISBN == books.ISBN && year_published == books.year_published && Objects.equals(title, books.title) && Objects.equals(publisher, books.publisher) && Objects.equals(authoringEntity, books.authoringEntity);
    }
}
