package com.example.jpaproject1;

import model.*;
import org.apache.derby.impl.store.raw.log.Scan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Logger;

// rayRay
// nabiha
// rami
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
            repeatMenu = false;
            System.out.println("Quitting Program");
         }
      }while(repeatMenu);
   }

   /**
    * Method that displays the interactions that user
    * can have with the Book entity
    */
   public void booksMenu(){
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("Main");
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
            //displayBooks();
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 2){
            //TODO: addBook()
            addBook();
            booksMenu();
            repeatMenu = false;

         }
         if (userChoice == 3){
            booksMenu();
            repeatMenu = false;

         }
         if (userChoice == 4){
            //TODO: deleteBook()
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 5){
            //TODO: updateBook()
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
      System.out.println("what book do you want to delete");
      List <Books> books = this.em.createNamedQuery("booksList", Books.class).getResultList();
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
      System.out.println("ISBN    TITLE     YEAR PUBLISHED      AUTHORING ENTITY    PUBLISHER");

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
    * Method to list the information about a specific book based on user's input of Title
    */
   public void listInfoBooks(){
      // Scanner to take user input on book title
      Scanner scanner = new Scanner(System.in);

      // display all Titles of books available to user
      displayBookTitles();

      // prompt user to enter a book title
      System.out.println("Please enter the Title of the Book you want to view: ");
      String desiredBook = scanner.nextLine();

      // executes the query to get the result of the book with the desired title
      List<Books> books = em.createNamedQuery("findBook", Books.class).setParameter(1, desiredBook).getResultList();

      // if a valid book title is entered, display information about that specific book
      if(!books.isEmpty()) {
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
    * Method to display all the titles of the books in the relational database
    */
   public void displayBookTitles(){
      System.out.println("Book Titles");
      List<Books> booksList= em.createNamedQuery("bookTitles",Books.class).getResultList();
      for(Books book:booksList){
         System.out.println(book);
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
      System.out.println( "4. Return to Main Menu\n" );
      System.out.println( "5. Exit\n" );

      // scanner to take user input for main menu

      System.out.println("Option: ");
      int userChoice = getIntRange(1, 5);
      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            repeatMenu = false;
         }
         else if (userChoice == 2){
            //TODO: addPublisher()
            repeatMenu = false;

         }
         else if (userChoice == 3){
            repeatMenu = false;

         }
         else if (userChoice == 4){
            //TODO: return2main()
            repeatMenu = false;

         }
         else if (userChoice == 5){
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
      System.out.println("NAME    EMAIL     PHONE");

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

      // display all the Names of publishers available to user
      displayPublisherNames();

      // prompt user to enter a publisher name
      System.out.println("Please enter the Name of the Publisher you want to view: ");
      String desiredPublihser = scanner.nextLine();

      // executes the query to get the result of the publisher with the chosen name
      List<Publishers> publishers = em.createNamedQuery("findPublisher", Publishers.class).setParameter(1, desiredPublihser).getResultList();

      // if a valid publisher name is entered, display information about that specific publisher
      if(!publishers.isEmpty()) {
         for (Publishers publisher : publishers) {
            System.out.println(publisher);
         }
      }
      // if user enters a publisher name which does not exist
      else{
         System.out.println("You have entered an invalid Publisher Name.");
      }
   }

   /**
    * Method to display all the names of the publishers in the relational database
    */
   public void displayPublisherNames(){
      System.out.println("Publisher Names");
      List<Publishers> publishersList = em.createNamedQuery("publisherNames",Publishers.class).getResultList();
      for(Publishers publisher:publishersList){
         System.out.println(publisher);
      }
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
    * Method that displays the interactions that the user
    * can have with the authoring entity
    */
   public void authoringEntitiesMenu(){
      System.out.println( "\n-----Authoring Entities Menu-----\nPlease select an option.\n" );
      System.out.println( "1. Display all Authoring Entities" );
      System.out.println( "2. Add an Authoring Entities" );
      System.out.println( "3. List information about Writing Groups" );
      System.out.println( "4. Return to Main Menu\n" );
      System.out.println( "5. Exit\n" );

      System.out.println("Option: ");
      int userChoice = getIntRange(1, 5);

      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            repeatMenu = false;
         }
         if (userChoice == 2){
            //TODO: addAuthoringEntities()
            repeatMenu = false;
         }
         if (userChoice == 3){
            //TODO: listInfoWritingGroups()
            repeatMenu = false;
         }
         if (userChoice == 4){
//        // displayMainMenu();
            repeatMenu = false;
         }
         if (userChoice == 5){
            repeatMenu = false;
         }
      }
      while(repeatMenu);
   }


   public void displayAuthoringEntities(){
      List<Authoring_Entities> authoringEntities = em.createNamedQuery("displayAllAuthoringEntities", Authoring_Entities.class).getResultList();
      System.out.println("List of all Authoring Entities");
      System.out.println("EMAIL    AUTHORING_TYPE     NAME     HEAD_WRITER    YEAR_FORMED");

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
    * show all primary keys"
    */
   public void displayPrimaryKeys(){
      System.out.println("Books");
      List<Books> booksList= em.createNamedQuery("bookIsbn",Books.class).getResultList();
      for(Books book:booksList){
         System.out.println(book.getIsbn());
      }
      System.out.println("Publishers");
      List<Publishers> publishersList =  em.createNamedQuery("publishers",Publishers.class).getResultList();
      for(Publishers publishers:publishersList){
         System.out.println(publishers.getName());
      }
      System.out.println("Authoring Entities");
      List<Authoring_Entities> authoringList= em.createNamedQuery("authoring", Authoring_Entities.class).getResultList();
      for(Authoring_Entities auth:authoringList){
         System.out.println(auth.getEmail());
      }

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



      booking.deleteBook();
      tx.commit();


   }





}