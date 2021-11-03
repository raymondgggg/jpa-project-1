package com.example.jpaproject1;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
      System.out.println( "\n-----Books Menu-----\nPlease select an option.\n" );
      System.out.println( "1. Display all Books" );
      System.out.println( "2. Add a Book" );
      System.out.println( "3. Information about a BookList" );
      System.out.println( "4. Delete a Book" );
      System.out.println( "5. Update a Book" );
      System.out.println( "6. Return to Main Menu\n" );
      System.out.println( "7. Exit\n" );

      //get user input
      System.out.println("Option: ");
      int userChoice = getIntRange(1, 7);
      boolean repeatMenu = true;

      do{
         if (userChoice == 1){
            // TODO : displaybooks()
            booksMenu();
            repeatMenu = false;
         }
         if (userChoice == 2){
            //TODO: addBook()
            booksMenu();
            repeatMenu = false;

         }
         if (userChoice == 3){
            //TODO: listInfoBooks()
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
    * Method that displays the interactions that the user
    * can have with the publishers entity
    */
   public static void publishersMenu(){
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
            //TODO: displayPublishers()
            repeatMenu = false;
         }
         else if (userChoice == 2){
            //TODO: addPublisher()
            repeatMenu = false;

         }
         else if (userChoice == 3){
            //TODO: list information about publisher
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
    * Method to add a book object to the relational database
    */
   public static void addBook(){

   }

   /**
    * Method that displays the interactions that the user
    * can have with the authoring entity
    */
   public static void authoringEntitiesMenu(){
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
            //TODO: displayAuthoringEntities()
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
//            displayMainMenu();
            repeatMenu = false;
         }
         if (userChoice == 5){
            repeatMenu = false;
         }
      }
      while(repeatMenu);
   }

   public void displayPrimaryKeys(){
      System.out.println("Books");
      List<Books> booksList= em.createNamedQuery("books",Books.class).getResultList();
      for(Books book:booksList){
         System.out.println(book);
      }
      System.out.println("Publishers");
      List<Publishers> publishersList =  em.createNamedQuery("publishers",Publishers.class).getResultList();
      for(Publishers publishers:publishersList){
         System.out.println(publishersList);
      }
      System.out.println("Authoring Entities");
      List<Authoring_Entities> authoringList= em.createNamedQuery("authoring", Authoring_Entities.class).getResultList();
      for(Authoring_Entities auth:authoringList){
         System.out.println(auth);
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


   public static void main(String[] args) {
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("Booking");
      EntityManager manager = factory.createEntityManager();

      Booking db = new Booking(manager);
      db.displayBooks();
   }





}