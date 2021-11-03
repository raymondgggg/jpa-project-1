package model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class representation of books
 */
@Entity

@Table(uniqueConstraints = {@UniqueConstraint(columnNames =
        {"title", "publisher_name"}),
        @UniqueConstraint(columnNames =
                {"title", "authoring_entity_name"})})

@NamedNativeQuery(
        name="bookTitles",
        query = "SELECT title " +
                "FROM   BOOKS",
        resultClass = Books.class
)


@NamedNativeQuery(
        name="bookIsbn",
        query = "SELECT * " +
                "FROM   BOOKS " +
                "WHERE  isbn = ? ",
        resultClass = Books.class
)
@NamedNativeQuery(
        name= "displayAllBooks",
        query = "SELECT * " +
                "FROM   BOOKS",
        resultClass = Books.class
)
@NamedNativeQuery(
        name="findBook",
        query = "SELECT * " +
                "FROM   BOOKS " +
                "WHERE  TITLE = ? ",
        resultClass = Books.class
)
@NamedNativeQuery(
        name="findBook",
        query = "SELECT * " +
                "FROM   BOOKS " +
                "WHERE  TITLE = ? ",
        resultClass = Books.class
)





public class Books {
    /** id of book */
    @Id
    @Column(nullable = false, length = 17)
    private String isbn;

    /** The Title of the Book */
    @Column(nullable = false, length = 80)
    private String title;

    /** The Year the Book was published */
    @Column(nullable = false, length = 4)
    private int year_published;

    /** name of publisher*/
    @ManyToOne
    @JoinColumn(name = "publisher_name", referencedColumnName = "name")
    private Publishers publisher_name;

    /** name of authoring entity*/
    @ManyToOne
    @JoinColumn(name = "authoring_entity_name")
    private Authoring_Entities authoring_entity_name;

    /**
     * Empty constructor
     */
    public Books(){
    }

    /**
     * constructor
     * @param isbn
     * @param title
     * @param year_published
     * @param publisher_name
     * @param authoring_entity_name
     */
    public Books(String isbn, String title, int year_published, Publishers publisher_name, Authoring_Entities authoring_entity_name) {
        this.isbn = isbn;
        this.title = title;
        this.year_published = year_published;
        this.publisher_name = publisher_name;
        this.authoring_entity_name = authoring_entity_name;
    }

    /**
     * getter method
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter method
     * @return int year_published
     */
    public int getYear_published() {
        return year_published;
    }

    /**
     * getter method
     * @return Publishers publisher_name
     */
    public Publishers getPublisher_name() {
        return publisher_name;
    }

    /**
     * getter method
     * @return Authoring_Entities authoring_entity_name
     */
    public Authoring_Entities getAuthoring_entity_name() {
        return authoring_entity_name;
    }

    /**
     * setter method
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * setter method
     * @param year_published
     */
    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    /**
     * getter method
     * @return String isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * setter method
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * setter method
     * @param publisher
     */
    public void setPublisher_name(Publishers publisher) {
        this.publisher_name = publisher;
    }

    /**
     * setter method
     * @param authoringEntity
     */
    public void setAuthoring_entity_name(Authoring_Entities authoringEntity) {
        this.authoring_entity_name = authoringEntity;
    }

    /**
     * Method that returns string representation of object
     * @return String
     */
    @Override
    public String toString() {
        return   isbn + "  \t  " +
                 title + "  \t   " +
                 year_published + "  \t  " +
                 this.getAuthoring_entity_name() + "  \t  " +
                 this.getPublisher_name()  ;

    }

    /**
     * method to determine if two objects are the same.
     * @param o
     * @return boolean value if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return year_published == books.year_published && isbn.equals(books.isbn) && title.equals(books.title) && publisher_name.equals(books.publisher_name) && authoring_entity_name.equals(books.authoring_entity_name);
    }

    /**
     * Method to get the hashcode of object
     * @return int hashcode of object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, year_published, publisher_name, authoring_entity_name);
    }
} //End of Books Class
