import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Library lib = new Library();
            new LoginFrame(lib).setVisible(true);
        });
    }
}
