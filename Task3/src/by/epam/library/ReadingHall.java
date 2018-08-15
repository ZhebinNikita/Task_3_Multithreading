package by.epam.library;


public class ReadingHall {

    private String name;


    public ReadingHall() {
        this.name = "Reading_Hall";
    }
    public ReadingHall(String name){
        this.name = name;
    }


    public void transferBooks(Reader sender, Reader receiver, Book...transferredBooks){

        try {
            // remove books from Sender
            Book[] books = sender.removeBooks(transferredBooks);

            if(books.length < transferredBooks.length){
                System.out.println(sender.getName() + ": у меня отсутствуют некоторые книги, которые я хочу передать " +
                        receiver.getName() + ".");
            }

            System.out.print(sender.getName() + " передал книги " + receiver.getName() + ":  ");
            for (Book book : books) {
                System.out.print("\"" + book.getName() + "\"" + "; ");
            }
            System.out.println();

            // add books to Receiver
            receiver.addBooks(books);
        }
        catch (Exception e){
            System.out.println("Exception! At ReadingHall.class, method transferBooks(); " +  e.getMessage());
            e.printStackTrace();
        }

    }


    public String getName() {
        return name;
    }


}
