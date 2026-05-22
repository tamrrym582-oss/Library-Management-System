// Admin can do everything
public class Admin extends User {

    public Admin(int id, String name, String password) {
        super(id, name, password, "admin");
    }

    public void manageSystemSettings() {
        System.out.println("[Admin] Accessing system settings...");
    }
}


// -------------------------------------------------------

class Librarian extends User {

    public Librarian(int id, String name, String password) {
        super(id, name, password, "librarian");
    }

    public void issueBook() {
        System.out.println("[Librarian] Issuing book...");
    }

    public void receiveReturn() {
        System.out.println("[Librarian] Receiving returned book...");
    }
}


// -------------------------------------------------------

class Member extends User {

    // how many books this member currently has borrowed
    private int borrowedCount;

    public Member(int id, String name, String password) {
        super(id, name, password, "member");
        this.borrowedCount = 0;
    }

    public int getBorrowedCount()   { return borrowedCount; }
    public void incrementBorrowed() { borrowedCount++; }
    public void decrementBorrowed() {
        if (borrowedCount > 0) borrowedCount--;
    }
}
