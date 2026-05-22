import java.util.ArrayList;

public class Library {

    private ArrayList<Book>        books;
    private ArrayList<User>        users;
    private ArrayList<Transaction> transactions;

    public Library() {
        books        = new ArrayList<>();
        users        = new ArrayList<>();
        transactions = new ArrayList<>();
        loadSampleData();
    }


    public void addBook(Book b) {
        // check if isbn already exists
        for (Book existing : books) {
            if (existing.getIsbn().equals(b.getIsbn())) {
                System.out.println("  A book with this ISBN already exists.");
                return;
            }
        }
        books.add(b);
        System.out.println("  Book added: " + b.getTitle());
    }

    public boolean removeBook(String isbn) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                System.out.println("  Removed: " + books.get(i).getTitle());
                books.remove(i);
                return true;
            }
        }
        System.out.println("  Book not found.");
        return false;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) return b;
        }
        return null;
    }

    public ArrayList<Book> searchBooks(String keyword) {
        ArrayList<Book> results = new ArrayList<>();
        String kw = keyword.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(kw)
                    || b.getAuthor().toLowerCase().contains(kw)
                    || b.getGenre().toLowerCase().contains(kw)) {
                results.add(b);
            }
        }
        return results;
    }

    public ArrayList<Book> getAllBooks() { return books; }


    public void addUser(User u) {
        users.add(u);
        System.out.println("  User added: " + u.getName() + " (" + u.getRole() + ")");
    }

    public boolean removeUser(int userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == userId) {
                System.out.println("  Removed user: " + users.get(i).getName());
                users.remove(i);
                return true;
            }
        }
        System.out.println("  User not found.");
        return false;
    }

    public User findUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public User login(String name, String password) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() { return users; }


    public boolean borrowBook(int memberId, String isbn) {
        User user = findUserById(memberId);
        Book book = findBookByIsbn(isbn);

        if (user == null) { System.out.println("  Member not found."); return false; }
        if (book == null) { System.out.println("  Book not found.");   return false; }
        if (!book.isAvailable()) {
            System.out.println("  Sorry, no copies available right now.");
            return false;
        }

        book.borrowBook();
        Transaction t = new Transaction(memberId, user.getName(), isbn, book.getTitle());
        transactions.add(t);

        if (user instanceof Member) {
            ((Member) user).incrementBorrowed();
        }

        System.out.println("  Borrowed! Due date: " + t.getDueDate());
        return true;
    }

    public boolean returnBook(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getTransactionId() == transactionId && !t.isReturned()) {
                t.markReturned();
                Book book = findBookByIsbn(t.getBookIsbn());
                if (book != null) book.returnBook();

                User user = findUserById(t.getMemberId());
                if (user instanceof Member) {
                    ((Member) user).decrementBorrowed();
                }

                double fine = t.calculateFine();
                if (fine > 0) {
                    System.out.printf("  Book returned. Fine to pay: %.1f EGP%n", fine);
                } else {
                    System.out.println("  Book returned. No fine. Thank you!");
                }
                return true;
            }
        }
        System.out.println("  Transaction not found or already returned.");
        return false;
    }


    public ArrayList<Transaction> getAllTransactions() { return transactions; }

    public ArrayList<Transaction> getMemberTransactions(int memberId) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getMemberId() == memberId) result.add(t);
        }
        return result;
    }

    public ArrayList<Transaction> getOverdueTransactions() {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.isOverdue()) result.add(t);
        }
        return result;
    }

    // ===================== SAMPLE DATA ============nnn=========

    private void loadSampleData() {
        // users
        users.add(new Admin(1, "admin", "admin123"));
        users.add(new Librarian(2, "sara", "sara123"));
        users.add(new Member(3, "ahmed", "ahmed123"));
        users.add(new Member(4, "mona", "mona123"));

        // books
        books.add(new Book("Clean Code",             "Robert Martin",  "Programming", "978-0132350884", 3));
        books.add(new Book("The Pragmatic Programmer","David Thomas",   "Programming", "978-0201616224", 2));
        books.add(new Book("Head First Java",         "Kathy Sierra",   "Programming", "978-0596009205", 4));
        books.add(new Book("1984",                    "George Orwell",  "Fiction",     "978-0451524935", 5));
        books.add(new Book("Brave New World",         "Aldous Huxley",  "Fiction",     "978-0060850524", 3));
        books.add(new Book("A Brief History of Time", "Stephen Hawking","Science",     "978-0553380163", 2));
        books.add(new Book("Thinking Fast and Slow",  "Daniel Kahneman","Psychology",  "978-0374533557", 2));
    }
}
