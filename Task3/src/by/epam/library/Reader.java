package by.epam.library;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Reader extends Thread{

    //private static ReentrantLock locker = new ReentrantLock(); // заглушка
    private ArrayList<Book> books;
    private int numberOfBooks;

    private Library library;
    private String[] desiredBooks;
    private String[] returnedBooks;

    private Reader receiver;
    private Book[] transferredBooks;


    public Reader(String name, Library library){
        setName(name);

        numberOfBooks = 0;
        books = new ArrayList<>();
        this.library = library;

        desiredBooks = new String[0];
        returnedBooks = new String[0];

        receiver = null;
        transferredBooks = new Book[0];
    }


    private void takeBook(String bookName) {

        try {
            Book book = library.getBook(bookName, this);
            if(numberOfBooks >= library.getBooksReaderLimit()){
                System.out.println(this.getName() + ": \t(Превышен лимит книг) " + bookName + " - недоступна!");
                return;
            }
            else if(book == null){
                System.out.println(this.getName() + ": \t(Книга " + bookName + " отсутствует в библиотеке)");
                return;
            }
            books.add(book);
            numberOfBooks++;
            System.out.println("Читатель " + this.getName() + " взял книгу " + book.getName() + ".");
        } catch (Exception e) {
            System.out.println("Exception! At Reader.class, method takeBook(String bookName); " + e.getMessage());
        }

    }


    private void returnBook(String bookName) {

        Book desiredBook = null;
        try {

            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getName().compareTo(bookName) == 0) {
                    desiredBook = books.get(i);
                    books.remove(i);
                }
            }
            // Проверяем нашел ли читатель у себя книгу, которую хочет вернуть
            if(desiredBook == null){
                System.out.println("Читатель " + this.getName() +
                        " не может вернуть книгу " + bookName
                        + " в библиотеку, т.к. у него этой книги нет.");
                return;
            }
            library.addBook(desiredBook);
            numberOfBooks--;

        } catch (Exception e) {
            System.out.println("Exception! At Reader.class, method returnBook(String bookName); " + e.getMessage());
        }

        System.out.println("Читатель " + this.getName() +
                " вернул книгу " + bookName
                + " в библиотеку.");

    }


    @Override
    public void run() {

        library.lockResource();

        // Читатель возвращает книги в библиотеку (если требуется)
        for(int i = 0; i < returnedBooks.length; i++){
            returnBook(returnedBooks[i]);
        }

        // Читатель берёт книги в библиотеке (если требуется)
        for(int i = 0; i < desiredBooks.length; i++){
            takeBook(desiredBooks[i]);
        }

        // Читатель передает книги другому читателю посредством читального зала (если требуется)
        if(transferredBooks.length > 0 && receiver != null)
            library.enterReadingHall().transferBooks(this, this.receiver, this.transferredBooks);



        library.unlockResource();

    }


    public void setTransferredBooksTo(Reader receiver, Book...args){
        this.receiver = receiver;
        this.transferredBooks = args;
    }


    public void setDesiredBooks(String...args){
        desiredBooks = args;
    }


    public void setReturnedBooks(String...args){
        returnedBooks = args;
    }


    public void addBooks(String...args){
        try {

            for (int i = 0; i < args.length; i++)
                books.add(new Book(args[i]));

        } catch(Exception e) {
            System.out.println("Exception! At Reader.class, method addBooks(String...args); " +  e.getMessage());
        }
    }
    public void addBooks(Book...args){
        try {

            for (int i = 0; i < args.length; i++)
                books.add(args[i]);

        } catch(Exception e) {
            System.out.println("Exception! At Reader.class, method addBooks(Book...args); " +  e.getMessage());
        }
    }


    public String[] removeBooks(String...args){

        String[] removedBooks = new String[0];

        try {

            int size = 0;
            for (int q = 0; q < books.size(); q++) {
                for (int i = 0; i < args.length; i++) {
                    if (books.get(q).getName().compareTo(args[i]) == 0) {
                        size++;
                    }
                }
            }

            removedBooks = new String[size];
            int iterator = 0;

            for (int q = 0; q < books.size(); q++) {
                for (int i = 0; i < args.length; i++) {
                    if (books.get(q).getName().compareTo(args[i]) == 0) {
                        removedBooks[iterator] = books.get(q).getName();
                        iterator++;
                        books.remove(q);
                    }
                }
            }

        } catch(Exception e) {
            System.out.println("Exception! At Reader.class, method removeBooks(String...args); " +  e.getMessage());
        }


        return removedBooks;

    }
    public Book[] removeBooks(Book...args){

        Book[] removedBooks = new Book[0];

        try {

            int size = 0;
            for (int q = 0; q < books.size(); q++) {
                for (int i = 0; i < args.length; i++) {
                    if (books.get(q).getName().compareTo(args[i].getName()) == 0) {
                        size++;
                    }
                }
            }

            removedBooks = new Book[size];
            int iterator = 0;

            for (int q = books.size()-1; q >= 0; q--) {
                for (int i = 0; i < args.length; i++) {
                    if (books.get(q).getName().compareTo(args[i].getName()) == 0) {
                        removedBooks[iterator] = books.get(q);
                        iterator++;
                        books.remove(q);
                        break;
                    }
                }
            }

        } catch(Exception e) {
            System.out.println("Exception! At Reader.class, method removeBooks(Book...args); " +  e.getMessage());
            e.printStackTrace();
        }


        return removedBooks;

    }


    public int getNumberOfBooks() {
        return this.numberOfBooks;
    }


    public void showBooks(){

        if(books.size() <= 0){
            System.out.print(this.getName() + " has no books.");
        }
        else {
            System.out.print(this.getName() + "'s Books: ");
            for (int i = 0; i < books.size(); i++) {
                System.out.print(books.get(i).getName() + "; ");
            }
            System.out.println();
        }
    }


}
