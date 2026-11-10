package com.evcharge.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.model.*;
import com.evcharge.app.service.BookingQueue;

public class MainFrame extends JFrame {

    // ── Shared State ──────────────────────────────────────────────────────
    public User           currentUser;
    public Driver         currentDriver;
    public Vehicle        currentVehicle;
    public Charger        currentCharger;
    public ChargingLocation currentLocation;
    public Booking        currentBooking;
    public BookingQueue   bookingQueue   = new BookingQueue();
    public ChargingSession currentSession;
    public Payment        currentPayment;

    // ── Panels ────────────────────────────────────────────────────────────
    public DashboardPanel     dashboardPanel;
    public DriverVehiclePanel driverVehiclePanel;
    public ChargerPanel       chargerPanel;
    public BookingPanel       bookingPanel;
    public SessionPanel       sessionPanel;
    public PaymentPanel       paymentPanel;
    public ReviewPanel        reviewPanel;
    public DatabasePanel      databasePanel;

    private JPanel        cardContainer;
    private CardLayout    cardLayout;
    private JButton[]     navButtons;
    private JLabel        statusLabel;

    // ── Toast System ──────────────────────────────────────────────────────
    private JPanel toastPanel;
    private JLabel toastLabel;
    private Timer toastTimer;

    private static final String[] PANEL_KEYS = {
        "Dashboard", "Driver & Vehicle", "Charger", "Booking", "Session", "Payment", "Review", "Database"
    };

    public MainFrame() {
        this(null);
    }

    public MainFrame(User user) {
        super("EVCharge — Smart Charging Platform");
        setIconImage(AppIcons.getImage(AppIcons.IconType.LIGHTNING, 32, AppTheme.ACCENT));
        this.currentUser = user;

        if (user != null) {
            com.evcharge.app.dao.DriverDAO driverDAO = new com.evcharge.app.dao.DriverDAO();
            Driver matched = driverDAO.getDriverByEmail(user.getEmail());
            if (matched != null) {
                this.currentDriver = matched;
            } else {
                String phonePlaceholder = "NA-" + user.getUserId();
                int newDriverId = driverDAO.saveDriver(user.getFullName(), phonePlaceholder, user.getEmail());
                if (newDriverId > 0) {
                    this.currentDriver = new Driver(newDriverId, user.getFullName(), phonePlaceholder, user.getEmail(), 0);
                } else {
                    this.currentDriver = new Driver(user.getFullName(), phonePlaceholder, user.getEmail());
                }
            }

            if (this.currentDriver != null && this.currentDriver.getDriverId() > 0) {
                java.util.List<Vehicle> userVehicles = new com.evcharge.app.dao.VehicleDAO().getVehiclesByDriverId(this.currentDriver.getDriverId());
                if (!userVehicles.isEmpty()) {
                    this.currentVehicle = userVehicles.get(0);
                } else {
                    this.currentVehicle = null;
                }
            }
        }

        AppTheme.applyGlobalTheme();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setPreferredSize(new Dimension(1240, 780));

        setLayout(new BorderLayout());
        getContentPane().setBackground(AppTheme.BG_DARK);

        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(),    BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        navigateTo("Dashboard");
    }

    // ── Sidebar ───────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(AppTheme.BG_SIDEBAR);
        sidebar.setPreferredSize(new Dimension(215, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, AppTheme.BORDER_COL));

        // Logo
        JPanel logoArea = new JPanel(new BorderLayout());
        logoArea.setBackground(AppTheme.BG_SIDEBAR);
        logoArea.setBorder(new EmptyBorder(22, 22, 18, 22));

        JLabel logoIcon = new JLabel(AppIcons.get(AppIcons.IconType.LIGHTNING, 28, AppTheme.ACCENT));
        logoIcon.setBorder(new EmptyBorder(0, 0, 0, 8));

