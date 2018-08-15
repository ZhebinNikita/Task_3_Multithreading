package by.epam.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;



public class Library {

    private String name;
    private ArrayList<Book> books;
    private int booksReaderLimit; // Maximum number of books a reader can hold

    private Semaphore permission; // to block this resource by some Thread

    private ReadingHall readingHall;


    public Library(String name, int booksReaderLimit) {
        this.name = name;
        this.booksReaderLimit = booksReaderLimit;
        books = new ArrayList<>();
        readingHall = new ReadingHall();

        permission = new Semaphore(1); // 1 разрешение
    }


    public void addBook(Book book) {
        try {
            books.add(book);
        } catch(Exception e) {
            System.out.println("Exception! At Library.class, method addBook(Book book); " + e.getMessage());
        }
        finally {
            System.out.println("В библиотеку добавлена книга " + book.getName() + ".");
        }
    }


    public Book getBook(String bookName, Reader reader){

        try {

            // Библиотека проверяет сколько книг уже имеется у читателя
            if(reader.getNumberOfBooks() < this.booksReaderLimit) {
                for (int i = 0; i < books.size(); i++) {
                    if (books.get(i).getName().compareTo(bookName) == 0) {
                        Book desiredBook = books.get(i);
                        books.remove(i);

                        return desiredBook;
                    }
                }
            }
            else{
                return null;
            }

        } catch(Exception e) {
            System.out.println("Exception! At Library.class, method getBook(String bookName, Reader reader); " + e.getMessage());
        }
        return null;
    }


    public void lockResource(){
        try {
            permission.acquire();
        } catch(Exception e){
            System.out.println("Exception! At Library.class, method lockResource(); " + e.getMessage());
        }
    }
    public void unlockResource(){
        permission.release();
    }


    public ReadingHall enterReadingHall(){
        return this.readingHall;
    }


    public String getName() {
        return name;
    }


    public int getBooksReaderLimit() {
        return booksReaderLimit;
    }


    public List<Book> getBooks() {
        return Collections.unmodifiableList(this.books);
    }


    public void showBooks(){

        if(books.size() <= 0){
            System.out.print("There're no books in the library.");
        }
        else {
            System.out.print("Library Books: ");
            for (int i = 0; i < books.size(); i++) {
                System.out.print(books.get(i).getName() + "; ");
            }
            System.out.println();
        }
    }


}
