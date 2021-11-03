package csulb.cecs323.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * The individual or group that is responsible for creating a book.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "authoring_entity_type", discriminatorType = DiscriminatorType.STRING)

@NamedNativeQuery(
        name = "ReturnAllAuthoringEntities",
        query = "SELECT * FROM Authoring_Entities",
        resultClass = Authoring_Entities.class
)
@NamedNativeQuery(
        name = "ReturnAllEmails",
        query = "SELECT * " +
                "FROM Authoring_Entities " +
                "WHERE email = ?",
        resultClass = Authoring_Entities.class
)

public class Authoring_Entities {

    /**
     * The email that the authoring entity uses. Limited to 30 characters.
     */
    @Id
    @Column(length = 30, nullable = false)
    private String email;

    /**
     * The name of the authoring enitity. Limited to 80 characters.
     */
    @Column(length = 80, nullable = false)
    private String name;



    /**
     * The constructor for the Autoring_entities class. Creates an Authoring_entities object.
     * @param email     The email of the authoring entity.
     * @param name      The name of the authoring entity.
     */
    public Authoring_Entities(String email, String name) {
        this.setEmail(email);
        this.setName(name);
    }

    /**
     * Default constructor of the Authoring_entities class.
     */
    public Authoring_Entities() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Authoring Email: " + this.getEmail() +
                " Name: " + this.getName();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authoring_Entities that = (Authoring_Entities) o;
        return Objects.equals(email, that.email) && Objects.equals(name, that.name);
    }
}