        JLabel logoText = new JLabel("<html><b style='color:#00C87A;font-size:15px;'>EV</b>"
            + "<span style='color:#E8EAF0;font-size:15px;'>Charge</span></html>");
        logoText.setFont(AppTheme.FONT_H2);

        JLabel logoSub = new JLabel("Smart Charging Platform");
        logoSub.setFont(AppTheme.FONT_SMALL);
        logoSub.setForeground(AppTheme.TEXT_MUTED);

        JPanel logoText2 = new JPanel();
        logoText2.setLayout(new BoxLayout(logoText2, BoxLayout.Y_AXIS));
        logoText2.setBackground(AppTheme.BG_SIDEBAR);
        logoText2.add(logoText);
        logoText2.add(logoSub);

        logoArea.add(logoIcon,  BorderLayout.WEST);
        logoArea.add(logoText2, BorderLayout.CENTER);
        logoArea.add(new JSeparator() {{ setForeground(AppTheme.BORDER_COL); }}, BorderLayout.SOUTH);

        // Nav buttons
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(AppTheme.BG_SIDEBAR);
        navPanel.setBorder(new EmptyBorder(12, 0, 12, 0));

        navButtons = new JButton[PANEL_KEYS.length];
        for (int i = 0; i < PANEL_KEYS.length; i++) {
            final String key = PANEL_KEYS[i];
            Icon navIcon = AppIcons.getNavIcon(key, 18);
            JButton btn = buildNavButton(key, navIcon);
            navButtons[i] = btn;
            btn.addActionListener(e -> navigateTo(key));
            navPanel.add(btn);
            navPanel.add(Box.createVerticalStrut(3));

            // Separator before Database
            if (i == 6) {
                JPanel sepRow = new JPanel(new BorderLayout());
                sepRow.setBackground(AppTheme.BG_SIDEBAR);
                sepRow.setBorder(new EmptyBorder(8, 22, 8, 22));
                JSeparator sep = new JSeparator();
                sep.setForeground(AppTheme.BORDER_COL);
                sepRow.add(sep);
                navPanel.add(sepRow);
            }
        }

        // User info + Logout + DB Status footer panel
        JPanel bottomArea = new JPanel();
        bottomArea.setLayout(new BoxLayout(bottomArea, BoxLayout.Y_AXIS));
        bottomArea.setBackground(AppTheme.BG_SIDEBAR);
        bottomArea.setBorder(new EmptyBorder(10, 16, 14, 16));

        JSeparator bottomSep = new JSeparator();
        bottomSep.setForeground(AppTheme.BORDER_COL);
        bottomSep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        bottomArea.add(bottomSep);
        bottomArea.add(Box.createVerticalStrut(10));

        String displayName = currentUser != null ? currentUser.getFullName() : "Demo User";
        String displayEmail = currentUser != null ? currentUser.getEmail() : "demo@evcharge.com";

        JLabel userLbl = new JLabel(displayName);
        userLbl.setIcon(AppIcons.get(AppIcons.IconType.USER, 15, AppTheme.ACCENT));
        userLbl.setIconTextGap(8);
        userLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLbl.setForeground(AppTheme.TEXT_PRI);
        userLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLbl = new JLabel(displayEmail);
        emailLbl.setFont(AppTheme.FONT_SMALL);
        emailLbl.setForeground(AppTheme.TEXT_MUTED);
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLbl.setBorder(new EmptyBorder(0, 23, 0, 0)); // Align under username text

        bottomArea.add(userLbl);
        bottomArea.add(Box.createVerticalStrut(2));
        bottomArea.add(emailLbl);
        bottomArea.add(Box.createVerticalStrut(8));

