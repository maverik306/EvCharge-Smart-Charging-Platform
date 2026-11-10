package com.evcharge.app;


import com.evcharge.app.dao.*;
import com.evcharge.app.model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class DatabasePanel extends JPanel {

    private final MainFrame frame;

    private JTextArea console;
    private JTextField driverIdField, bookingIdField, reviewDriverId, reviewLocationId, reviewBookingId;
    private JComboBox<String> reviewRatingCombo;
    private JTextArea reviewCommentArea;

    public DatabasePanel(MainFrame frame) {
        this.frame = frame;
        setBackground(AppTheme.BG_DARK);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(28, 28, 28, 28));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(AppTheme.BG_DARK);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.weighty = 1;

        g.gridx = 0; g.weightx = 0.38; g.insets = new Insets(0, 0, 0, 18);
        body.add(buildOpsPanel(), g);

        g.gridx = 1; g.weightx = 0.62; g.insets = new Insets(0, 0, 0, 0);
        body.add(buildConsolePanel(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("MySQL Database", AppIcons.get(AppIcons.IconType.DATABASE, 22, AppTheme.ACCENT), JLabel.LEFT);
        title.setIconTextGap(8);
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Run live database operations — reads and writes to MySQL via your DAO classes");
        sub.setFont(AppTheme.FONT_BODY);
        sub.setForeground(AppTheme.TEXT_SEC);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(AppTheme.BG_DARK);
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(sub);

        JButton testConn = AppTheme.primaryBtn("Test Connection", AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.BG_DARK));
        testConn.addActionListener(e -> testConnection());

        JButton clearConsole = AppTheme.secondaryBtn("Clear Console", AppIcons.get(AppIcons.IconType.CLEAR, 14, AppTheme.TEXT_PRI));
        clearConsole.addActionListener(e -> console.setText(""));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btns.setBackground(AppTheme.BG_DARK);
        btns.add(clearConsole);
        btns.add(testConn);

        p.add(left,  BorderLayout.WEST);
        p.add(btns,  BorderLayout.EAST);
        return p;
    }

    // ── Operations panel (left) ───────────────────────────────────────────
    private JPanel buildOpsPanel() {
        JPanel wrap = new JPanel();
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setBackground(AppTheme.BG_DARK);

        wrap.add(buildDriverSection());
        wrap.add(Box.createVerticalStrut(14));
        wrap.add(buildBookingSection());
        wrap.add(Box.createVerticalStrut(14));
        wrap.add(buildReviewSection());

        JScrollPane sp = AppTheme.scrollPane(wrap);
        sp.setBorder(null);
        sp.getViewport().setBackground(AppTheme.BG_DARK);

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(AppTheme.BG_DARK);
        outer.add(sp, BorderLayout.CENTER);
        return outer;
    }

    private JPanel buildDriverSection() {
        JPanel card = AppTheme.card("Driver — Read", AppIcons.get(AppIcons.IconType.USER, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 10, 0);

        driverIdField = AppTheme.textField();
        driverIdField.setText("1");

        g.gridy = 0; form.add(AppTheme.formRow("Driver ID", driverIdField), g);

        JButton fetch = AppTheme.primaryBtn("Fetch Driver", AppIcons.get(AppIcons.IconType.USER, 14, AppTheme.BG_DARK));
        fetch.addActionListener(e -> fetchDriver());
        fetch.setPreferredSize(new Dimension(150, 38));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(fetch);

        card.add(form,   BorderLayout.CENTER);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildBookingSection() {
        JPanel card = AppTheme.card("Booking — Write", AppIcons.get(AppIcons.IconType.BOOKING, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 10, 0);

        bookingIdField = AppTheme.textField();
        bookingIdField.setText("1");

        g.gridy = 0; form.add(AppTheme.formRow("Booking ID (DB int)", bookingIdField), g);

        JLabel hint = new JLabel("Uses active driver/charger IDs (1,1) with COMPLETED status.");
        hint.setFont(AppTheme.FONT_SMALL);
        hint.setForeground(AppTheme.TEXT_MUTED);

        JButton save = AppTheme.primaryBtn("Save Booking", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        save.addActionListener(e -> saveBooking());
        save.setPreferredSize(new Dimension(150, 38));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(save);

        card.add(form,   BorderLayout.CENTER);
        card.add(hint,   BorderLayout.NORTH);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildReviewSection() {
        JPanel card = AppTheme.card("Review — Write", AppIcons.get(AppIcons.IconType.REVIEW, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 10, 0);

        reviewDriverId   = AppTheme.textField(); reviewDriverId.setText("1");
        reviewLocationId = AppTheme.textField(); reviewLocationId.setText("1");
        reviewBookingId  = AppTheme.textField(); reviewBookingId.setText("1");
        reviewRatingCombo = AppTheme.combo(new String[]{"5","4","3","2","1"});

        reviewCommentArea = new JTextArea(2, 10);
        reviewCommentArea.setFont(AppTheme.FONT_BODY);
        reviewCommentArea.setForeground(AppTheme.TEXT_PRI);
        reviewCommentArea.setBackground(AppTheme.BG_INPUT);
        reviewCommentArea.setCaretColor(AppTheme.ACCENT);
        reviewCommentArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        reviewCommentArea.setText("Fast charging and clean rest area.");

        JScrollPane cs = AppTheme.scrollPane(reviewCommentArea);
        cs.setPreferredSize(new Dimension(100, 70));

        g.gridy = 0; form.add(AppTheme.formRow("Driver ID",   reviewDriverId),   g);
        g.gridy = 1; form.add(AppTheme.formRow("Location ID", reviewLocationId), g);
        g.gridy = 2; form.add(AppTheme.formRow("Booking ID",  reviewBookingId),  g);
        g.gridy = 3; form.add(AppTheme.formRow("Rating",      reviewRatingCombo),g);
        g.gridy = 4; form.add(AppTheme.formRow("Comment",     cs),               g);

        JButton submit = AppTheme.primaryBtn("Submit Review", AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 14, AppTheme.BG_DARK));
        submit.addActionListener(e -> submitReview());
        submit.setPreferredSize(new Dimension(160, 38));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(submit);

        card.add(form,   BorderLayout.CENTER);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    // ── Console panel (right) ─────────────────────────────────────────────
    private JPanel buildConsolePanel() {
        JPanel card = AppTheme.card("Console Output", AppIcons.get(AppIcons.IconType.ANALYTICS, 16, AppTheme.TEXT_PRI));

        console = AppTheme.consoleArea();
        console.setText("[ EV Charging App — Database Console ]\n"
            + "Connect to MySQL and run operations here.\n\n");

        JScrollPane sp = AppTheme.scrollPane(console);
        sp.getViewport().setBackground(console.getBackground());

        card.add(sp, BorderLayout.CENTER);

        JLabel hint = new JLabel("  Output mirrors your System.out / DAO print statements.");
        hint.setFont(AppTheme.FONT_SMALL);
        hint.setForeground(AppTheme.TEXT_MUTED);
        hint.setBorder(new EmptyBorder(8, 0, 0, 0));
        card.add(hint, BorderLayout.SOUTH);
        return card;
    }

    // ── DB operations ─────────────────────────────────────────────────────
    private void testConnection() {
        log("\n=== Testing MySQL Connection ===");
        new Thread(() -> {
            try (java.sql.Connection conn = DBConnection.getConnection()) {
                if (conn != null) {
                    logSwing("[SUCCESS] Connected to MySQL successfully!");
                    logSwing("   URL: jdbc:mysql://localhost:3306/ev_charging_app");
                } else {
                    logSwing("[ERROR] Connection returned null.");
                }
            } catch (Exception ex) {
                logSwing("[ERROR] Connection failed: " + ex.getMessage());
            }
        }).start();
    }

    private void fetchDriver() {
        try {
            int id = Integer.parseInt(driverIdField.getText().trim());
            log("\n=== Fetching Driver (ID=" + id + ") ===");
            new Thread(() -> {
                // Redirect stdout to console
                java.io.PrintStream old = System.out;
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                System.setOut(new java.io.PrintStream(baos));
                new DriverDAO().printDriverDetails(id);
                System.setOut(old);
                logSwing(baos.toString());
            }).start();
        } catch (NumberFormatException e) {
            log("[WARN] Driver ID must be an integer.");
        }
    }

    private void saveBooking() {
        try {
            int id = Integer.parseInt(bookingIdField.getText().trim());
            log("\n=== Saving Booking (ID=" + id + ") ===");
            new Thread(() -> {
                java.io.PrintStream old = System.out;
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                System.setOut(new java.io.PrintStream(baos));
                boolean ok = new BookingDAO().saveBooking(id, 1, 1, "EMERGENCY", "COMPLETED");
                System.setOut(old);
                logSwing(baos.toString());
                if (!baos.toString().contains("SUCCESS") && !baos.toString().contains("saved"))
                    logSwing(ok ? "[SUCCESS] Booking " + id + " saved." : "[ERROR] Could not save booking.");
            }).start();
        } catch (NumberFormatException e) {
            log("[WARN] Booking ID must be an integer.");
        }
    }

    private void submitReview() {
        try {
            int dId = Integer.parseInt(reviewDriverId.getText().trim());
            int lId = Integer.parseInt(reviewLocationId.getText().trim());
            int bId = Integer.parseInt(reviewBookingId.getText().trim());
            int rating = Integer.parseInt((String) reviewRatingCombo.getSelectedItem());
            String comment = reviewCommentArea.getText().trim();
            log("\n=== Submitting Review (driver=" + dId + ", location=" + lId + ", booking=" + bId + ") ===");
            new Thread(() -> {
                java.io.PrintStream old = System.out;
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                System.setOut(new java.io.PrintStream(baos));
                new ReviewDAO().submitReview(dId, lId, bId, rating, comment);
                System.setOut(old);
                logSwing(baos.toString());
                if (!baos.toString().contains("SUCCESS") && !baos.toString().contains("Review submitted"))
                    logSwing("[ERROR] Review submission failed. Check console.");
            }).start();
        } catch (NumberFormatException e) {
            log("[WARN] IDs and rating must be integers.");
        }
    }

    private void log(String msg) {
        console.append(msg + "\n");
        console.setCaretPosition(console.getDocument().getLength());
    }

    private void logSwing(String msg) {
        SwingUtilities.invokeLater(() -> log(msg));
    }
}
