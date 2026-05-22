import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class LoginFrame extends JFrame {

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JButton        loginBtn;
    private Library        library;

    // Design tokens
    static final Color BG_DARK      = new Color(15,  23,  42);   // slate-900
    static final Color BG_CARD      = new Color(30,  41,  59);   // slate-800
    static final Color ACCENT       = new Color(245, 158,  11);  // amber-500
    static final Color ACCENT_DARK  = new Color(180, 110,   5);  // amber-700
    static final Color TEXT_PRIMARY = new Color(248, 250, 252);  // slate-50
    static final Color TEXT_MUTED   = new Color(148, 163, 184);  // slate-400
    static final Color BORDER_COLOR = new Color(51,  65,  85);   // slate-700
    static final Color INPUT_BG     = new Color(15,  23,  42);   // slate-900
    static final Font  FONT_DISPLAY = new Font("Georgia", Font.BOLD, 22);
    static final Font  FONT_LABEL   = new Font("SansSerif", Font.PLAIN, 12);
    static final Font  FONT_BTN     = new Font("SansSerif", Font.BOLD, 13);

    public LoginFrame(Library library) {
        this.library = library;
        setTitle("Library Management System");
        setSize(440, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(BG_DARK);
        buildUI();
    }

    private void buildUI() {
        // Root panel — dark background
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG_DARK);

        // Card panel
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                // Subtle top accent line
                g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
                g2.dispose();
            }
        };
        card.setLayout(new GridBagLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(40, 44, 40, 44));
        card.setPreferredSize(new Dimension(380, 420));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(0, 0, 0, 0);
        g.gridx = 0; g.gridy = 0; g.weightx = 1.0;

        // Icon + Title
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("📖");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 40));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(icon);
        header.add(Box.createVerticalStrut(10));

        JLabel titleLabel = new JLabel("Library System");
        titleLabel.setFont(FONT_DISPLAY);
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(titleLabel);
        header.add(Box.createVerticalStrut(6));

        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setFont(FONT_LABEL);
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(subtitle);

        g.insets = new Insets(0, 0, 28, 0);
        card.add(header, g);

        // Username
        g.gridy = 1; g.insets = new Insets(0, 0, 6, 0);
        card.add(makeFieldLabel("USERNAME"), g);

        g.gridy = 2; g.insets = new Insets(0, 0, 16, 0);
        usernameField = makeStyledTextField();
        card.add(usernameField, g);

        // Password
        g.gridy = 3; g.insets = new Insets(0, 0, 6, 0);
        card.add(makeFieldLabel("PASSWORD"), g);

        g.gridy = 4; g.insets = new Insets(0, 0, 28, 0);
        passwordField = makeStyledPasswordField();
        card.add(passwordField, g);

        // Login button
        g.gridy = 5; g.insets = new Insets(0, 0, 0, 0);
        loginBtn = makeStyledButton("Sign In");
        card.add(loginBtn, g);

        loginBtn.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin());

        // Hint
        g.gridy = 6; g.insets = new Insets(16, 0, 0, 0);
        JLabel hint = new JLabel("Default admin: admin / admin123");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        hint.setForeground(TEXT_MUTED);
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(hint, g);

        root.add(card);
        setContentPane(root);
    }

    private JLabel makeFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    private JTextField makeStyledTextField() {
        JTextField f = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INPUT_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        styleInputField(f);
        return f;
    }

    private JPasswordField makeStyledPasswordField() {
        JPasswordField f = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INPUT_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        styleInputField(f);
        return f;
    }

    private void styleInputField(JTextField f) {
        f.setOpaque(false);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("SansSerif", Font.PLAIN, 14));
        f.setPreferredSize(new Dimension(0, 44));
        f.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(BORDER_COLOR, 10, 1),
            new EmptyBorder(0, 14, 0, 14)
        ));
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(ACCENT, 10, 2),
                    new EmptyBorder(0, 14, 0, 14)
                ));
            }
            public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(BORDER_COLOR, 10, 1),
                    new EmptyBorder(0, 14, 0, 14)
                ));
            }
        });
    }

    private JButton makeStyledButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? ACCENT_DARK : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(new Color(15, 23, 42));
        btn.setFont(FONT_BTN);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(0, 46));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void doLogin() {
        String name = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        User user = library.login(name, pass);
        if (user == null) {
            // Shake the window on bad login
            Point orig = getLocation();
            Timer t = new Timer(30, null);
            int[] step = {0};
            int[] offsets = {-8, 8, -6, 6, -4, 4, -2, 2, 0};
            t.addActionListener(e -> {
                if (step[0] < offsets.length) {
                    setLocation(orig.x + offsets[step[0]], orig.y);
                    step[0]++;
                } else {
                    setLocation(orig);
                    ((Timer) e.getSource()).stop();
                }
            });
            t.start();
            JOptionPane.showMessageDialog(this,
                "Wrong username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            return;
        }

        dispose();
        new DashboardFrame(library, user).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Library lib = new Library();
            new LoginFrame(lib).setVisible(true);
        });
    }

    static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int   radius;
        private final int   thickness;

        RoundedBorder(Color color, int radius, int thickness) {
            this.color = color; this.radius = radius; this.thickness = thickness;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) { return new Insets(thickness, thickness, thickness, thickness); }
    }
}