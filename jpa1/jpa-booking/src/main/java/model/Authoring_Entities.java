package model;

import javax.persistence.*;

/**
 * Class representation of Authoring_Entities
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "authoring_entities_type", discriminatorType = DiscriminatorType.STRING)
@NamedNativeQuery(
        name="authoring",
        query = "SELECT email " +
                "FROM    Authoring_Entities"
)
@NamedNativeQuery(
        name= "allAuth",
        query = "SELECT * " +
                "FROM Authoring_Entities " +
                "WHERE EMAIL = ?" ,
        resultClass = Authoring_Entities.class
)
@NamedNativeQuery(
        name= "displayAllAuthoringEntities",
        query = "SELECT * " +
                "FROM Authoring_Entities " ,
        resultClass = Authoring_Entities.class
)

@NamedNativeQuery(
        name="findAuthoringEntity",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE  EMAIl = ? ",
        resultClass = Authoring_Entities.class
)


public class Authoring_Entities {

    /** email of entity*/
    @Id
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    /** name of entity*/
    @Column(nullable = false, length = 80)
    private String name;

    /** default constructor*/
    public Authoring_Entities() {
    }

    /**
     * Constructor
     * @param email
     * @param name
     */
    public Authoring_Entities(String email, String name) {
        this.email = email;
        this.name = name;

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

    /**
     * get string representation of object
     * @return String
     */
    @Override
    public String toString(){
        return  this.email + "         "+
                name + "      "
                 ;
    }
}
