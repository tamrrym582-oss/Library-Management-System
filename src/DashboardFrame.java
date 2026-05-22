import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {

    private Library library;
    private User    currentUser;

    static final Color BG_DARK      = new Color(15,  23,  42);
    static final Color BG_CARD      = new Color(30,  41,  59);
    static final Color BG_SIDEBAR   = new Color(22,  33,  55);
    static final Color ACCENT       = new Color(245, 158,  11);
    static final Color ACCENT_HOVER = new Color(180, 110,   5);
    static final Color SUCCESS      = new Color( 34, 197,  94);
    static final Color DANGER       = new Color(239,  68,  68);
    static final Color INFO         = new Color( 59, 130, 246);
    static final Color TEXT_PRIMARY = new Color(248, 250, 252);
    static final Color TEXT_MUTED   = new Color(148, 163, 184);
    static final Color BORDER_COLOR = new Color( 51,  65,  85);
    static final Color INPUT_BG     = new Color(15,  23,  42);
    static final Color ROW_ALT      = new Color( 22,  33,  55);
    static final Color ROW_HOVER    = new Color( 44,  60,  88);
    static final Color WHITE        = Color.WHITE;

    public DashboardFrame(Library library, User currentUser) {
        this.library     = library;
        this.currentUser = currentUser;

        String roleLabel = currentUser.getRole().substring(0, 1).toUpperCase()
                         + currentUser.getRole().substring(1);
        setTitle("Library System  —  " + currentUser.getName() + " · " + roleLabel);
        setSize(1060, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);

        root.add(buildTopBar(), BorderLayout.NORTH);
        root.add(buildCustomTabs(), BorderLayout.CENTER);

        setContentPane(root);
    }

    //  nav bar
   
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(BG_SIDEBAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Bottom border line
                g2.setColor(BORDER_COLOR);
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);
                // Left amber bar
                g2.setColor(ACCENT);
                g2.fillRect(0, 0, 4, getHeight());
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(0, 58));
        bar.setBorder(new EmptyBorder(0, 20, 0, 16));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel bookIcon = new JLabel("📖");
        bookIcon.setFont(new Font("SansSerif", Font.PLAIN, 22));
        left.add(bookIcon);

        JLabel title = new JLabel("Library Management");
        title.setFont(new Font("Georgia", Font.BOLD, 17));
        title.setForeground(TEXT_PRIMARY);
        left.add(title);

        JLabel sep = new JLabel("  |  ");
        sep.setForeground(BORDER_COLOR);
        left.add(sep);

        left.add(makeRoleBadge(currentUser.getRole()));

        bar.add(left, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        JLabel userInfo = new JLabel("👤 " + currentUser.getName());
        userInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        userInfo.setForeground(TEXT_MUTED);
        right.add(userInfo);

        JButton logoutBtn = makeDangerButton("Sign Out");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(library).setVisible(true);
        });
        right.add(logoutBtn);

        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JLabel makeRoleBadge(String role) {
        Color bg;
        switch (role) {
            case "admin":     bg = new Color(124, 58,  237); break; // purple
            case "librarian": bg = new Color( 59, 130, 246); break; // blue
            default:          bg = new Color( 22, 163, 74);  break; // green
        }
        JLabel badge = new JLabel("  " + role.toUpperCase() + "  ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setForeground(WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 10));
        badge.setBorder(new EmptyBorder(3, 2, 3, 2));
        return badge;
    }

   
    private JPanel buildCustomTabs() {
        // Collect tab definitions
        java.util.List<String>  labels = new java.util.ArrayList<>();
        java.util.List<JPanel>  pages  = new java.util.ArrayList<>();

        labels.add("📚  Books");          pages.add(buildBooksTab());
        labels.add("🔍  Search");         pages.add(buildSearchTab());
        labels.add("🔄  Borrow / Return");pages.add(buildBorrowTab());
        labels.add("📋  Transactions");   pages.add(buildTransactionsTab());
        if (!currentUser.getRole().equals("member")) {
            labels.add("👥  Users");      pages.add(buildUsersTab());
        }
        if (currentUser.getRole().equals("admin")) {
            labels.add("⚠️  Overdue");    pages.add(buildOverdueTab());
        }

        // Content area with CardLayout
        CardLayout cards = new CardLayout();
        JPanel content = new JPanel(cards);
        content.setBackground(BG_DARK);
        for (int i = 0; i < pages.size(); i++) {
            content.add(pages.get(i), labels.get(i));
        }

        JPanel strip = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(BG_SIDEBAR);
                g.fillRect(0, 0, getWidth(), getHeight());
                // bottom border
                g.setColor(BORDER_COLOR);
                g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        strip.setOpaque(false);
        strip.setPreferredSize(new Dimension(0, 46));

        int[] selected = {0};
        JLabel[] tabLabels = new JLabel[labels.size()];

        for (int i = 0; i < labels.size(); i++) {
            final int idx = i;
            JLabel tab = new JLabel(labels.get(i)) {
                private boolean hovered = false;
                {
                    addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                        public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                        public void mouseClicked(MouseEvent e) {
                            selected[0] = idx;
                            cards.show(content, labels.get(idx));
                            for (JLabel t : tabLabels) t.repaint();
                        }
                    });
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    boolean active = (selected[0] == idx);
                    // Background
                    g2.setColor(active ? BG_DARK : (hovered ? new Color(30, 44, 65) : BG_SIDEBAR));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    // Amber underline for active tab
                    if (active) {
                        g2.setColor(ACCENT);
                        g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                    }
                    // Right separator
                    g2.setColor(BORDER_COLOR);
                    g2.fillRect(getWidth() - 1, 8, 1, getHeight() - 16);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            tab.setForeground(i == 0 ? TEXT_PRIMARY : TEXT_MUTED);
            tab.setFont(new Font("SansSerif", i == 0 ? Font.BOLD : Font.PLAIN, 13));
            tab.setBorder(new EmptyBorder(0, 18, 0, 18));
            tab.setPreferredSize(new Dimension(tab.getPreferredSize().width + 36, 46));
            tab.setOpaque(false);

            // Update font/color when selection changes
            MouseAdapter selector = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < tabLabels.length; j++) {
                        boolean isActive = (selected[0] == j);
                        tabLabels[j].setForeground(isActive ? TEXT_PRIMARY : TEXT_MUTED);
                        tabLabels[j].setFont(new Font("SansSerif", isActive ? Font.BOLD : Font.PLAIN, 13));
                    }
                }
            };
            tab.addMouseListener(selector);

            tabLabels[i] = tab;
            strip.add(tab);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_DARK);
        wrapper.add(strip,   BorderLayout.NORTH);
        wrapper.add(content, BorderLayout.CENTER);
        return wrapper;
    }


    private JPanel buildBooksTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        // Table
        String[] cols = {"Title", "Author", "Genre", "ISBN", "Available", "Total"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);
        refreshBooksTable(model);

        JScrollPane scroll = styledScroll(table);
        panel.add(scroll, BorderLayout.CENTER);

        if (!currentUser.getRole().equals("member")) {
            JPanel toolbar = buildToolbar(
                new Object[]{
                    "+ Add Book",    ACCENT,              (Runnable)(() -> { addBookDialog(); refreshBooksTable(model); }),
                    "✎ Edit",        INFO,                (Runnable)(() -> {
                        int row = table.getSelectedRow();
                        if (row < 0) { toast("Select a book first."); return; }
                        editBookDialog((String) model.getValueAt(row, 3));
                        refreshBooksTable(model);
                    }),
                    "✕ Remove",      DANGER,              (Runnable)(() -> {
                        int row = table.getSelectedRow();
                        if (row < 0) { toast("Select a book first."); return; }
                        String isbn = (String) model.getValueAt(row, 3);
                        String ttl  = (String) model.getValueAt(row, 0);
                        if (confirm("Remove \"" + ttl + "\"?")) {
                            library.removeBook(isbn);
                            refreshBooksTable(model);
                        }
                    }),
                    "↺ Refresh",     new Color(71, 85, 105), (Runnable)(() -> refreshBooksTable(model))
                }
            );
            panel.add(toolbar, BorderLayout.SOUTH);
        }

        return panel;
    }

    private void refreshBooksTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Book b : library.getAllBooks()) {
            model.addRow(new Object[]{
                b.getTitle(), b.getAuthor(), b.getGenre(),
                b.getIsbn(), b.getAvailableCopies(), b.getTotalCopies()
            });
        }
    }

    private void addBookDialog() {
        JTextField titleF  = new JTextField(20);
        JTextField authorF = new JTextField(20);
        JTextField genreF  = new JTextField(20);
        JTextField isbnF   = new JTextField(20);
        JTextField copiesF = new JTextField("1", 20);

        JPanel p = styledFormPanel(
            "Title",  titleF, "Author", authorF,
            "Genre",  genreF, "ISBN",   isbnF, "Copies", copiesF
        );

        if (JOptionPane.showConfirmDialog(this, p, "Add New Book",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        try {
            library.addBook(new Book(
                titleF.getText().trim(), authorF.getText().trim(),
                genreF.getText().trim(), isbnF.getText().trim(),
                Integer.parseInt(copiesF.getText().trim())
            ));
        } catch (NumberFormatException ex) {
            toast("Copies must be a number.");
        }
    }

    private void editBookDialog(String isbn) {
        Book b = library.findBookByIsbn(isbn);
        if (b == null) return;

        JTextField titleF  = new JTextField(b.getTitle(),  20);
        JTextField authorF = new JTextField(b.getAuthor(), 20);
        JTextField genreF  = new JTextField(b.getGenre(),  20);
        JTextField copiesF = new JTextField(String.valueOf(b.getTotalCopies()), 20);

        JPanel p = styledFormPanel(
            "Title", titleF, "Author", authorF,
            "Genre", genreF, "Copies", copiesF
        );

        if (JOptionPane.showConfirmDialog(this, p, "Edit Book",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        b.setTitle(titleF.getText().trim());
        b.setAuthor(authorF.getText().trim());
        b.setGenre(genreF.getText().trim());
        try { b.setTotalCopies(Integer.parseInt(copiesF.getText().trim())); }
        catch (NumberFormatException ignored) {}
    }


    private JPanel buildSearchTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        // Search bar
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        searchBar.setBackground(BG_SIDEBAR);
        searchBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel lbl = new JLabel("🔍");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchBar.add(lbl);

        JTextField searchField = makeInputField(28);
        searchField.putClientProperty("JTextField.placeholderText", "Search by title, author or genre…");
        searchBar.add(searchField);

        JButton searchBtn = makeAccentButton("Search");
        searchBar.add(searchBtn);

        String[] cols = {"Title", "Author", "Genre", "ISBN", "Available"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);

        Runnable doSearch = () -> {
            String kw = searchField.getText().trim();
            model.setRowCount(0);
            for (Book b : library.searchBooks(kw)) {
                model.addRow(new Object[]{
                    b.getTitle(), b.getAuthor(), b.getGenre(),
                    b.getIsbn(), b.getAvailableCopies()
                });
            }
        };

        searchBtn.addActionListener(e -> doSearch.run());
        searchField.addActionListener(e -> doSearch.run());

        panel.add(searchBar,       BorderLayout.NORTH);
        panel.add(styledScroll(table), BorderLayout.CENTER);
        return panel;
    }

   
    private JPanel buildBorrowTab() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG_DARK);

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.weightx = 1; g.weighty = 1;
        g.insets = new Insets(30, 30, 30, 15);
        g.gridx = 0;
        outer.add(buildActionCard("Borrow Book", "📥", ACCENT, (card) -> {
            JTextField isbnF   = makeInputField(18);
            JTextField memberF = makeInputField(18);
            if (currentUser.getRole().equals("member")) {
                memberF.setText(String.valueOf(currentUser.getId()));
                memberF.setEditable(false);
                memberF.setForeground(TEXT_MUTED);
            }
            JLabel msg = makeStatusLabel();
            JButton btn = makeAccentButton("Confirm Borrow");
            btn.addActionListener(e -> {
                try {
                    boolean ok = library.borrowBook(
                        Integer.parseInt(memberF.getText().trim()),
                        isbnF.getText().trim()
                    );
                    setStatus(msg, ok, ok ? "✓ Borrowed successfully!" : "✗ Could not borrow.");
                    if (ok) isbnF.setText("");
                } catch (NumberFormatException ex) {
                    setStatus(msg, false, "✗ Invalid Member ID.");
                }
            });
            card.add(makeFormRow("Book ISBN",  isbnF));
            card.add(Box.createVerticalStrut(12));
            card.add(makeFormRow("Member ID", memberF));
            card.add(Box.createVerticalStrut(20));
            card.add(btn);
            card.add(Box.createVerticalStrut(10));
            card.add(msg);
        }), g);

        g.gridx = 1;
        g.insets = new Insets(30, 15, 30, 30);
        outer.add(buildActionCard("Return Book", "📤", DANGER, (card) -> {
            JTextField txnF = makeInputField(18);
            JLabel msg      = makeStatusLabel();
            JButton btn     = makeDangerButton("Confirm Return");
            btn.addActionListener(e -> {
                try {
                    boolean ok = library.returnBook(Integer.parseInt(txnF.getText().trim()));
                    setStatus(msg, ok, ok ? "✓ Returned successfully!" : "✗ Transaction not found.");
                    if (ok) txnF.setText("");
                } catch (NumberFormatException ex) {
                    setStatus(msg, false, "✗ Invalid Transaction ID.");
                }
            });
            card.add(makeFormRow("Transaction ID", txnF));
            card.add(Box.createVerticalStrut(20));
            card.add(btn);
            card.add(Box.createVerticalStrut(10));
            card.add(msg);
        }), g);

        return outer;
    }

    interface CardBuilder { void build(JPanel card); }

    private JPanel buildActionCard(String title, String icon, Color accent, CardBuilder builder) {
        JPanel wrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, getWidth(), 4, 4, 4);
                g2.dispose();
            }
        };
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(28, 32, 28, 32));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 36));
        iconLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(iconLbl);
        wrapper.add(Box.createVerticalStrut(8));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 18));
        titleLbl.setForeground(TEXT_PRIMARY);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(titleLbl);
        wrapper.add(Box.createVerticalStrut(24));

        builder.build(wrapper);
        return wrapper;
    }

    private JPanel makeFormRow(String label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(label.toUpperCase());
        lbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        lbl.setForeground(TEXT_MUTED);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(lbl, BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JLabel makeStatusLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void setStatus(JLabel lbl, boolean ok, String text) {
        lbl.setForeground(ok ? SUCCESS : DANGER);
        lbl.setText(text);
    }

    
    private JPanel buildTransactionsTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        String[] cols = {"TXN#", "Member", "Book", "Borrow Date", "Due Date", "Status", "Fine (EGP)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        // Color status cells
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String s = String.valueOf(val);
                if (s.equals("OVERDUE")) { c.setForeground(DANGER); c.setFont(c.getFont().deriveFont(Font.BOLD)); }
                else if (s.equals("Returned")) c.setForeground(SUCCESS);
                else c.setForeground(INFO);
                return c;
            }
        });

        refreshTxnTable(model);

        JPanel toolbar = buildToolbar(new Object[]{
            "↺ Refresh", new Color(71, 85, 105), (Runnable)(() -> refreshTxnTable(model))
        });
        panel.add(toolbar,             BorderLayout.NORTH);
        panel.add(styledScroll(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshTxnTable(DefaultTableModel model) {
        model.setRowCount(0);
        ArrayList<Transaction> list = currentUser.getRole().equals("member")
                ? library.getMemberTransactions(currentUser.getId())
                : library.getAllTransactions();

        for (Transaction t : list) {
            String status = t.isReturned() ? "Returned" : (t.isOverdue() ? "OVERDUE" : "Active");
            double fine = t.calculateFine();
            model.addRow(new Object[]{
                t.getTransactionId(), t.getMemberName(), t.getBookTitle(),
                t.getBorrowDate(), t.getDueDate(),
                status,
                fine > 0 ? String.format("%.1f", fine) : "—"
            });
        }
    }

    private JPanel buildUsersTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        String[] cols = {"ID", "Name", "Role"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);

        // Role cell renderer
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                String r = String.valueOf(val);
                if (r.equals("admin"))         c.setForeground(new Color(167, 139, 250));
                else if (r.equals("librarian")) c.setForeground(INFO);
                else                            c.setForeground(SUCCESS);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
                return c;
            }
        });

        refreshUsersTable(model);

        JPanel toolbar = buildToolbar(new Object[]{
            "+ Add User",   ACCENT,                (Runnable)(() -> { addUserDialog(); refreshUsersTable(model); }),
            "✕ Remove",     DANGER,                (Runnable)(() -> {
                int row = table.getSelectedRow();
                if (row < 0) { toast("Select a user first."); return; }
                int uid = (int) model.getValueAt(row, 0);
                String nm = (String) model.getValueAt(row, 1);
                if (confirm("Remove user \"" + nm + "\"?")) {
                    library.removeUser(uid);
                    refreshUsersTable(model);
                }
            }),
            "↺ Refresh",    new Color(71, 85, 105), (Runnable)(() -> refreshUsersTable(model))
        });

        panel.add(toolbar,             BorderLayout.SOUTH);
        panel.add(styledScroll(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshUsersTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (User u : library.getAllUsers()) {
            model.addRow(new Object[]{ u.getId(), u.getName(), u.getRole() });
        }
    }

    private void addUserDialog() {
        JTextField nameField = new JTextField(18);
        JPasswordField passField = new JPasswordField(18);
        String[] roles = {"member", "librarian", "admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        JPanel p = styledFormPanel("Name", nameField, "Password", passField, "Role", roleBox);

        if (JOptionPane.showConfirmDialog(this, p, "Add User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();
        int newId   = library.getAllUsers().size() + 1;

        User u;
        if (role.equals("admin"))         u = new Admin(newId, name, pass);
        else if (role.equals("librarian")) u = new Librarian(newId, name, pass);
        else                              u = new Member(newId, name, pass);

        library.addUser(u);
    }

    
    private JPanel buildOverdueTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_DARK);

        String[] cols = {"TXN#", "Member", "Book", "Due Date", "Days Late", "Fine (EGP)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        styleTable(table);

        // Highlight entire overdue rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                c.setForeground(sel ? WHITE : new Color(252, 165, 165)); // red-300
                c.setBackground(sel ? new Color(127, 29, 29) : new Color(69, 10, 10));
                ((JLabel) c).setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        Runnable load = () -> {
            model.setRowCount(0);
            for (Transaction txn : library.getOverdueTransactions()) {
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                        txn.getDueDate(), java.time.LocalDate.now());
                model.addRow(new Object[]{
                    txn.getTransactionId(), txn.getMemberName(), txn.getBookTitle(),
                    txn.getDueDate(), daysLate,
                    String.format("%.1f", txn.calculateFine())
                });
            }
        };
        load.run();

        JPanel toolbar = buildToolbar(new Object[]{
            "↺ Refresh", new Color(71, 85, 105), (Runnable) load
        });

        panel.add(toolbar,             BorderLayout.NORTH);
        panel.add(styledScroll(table), BorderLayout.CENTER);
        return panel;
    }



    /** Build a consistent dark toolbar with action buttons */
    private JPanel buildToolbar(Object[] actions) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        bar.setBackground(BG_SIDEBAR);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        for (int i = 0; i < actions.length; i += 3) {
            String  label   = (String)  actions[i];
            Color   color   = (Color)   actions[i + 1];
            Runnable action = (Runnable) actions[i + 2];
            JButton btn = makeToolbarButton(label, color);
            btn.addActionListener(e -> action.run());
            bar.add(btn);
        }
        return bar;
    }

    private JButton makeToolbarButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            private boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hov = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = hov ? bg.darker() : bg;
                g2.setColor(c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setBorder(new EmptyBorder(6, 16, 6, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton makeAccentButton(String text) {
        return makeToolbarButton(text, ACCENT);
    }

    private JButton makeDangerButton(String text) {
        return makeToolbarButton(text, DANGER);
    }

    private JTextField makeInputField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new LoginFrame.RoundedBorder(BORDER_COLOR, 8, 1),
            new EmptyBorder(6, 12, 6, 12)
        ));
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    new LoginFrame.RoundedBorder(ACCENT, 8, 2),
                    new EmptyBorder(6, 12, 6, 12)
                ));
            }
            public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    new LoginFrame.RoundedBorder(BORDER_COLOR, 8, 1),
                    new EmptyBorder(6, 12, 6, 12)
                ));
            }
        });
        return f;
    }

    private JScrollPane styledScroll(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(BG_DARK);
        scroll.setBackground(BG_DARK);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
        scroll.getVerticalScrollBar().setBackground(BG_SIDEBAR);
        scroll.getHorizontalScrollBar().setBackground(BG_SIDEBAR);
        return scroll;
    }

    static void styleTable(JTable table) {
        table.setBackground(BG_DARK);
        table.setForeground(TEXT_PRIMARY);
        table.setRowHeight(36);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setGridColor(BORDER_COLOR);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(44, 60, 88));
        table.setSelectionForeground(WHITE);

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                JLabel c = (JLabel) super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                c.setBackground(sel ? new Color(44, 60, 88) : (row % 2 == 0 ? BG_DARK : ROW_ALT));
                c.setForeground(sel ? WHITE : TEXT_PRIMARY);
                c.setBorder(new EmptyBorder(0, 12, 0, 12));
                return c;
            }
        });

        // Header
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_SIDEBAR);
        header.setForeground(TEXT_MUTED);
        header.setFont(new Font("SansSerif", Font.BOLD, 11));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setBorder(new EmptyBorder(0, 12, 0, 12));
    }

    /** Build a simple 2-column form panel with dark styling */
    private JPanel styledFormPanel(Object... pairs) {
        JPanel p = new JPanel(new GridLayout(pairs.length / 2, 2, 10, 10));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));
        p.setBackground(BG_CARD);
        for (Object o : pairs) {
            if (o instanceof String) {
                JLabel lbl = new JLabel((String) o + ":");
                lbl.setForeground(TEXT_PRIMARY);
                lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
                p.add(lbl);
            } else {
                p.add((Component) o);
            }
        }
        return p;
    }

    private void toast(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Notice", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean confirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // Legacy static helpers kept for compatibility
    static JButton makeButton(String text, Color bg) {
        DashboardFrame dummy = new DashboardFrame(null, null);
        return dummy.makeToolbarButton(text, bg);
    }
}