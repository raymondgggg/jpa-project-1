package csulb.cecs323.model;

import javax.persistence.*;
import java.util.*;

/**
 * A group of random people that meet to form a team and work together to achieve a goal,
 * in this case, writing a book.
 */
@Entity
@DiscriminatorValue("Ad Hoc Teams")

@NamedNativeQuery(
        name = "ReturnAllTeamNames",
        query = "SELECT name " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Ad Hoc Teams'",
        resultClass = Ad_hoc_teams.class
)
@NamedNativeQuery(
        name = "ReturnAllTeamInfo",
        query = "SELECT * " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Ad Hoc Teams'",
        resultClass = Ad_hoc_teams.class
)
@NamedNativeQuery(
        name = "ReturnAllTeamEmail",
        query = "SELECT email " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Ad Hoc Teams' and email = ?",
        resultClass = Individual_author.class
)
public class Ad_hoc_teams extends Authoring_Entities{
    /**
     * The list of individuals that are a part of the team.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Ad_hoc_teams_member",
            joinColumns = @JoinColumn(name = "ad_hoc_teams_email"),
            inverseJoinColumns = @JoinColumn(name = "individual_authors_email")
    )
    private List<Individual_author> individualAuthors;

    /**
     * The constructor of the Ad_hoc_teams class. Creates an Ad_hoc_teams object.
     * @param email                 The email of the team.
     * @param name                  The name of the team.
     * @param individual_authors    The list of individual authors that are on the team.
     */
    public Ad_hoc_teams(String email, String name, List<Individual_author> individual_authors) {
        super(email, name);
        this.setIndividualAuthors(individual_authors);
    }

    /**
     * The default constructor of the Ad_hoc_teams class.
     */
    public Ad_hoc_teams() {
    }

    public List<Individual_author> getIndividual_authors() {
        return individualAuthors;
    }

    public void setIndividualAuthors(List<Individual_author> individualAuthors) {
        this.individualAuthors = individualAuthors;
    }

    /**
     * Adds an individual author to the team.
     * @param individualAuthor      The individual author that we want to add to the team.
     */
    public void add_individual_authors(Individual_author individualAuthor){
        if(! this.individualAuthors.contains(individualAuthor)){
            this.individualAuthors.add(individualAuthor);
            individualAuthor.add_team(this);
        }
    }

    /**
     * Removes an individual author from the team.
     * @param individual_author     The individual author that we want to remove from the team.
     */
    public void remove_individual_authors(Individual_author individual_author){
        if(this.individualAuthors.contains(individual_author)){
            this.individualAuthors.remove(individual_author);
            individual_author.remove_team(this);
        }
    }

    @Override
    public String toString() {
        return "Ad Hoc Team Name: " + this.getName() + " Ad Hoc Team Email: " + this.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ad_hoc_teams that = (Ad_hoc_teams) o;
        return Objects.equals(individualAuthors, that.individualAuthors);
    }
}

//import javax.persistence.*;
//
//@Entity
//public class Ad_Hoc_Team_Member{
//
//    @Id
//    @OneToOne(fetch = FetchType.LAZY)
//    @PrimaryKeyJoinColumn
//    private Authoring_Entities ad_hoc_teams_email;
//
//    @Id
//    @OneToOne(fetch = FetchType.LAZY)
//    @PrimaryKeyJoinColumn
//    private Authoring_Entities individual_authors_email;
//
//
//}
