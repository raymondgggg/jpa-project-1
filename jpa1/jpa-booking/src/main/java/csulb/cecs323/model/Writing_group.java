package csulb.cecs323.model;

import javax.persistence.*;
import java.util.*;

/**
 * A group of people that work together in order to write a book.
 */
@Entity
@DiscriminatorValue("Writing Groups")
@NamedNativeQuery(
        name = "ReturnAllWritingGroups",
        query = "SELECT * " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Writing Groups'",
        resultClass = Writing_group.class
)

@NamedNativeQuery(
        name = "ReturnAllWritingNames",
        query = "SELECT name " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Writing Groups'",
        resultClass = Writing_group.class
)

@NamedNativeQuery(
        name = "ReturnAllGroupEmail",
        query = "SELECT email " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Writing Groups' and email = ?",
        resultClass = Individual_author.class
)
public class Writing_group extends Authoring_Entities {

    /**
     * A person who leads the group members. Limited to 80 characters.
     */
    @Column(length = 80)
    private String head_writer;

    /**
     * The year that the group was formed. Limited to 4 characters.
     */
    @Column(length = 4)
    private int year_formed;

    /**
     * The constructor of the Writing_groups class. Creates the Writing_groups object.
     * @param name          The name of the writing group.
     * @param email         The email of the writing group.
     * @param headWriter    The name of the person that is the leader of the group.
     * @param year_formed   The year that the group was formed.
     */
    public Writing_group(String email, String name, String headWriter, int year_formed) {
        super(email, name);
        this.setHead_writer(headWriter);
        this.setYear_formed(year_formed);
    }

    /**
     * The default constructor of the Writing_group class.
     */
    public Writing_group(){
    }

    public String getHead_writer() {
        return head_writer;
    }

    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    public int getYear_formed() {
        return year_formed;
    }

    public void setYear_formed(int year_formed) {
        this.year_formed = year_formed;
    }

    @Override
    public String toString() {
        return  "team_name: " + this.getName() +
                " team_email: " + this.getEmail() +
                " head_writer: " + this.getHead_writer() +
                ", year_formed: " + this.getYear_formed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Writing_group that = (Writing_group) o;
        return year_formed == that.year_formed && Objects.equals(head_writer, that.head_writer);
    }
}