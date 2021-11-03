package csulb.cecs323.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * A person or company that prepares and issues the books for sale.
 */
@Entity
@NamedNativeQuery(
        name = "ReturnAllPublishers",
        query = "SELECT * FROM Publishers",
        resultClass = Publishers.class
)
@NamedNativeQuery(
        name = "ReturnAllPublisherNames",
        query = "SELECT name " +
                "FROM Publishers " +
                "WHERE name = ?",
        resultClass = Publishers.class
)
public class Publishers {
    /**
     * The name of the company or individual that prepares the books for sale. Limited to 80 characters.
     */
    @Id
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    /**
     * The email of the publisher. Limited to 80 characters.
     */
    @Column(length = 80, nullable = false, unique = true)
    private String email;

    /**
     * The phone number of the publisher. Limited to 24 characters.
     */
    @Column(length = 24, nullable = false, unique = true)
    private String phone;

    /**
     * The list of books that the publisher has published.
     */
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.PERSIST)
    private List<Books> books;

    /**
     * The constructor of the Publishers class. Creates a Publishers object.
     * @param name      The name of the publisher.
     * @param email     The email of the publisher.
     * @param phone     The phone number of the publisher.
     */
    public Publishers(String name, String email, String phone) {
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
    }

    /**
     * Default constructor of the Publisher class.
     */
    public Publishers() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "name: " + this.getName() +
                ", email: " + this.getEmail() +
                ", phone: " + this.getPhone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publishers that = (Publishers) o;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(books, that.books);
    }
}