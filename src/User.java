public class User {

    protected int id;
    protected String name;
    protected String password;
    protected String role;   // "admin" / "librarian" / "member"

    public User(int id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int    getId()       { return id; }
    public String getName()     { return name; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }

    public void setName(String name)         { this.name = name; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return String.format("ID: %-5d Name: %-20s Role: %s", id, name, role);
    }
}
