package model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@NamedNativeQuery(
        name = "returnTeam",
        query = "SELECT * " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Ad Hoc Teams'",
        resultClass = Ad_Hoc_Teams.class
)
@NamedNativeQuery(
        name = "ReturnEmail",
        query = "SELECT email " +
                "FROM   Authoring_Entities " +
                "WHERE authoring_entity_type = 'Ad Hoc Teams' and email = ?",
        resultClass = Individual_Authors.class
)
/**
 * Class representation of team of authors
 */
@Entity
@DiscriminatorValue("Ad Hoc Teams")
public class Ad_Hoc_Teams extends Authoring_Entities{

    /**
     * List of authors in the team
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "AD_HOC_TEAMS_MEMBER", joinColumns = @JoinColumn(name = "AD_HOC_TEAMS_EMAIL"), inverseJoinColumns = @JoinColumn(name = "INDIVIDUAL_AUTHOR_EMAIL"))
    private List<Individual_Authors> individual_authorsList;

    /** Default constructor */
    public Ad_Hoc_Teams(){}

    /**
     * Constructor
     * @param email
     * @param name
     * @param individual_authorsList
     */
    public Ad_Hoc_Teams(String email, String name, List<Individual_Authors> individual_authorsList) {
        super(email, name);
        this.individual_authorsList = individual_authorsList;
    }

    public List<Individual_Authors> getIndividual_authorsList() {
        return individual_authorsList;
    }

    public void setIndividual_authorsList(List<Individual_Authors> individual_authorsList) {
        this.individual_authorsList = individual_authorsList;
    }

    /**
     * Method to remove author from team association
     * @param author
     */
    public void removeAuthor(Individual_Authors author){
        if (this.individual_authorsList.contains(author) == true){
            this.individual_authorsList.remove(author);
            author.removeTeam(this);
        }
    }

    /**
     * Method to add author to team association
     * @param author
     */
    public void addAuthor(Individual_Authors author){
        if (this.individual_authorsList.contains(author) == false){
            this.individual_authorsList.add(author);
            author.appenedTeam(this);
        }
    }
}