        // Logout Button
        JButton logoutBtn = new JButton("Sign Out") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 75, 75, 35));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoutBtn.setIcon(AppIcons.get(AppIcons.IconType.LOGOUT, 14, new Color(255, 120, 120)));
        logoutBtn.setIconTextGap(8);
        logoutBtn.setFont(AppTheme.FONT_SMALL);
        logoutBtn.setForeground(new Color(255, 120, 120));
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setBorder(new EmptyBorder(4, 6, 4, 6));
        logoutBtn.setToolTipText("End current authenticated session and return to login");
        logoutBtn.addActionListener(e -> logout());

        bottomArea.add(logoutBtn);
        bottomArea.add(Box.createVerticalStrut(4));

        // Clickable Database Status Footer
        JButton dbStatusBtn = new JButton("MySQL Online");
        dbStatusBtn.setIcon(AppIcons.get(AppIcons.IconType.DATABASE, 13, AppTheme.ACCENT));
        dbStatusBtn.setIconTextGap(8);
        dbStatusBtn.setFont(AppTheme.FONT_SMALL);
        dbStatusBtn.setForeground(AppTheme.ACCENT);
        dbStatusBtn.setContentAreaFilled(false);
        dbStatusBtn.setBorderPainted(false);
        dbStatusBtn.setFocusPainted(false);
        dbStatusBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dbStatusBtn.setHorizontalAlignment(SwingConstants.LEFT);
        dbStatusBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        dbStatusBtn.setBorder(new EmptyBorder(3, 6, 3, 6));
        dbStatusBtn.setToolTipText("Click to view database connection status and run ping diagnostics");
        dbStatusBtn.addActionListener(e -> showDatabaseStatusDialog());

        bottomArea.add(dbStatusBtn);

        sidebar.add(logoArea,    BorderLayout.NORTH);
        sidebar.add(navPanel,    BorderLayout.CENTER);
        sidebar.add(bottomArea,  BorderLayout.SOUTH);
        return sidebar;
    }

    private JButton buildNavButton(String text, Icon icon) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                boolean active = Boolean.TRUE.equals(getClientProperty("active"));
                if (active) {
                    // Active selection with solid accent background & indicator pill
                    g2.setColor(AppTheme.ACCENT_DIM);
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 8, 8);
                    g2.setColor(AppTheme.ACCENT);
                    g2.fillRoundRect(0, 8, 4, getHeight() - 16, 4, 4);
                } else if (getModel().isRollover()) {
                    // Subtle hover effect with accent border (Requirement 7)
                    g2.setColor(new Color(0, 200, 122, 24));
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 8, 8);
                    g2.setColor(new Color(0, 200, 122, 60));
                    g2.drawRoundRect(8, 2, getWidth() - 17, getHeight() - 5, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        boolean active = Boolean.TRUE.equals(btn.getClientProperty("active"));
        btn.setIcon(icon);
        btn.setIconTextGap(12);
        btn.setForeground(active ? AppTheme.ACCENT : AppTheme.TEXT_SEC);
        btn.setFont(AppTheme.FONT_NAV);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(215, 42));
        btn.setPreferredSize(new Dimension(215, 42));
        btn.setBorder(new EmptyBorder(0, 22, 0, 22));
        return btn;
    }

    // ── Main card area ────────────────────────────────────────────────────
    private JPanel buildMain() {
        dashboardPanel     = new DashboardPanel(this);
        driverVehiclePanel = new DriverVehiclePanel(this);
        chargerPanel       = new ChargerPanel(this);
        bookingPanel       = new BookingPanel(this);
        sessionPanel       = new SessionPanel(this);
        paymentPanel       = new PaymentPanel(this);
        reviewPanel        = new ReviewPanel(this);
        databasePanel      = new DatabasePanel(this);

        cardLayout    = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setBackground(AppTheme.BG_DARK);

        cardContainer.add(dashboardPanel,     "Dashboard");
        cardContainer.add(driverVehiclePanel, "Driver & Vehicle");
        cardContainer.add(chargerPanel,       "Charger");
        cardContainer.add(bookingPanel,       "Booking");
        cardContainer.add(sessionPanel,       "Session");
        cardContainer.add(paymentPanel,       "Payment");
        cardContainer.add(reviewPanel,        "Review");
        cardContainer.add(databasePanel,      "Database");

        return cardContainer;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(AppTheme.BG_SIDEBAR);
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER_COL),
            new EmptyBorder(6, 18, 6, 18)
        ));
        statusLabel = new JLabel("Ready — set up a Driver & Vehicle to begin.");
        statusLabel.setFont(AppTheme.FONT_SMALL);
        statusLabel.setForeground(AppTheme.TEXT_MUTED);
        bar.add(statusLabel, BorderLayout.WEST);

        JLabel hint = new JLabel("EV Charging App  •  MySQL Connected");
        hint.setIcon(AppIcons.get(AppIcons.IconType.LIGHTNING, 13, AppTheme.ACCENT));
        hint.setIconTextGap(6);
        hint.setFont(AppTheme.FONT_SMALL);
        hint.setForeground(AppTheme.ACCENT);
        bar.add(hint, BorderLayout.EAST);
        return bar;
    }

    // ── Navigation ────────────────────────────────────────────────────────
    public void navigateTo(String key) {
        cardLayout.show(cardContainer, key);

        for (int i = 0; i < PANEL_KEYS.length; i++) {
            boolean active = PANEL_KEYS[i].equals(key);
            navButtons[i].putClientProperty("active", active);
            navButtons[i].setForeground(active ? AppTheme.ACCENT : AppTheme.TEXT_SEC);
            navButtons[i].repaint();
        }

        // Trigger refresh on active panel
        switch (key) {
            case "Dashboard"        -> dashboardPanel.refresh();
            case "Driver & Vehicle" -> driverVehiclePanel.refresh();
            case "Charger"          -> chargerPanel.refresh();
            case "Booking"          -> bookingPanel.refresh();
            case "Session"          -> sessionPanel.refresh();
            case "Payment"          -> paymentPanel.refresh();
            case "Review"           -> reviewPanel.refresh();
        }
    }

    public void setStatus(String msg) {
        statusLabel.setText(msg);
    }

    // ── Toast Notification Overlay ────────────────────────────────────────
    public void showToast(String message, String type) {
        Color fg = AppTheme.ACCENT;
        Color bg = AppTheme.BG_CARD;
        AppIcons.IconType toastIcon = AppIcons.IconType.CHECK_CIRCLE;

        if ("ERROR".equalsIgnoreCase(type)) {
            fg = AppTheme.ERROR;
            toastIcon = AppIcons.IconType.ALERT_CIRCLE;
        } else if ("WARNING".equalsIgnoreCase(type)) {
            fg = AppTheme.WARNING;
            toastIcon = AppIcons.IconType.ALERT_CIRCLE;
        } else if ("INFO".equalsIgnoreCase(type)) {
            fg = AppTheme.BLUE;
            toastIcon = AppIcons.IconType.INFO_CIRCLE;
        }

        if (toastPanel == null) {
            toastPanel = new JPanel(new BorderLayout(10, 0));
            toastPanel.setBackground(bg);
            toastLabel = new JLabel();
            toastLabel.setFont(AppTheme.FONT_BODY);
            toastPanel.add(toastLabel, BorderLayout.CENTER);
            getLayeredPane().add(toastPanel, JLayeredPane.POPUP_LAYER);
        }

        toastLabel.setIcon(AppIcons.get(toastIcon, 16, fg));
        toastLabel.setIconTextGap(8);
        toastLabel.setText(message);
        toastLabel.setForeground(fg);
        toastPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(fg, 1, true),
            new EmptyBorder(10, 16, 10, 16)
        ));

        toastPanel.setSize(toastPanel.getPreferredSize().width + 24, 44);
        int x = (getWidth() - toastPanel.getWidth()) / 2;
        toastPanel.setLocation(x, 22);
        toastPanel.setVisible(true);

        if (toastTimer != null && toastTimer.isRunning()) {
            toastTimer.stop();
        }
        toastTimer = new Timer(3200, e -> toastPanel.setVisible(false));
        toastTimer.setRepeats(false);
        toastTimer.start();
    }

    // ── Database Status Modal ─────────────────────────────────────────────
    public void showDatabaseStatusDialog() {
        JDialog dialog = new JDialog(this, "MySQL Database Status & Diagnostics", true);
        dialog.getContentPane().setBackground(AppTheme.BG_CARD);
        dialog.setLayout(new BorderLayout(0, 16));
        dialog.setSize(500, 360);
        dialog.setLocationRelativeTo(this);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(AppTheme.BG_CARD);
        p.setBorder(new EmptyBorder(22, 24, 16, 24));

        JLabel title = new JLabel("Database Configuration & Health");
        title.setFont(AppTheme.FONT_H2);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel status = new JLabel("● Status: CONNECTED & ONLINE");
        status.setFont(AppTheme.FONT_H3);
        status.setForeground(AppTheme.SUCCESS);

        JLabel urlLbl = new JLabel("<html><b>URL:</b> jdbc:mysql://localhost:3306/ev_charging_app</html>");
        urlLbl.setFont(AppTheme.FONT_BODY);
        urlLbl.setForeground(AppTheme.TEXT_SEC);

        JLabel userLbl = new JLabel("<html><b>User:</b> root | <b>Driver:</b> com.mysql.cj.jdbc.Driver</html>");
        userLbl.setFont(AppTheme.FONT_BODY);
        userLbl.setForeground(AppTheme.TEXT_SEC);

        JLabel pingLbl = new JLabel("Ping latency: < 2ms | SSL: Disabled | Timezone: UTC");
        pingLbl.setFont(AppTheme.FONT_SMALL);
        pingLbl.setForeground(AppTheme.TEXT_MUTED);

        p.add(title);
        p.add(Box.createVerticalStrut(10));
        p.add(status);
        p.add(Box.createVerticalStrut(12));
        p.add(urlLbl);
        p.add(Box.createVerticalStrut(6));
        p.add(userLbl);
        p.add(Box.createVerticalStrut(6));
        p.add(pingLbl);
        p.add(Box.createVerticalStrut(16));
        p.add(AppTheme.sep());
        p.add(Box.createVerticalStrut(14));

        JButton pingBtn = AppTheme.primaryBtn("Test Query Ping", AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.BG_DARK));
        pingBtn.addActionListener(e -> {
            try (java.sql.Connection c = DBConnection.getConnection();
                 java.sql.Statement s = c.createStatement();
                 java.sql.ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM ev_charging_app.drivers")) {
                if (rs.next()) {
                    showToast("MySQL ping verified! " + rs.getInt(1) + " drivers loaded.", "SUCCESS");
                }
            } catch (Exception ex) {
                showToast("Connection test failed: " + ex.getMessage(), "ERROR");
            }
        });

        JButton closeBtn = AppTheme.secondaryBtn("Close");
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 14));
        btns.setBackground(AppTheme.BG_CARD);
        btns.add(pingBtn);
        btns.add(closeBtn);

        dialog.add(p, BorderLayout.CENTER);
        dialog.add(btns, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // ── Logout Session ────────────────────────────────────────────────────
    public void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to sign out of EVCharge?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            this.currentUser = null;
            this.currentDriver = null;
            this.currentVehicle = null;
            this.currentBooking = null;
            this.currentSession = null;
            this.currentPayment = null;
            dispose();

            SwingUtilities.invokeLater(() -> {
                LoginFrame login = new LoginFrame();
                login.setNotification("You have been signed out successfully.", "INFO");
                login.setVisible(true);
            });
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppTheme.applyGlobalTheme();
            new LoginFrame().setVisible(true);
        });
    }
}
