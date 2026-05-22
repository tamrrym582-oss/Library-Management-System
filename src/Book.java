public class Book {

    private String title;
    private String author;
    private String genre;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

    public Book(String title, String author, String genre, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    // getters
    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public String getGenre()  { return genre; }
    public String getIsbn()   { return isbn; }
    public int getTotalCopies()     { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    // setters
    public void setTitle(String title)   { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre)   { this.genre = genre; }
    public void setTotalCopies(int n)    { this.totalCopies = n; }

    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    @Override
    public String toString() {
        return String.format("%-30s %-20s %-15s %-15s Copies: %d/%d",
                title, author, genre, isbn, availableCopies, totalCopies);
    }
}
