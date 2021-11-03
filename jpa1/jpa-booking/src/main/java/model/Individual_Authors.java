package model;


import javax.persistence.*;
import java.util.List;

/**
 * Class representation of Individual author.
 */
@Entity
@DiscriminatorValue("Individual Authors")
public class Individual_Authors extends Authoring_Entities{
    /**
     * List of teams the author is part of
     */
    @ManyToMany(mappedBy = "",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ad_Hoc_Teams> ad_hoc_teamsList;

    /**
     * Default Constructor
     */
    public Individual_Authors() {

    }

    /**
     * Constructor
     * @param email of author
     * @param name of author
     * @param ad_hoc_teamsList List of teams the author is part of
     */
    public Individual_Authors(String email, String name, List<Ad_Hoc_Teams> ad_hoc_teamsList) {
        super(email, name);
        this.ad_hoc_teamsList = ad_hoc_teamsList;
    }

    public List<Ad_Hoc_Teams> getAd_hoc_teamsList() {
        return ad_hoc_teamsList;
    }

    public void setAd_hoc_teamsList(List<Ad_Hoc_Teams> ad_hoc_teamsList) {
        this.ad_hoc_teamsList = ad_hoc_teamsList;
    }

    /**
     * Method to add team association for author
     * @param t team
     */
    public void appenedTeam(Ad_Hoc_Teams t){
        if (this.ad_hoc_teamsList.contains(t) == false){
            this.ad_hoc_teamsList.add(t);
            t.addAuthor(this);
        }
    }

    /**
     * Method to remove team association from author
     * @param t team
     */
    public void removeTeam(Ad_Hoc_Teams t){
        if (this.ad_hoc_teamsList.contains(t) == true){
            this.ad_hoc_teamsList.remove(t);
            t.removeAuthor(this);

        }
    }
}
