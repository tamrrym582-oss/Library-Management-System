import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {

    private static int counter = 1;   

    private int    transactionId;
    private int    memberId;
    private String memberName;
    private String bookIsbn;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;     
    private boolean returned;

    private static final int BORROW_DAYS = 14;

    private static final double FINE_PER_DAY = 5.0;

    public Transaction(int memberId, String memberName, String bookIsbn, String bookTitle) {
        this.transactionId = counter++;
        this.memberId   = memberId;
        this.memberName = memberName;
        this.bookIsbn   = bookIsbn;
        this.bookTitle  = bookTitle;
        this.borrowDate = LocalDate.now();
        this.dueDate    = borrowDate.plusDays(BORROW_DAYS);
        this.returnDate = null;
        this.returned   = false;
    }

    public int       getTransactionId() { return transactionId; }
    public int       getMemberId()      { return memberId; }
    public String    getMemberName()    { return memberName; }
    public String    getBookIsbn()      { return bookIsbn; }
    public String    getBookTitle()     { return bookTitle; }
    public LocalDate getBorrowDate()    { return borrowDate; }
    public LocalDate getDueDate()       { return dueDate; }
    public LocalDate getReturnDate()    { return returnDate; }
    public boolean   isReturned()       { return returned; }

    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.returned   = true;
    }

    public double calculateFine() {
        LocalDate checkDate = returned ? returnDate : LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(dueDate, checkDate);
        if (daysLate <= 0) return 0.0;
        return daysLate * FINE_PER_DAY;
    }

    public boolean isOverdue() {
        if (returned) return false;
        return LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        String status = returned ? "Returned (" + returnDate + ")" : (isOverdue() ? "OVERDUE" : "Active");
        double fine = calculateFine();
        String fineStr = fine > 0 ? String.format("  Fine: %.1f EGP", fine) : "";
        return String.format("TXN#%-4d Member: %-15s Book: %-25s Due: %s  Status: %s%s",
                transactionId, memberName, bookTitle, dueDate, status, fineStr);
    }
}
