/**
 * A class that creates, adds, edits, and updates relevant book information.
 * Homework Assignment: JPA Booking
 *
 * @author John Apale
 * @author Matthew Chung
 * @author Quan Do
 * @version 1.0 11/02/2021
 */

package csulb.cecs323.app;

import java.util.*;
import java.util.logging.Logger;
import javax.persistence.*;
import csulb.cecs323.model.*;

public class Booking {

   /**
    * Declaration of the EntityManager type variable called entityManager.
    * Allow applications to manage and search for entities.
    */
   private EntityManager entityManager;

   /**
    * Declaration of the Logger type variable called LOGGER. Logs the program.
    */
   private static final Logger LOGGER = Logger.getLogger(Booking.class.getName());

   /**
    * The constructor for the Main class. Stashes the provided EntityManager for use
    * later in the application.
    * @param manager The EntityManager that we will use.
    */
   public Booking(EntityManager manager) {
      this.entityManager = manager;
   }

   /**
    * Creates a scanner to take user inputs
    */
   private static Scanner scan = new Scanner(System.in);

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Booking");
      EntityManager manager = factory.createEntityManager();

      // Create an instance of Books
      Booking booking = new Booking(manager);

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();

      List <Authoring_Entities> entities = new ArrayList<>();
      List <Publishers> publishers = new ArrayList<>();
      List <Books> books = new ArrayList<>();


      entities.add(new Authoring_Entities("company1@gmail.com", "James Gunn"));
      entities.add(new Authoring_Entities("company2@gmail.com","Charles Park"));
      entities.add(new Authoring_Entities("company3@gmail.com", "Nick James"));
      entities.add(new Authoring_Entities("company4@gmail.com",  "Harley Madison"));
      entities.add(new Authoring_Entities("company5@gmail.com", "Jessica Quinn"));

      publishers.add(new Publishers("Bloomsbury Publishing", "bloomsburypublishing@gmail.com", "1111111111" ));
      publishers.add(new Publishers("Activision", "activision@gmail.com", "2222222222"));
      publishers.add(new Publishers("Scholastic Press", "scholasticpress@gmail.com", "3333333333"));
      publishers.add(new Publishers("Delacorte Press", "delacortepress@gmail.com", "4444444444"));
      publishers.add(new Publishers("Katherine Tegen Books", "katherinetegenbooks@gmail.com", "5555555555"));
      booking.createEntity(publishers);

      books.add(new Books("9396714171891", "Harry Potter", 2001, publishers.get(0),  entities.get(0)));
      books.add(new Books("2321900032473", "Percy Jackson", 2002, publishers.get(1), entities.get(1)));
      books.add(new Books("7022372062665", "Hunger Games", 2003, publishers.get(2), entities.get(2)));
      books.add(new Books("5212348852569", "Maze Runner", 2004, publishers.get(3), entities.get(3)));
      books.add(new Books("9311434127573", "Divergent", 2005, publishers.get(4), entities.get(4)));

      booking.createEntity(entities);
      booking.createEntity(publishers);
      booking.createEntity(books);

      boolean input = false;
      while(!input){

         int user = menu();

         switch(user){
            case 1:
               //add new object
               /*
                * Writing Group
                * Individual Author
                * Ad Hoc Team
                * An Individual Author to an existing Ad Hoc Team
                * Publisher
                * Book
                */
               booking.addObject();
               break;
            case 2:
               //list info about an object
               /*
                * Publisher
                * Book (include publisher and Authoring Entity
                * Writing Group
                */
               booking.listInfo();
               break;
            case 3:
               //Delete a book
               /*
                * prompt all elements of a candidate key
                * Make sure the book actually exists
                */
               booking.removeBook();
               break;
            case 4:
               //Update a book
               /*
                *Change the authoring entity for an existing book
                */
               booking.updateBook();
               break;
            case 5:
               //List the pk of all the rows
               /*
                * Publishers
                * Books (show the title and ISBN)
                * Authoring Entities (also show type of authoring entity)
                */
               booking.listPrimaryKeys(factory, booking);
               break;
            case 6:
               //Exit
               input = true;
               break;

            default:
               System.out.println("Invalid Response. Please try again.");
         }
      }

