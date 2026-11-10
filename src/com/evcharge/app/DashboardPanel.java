package com.evcharge.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.*;
import com.evcharge.app.model.*;

public class DashboardPanel extends JPanel {

    private final MainFrame frame;

    // Stat cards
    private JPanel driverCard, vehicleCard, chargerCard, bookingCard, sessionCard, loyaltyCard;
    private JLabel driverVal, vehicleVal, chargerVal, bookingVal, sessionVal, pointsVal;
    private JLabel driverSub, vehicleSub, chargerSub, bookingBadge;

    // Progress Tracker (Read-Only Status Display)
    private JPanel progressPanel;
    private JLabel trackerBookingInfo;
    private JLabel[] stepLabels;
    private JPanel[] stepDots;
    private JPanel[] stepLines;
    private static final String[] STEPS = {"PENDING", "CONFIRMED", "CHARGING", "COMPLETED"};

    // Telemetry Chart (Strictly Read-Only Sensor Observation)
    private TelemetryChartPanel telemetryChart;

    public DashboardPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(AppTheme.BG_DARK);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(24, 26, 24, 26));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(AppTheme.BG_DARK);

        // 1. Stat cards row (Click to view/edit profile or top up)
        body.add(buildStatRow());
        body.add(Box.createVerticalStrut(18));

        // 2. Active Session Lifecycle Tracker (Read-only stage indicator)
        body.add(buildProgressCard());
        body.add(Box.createVerticalStrut(18));

        // 3. Live Telemetry Stream (Read-only observation of station sensors)
        telemetryChart = new TelemetryChartPanel(frame);
        body.add(telemetryChart);
        body.add(Box.createVerticalStrut(18));

        // 4. Quick Module Navigation
        body.add(buildQuickNavigation());

        JScrollPane scroll = AppTheme.scrollPane(body);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.BG_DARK);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 18, 0));

        JLabel title = new JLabel(" EV Charging Operations Dashboard");
        title.setIcon(AppIcons.get(AppIcons.IconType.ANALYTICS, 24, AppTheme.ACCENT));
        title.setIconTextGap(10);
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Executive overview, live sensor telemetry streaming & station metrics");
        sub.setFont(AppTheme.FONT_BODY);
        sub.setForeground(AppTheme.TEXT_SEC);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(AppTheme.BG_DARK);
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(sub);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(AppTheme.BG_DARK);

        JButton dbBtn = AppTheme.secondaryBtn("DB Status", AppIcons.get(AppIcons.IconType.DATABASE, 14, AppTheme.TEXT_PRI));
        dbBtn.setPreferredSize(new Dimension(125, 36));
        dbBtn.addActionListener(e -> frame.showDatabaseStatusDialog());

        JButton refresh = AppTheme.secondaryBtn("Refresh", AppIcons.get(AppIcons.IconType.REFRESH, 14, AppTheme.TEXT_PRI));
        refresh.setPreferredSize(new Dimension(115, 36));
        refresh.addActionListener(e -> {
            refresh();
            frame.showToast("Dashboard synchronized with MySQL database.", "INFO");
        });

        JButton logoutBtn = AppTheme.secondaryBtn("Sign Out", AppIcons.get(AppIcons.IconType.LOGOUT, 14, new Color(255, 120, 120)));
        logoutBtn.setPreferredSize(new Dimension(115, 36));
        logoutBtn.addActionListener(e -> frame.logout());

        right.add(dbBtn);
        right.add(refresh);
        right.add(logoutBtn);

        p.add(left,  BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);
        return p;
    }

    // ── 1. Interactive Stat Cards ──────────────────────────────────────────
    private JPanel buildStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 6, 12, 0));
        row.setBackground(AppTheme.BG_DARK);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));

        // Driver Card
        driverCard = createCard(AppIcons.get(AppIcons.IconType.USER, 20, AppTheme.TEXT_PRI), "—", "Driver", "Click to edit", AppTheme.TEXT_PRI, () -> showDriverEditDialog());
        driverVal = findValueLabel(driverCard);
        driverSub = findSubLabel(driverCard);
        row.add(driverCard);

        // Vehicle Card
        vehicleCard = createCard(AppIcons.get(AppIcons.IconType.CAR, 20, AppTheme.BLUE), "—", "Vehicle", "Click to edit", AppTheme.BLUE, () -> showVehicleEditDialog());
        vehicleVal = findValueLabel(vehicleCard);
        vehicleSub = findSubLabel(vehicleCard);
        row.add(vehicleCard);

        // Charger Card
        chargerCard = createCard(AppIcons.get(AppIcons.IconType.CHARGER, 20, AppTheme.PURPLE), "—", "Charger", "Click to config", AppTheme.PURPLE, () -> showChargerEditDialog());
        chargerVal = findValueLabel(chargerCard);
        chargerSub = findSubLabel(chargerCard);
        row.add(chargerCard);

        // Booking Status Card (Navigates to Booking panel)
        bookingCard = createCard(AppIcons.get(AppIcons.IconType.BOOKING, 20, AppTheme.WARNING), "—", "Booking Status", "Go to Queue →", AppTheme.WARNING, () -> frame.navigateTo("Booking"));
        bookingVal = findValueLabel(bookingCard);
        bookingBadge = findSubLabel(bookingCard);
        row.add(bookingCard);

        // Bill Card (Navigates to Session panel)
        sessionCard = createCard(AppIcons.get(AppIcons.IconType.PAYMENT, 20, AppTheme.ACCENT), "₹0.00", "Total Bill", "View Breakdown →", AppTheme.ACCENT, () -> frame.navigateTo("Session"));
        sessionVal = findValueLabel(sessionCard);
        row.add(sessionCard);

        // Loyalty Card (With Top Up Action)
        loyaltyCard = createCard(AppIcons.get(AppIcons.IconType.REVIEW, 20, AppTheme.WARNING), "0 pts", "Loyalty Wallet", "Top up points +", AppTheme.WARNING, () -> showTopUpDialog());
        pointsVal = findValueLabel(loyaltyCard);
        row.add(loyaltyCard);

        return row;
    }

    private JPanel createCard(Icon icon, String value, String label, String hint, Color valColor, Runnable onClick) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(AppTheme.BG_CARD);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(12, 14, 12, 14)
        ));

        JLabel ico = new JLabel(icon);
        ico.setAlignmentX(LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 17));
        val.setForeground(valColor);
        val.setAlignmentX(LEFT_ALIGNMENT);
        val.putClientProperty("role", "value");

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.FONT_LABEL);
        lbl.setForeground(AppTheme.TEXT_SEC);
        lbl.setAlignmentX(LEFT_ALIGNMENT);

        JLabel sub = new JLabel(hint);
        sub.setFont(AppTheme.FONT_SMALL);
        sub.setForeground(AppTheme.TEXT_MUTED);
        sub.setAlignmentX(LEFT_ALIGNMENT);
        sub.putClientProperty("role", "sub");

        p.add(ico);
        p.add(Box.createVerticalStrut(4));
        p.add(val);
        p.add(Box.createVerticalStrut(2));
        p.add(lbl);
        p.add(Box.createVerticalStrut(1));
        p.add(sub);

        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                p.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppTheme.ACCENT, 1, true),
                    new EmptyBorder(12, 14, 12, 14)
                ));
            }
            @Override public void mouseExited(MouseEvent e) {
                p.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppTheme.BORDER_COL, 1, true),
                    new EmptyBorder(12, 14, 12, 14)
                ));
            }
            @Override public void mouseClicked(MouseEvent e) {
                if (onClick != null) onClick.run();
            }
        });

        return p;
    }

    private JLabel findValueLabel(JPanel card) {
        for (Component c : card.getComponents())
            if (c instanceof JLabel l && "value".equals(l.getClientProperty("role"))) return l;
        return new JLabel();
    }

    private JLabel findSubLabel(JPanel card) {
        for (Component c : card.getComponents())
            if (c instanceof JLabel l && "sub".equals(l.getClientProperty("role"))) return l;
        return new JLabel();
    }

    // ── 2. Read-Only Session Progress Tracker ──────────────────────────────
    private JPanel buildProgressCard() {
        JPanel card = AppTheme.card("Session Lifecycle Status");
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setBackground(AppTheme.BG_CARD);

        JLabel hTitle = new JLabel("Live lifecycle tracking for current vehicle booking");
        hTitle.setFont(AppTheme.FONT_SMALL);
        hTitle.setForeground(AppTheme.TEXT_SEC);

        trackerBookingInfo = new JLabel("No active booking");
        trackerBookingInfo.setFont(AppTheme.FONT_SMALL);
        trackerBookingInfo.setForeground(AppTheme.TEXT_MUTED);

        headerRow.add(hTitle, BorderLayout.WEST);
        headerRow.add(trackerBookingInfo, BorderLayout.EAST);

        // Step Nodes
        progressPanel = new JPanel(new GridBagLayout());
        progressPanel.setBackground(AppTheme.BG_CARD);
        progressPanel.setBorder(new EmptyBorder(14, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 4, 0, 4);

        stepDots   = new JPanel[STEPS.length];
        stepLabels = new JLabel[STEPS.length];
        stepLines  = new JPanel[STEPS.length - 1];

        for (int i = 0; i < STEPS.length; i++) {
            // Node
            JPanel dot = new JPanel();
            dot.setPreferredSize(new Dimension(16, 16));
            dot.setBackground(AppTheme.BORDER_COL);
            dot.setBorder(new LineBorder(AppTheme.BG_CARD, 2, true));
            stepDots[i] = dot;

            JLabel lbl = new JLabel(STEPS[i]);
            lbl.setFont(AppTheme.FONT_SMALL);
            lbl.setForeground(AppTheme.TEXT_MUTED);
            stepLabels[i] = lbl;

            JPanel col = new JPanel();
            col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
            col.setBackground(AppTheme.BG_CARD);
            dot.setAlignmentX(CENTER_ALIGNMENT);
            lbl.setAlignmentX(CENTER_ALIGNMENT);
            col.add(dot);
            col.add(Box.createVerticalStrut(6));
            col.add(lbl);

            gbc.gridx = i * 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            progressPanel.add(col, gbc);

            // Connector line
            if (i < STEPS.length - 1) {
                JPanel line = new JPanel();
                line.setBackground(AppTheme.BORDER_COL);
                line.setPreferredSize(new Dimension(140, 3));
                stepLines[i] = line;

                gbc.gridx = i * 2 + 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1;
                progressPanel.add(line, gbc);
            }
        }

        card.add(headerRow, BorderLayout.NORTH);
        card.add(progressPanel, BorderLayout.CENTER);
        return card;
    }

    private void updateProgressUI(String status) {
        int activeIdx = -1;
        for (int i = 0; i < STEPS.length; i++) {
            if (STEPS[i].equalsIgnoreCase(status)) {
                activeIdx = i;
                break;
            }
        }

        for (int i = 0; i < stepDots.length; i++) {
            Color c = i <= activeIdx ? AppTheme.ACCENT : AppTheme.BORDER_COL;
            Color tc = i <= activeIdx ? AppTheme.ACCENT : AppTheme.TEXT_MUTED;
            stepDots[i].setBackground(c);
            stepLabels[i].setForeground(tc);
            stepDots[i].repaint();
        }

        for (int i = 0; i < stepLines.length; i++) {
            stepLines[i].setBackground(i < activeIdx ? AppTheme.ACCENT : AppTheme.BORDER_COL);
            stepLines[i].repaint();
        }

        if (bookingBadge != null) {
            bookingBadge.setText("Status: " + (status != null ? status : "—"));
        }
    }

    // ── 3. Quick Navigation ────────────────────────────────────────────────
    private JPanel buildQuickNavigation() {
        JPanel card = AppTheme.card("Quick Module Navigation");
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setBackground(AppTheme.BG_CARD);

        JButton b1 = AppTheme.secondaryBtn("Driver & Vehicle", AppIcons.get(AppIcons.IconType.CAR, 14, AppTheme.TEXT_PRI));
        b1.addActionListener(e -> frame.navigateTo("Driver & Vehicle"));

        JButton b2 = AppTheme.secondaryBtn("Charger Stations", AppIcons.get(AppIcons.IconType.CHARGER, 14, AppTheme.TEXT_PRI));
        b2.addActionListener(e -> frame.navigateTo("Charger"));

        JButton b3 = AppTheme.primaryBtn("Booking Queue", AppIcons.get(AppIcons.IconType.BOOKING, 14, AppTheme.BG_DARK));
        b3.addActionListener(e -> frame.navigateTo("Booking"));

        JButton b4 = AppTheme.secondaryBtn("Charging Session", AppIcons.get(AppIcons.IconType.SESSION, 14, AppTheme.TEXT_PRI));
        b4.addActionListener(e -> frame.navigateTo("Session"));

        JButton b5 = AppTheme.secondaryBtn("Payment", AppIcons.get(AppIcons.IconType.PAYMENT, 14, AppTheme.TEXT_PRI));
        b5.addActionListener(e -> frame.navigateTo("Payment"));

        JButton b6 = AppTheme.secondaryBtn("Reviews", AppIcons.get(AppIcons.IconType.REVIEW, 14, AppTheme.TEXT_PRI));
        b6.addActionListener(e -> frame.navigateTo("Review"));

        row.add(b1);
        row.add(b2);
        row.add(b3);
        row.add(b4);
        row.add(b5);
        row.add(b6);

        card.add(row, BorderLayout.CENTER);
        return card;
    }

    // ── 4. Editable Modals ────────────────────────────────────────────────
    private void showDriverEditDialog() {
        JDialog d = createThemedDialog("Edit Driver Profile", 420, 350);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 14));
        form.setBackground(AppTheme.BG_CARD);
        form.setBorder(new EmptyBorder(16, 20, 16, 20));

        JTextField nameF = AppTheme.textField();
        JTextField phoneF = AppTheme.textField();
        JTextField emailF = AppTheme.textField();
        JTextField ptsF = AppTheme.textField();

        if (frame.currentDriver != null) {
            nameF.setText(frame.currentDriver.getName());
            String p = frame.currentDriver.getPhoneNumber();
            phoneF.setText(p != null && !p.startsWith("NA-") ? p : "");
            emailF.setText(frame.currentDriver.getEmail());
            ptsF.setText(String.valueOf(frame.currentDriver.getLoyaltyPoints()));
        } else {
            nameF.setText(""); phoneF.setText(""); emailF.setText(""); ptsF.setText("0");
        }

        form.add(new JLabel("Full Name:")); form.add(nameF);
        form.add(new JLabel("Phone:")); form.add(phoneF);
        form.add(new JLabel("Email:")); form.add(emailF);
        form.add(new JLabel("Loyalty Pts:")); form.add(ptsF);

        JButton saveBtn = AppTheme.primaryBtn("Update Driver", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        saveBtn.addActionListener(e -> {
            String n = nameF.getText().trim();
            String p = phoneF.getText().trim();
            String em = emailF.getText().trim();
            if (n.isEmpty()) {
                frame.showToast("Driver name is required.", "WARNING");
                return;
            }
            if (em.isEmpty()) {
                frame.showToast("Email address is required.", "WARNING");
                return;
            }
            if (p.isEmpty()) {
                p = (frame.currentUser != null) ? "NA-" + frame.currentUser.getUserId() : "Not Provided";
            }
            int pts = 0;
            try { pts = Integer.parseInt(ptsF.getText().trim()); } catch (Exception ignored) {}

            int dbId = new DriverDAO().saveDriver(n, p, em);
            if (dbId > 0) new DriverDAO().updateLoyaltyPoints(dbId, pts);
            frame.currentDriver = new Driver(dbId > 0 ? dbId : (frame.currentDriver != null ? frame.currentDriver.getDriverId() : 1), n, p, em, pts);

            refresh();
            frame.showToast("Driver " + n + " updated in MySQL!", "SUCCESS");
            d.dispose();
        });

        d.add(form, BorderLayout.CENTER);
        d.add(buildDialogButtons(saveBtn, d), BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void showVehicleEditDialog() {
        boolean isNew = (frame.currentVehicle == null);
        JDialog d = createThemedDialog(isNew ? "Register Vehicle Specs" : "Edit Vehicle Specs", 420, 360);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 14));
        form.setBackground(AppTheme.BG_CARD);
        form.setBorder(new EmptyBorder(16, 20, 16, 20));

        JTextField modelF = AppTheme.textField();
        JTextField capF = AppTheme.textField();
        JTextField pctF = AppTheme.textField();
        JComboBox<String> connF = AppTheme.combo(new String[]{"CCS2", "Type 2", "CHAdeMO", "GB/T"});

        if (frame.currentVehicle != null) {
            modelF.setText(frame.currentVehicle.getModel());
            capF.setText(String.valueOf(frame.currentVehicle.getBatteryCapacityKwh()));
            pctF.setText(String.valueOf(frame.currentVehicle.getCurrentBatteryPercentage()));
            connF.setSelectedItem(frame.currentVehicle.getConnectorType());
        } else {
            modelF.setText("");
            capF.setText("");
            pctF.setText("");
        }

        form.add(new JLabel("Model:")); form.add(modelF);
        form.add(new JLabel("Capacity (kWh):")); form.add(capF);
        form.add(new JLabel("Battery %:")); form.add(pctF);
        form.add(new JLabel("Connector:")); form.add(connF);

        JButton saveBtn = AppTheme.primaryBtn(isNew ? "Save Vehicle" : "Update Vehicle", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        saveBtn.addActionListener(e -> {
            String m = modelF.getText().trim();
            if (m.isEmpty()) {
                frame.showToast("Vehicle model is required.", "WARNING");
                return;
            }
            double cap;
            int pct;
            try {
                cap = Double.parseDouble(capF.getText().trim());
                pct = Integer.parseInt(pctF.getText().trim());
            } catch (Exception ex) {
                frame.showToast("Capacity and Battery % must be valid numbers.", "ERROR");
                return;
            }
            if (cap <= 0) {
                frame.showToast("Battery capacity must be greater than 0 kWh.", "WARNING");
                return;
            }
            if (pct < 0 || pct > 100) {
                frame.showToast("Battery percentage must be between 0 and 100.", "WARNING");
                return;
            }
            String conn = (String) connF.getSelectedItem();

            int dId = frame.currentDriver != null && frame.currentDriver.getDriverId() > 0 ?
                      frame.currentDriver.getDriverId() : 1;
            int vId = new VehicleDAO().saveVehicle(dId, m, cap, conn, pct);
            frame.currentVehicle = new Vehicle(vId > 0 ? vId : 1, dId, m, cap, conn, pct);

            telemetryChart.resetTelemetry(pct);
            refresh();
            frame.showToast("Vehicle " + m + " (" + conn + ") saved!", "SUCCESS");
            d.dispose();
        });

        d.add(form, BorderLayout.CENTER);
        d.add(buildDialogButtons(saveBtn, d), BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void showChargerEditDialog() {
        JDialog d = createThemedDialog("Edit Charger Configuration", 420, 360);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 14));
        form.setBackground(AppTheme.BG_CARD);
        form.setBorder(new EmptyBorder(16, 20, 16, 20));

        JTextField idF = AppTheme.textField();
        JComboBox<String> typeF = AppTheme.combo(new String[]{"Fast", "Slow", "Ultra-Fast"});
        JComboBox<String> connF = AppTheme.combo(new String[]{"CCS2", "Type 2", "CHAdeMO", "GB/T"});
        JTextField priceF = AppTheme.textField();

        if (frame.currentCharger != null) {
            idF.setText(frame.currentCharger.getChargerId());
            typeF.setSelectedItem(frame.currentCharger.getChargingMode());
            connF.setSelectedItem(frame.currentCharger.getConnectorType());
            priceF.setText(String.format("%.2f", frame.currentCharger.getPricePerKwh()));
        } else {
            idF.setText("GC-01"); priceF.setText("18.50");
        }

        form.add(new JLabel("Charger ID:")); form.add(idF);
        form.add(new JLabel("Mode:")); form.add(typeF);
        form.add(new JLabel("Connector:")); form.add(connF);
        form.add(new JLabel("Price/kWh (₹):")); form.add(priceF);

        JButton saveBtn = AppTheme.primaryBtn("Update Charger", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        saveBtn.addActionListener(e -> {
            String cid = idF.getText().trim();
            String type = (String) typeF.getSelectedItem();
            String conn = (String) connF.getSelectedItem();
            double p = Double.parseDouble(priceF.getText().trim());

            int dbId = new ChargerDAO().saveCharger(1, cid, type, conn, p, true);
            frame.currentCharger = new Charger(dbId > 0 ? dbId : 1, 1, cid, type, conn, p, true);

            refresh();
            frame.showToast("Charger " + cid + " (" + conn + ") updated in MySQL!", "SUCCESS");
            d.dispose();
        });

        d.add(form, BorderLayout.CENTER);
        d.add(buildDialogButtons(saveBtn, d), BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void showTopUpDialog() {
        JDialog d = createThemedDialog("Top Up Loyalty Points", 400, 280);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(AppTheme.BG_CARD);
        body.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("Add wallet balance or loyalty rewards");
        title.setFont(AppTheme.FONT_BODY);
        title.setForeground(AppTheme.TEXT_PRI);

        JPanel quickBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        quickBtns.setBackground(AppTheme.BG_CARD);

        JTextField customField = AppTheme.textField();
        customField.setText("50");

        JButton b1 = AppTheme.secondaryBtn("+25 pts");
        b1.addActionListener(e -> customField.setText("25"));
        JButton b2 = AppTheme.secondaryBtn("+50 pts");
        b2.addActionListener(e -> customField.setText("50"));
        JButton b3 = AppTheme.secondaryBtn("+100 pts");
        b3.addActionListener(e -> customField.setText("100"));

        quickBtns.add(b1); quickBtns.add(b2); quickBtns.add(b3);

        JButton topUpBtn = AppTheme.primaryBtn("Confirm Top Up", AppIcons.get(AppIcons.IconType.PAYMENT, 14, AppTheme.BG_DARK));
        topUpBtn.addActionListener(e -> {
            try {
                int add = Integer.parseInt(customField.getText().trim());
                if (frame.currentDriver == null) {
                    frame.showToast("Please register or log in as a driver first.", "WARNING");
                    return;
                }
                frame.currentDriver.addLoyaltyPoints(add);
                new DriverDAO().updateLoyaltyPoints(frame.currentDriver.getDriverId(), frame.currentDriver.getLoyaltyPoints());

                refresh();
                frame.showToast("Wallet topped up! +" + add + " loyalty points added to MySQL.", "SUCCESS");
                d.dispose();
            } catch (Exception ex) {
                frame.showToast("Invalid points amount.", "ERROR");
            }
        });

        body.add(title);
        body.add(Box.createVerticalStrut(10));
        body.add(quickBtns);
        body.add(Box.createVerticalStrut(10));
        body.add(AppTheme.formRow("Points to Add", customField));

        d.add(body, BorderLayout.CENTER);
        d.add(buildDialogButtons(topUpBtn, d), BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private JDialog createThemedDialog(String title, int w, int h) {
        JDialog d = new JDialog(frame, title, true);
        d.getContentPane().setBackground(AppTheme.BG_CARD);
        d.setLayout(new BorderLayout());
        d.setSize(w, h);
        d.setLocationRelativeTo(frame);
        return d;
    }

    private JPanel buildDialogButtons(JButton primary, JDialog dialog) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 14));
        p.setBackground(AppTheme.BG_CARD);
        JButton cancel = AppTheme.secondaryBtn("Cancel");
        cancel.addActionListener(e -> dialog.dispose());
        p.add(primary);
        p.add(cancel);
        return p;
    }

    // ── Refresh ───────────────────────────────────────────────────────────
    public void refresh() {
        // Sync driver from MySQL if null and a user is logged in
        if (frame.currentDriver == null && frame.currentUser != null) {
            Driver d = new DriverDAO().getDriverByEmail(frame.currentUser.getEmail());
            if (d != null) frame.currentDriver = d;
        }

        // Only sync vehicle for current driver if registered in MySQL (do not force dummy vehicle 1)
        if (frame.currentVehicle == null && frame.currentDriver != null && frame.currentDriver.getDriverId() > 0) {
            java.util.List<Vehicle> list = new VehicleDAO().getVehiclesByDriverId(frame.currentDriver.getDriverId());
            if (!list.isEmpty()) {
                frame.currentVehicle = list.get(0);
            }
        }

        // Default station charger if not selected
        if (frame.currentCharger == null) {
            Charger c = new ChargerDAO().getChargerById(1);
            if (c != null) frame.currentCharger = c;
        }

        // Driver
        if (frame.currentDriver != null) {
            driverVal.setText(frame.currentDriver.getName());
            driverSub.setText(frame.currentDriver.getEmail());
        } else {
            driverVal.setText("—");
            driverSub.setText("Click to set profile");
        }

        // Vehicle (empty placeholder if driver hasn't registered a vehicle yet)
        if (frame.currentVehicle != null) {
            vehicleVal.setText(frame.currentVehicle.getModel());
            vehicleSub.setText(frame.currentVehicle.getConnectorType() + " • " + frame.currentVehicle.getCurrentBatteryPercentage() + "%");
        } else {
            vehicleVal.setText("—");
            vehicleSub.setText("Click to add vehicle");
        }

        // Charger
        if (frame.currentCharger != null) {
            chargerVal.setText(frame.currentCharger.getChargerId());
            chargerSub.setText(frame.currentCharger.getConnectorType() + " • ₹" + frame.currentCharger.getPricePerKwh());
        } else {
            chargerVal.setText("—");
            chargerSub.setText("Click to config");
        }

        // Booking & Progress Lifecycle
        if (frame.currentBooking != null) {
            String status = frame.currentBooking.getStatus();
            bookingVal.setText(frame.currentBooking.getBookingId());
            trackerBookingInfo.setText(frame.currentBooking.getBookingId() + " (" + frame.currentBooking.getChargingMode() + ") • " + frame.currentBooking.getStatus());
            updateProgressUI(status);

            // Automatically start/stop telemetry chart observation based on true session state
            boolean charging = "CHARGING".equalsIgnoreCase(status);
            telemetryChart.setCharging(charging);
        } else {
            trackerBookingInfo.setText("No active booking");
            updateProgressUI(null);
            telemetryChart.setCharging(false);
        }

        // Bill
        if (frame.currentSession != null && frame.currentSession.isCompleted()) {
            sessionVal.setText(String.format("₹%.2f", frame.currentSession.getTotalAmount()));
        } else {
            sessionVal.setText("₹0.00");
        }

        // Loyalty
        if (frame.currentDriver != null) {
            pointsVal.setText(frame.currentDriver.getLoyaltyPoints() + " pts");
        } else {
            pointsVal.setText("0 pts");
        }

        repaint();
    }
}
