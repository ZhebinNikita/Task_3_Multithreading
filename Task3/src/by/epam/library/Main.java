package by.epam.library;


public class Main {

    public static void main(String[] args) {

        Library nationalLibrary = new Library("National Library of Belarus", 3);
        for(int i = 1; i <= 10; i++){
            nationalLibrary.addBook(new Book("Book" + i));
        }
        System.out.println();


        Reader antoshka = new Reader("Antoshka", nationalLibrary);
        Reader nikita = new Reader("Nikita", nationalLibrary);
        Reader kristina = new Reader("Kristina", nationalLibrary);


        // take & return books example
        /*antoshka.setDesiredBooks("Book1", "Book2", "Book3");
        antoshka.addBooks("RBook1", "RBook2", "RBook3");
        antoshka.setReturnedBooks("RBook1", "RBook2", "RBook3");

        nikita.setDesiredBooks("Book1", "Book2", "Book3");

        kristina.setDesiredBooks("Book1", "Book2", "Book3", "Book4", "Book5", "Book6", "Book7");
*/

        // transfer books in a Reading Hall example
        antoshka.addBooks("b1", "b2", "b3");
        antoshka.setTransferredBooksTo(nikita, new Book("b1"), new Book("b2"), new Book("b3"));

        antoshka.start();
        nikita.start();
        kristina.start();






    }
}