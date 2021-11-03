package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Class representation of the writing groups
 */
@Entity
@DiscriminatorValue("Writing Groups")
class Writing_Groups extends Authoring_Entities{

    /** Head writer of the group*/
    @Column(nullable = true, length = 80)
    private String head_writer;

    /** year the group was formed*/
    @Column(nullable = true)
    private int year_formed;

    /** default constructor */
    public Writing_Groups(){

    }

    /**
     * Constructor
     * @param email of the group
     * @param name of group
     */
    public Writing_Groups(String email, String name) {
        super(email, name);
    }


    public String getHead_writer(){
        return this.head_writer;
    }

    public void setHead_writer(String head_writer){
        this.head_writer = head_writer;
    }

    public int getYear_formed(){
        return this.year_formed;
    }

    public void setYear_formed(int year_formed){
        this.year_formed = year_formed;
    }

    /**
     * returns string representation of object
     * @return String object
     */
    @Override
    public String toString() {
        return "{head_writer: " + this.head_writer + ", year_formed: " + this.year_formed + "}";
    }
}