      System.out.println("Interaction Complete");
      tx.commit();
      LOGGER.fine("End of Interaction");

   }//End of main method

   /**
    * The main menu that will prompt the user to interact with the program.
    * @return     A numeric choice that the user selected.
    */
   public static int menu() {
      System.out.print("\nMenu:\n" +
              "1: Add a new object\n" +
              "2: List info \n" +
              "3: Remove a Book\n" +
              "4: Update a Book\n" +
              "5: List all of the Primary Keys\n" +
              "6: Exit\n" +
              "Enter a numeric value: ");

      return scan.nextInt();
   }

   /**
    * The addObject option that will prompt the user to add an entity to
    * the database. This method uses other helper methods in order to add
    * the desired entity to the database. The entity options are: Writing
    * Group, Individual Author, Ad Hoc Team, Publisher, Book, and an option
    * to add an existing Individual Author entity to an Ad Hoc Team entity.
    */
   public void addObject(){
      int input = 0;
      boolean valid = false;
      while(!valid){
         System.out.print("\nWhat type of object do you want to add:\n" +
                 "1: Writing Group\n" +
                 "2: Individual Author\n" +
                 "3: Ad Hoc Team\n" +
                 "4: Add an Individual Author to an Ad Hoc Team\n" +
                 "5: Publisher\n" +
                 "6: Book\n" +
                 "Enter a numeric value: ");
         input = scan.nextInt();
         scan.nextLine();
         if(input > 0 && input < 7){
            valid = true;
         }
      }

      switch (input) {
         case 1:
            this.addGroupWriting();
            break;
         case 2:
            this.addIndividualAuthor();
            break;
         case 3:
            this.addAdHocTeam();
            break;
         case 4:
            addTeamMember();
            break;
         case 5:
            addPublisher();
            break;
         case 6:
            addBook();
            break;
         default:
            System.out.println("Invalid Input");
      }
   }

   /**
    * Adds a Writing Group Entity to the database. Asks the user to
    * insert the Writing Group entity's information and creates an entity
    * with that information. Then persists the entity to the database.
    * A helper method to the addObject() method.
    */
   public void addGroupWriting(){
      System.out.print("Enter the name of the Writing Group: ");
      String groupName = scan.nextLine();

      System.out.print("Enter the name of the Head Writer: ");
      String headWriter = scan.nextLine();

      System.out.print("Enter the year that the Writing Group was formed: ");
      int year = scan.nextInt();

      scan.nextLine();
      boolean valid = false;
      while (!valid) {
         System.out.print("Enter the email of the Writing Group: ");
         String inputEmail = scan.nextLine();
         try{
            if(inputEmail.contains("@") && inputEmail.contains(".")){
//               List<Writing_group> groupEmail = this.entityManager.createNamedQuery("ReturnAllGroupEmail", Writing_group.class).setParameter(1, inputEmail).getResultList();
               List<Authoring_Entities> authEmail = this.entityManager.createNamedQuery("ReturnAllEmails", Authoring_Entities.class).setParameter(1, inputEmail).getResultList();
               //Validate Writing Group
               if(authEmail.size() == 0) {
                  List<Writing_group> writing_groups = new ArrayList<>();
                  writing_groups.add(new Writing_group(inputEmail, groupName, headWriter, year));
                  this.createEntity(writing_groups);
                  System.out.println("Writing group has been added.");
                  valid = true;
               }else{
                  System.out.println("Invalid Email");
               }
            }
            else{
               System.out.println("Invalid Input.");
            }
         }catch(Exception e){
            System.out.println("Invalid email");
         }
      }
   }

   /**
    * Adds an Ad Hoc Team entity to the database. Prompts the user to enter
    * the Ad Hoc Team entity's information. Then an Ad Hoc Team entity is
    * created with that information and is then persisted to the database.
    * Is a helper method to the addObject() method.
    */
   public void addAdHocTeam(){
      System.out.print("Enter the name of the Ad Hoc Team: ");
      String teamName = scan.nextLine();

      boolean valid = false;
      while (!valid) {
         System.out.print("Enter the email of the Ad Hoc Team: ");
         String teamEmail = scan.nextLine();
         try{
            if(teamEmail.contains("@") && teamEmail.contains(".")){
//               List<Ad_hoc_teams> adHocTeamsEmail = this.entityManager.createNamedQuery("ReturnAllTeamEmail", Ad_hoc_teams.class).setParameter(1, teamEmail).getResultList();
               List<Authoring_Entities> authEmail = this.entityManager.createNamedQuery("ReturnAllEmails", Authoring_Entities.class).setParameter(1, teamEmail).getResultList();
               if(authEmail.size() == 0) { //Validate Ad Hoc Team
                  List<Ad_hoc_teams> team = new ArrayList<>();
                  team.add(new Ad_hoc_teams(teamEmail, teamName, null));
                  this.createEntity(team);
                  System.out.println("Team has been added.");
                  valid = true;
               }
               else{
                  System.out.println("Invalid Email");
               }
            }
            else{
               System.out.println("Invalid Input.");
            }
         }catch(Exception e){
            System.out.println("Invalid email");
         }
      }
   }

   /**
    * Adds an Individual Author entity to the database. Prompts the user to enter
    * the Individual Author entity's information. Then an Individual Author entity is
    * created with that information and is then persisted to the database.
    * Is a helper method to the addObject() method.
    */
   public void addIndividualAuthor(){
      System.out.print("Enter the name of the Individual Author: ");
      String individualName = scan.nextLine();

      boolean valid = false;
      while (!valid) {
         System.out.print("Enter the email of the Individual Author: ");
         String inputEmail = scan.nextLine();
         try{
            if(inputEmail.contains("@") && inputEmail.contains(".")){
//               List<Individual_author> individualEmail = this.entityManager.createNamedQuery("ReturnAllIndividualEmail", Individual_author.class).setParameter(1, inputEmail).getResultList();
               List<Authoring_Entities> authEmail = this.entityManager.createNamedQuery("ReturnAllEmails", Authoring_Entities.class).setParameter(1, inputEmail ).getResultList();
               if(authEmail.size() == 0) { //Validate Individual Author
                  List<Individual_author> individualAuthors = new ArrayList<>();
                  individualAuthors.add(new Individual_author(inputEmail, individualName, null));
                  this.createEntity(individualAuthors);
                  System.out.println("Individual Author has been added.");
                  valid = true;
               }
               else{
                  System.out.println("Invalid Email");
               }
            }
            else{
               System.out.println("Invalid Input.");
            }
         }catch(Exception e){
            System.out.println("Invalid email");
         }
      }
   }

   /**
    * Adds an existing Individual Author entity to an existing Ad Hoc Team
    * entity. Prompts the user to choose which individual author to add to
    * a team. A helper method of the addObject() method.
    */
   public void addTeamMember(){
      Scanner input = new Scanner(System.in);
      // get list of all teams
      List<Ad_hoc_teams> teams = this.entityManager.createNamedQuery("ReturnAllTeamInfo", Ad_hoc_teams.class).getResultList();

      // initialize a team
      Ad_hoc_teams selectedTeam = new Ad_hoc_teams();

      // print out list of teams
      System.out.print("\nList of teams: ");
      for (Ad_hoc_teams team : teams) {
         System.out.println("Team Name: " + team.getName() + " Team Email: " + team.getEmail());
      }
      boolean valid = false;
      String inputTeam = "";

      // get user input for team they want to add to
      while (!valid) {
         System.out.print("\nEnter the enter the email of the team you want to add a member to: ");
         inputTeam = input.nextLine();
         try {
            List<Ad_hoc_teams> adHocTeamsEmail = this.entityManager.createNamedQuery("ReturnAllTeamEmail", Ad_hoc_teams.class).setParameter(1, inputTeam).getResultList();
            if(adHocTeamsEmail.size() != 0) { //Validate Ad Hoc Team
               selectedTeam = entityManager.find(Ad_hoc_teams.class, inputTeam); //Validate Ad Hoc Team
               valid = true;
            }else{
               System.out.println("Invalid team.");
            }
         } catch (Exception e) {
            System.out.print("\nTeam does not exists.");
         }
      }

      // get the list of authors
      List<Individual_author> author = this.entityManager.createNamedQuery("ReturnAllIndividualInfo", Individual_author.class).getResultList();

      // initialize an individual
      Individual_author selectedIndividual = new Individual_author();

      // print list of all individual authors
      System.out.print("\nList of Individual Authors: ");
      for (Individual_author auth : author) {
         System.out.println("Author Name: " + auth.getName() + " Author Email: " + auth.getEmail());
      }
      boolean validIndividual = false;
      String inputIndividual = "";

      // get user input for author they want to add
      while (!validIndividual) {
         System.out.print("Enter the enter the email of the Individual Author you want to add a to a team: ");
         inputIndividual = input.nextLine();
         try {
            List<Individual_author> individualEmail = this.entityManager.createNamedQuery("ReturnAllIndividualEmail", Individual_author.class).setParameter(1, inputIndividual).getResultList();
            if(individualEmail.size() != 0) { //Validate Individual Author
               selectedIndividual = entityManager.find(Individual_author.class, inputIndividual); //Validate Individual Author input
               validIndividual = true;
            }else{
               System.out.println("Invalid Author.");
            }
         } catch (Exception e) {
            System.out.println("Author does not exists.");
         }
      }
      selectedTeam.add_individual_authors(selectedIndividual);
      System.out.println("Individual author has been added to team.");
   }

   /**
    * Adds Publisher entity to the database. Prompts the user to enter
    * the Publisher entity's information. Then a Publisher entity is
    * created with that information and is then persisted to the database.
    * Is a helper method to the addObject() method.
    */
   public void addPublisher(){
      String pubName = "";
      boolean valid = false;
      while (!valid) {
         System.out.print("Enter the name of the Publisher: ");
         pubName = scan.nextLine();
         try{
            List<Publishers> publishersNames = this.entityManager.createNamedQuery("ReturnAllPublisherNames", Publishers.class).setParameter(1, pubName).getResultList();
            if(publishersNames.size() == 0) { //Validate no duplicate publisher
               valid = true;
            }
            else{
               System.out.println("Invalid Input.");
            }
         }catch(Exception e){
            System.out.println("Invalid Publisher");
         }
      }
      System.out.print("Enter the email of the Publisher: ");
      String pubEmail = scan.nextLine();

      System.out.print("Enter the phone number of the Publisher: ");
      String pubPhone = scan.nextLine();

      List<Publishers> publishers = new ArrayList<>();
      publishers.add(new Publishers(pubName, pubEmail, pubPhone));
      this.createEntity(publishers);
      System.out.println("Publisher has been added.");
   }

   /**
    * Adds a Book entity to the database. Prompts the user to enter
    * the Book entity's information. Then a Book entity is created
    * with that information and is then persisted to the database.
    * Is a helper method to the addObject() method.
    */
   public void addBook(){
      Scanner input = new Scanner(System.in);
      String title, name = null, entityEmail = null;
      String ISBN = "";
      int publishedYear;

      Authoring_Entities authEntity = new Authoring_Entities();
      Publishers publisher = new Publishers();

      System.out.print("Enter book title: ");
      title = input.nextLine();
      boolean valid = false;
      while(!valid) {
         System.out.print("Enter ISBN: ");
         ISBN = input.nextLine();
         List<Books> books = this.entityManager.createNamedQuery("ReturnAllBookISBN", Books.class).setParameter(1, ISBN).getResultList();
         if (books.size() == 0){ //Validate ISBN, noduplicate
            valid = true;
         } else {
            System.out.print("Duplicate ISBN. Try again.");
         }
      }
      System.out.print("Enter the published year: ");
      publishedYear = input.nextInt();
      input.nextLine();

      List <Publishers> publishers = this.entityManager.createNamedQuery("ReturnAllPublishers", Publishers.class).getResultList();

      valid = false;
      while(!valid) {
         System.out.println("Publisher options:");
         for (Publishers pub : publishers) {
            System.out.println(pub.toString());
         }
         System.out.print("Enter name of the publisher: ");
         name = input.nextLine();
         try{
            List<Publishers> publishersNames = this.entityManager.createNamedQuery("ReturnAllPublisherNames", Publishers.class).setParameter(1, name).getResultList();
            if(publishersNames.size() != 0) { //Validate no duplicate publisher
               publisher = entityManager.find(Publishers.class, name); //Validate Publisher
               valid = true;
            }else{
               System.out.println("Invalid Publisher.");
            }
         } catch (Exception e) {
            System.out.println("Invalid Publisher name.");
         }
      }

      List <Authoring_Entities> authoring_entities = this.entityManager.createNamedQuery("ReturnAllAuthoringEntities", Authoring_Entities.class).getResultList();

      valid = false;
      while(!valid) {
         System.out.println("Authoring Entity Options: ");
         for (Authoring_Entities authors : authoring_entities) {
            System.out.println(authors.toString());
         }
         System.out.print("Enter email of authoring entity: ");
         entityEmail = input.nextLine();
         try{
            List<Authoring_Entities> authEmail = this.entityManager.createNamedQuery("ReturnAllEmails", Authoring_Entities.class).setParameter(1, entityEmail).getResultList();
            if(authEmail.size() != 0) {//Validate Authoring Entity
               authEntity = entityManager.find(Authoring_Entities.class, entityEmail); //Validate Authoring Entity
               valid = true;
            }else{
               System.out.println("Invalid Author");
            }
         } catch (Exception e) {
            System.out.println("Invalid email.");
         }
      }

      List <Books> book = new ArrayList<>();
      book.add(new Books(ISBN, title, publishedYear, publisher, authEntity));
      this.createEntity(book);
      System.out.println("Book has been added.");
   }

   /**
    * Lists the info associated with the entity that the user wants to
    * view. The user can choose to view the Publishers, Books, or Writing
    * Groups entities.
    */
   public void listInfo(){
      boolean valid = false;
      int response = 0;
      while(!valid){
         System.out.println(  "Select which object information you would like to see.\n" +
                 "1. Publishers\n" +
                 "2. Books\n" +
                 "3. Writing Group");
         response = scan.nextInt();
         scan.nextLine();
         if (response > 0 && response < 4){
            valid = true;
         }
      }
      switch (response) {
         case 1:
            List <Publishers> publishers = this.entityManager.createNamedQuery("ReturnAllPublishers", Publishers.class).getResultList();
            // print publishers
            System.out.println("\nPublishers Info: ");
            if(publishers.size() == 0){
               System.out.println("\nNo current Writing Groups");
            }else{
               for (Publishers publisher : publishers){
                  System.out.println(publisher.toString());
               }
            }
            break;
         case 2:
            List <Books> books = this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
            // print books
            System.out.println("\nBooks Info: ");
            if(books.size() == 0){
               System.out.println("\nNo current Books");
            }else{
               for (Books book : books){
                  System.out.println(book.toString());
               }
            }
            break;
         case 3:

            List <Writing_group> writing_groups = this.entityManager.createNamedQuery("ReturnAllWritingGroups", Writing_group.class).getResultList();
            // print authoring entities
            System.out.println("\nWriting Group Info: ");
            if(writing_groups.size() == 0){
               System.out.println("\nNo current Writing Groups");
            }else{
               for (Writing_group writing_group : writing_groups){
                  System.out.println(writing_group.toString());
               }
            }
            break;
      }
   }

   /**
    * Removes an existing Book entity that the user specifies by the ISBN.
    */
   public void removeBook(){

      List <Books> books = new ArrayList<>();
      books = this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();

      // show list of books
      System.out.println("\nList of books:");
      for (Books book : books) {
         System.out.println(book.toString());
      }
      boolean deleted = false;
      while(!deleted){
         Scanner input = new Scanner(System.in);
         System.out.print("\nEnter the ISBN of the book you would like to remove or press 1 to cancel: ");
         String selection = input.nextLine();

         if(selection.equals("1")){
            deleted = true;
         }
         try {
            // remove the user selection
            Books book = entityManager.find(Books.class, selection); //ISBN Validation
            this.entityManager.remove(book);
            System.out.println("Book has been removed.");
            deleted = true;
         } catch (Exception e) {
            System.out.println("Invalid ISBN.");
         }
      }
   }

   /**
    * Updates the Authoring Entity of a Book entity. Prompts the
    * user which book they want to change and the authoring entity
    * that the user wants to apply to that book.
    */
   public void updateBook(){
      Books book = null;
      List <Books> books = new ArrayList<>();
      books = this.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList();
      boolean updated = false;
      while(!updated){
         Scanner input = new Scanner(System.in);
         String selection = "";
         try {
            boolean truISBN = false;
            while(!truISBN){
               System.out.println("\nList of books:");
               for (Books bookList : books) {
                  System.out.println(bookList.toString());
               }
               System.out.print("\nEnter the ISBN of the book you would like to update or 1 to cancel: ");
               selection = input.nextLine();
               if(selection.equals("1")){
                  updated = true;
               }

               List<Books> booksISBN = this.entityManager.createNamedQuery("ReturnAllBookISBN", Books.class).setParameter(1, selection).getResultList();
               if (booksISBN.size() != 0) { //Validate ISBN, noduplicate
                  book = entityManager.find(Books.class, selection); //ISBN validation
                  truISBN = true;
               }else{
                  System.out.println("Invalid ISBN.");
               }
            }
            // Show list of authoring entity
            List <Authoring_Entities> authoring_entities = this.entityManager.createNamedQuery("ReturnAllAuthoringEntities", Authoring_Entities.class).getResultList();
            System.out.println("\nList of Authoring Entities:");
            for (Authoring_Entities auth_ent : authoring_entities){
               System.out.println(auth_ent.toString());
            }
            // Prompt user to enter which authoring entity to select
            System.out.print("\nEnter the email of authoring entity would you like to update to? ");
            String email = input.nextLine();

            try {
               Authoring_Entities newAuth = entityManager.find(Authoring_Entities.class, email); //Authoring Entity Validation
               assert book != null;
               book.setAuthoringEntity(newAuth);
               System.out.println("Book has been updated.");
               updated = true;
            } catch(Exception e) {
               System.out.println("Authoring entity does not exist.");
            }
         } catch (Exception e){
            System.out.println("Invalid ISBN.");
         }
      }
   }

   /**
    * Lists the all the primary keys of the Publishers, Books, or Authoring Entities
    * tables depending on which table that the user wants to display. For Books, the
    * ISBN and primary keys are displayed. For the Authoring Entities, the primary keys
    * and type of Authoring Entity is displayed.
    * @param factory       Provides instances of EntityManager for connecting to same database.
    * @param booking       The entity manager of the Booking class to access database information.
    */
   public void listPrimaryKeys(EntityManagerFactory factory, Booking booking ){
      System.out.println("List the primary key of all the rows of:\n" +
              "1: Publishers\n" +
              "2: Books\n" +
              "3: Authoring Entities");
      int userInput = scan.nextInt();
      scan.nextLine();

      switch (userInput){
         case 1:
            System.out.println("The Primary Keys of Publisher:\n");
            for(Publishers pub : booking.entityManager.createNamedQuery("ReturnAllPublishers", Publishers.class).getResultList()){
               Object publisherPK = factory.getPersistenceUnitUtil().getIdentifier(pub);
               System.out.println("Publisher name: " + publisherPK);
            }
            break;
         case 2:
            System.out.println("The Primary keys of Books:\n");
            for(Books bookPK : booking.entityManager.createNamedQuery("ReturnAllBooks", Books.class).getResultList()){
               Object bookPKObj = factory.getPersistenceUnitUtil().getIdentifier(bookPK);
               System.out.println("Book title: " + bookPK.getTitle() + ", ISBN: " + bookPKObj);
            }
            break;
         case 3:
            System.out.println("The Primary keys of Authoring Entities\n");
            for(Authoring_Entities auth_ent: booking.entityManager.createNamedQuery("ReturnAllAuthoringEntities", Authoring_Entities.class).getResultList()){
               Object AuthorPK = factory.getPersistenceUnitUtil().getIdentifier(auth_ent);
               if(auth_ent instanceof Writing_group){
                  System.out.println("Email: " + AuthorPK + ", Type: Writing Group");
               }else if(auth_ent instanceof Individual_author){
                  System.out.println("Email: " + AuthorPK + ", Type: Individual Author");
               }else if(auth_ent instanceof  Ad_hoc_teams){
                  System.out.println("Email: " + AuthorPK + ", Type: Ad Hoc Team");
               }else
                  System.out.println("Email: " + AuthorPK + ", Type: Authoring Entity");
            }
            break;
      }

   }

   /**
    * Create and persist a list of objects to the database.
    * @param entities The list of entities to persist. These can be any object that has been
    *                 properly annotated in JPA and marked as "persistable."
    */
   public <E> void createEntity(List<E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CarClub entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method
}// End of Main class
