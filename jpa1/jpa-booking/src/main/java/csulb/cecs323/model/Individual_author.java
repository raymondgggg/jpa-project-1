package csulb.cecs323.model;

import javax.persistence.*;
import java.util.*;

/**
 * A single person that writes a book.
 */
@Entity
@DiscriminatorValue("Individual Authors")

@NamedNativeQuery(
        name = "ReturnAllIndividualName",
        query = "SELECT name " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Individual Authors'",
        resultClass = Individual_author.class
)

@NamedNativeQuery(
        name = "ReturnAllIndividualInfo",
        query = "SELECT * " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Individual Authors'",
        resultClass = Individual_author.class
)

@NamedNativeQuery(
        name = "ReturnAllIndividualEmail",
        query = "SELECT email " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Individual Authors' and email = ?",
        resultClass = Individual_author.class
)
public class Individual_author extends Authoring_Entities{
    /**
     * A team of random people that work together on writing a book.
     */
    @ManyToMany(mappedBy = "individualAuthors",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ad_hoc_teams> ad_hoc_teams;

    /**
     * The constructor of the Individual_author class. Creates an Individual_author object.
     * @param email             The email of the individual author.
     * @param name              The name of the individual author.
     * @param ad_hoc_teams      The team that the individual author belongs to.
     */
    public Individual_author(String email, String name, List<Ad_hoc_teams> ad_hoc_teams) {
        super(email, name);
        this.setAd_hoc_teams(ad_hoc_teams);
    }

    /**
     * The default constructor of the Individual_author class.
     */
    public Individual_author() {
    }

    public List<Ad_hoc_teams> getAd_hoc_teams() {
        return ad_hoc_teams;
    }

    public void setAd_hoc_teams(List<Ad_hoc_teams> ad_hoc_teams) {
        this.ad_hoc_teams = ad_hoc_teams;
    }

    /**
     * Add an individual author to an Ad Hoc Team.
     * @param team      The team of people that we want the individual to be added to.
     */
    public void add_team (Ad_hoc_teams team){
        if(! this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.add(team);
            team.add_individual_authors(this);
        }
    }

    /**
     * Remove the individual author from an Ad Hoc Team.
     * @param team      The team of people that we want the individual removed from.
     */
    public void remove_team (Ad_hoc_teams team){
        if(this.ad_hoc_teams.contains(team)){
            this.ad_hoc_teams.remove(team);
            team.remove_individual_authors(this);
        }
    }

    @Override
    public String toString() {
        return "Individual Author Name: " + this.getName() + " Individual Author Email: " + this.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Individual_author that = (Individual_author) o;
        return Objects.equals(ad_hoc_teams, that.ad_hoc_teams);
    }
}