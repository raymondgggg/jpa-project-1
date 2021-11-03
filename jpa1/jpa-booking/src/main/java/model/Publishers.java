package model;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**Class representation of the publishers*/
@Entity

@NamedNativeQuery(
        name="publishers",
        query = "SELECT name"+
                "FROM   Publishers"
)
@NamedNativeQuery(
        name="publisherNames",
        query = "SELECT name " +
                "FROM   PUBLISHERS"
)

@NamedNativeQuery(
        name= "displayAllPublishers",
        query = "SELECT * " +
                "FROM   PUBLISHERS",
        resultClass = Publishers.class
)

@NamedNativeQuery(
        name="findPublisher",
        query = "SELECT * " +
                "FROM   PUBLISHER " +
                "WHERE  NAME  = ? ",
        resultClass = Publishers.class
)
public class Publishers {

    /**
     * ID
     */
    @Id
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    /**
     * contact info
     */
    @Column(nullable = false, length = 80, unique = true)
    private String email;

    /**
     * number
     */
    @Column(nullable = false, length = 24, unique = true)
    private String phone;

    /**
     * list of books the publisher has published
     */
    @OneToMany(mappedBy = "publisher_name", cascade = CascadeType.PERSIST)
    private List<Books> booksList;

    /**
     * empty constructor
     */
    public Publishers() {
    }

    /**
     * constructor to initialize object
     * @param name
     * @param email
     * @param phone
     */
    public Publishers(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * getter method for email field
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter method for email field
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter method for phone field
     * @return String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter method for phone field
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter method for name field
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     *  setter method for name field
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     *  method that returns string representation of object
     * @return String representation of object
     */
    @Override
    public String toString() {
        return  name + "             " +
                email + "             " +
                phone ;
    }

    /**
     * method to determine if two objects are the same
     * @param o object that will be used to compare
     * @return boolean value regarding whether the objects are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publishers that = (Publishers) o;
        return name.equals(that.name) && email.equals(that.email) && phone.equals(that.phone);
    }

    /**
     *  method to return the hash code of object
     * @return int hashcode of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone);
    }
}
