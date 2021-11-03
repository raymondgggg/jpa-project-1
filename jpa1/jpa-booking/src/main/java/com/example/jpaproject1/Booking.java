/**
 * @author Raymond Guevara Lozano
 * @author Nabiha Bashir
 * @author Rami Iskender
 */

package com.example.jpaproject1;
import model.*;
import org.apache.derby.impl.store.raw.log.Scan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLOutput;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Logger;


public class Booking {
   /** Entity manager that will be used to interact with the database */
   private EntityManager em;
   /** Logger that will be used throughout the program*/
   private static final Logger LOGGER = Logger.getLogger(Booking.class.getName());

   /**
    * Constructor
    * @param em entity manager object
    */
   public Booking(EntityManager em){
      this.em = em;
   }

   /**
    * Method that displays the main interactions the user can have with the
    * application.
    */
   public void displayMainMenu(){
      Books b = new Books();
      System.out.println( "\n-----Main Menu-----\nPlease select an option.\n" );
      System.out.println( "1. Books" );
      System.out.println( "2. Publishers" );
      System.out.println( "3. Authoring Entity" );
      System.out.println( "4. List all of the Primary Keys");
      System.out.println( "5. Quit\n" );
      // scanner to take user input for main men
      System.out.print("Option: ");
      int userChoice = getIntRange(1, 5);
      boolean repeatMenu = true;
      do{
         if (userChoice == 1){
            repeatMenu = false;
            booksMenu();
         }
         else if (userChoice == 2){
            repeatMenu = false;
            publishersMenu();
         }
         else if (userChoice == 3){
            repeatMenu = false;
            authoringEntitiesMenu();
         }
         else if (userChoice == 4){
            displayPrimaryKeys();
            repeatMenu = false;
         }
         else if (userChoice == 5){
         }
      }while(repeatMenu);
   }

   /**
    * Method that displays the interactions that user
    * can have with the Book entity
    */
   public void booksMenu(){
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("Booking");
      EntityManager em  = emf.createEntityManager();
      Booking m = new Booking(em);
      System.out.println( "\n-----Books Menu-----\nPlease select an option.\n\"1. Display all Books\n2. Add a Book\n" +
              "3. Information about a BookList\n4. Delete a Book\n5. Update a Book\n6. Return to Main Menu\n" +
              "7. Exit\n" );
      System.out.println("Option: ");
      int userChoice = getIntRange(1, 7); //get user input
      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            displayBooks();
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 2){
            addBook();
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 3){
            listInfoBooks();
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 4){
            deleteBook();
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 5){
            updateBooks();
            booksMenu();
            repeatMenu = false;

         }
         if (userChoice == 6){
            displayMainMenu();
            repeatMenu = false;
         }
         if (userChoice == 7){
            repeatMenu = false;
         }
      }
      while(repeatMenu);
   }

   /**
    * Method to delete book fropm database
    */
   public void deleteBook(){
      List <Books> books = new ArrayList<>();
      books = this.em.createNamedQuery("booksList", Books.class).getResultList();
      int book=getIntRange(0, books.size());
      books = this.em.createNamedQuery("booksList", Books.class).getResultList();
      for(int i=0; i<books.size();i++){
         System.out.println(i+" "+books.get(i));
      }
      Books deleteBook=books.get(getIntRange(0, books.size()));
      this.em.remove(deleteBook);
      System.out.println("Book is removed");

   }

   /**
    * Method to display all the books in the relational database
    */
   public void displayBooks(){
      List<Books> books = em.createNamedQuery("displayAllBooks", Books.class).getResultList();
      System.out.println("List of all books");
      System.out.println("ISBN              TITLE                  YEAR PUBLISHED        AUTHORING ENTITY      PUBLISHER");

      if(!books.isEmpty()) {
         for (Books book : books) {
            System.out.println(book);
         }
      }
      else{
         System.out.println("No record of books exist.");
      }
   }

/**
 * Updates book information
 */
public void updateBooks(){
   System.out.println("what book do you want to update");
   List <Books> books = this.em.createNamedQuery("booksList", Books.class).getResultList();
   for(int i=0; i<books.size();i++){
      System.out.println(i+" "+books.get(i).getTitle());
   }
   Books updateBook=books.get(getIntRange(0, books.size()));
   System.out.println("Who is the new authoring entity");
   List <Authoring_Entities> auth = this.em.createNamedQuery("displayAllAuthoringEntities", Authoring_Entities.class).getResultList();
   for (int i=0;i< auth.size();i++){
      System.out.println(i+" "+auth.get(i).getName());
   }
   int authChoice=getIntRange(0,auth.size());
   Authoring_Entities newAuth = auth.get(authChoice);
   try {
      updateBook.setAuthoring_entity_name(newAuth);
   }
   catch (Exception e){
      System.out.println("book name already exists in Authoring Entity");
   }
}
   /**
    * adds a publisher
    */
   public void addPublisher(){
         System.out.print("Enter name of Publisher: ");
         Scanner inp=new Scanner(System.in);
         String pubName = inp.nextLine();
         List<Publishers> pubNames = this.em.createNamedQuery("findPublisher", Publishers.class).setParameter(1, pubName).getResultList();
         if(pubNames.size() != 0) {
            System.out.println("name exists");
            }
         else{
            System.out.print("Enter the email of the Publisher: ");
            String pubEmail = inp.nextLine();
            List<Publishers> pubEmails = this.em.createNamedQuery("pubEmail", Publishers.class).setParameter(1, pubEmail).getResultList();
            if(pubEmails.size() != 0) {
               System.out.println("email exists");
            }
            else{

               System.out.print("Enter the phone number of the Publisher: ");
               String pubPhone = inp.nextLine();
               List<Publishers> pubPhones = this.em.createNamedQuery("pubEmail", Publishers.class).setParameter(1, pubEmail).getResultList();
               if(pubEmails.size() != 0) {
                  System.out.println("email exists");
               }
               else{
                  List<Publishers> publishers = new ArrayList<>();
                  publishers.add(new Publishers(pubName, pubEmail, pubPhone));
                  this.createEntity(publishers);
               }
            }
            }
   }

   /**
    * Method to list the information about a specific book based on user's input of Title
    */
   public void listInfoBooks(){
      // Scanner to take user input on book title
      Scanner scanner = new Scanner(System.in);

      // prompt user to enter a book title
      System.out.println("Please enter the Title of the Book you want to view: ");
      String desiredBook = scanner.nextLine();

      // executes the query to get the result of the book with the desired title
      List<Books> books = em.createNamedQuery("findBook", Books.class).setParameter(1, desiredBook).getResultList();

      // if a valid book title is entered, display information about that specific book
      if(!books.isEmpty()) {
         System.out.println("ISBN              TITLE            YEAR PUBLISHED    AUTHORING ENTITY    PUBLISHER");
         for (Books book : books) {
            System.out.println(book);
         }
      }
      // if user enters a book title which does not exist
      else{
         System.out.println("You have entered an invalid Book Title.");
      }
   }

   /**
    * Method that displays the interactions that the user
    * can have with the publishers entity
    */
   public void publishersMenu(){
      System.out.println( "\n-----Publishers Menu-----\nPlease select an option.\n" );
      System.out.println( "1. Display all Publishers" );
      System.out.println( "2. Add a Publisher" );
      System.out.println( "3. List information about a Publisher" );
      System.out.println( "4. Exit\n" );

      // scanner to take user input for main menu

      System.out.println("Option: ");
      int userChoice = getIntRange(1, 4);
      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            displayPublishers();
            repeatMenu = false;
         }
         else if (userChoice == 2){
            addPublisher();
            repeatMenu = false;
         }
         else if (userChoice == 3){
            listInfoPublisher();
            repeatMenu = false;

         }
         else if (userChoice == 4){
            repeatMenu = false;
         }

      }while(repeatMenu);
   }

   /**
    * Method to display all the publishers in the relational database
    */
   public void displayPublishers(){
      List<Publishers> publishers = em.createNamedQuery("displayAllPublishers", Publishers.class).getResultList();
      System.out.println("List of all Publishers");
      System.out.println("NAME              EMAIL                  PHONE");

      if(!publishers.isEmpty()) {
         for (Publishers publisher : publishers) {
            System.out.println(publisher);
         }
      }
      else{
         System.out.println("No record of Publishers exist.");
      }
   }

   /**
    * Method to list the information about a specific publisher based on user's input of Name
    */
   public void listInfoPublisher(){
      // Scanner to take user input on publisher name
      Scanner scanner = new Scanner(System.in);

      // prompt user to enter a publisher name
      System.out.println("Please enter the Name of the Publisher you want to view: ");
      String desiredPublisher = scanner.nextLine();

      // executes the query to get the result of the publisher with the chosen name
      List<Publishers> publishers = em.createNamedQuery("findPublisher", Publishers.class).setParameter(1, desiredPublisher).getResultList();

      // if a valid publisher name is entered, display information about that specific publisher
      if(!publishers.isEmpty()) {
         System.out.println("NAME               EMAIL                     PHONE");
         for (Publishers publisher : publishers) {
            System.out.println(publisher);
         }
      }
      // if user enters a publisher name which does not exist
      else{
         System.out.println("You have entered an invalid Publisher Name.");
      }
   }

   public void addAuthor2Team(){
      List<Ad_Hoc_Teams> teamList = this.em.createNamedQuery("returnTeam", Ad_Hoc_Teams.class).getResultList();

      Ad_Hoc_Teams team = new Ad_Hoc_Teams();

      boolean exitCondition = false;
      String usrInput;
      do{
         System.out.println("Enter the team email: ");
         usrInput = getString();
         List<Ad_Hoc_Teams> adHocTeamEmailList = this.em.createNamedQuery("ReturnEmail", Ad_Hoc_Teams.class).setParameter(1,usrInput).getResultList();
         if (adHocTeamEmailList.size() != 0){
            team = em.find(Ad_Hoc_Teams.class, usrInput); // validate team
            exitCondition = true;
         }
      }while(exitCondition == false);

      List<Individual_Authors> a = this.em.createNamedQuery("individualInfo", Individual_Authors.class).getResultList();

      Individual_Authors ia = new Individual_Authors();

      exitCondition = false;

      do{
         System.out.print("Enter the email of the author you would like to add: ");
         String author = getString();
         List<Individual_Authors> authorEmail = this.em.createNamedQuery("ReturnEmail", Individual_Authors.class).setParameter(1, author).getResultList();
         if (authorEmail.size() !=0){
            ia = em.find(Individual_Authors.class, author);
            exitCondition = true;
         }

      }while(exitCondition == false);
      team.addAuthor(ia);
   }

   /**
    * Method to add a book object to the relational database
    */
   public void addBook(){
      Scanner input=new Scanner(System.in);
      System.out.println("What is book title");
      String name=input.nextLine();
      System.out.println("What is ISBN");
      String isbn=input.nextLine();

      List<Books> books = this.em.createNamedQuery("bookIsbn", Books.class).setParameter(1, isbn).getResultList();//check unique isbn
      if (books.size() != 0){
         System.out.print("error. ISBN is already in.");
      }
      else { //prompt book info
         System.out.println("What year was this book published?");
         int year=getIntRange(0,2021);
         System.out.println("Who is the publisher");
         List <Publishers> publishers = this.em.createNamedQuery("displayAllPublishers", Publishers.class).getResultList();
         for (int i=0;i< publishers.size();i++){
            System.out.println(i+" "+publishers.get(i).getName());
         }
         int choice=getIntRange(0,publishers.size());
         System.out.println("Who is the authoring entity");
         List <Authoring_Entities> auth = this.em.createNamedQuery("displayAllAuthoringEntities", Authoring_Entities.class).getResultList();
         for (int i=0;i< auth.size();i++){
            System.out.println(i+" "+auth.get(i).getName());
         }
         int authChoice=getIntRange(0,auth.size());
         ArrayList<Books> book = new ArrayList<Books>();
         try {
            book.add(new Books(isbn, name, year, publishers.get(choice), auth.get(authChoice)));
            this.createEntity(book);
         }
         catch (Exception e){
            System.out.println("error. Book name already exists with publisher or authoring entity");
         }
      }
   }

   /**
    * Method to add a writing group to database
    */
   public void addWritingGroup(){
      System.out.print("What is the name of the group: ");
      String writingGroup = getString();

      System.out.print("What is the name of the head writer? ");
      String headWriter = getString();

      System.out.print("Year the group was formed");
      int year = getIntRange(0, 2021);

      boolean exitConditon = false;

      do {
         System.out.print("Enter the email of the group: ");
         String email = getString();
         List<Authoring_Entities> aEmail = this.em.createNamedQuery("allAuth", Authoring_Entities.class).setParameter(1, email).getResultList();

         if (aEmail.size() == 0){
            List<Writing_Groups> wg = new ArrayList<Writing_Groups>();
            Writing_Groups newWg = new Writing_Groups(email, writingGroup);
            newWg.setHead_writer(headWriter);
            newWg.setYear_formed(year);
            wg.add(newWg);
            this.createEntity(wg);
            exitConditon = true;
         }
      }while(exitConditon == false);
   }

   /**
    * Method that displays the interactions that the user
    * can have with the authoring entity
    */
   public void authoringEntitiesMenu(){
      System.out.println( "\n-----Authoring Entities Menu-----\nPlease select an option.\n" );
      System.out.println( "1. Display all Authoring Entities" );
      System.out.println( "2. Add ad hoc team" );
      System.out.println( "3. List information about Writing Groups" );

      System.out.println( "4. Add individual Author" );

      System.out.println( "5. Add writing group" );

      System.out.println( "6. Exit\n" );

      System.out.println("Option: ");
      int userChoice = getIntRange(1, 5);

      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            displayAuthoringEntities();
            repeatMenu = false;
         }
         if (userChoice == 2){
         addAdHocTeam();
            repeatMenu = false;
         }
         if (userChoice == 3){
            listInfoAuthoringEntity();
            repeatMenu = false;
         }
         if (userChoice == 4){
            addIndividualAuthor();
            repeatMenu = false;
         }
         if (userChoice == 5){
            addWritingGroup();
            repeatMenu = false;
         }
         if (userChoice == 6){
            repeatMenu = false;
         }
      }
      while(repeatMenu);
   }

   /**
    * Method to add an Ad Hoc Team to the authoring entities table in the database.
    */
   public void addAdHocTeam(){

   Scanner input= new Scanner(System.in);
      System.out.print("Enter name of Ad Hoc Team: ");
   String teamName = input.nextLine();
      System.out.print("Enter the email of the Ad Hoc Team: ");
   String teamEmail = input.nextLine();
   List<Authoring_Entities> authEmail = this.em.createNamedQuery("allAuth", Authoring_Entities.class).setParameter(1, teamEmail).getResultList();
      if(authEmail.size() != 0) {
      System.out.println("Email already exists");
   }
               else{
      List<Ad_Hoc_Teams> hoc = new ArrayList<>();
      hoc.add(new Ad_Hoc_Teams(teamEmail, teamName, null));
      this.createEntity(hoc);
   }

}

   /**
    * Method display the authoring entities
    */
   public void displayAuthoringEntities(){
      List<Authoring_Entities> authoringEntities = em.createNamedQuery("displayAllAuthoringEntities", Authoring_Entities.class).getResultList();
      System.out.println("List of all Authoring Entities");
      System.out.println("EMAIL                     NAME     ");

      if(!authoringEntities.isEmpty()) {
         for (Authoring_Entities authoringEntity : authoringEntities) {
            System.out.println(authoringEntity);
         }
      }
      else{
         System.out.println("No record of Authoring Entities exist.");
      }
   }

   /**
    * Method to list the information about a specific authority entity based on user's input of Email
    */
   public void listInfoAuthoringEntity(){
      // Scanner to take user input on author's email
      Scanner scanner = new Scanner(System.in);

      // prompt user to enter an author email
      System.out.println("Please enter the Email of the Author you want to view: ");
      String desiredAuthor = scanner.nextLine();

      // executes the query to get the result of the publisher with the chosen name
      List<Authoring_Entities> authoringEntities = em.createNamedQuery("findAuthoringEntity", Authoring_Entities.class).setParameter(1, desiredAuthor).getResultList();

      // if a valid author's email is entered, display information about that specific author
      if(!authoringEntities.isEmpty()) {
         System.out.println("EMAIL    AUTHORING_TYPE     NAME    ");
         for (Authoring_Entities authorityEntity : authoringEntities) {
            System.out.println(authorityEntity);
         }
      }
      // if user enters a publisher name which does not exist
      else{
         System.out.println("You have entered an invalid Email.");
      }
   }

   /**
    * show all primary keys"
    */
   public void displayPrimaryKeys(){
      System.out.println("Books");
      List<Books> booksList= em.createNamedQuery("displayAllBooks",Books.class).getResultList();
      if(!booksList.isEmpty()){
         System.out.println("-----Books Primary Keys-----\n");
         for(Books book:booksList){
            System.out.println(book.getIsbn());
         }}
      System.out.println("Publishers");
      List<Publishers> publishersList =  em.createNamedQuery("displayAllPublishers",Publishers.class).getResultList();
      if(!publishersList.isEmpty()){
         System.out.println("-----Publishers Primary Keys-----\n");
         for(Publishers publisher:publishersList){
            System.out.println(publisher.getName());
         }}
      System.out.println("Authoring Entities");
      List<Authoring_Entities> authoringList= em.createNamedQuery("displayAllAuthoringEntities", Authoring_Entities.class).getResultList();
      if(!authoringList.isEmpty()){
         System.out.println("-----Authoring Entities Primary Keys-----\n");
      for(Authoring_Entities auth:authoringList){
         System.out.println(auth.getEmail());
      }}

   }
   /**
    * A method which takes the User's input that is in a given integer range only.
    * @param low the minimum integer value the input can be
    * @param high the maximum integer value the input can be
    * @return the user's integer input in the desired range
    */
   public static int getIntRange( int low, int high ) {
      Scanner in = new Scanner( System.in );
      int input = 0;
      boolean valid = false;
      while( !valid ) {
         if( in.hasNextInt() ) {
            input = in.nextInt();
            if( input <= high && input >= low ) {
               valid = true;
            } else {
               System.out.println( "Invalid range" );
            }
         } else {
            in.next(); //clear invalid string
            System.out.println( "Invalid Input." );
         }
      }
      return input;
   } //End of the getIntRange method

   /**
    * Method to add an individual author to the authoring entities table in the database.
    */
   public void addIndividualAuthor(){
      System.out.print("What is the name of the of the author: ");
      String name = getString();
      System.out.print("What is the email of the author: ");
      String email = getString();
      boolean exitCondition = false;

      do {
         List<Authoring_Entities> aEmail = this.em.createNamedQuery("allAuth", Authoring_Entities.class).setParameter(1,email).getResultList();
         if (aEmail.size() == 0){
            List<Individual_Authors> ia = new ArrayList<>();
            ia.add(new Individual_Authors(email, name, null));
            this.createEntity(ia);
            exitCondition = true;
         }

      }while(exitCondition == false);
   }

   /**
    * Takes in a string from the user.
    * @return the inputted String.
    */
   public static String getString() {
      Scanner in = new Scanner( System.in );
      String input = in.nextLine();
      return input;
   }

   /**
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the CustomerOrders entityManager instance variable to get our EntityManager.
         this.em.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

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
      List <Books> books = new ArrayList<>();
      List <Publishers> publishers = new ArrayList<>();

      entities.add(new Authoring_Entities("AnnaWeiner@gmail.com", "Anna Weiner"));
      entities.add(new Authoring_Entities("FlynnBerry@gmail.com","Flynn Berry"));
      entities.add(new Authoring_Entities("SarahPearse@gmail.com", "Sarah Pearse"));
      entities.add(new Authoring_Entities("PatriciaEngel@gmail.com",  "Patricia Engel"));
      entities.add(new Authoring_Entities("PenelopeLively@gmail.com", "Penelope Lively"));

      publishers.add(new Publishers("Scholastic", "Scholastic@gmail.com", "8475647465" ));
      publishers.add(new Publishers("Simon & Schuster", "SimonSchuster@gmail.com", "1246479584"));
      publishers.add(new Publishers("Wiley Press", "WileyPress@gmail.com", "8475873674"));
      publishers.add(new Publishers("Penguin Books", "PenguinBooks@gmail.com", "6745653389"));
      publishers.add(new Publishers("Chronicle Books", "ChronicleBooks@gmail.com", "9087653647"));


      booking.createEntity(publishers);


      books.add(new Books("9375638264859", "To Kill a Mocking Bird", 2002, publishers.get(0),  entities.get(0)));
      books.add(new Books("8263548392726", "The Disappearances ", 2009, publishers.get(1), entities.get(1)));
      books.add(new Books("8476252749585", "Beautiful Ruins", 2010, publishers.get(2), entities.get(2)));
      books.add(new Books("9283746585746", "The Novice", 2018, publishers.get(3), entities.get(3)));
      books.add(new Books("48572625474834", "Northern Spy", 2003, publishers.get(4), entities.get(4)));



      booking.createEntity(entities);
      booking.createEntity(publishers);
      booking.createEntity(books);

      boolean exitCondition = false;

      do{
         booking.displayMainMenu();
      }while(exitCondition == false);



      tx.commit();





   }
   
}