package com.evcharge.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.ChargingSessionDAO;
import com.evcharge.app.model.*;
import com.evcharge.app.service.BookingService;

public class SessionPanel extends JPanel {

    private final MainFrame frame;
    private final ChargingSessionDAO sessionDAO = new ChargingSessionDAO();

    // Form
    private JTextField energyField;
    private JLabel statusLabel;

    // Result display
    private JPanel energyResult, rateResult, billResult;
    private JLabel sessionIdLabel;
    private JPanel resultCard;

    public SessionPanel(MainFrame frame) {
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

        g.gridx = 0; g.weightx = 0.4; g.insets = new Insets(0, 0, 0, 18);
        body.add(buildFormCard(), g);

        g.gridx = 1; g.weightx = 0.6; g.insets = new Insets(0, 0, 0, 0);
        body.add(buildResultCard(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Charging Session");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Record energy consumed, calculate bill, and persist completed session to MySQL");
        sub.setFont(AppTheme.FONT_BODY);
        sub.setForeground(AppTheme.TEXT_SEC);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(AppTheme.BG_DARK);
        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(sub);
        p.add(left, BorderLayout.WEST);
        return p;
    }

    private JPanel buildFormCard() {
        JPanel card = AppTheme.card("Complete Session", AppIcons.get(AppIcons.IconType.SESSION, 16, AppTheme.TEXT_PRI));

        // Booking info
        sessionIdLabel = new JLabel("No active booking.");
        sessionIdLabel.setFont(AppTheme.FONT_BODY);
        sessionIdLabel.setForeground(AppTheme.TEXT_SEC);
        sessionIdLabel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        sessionIdLabel.setBackground(AppTheme.BG_INPUT);
        sessionIdLabel.setOpaque(true);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        energyField = AppTheme.textField();
        energyField.setText("18.4");

        g.gridy = 0; form.add(sessionIdLabel,                            g);
        g.gridy = 1; form.add(Box.createVerticalStrut(6),                g);
        g.gridy = 2; form.add(AppTheme.formRow("Energy Used (kWh)", energyField), g);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_SMALL);

        JButton completeBtn = AppTheme.primaryBtn("Complete Session", AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 14, AppTheme.BG_DARK));
        completeBtn.addActionListener(e -> completeSession());

        JButton nextBtn = AppTheme.secondaryBtn("Next: Payment", AppIcons.get(AppIcons.IconType.PAYMENT, 14, AppTheme.TEXT_PRI));
        nextBtn.addActionListener(e -> frame.navigateTo("Payment"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(completeBtn);
        btnRow.add(nextBtn);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(btnRow);
        south.add(Box.createVerticalStrut(6));
        south.add(statusLabel);

        // Hint about charger rate
        JLabel rateHint = new JLabel("Bill = Energy (kWh) × Charger Rate (₹/kWh)",
                AppIcons.get(AppIcons.IconType.INFO_CIRCLE, 13, AppTheme.TEXT_MUTED), JLabel.LEFT);
        rateHint.setIconTextGap(6);
        rateHint.setFont(AppTheme.FONT_SMALL);
        rateHint.setForeground(AppTheme.TEXT_MUTED);
        rateHint.setBorder(new EmptyBorder(14, 0, 0, 0));

        card.add(form,     BorderLayout.CENTER);
        card.add(south,    BorderLayout.SOUTH);
        card.add(rateHint, BorderLayout.NORTH);
        return card;
    }

    private JPanel buildResultCard() {
        resultCard = AppTheme.card("Session Summary", AppIcons.get(AppIcons.IconType.PAYMENT, 16, AppTheme.TEXT_PRI));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(AppTheme.BG_CARD);

        energyResult = bigStat("—", "Energy Consumed", AppTheme.BLUE);
        rateResult   = bigStat("—", "Rate per kWh", AppTheme.TEXT_SEC);
        billResult   = bigStat("—", "Total Bill", AppTheme.ACCENT);

        JSeparator sep1 = AppTheme.sep();
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        JSeparator sep2 = AppTheme.sep();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        inner.add(energyResult);
        inner.add(Box.createVerticalStrut(12));
        inner.add(sep1);
        inner.add(Box.createVerticalStrut(12));
        inner.add(rateResult);
        inner.add(Box.createVerticalStrut(12));
        inner.add(sep2);
        inner.add(Box.createVerticalStrut(20));
        inner.add(billResult);

        resultCard.add(inner, BorderLayout.CENTER);
        return resultCard;
    }

    /** Large stat row: value label + sub-label */
    private JPanel bigStat(String value, String label, Color valColor) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(AppTheme.BG_CARD);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.FONT_SMALL);
        lbl.setForeground(AppTheme.TEXT_SEC);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(valColor);
        val.putClientProperty("role", "value");

        row.add(lbl, BorderLayout.NORTH);
        row.add(val, BorderLayout.CENTER);
        return row;
    }

    private JLabel findVal(JPanel p) {
        for (Component c : p.getComponents())
            if (c instanceof JLabel l && "value".equals(l.getClientProperty("role"))) return l;
        return new JLabel();
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void completeSession() {
        if (frame.currentBooking == null) {
            show("No active booking. Go to Booking panel first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        try {
            double energy = Double.parseDouble(energyField.getText().trim());
            if (energy <= 0) {
                show("Energy must be positive.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
                return;
            }

            String sessionId = "CS-" + frame.currentBooking.getBookingId();
            if (frame.currentSession == null) {
                frame.currentSession = new ChargingSession(sessionId, frame.currentBooking);
            }
            frame.currentSession.completeSession(energy);

            double rate  = frame.currentCharger != null ? frame.currentCharger.getPricePerKwh() : 18.50;
            double total = frame.currentSession.getTotalAmount();

            // Persist to MySQL database
            int bId = frame.currentBooking.getId() > 0 ? frame.currentBooking.getId() : 1;
            int sId = sessionDAO.startSession(bId);
            if (sId > 0) {
                sessionDAO.completeSession(sId, energy, total);
                frame.currentSession.setId(sId);
            }

            // Mark booking completed in DB and release charger
            new BookingService().completeBooking(frame.currentBooking);

            findVal(energyResult).setText(String.format("%.2f kWh", energy));
            findVal(rateResult).setText(String.format("₹%.2f/kWh", rate));
            findVal(billResult).setText(String.format("₹%.2f", total));

            show("Session complete! Total bill: ₹" + String.format("%.2f", total) + " (Saved to MySQL)", AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
            frame.setStatus("Session complete | Bill: ₹" + String.format("%.2f", total));

        } catch (NumberFormatException e) {
            show("Energy must be a number (e.g. 18.4)", AppTheme.ERROR, AppIcons.IconType.ALERT_CIRCLE);
        }
    }

    private void show(String msg, Color c, AppIcons.IconType iconType) {
        statusLabel.setText(msg);
        statusLabel.setForeground(c);
        statusLabel.setIcon(iconType != null ? AppIcons.get(iconType, 13, c) : null);
        statusLabel.setIconTextGap(6);
    }

    public void refresh() {
        if (frame.currentBooking != null) {
            sessionIdLabel.setText("Booking: " + frame.currentBooking.getBookingId()
                + "  |  Status: " + frame.currentBooking.getStatus()
                + "  |  Driver: " + frame.currentBooking.getDriver().getName());
        } else {
            sessionIdLabel.setText("No active booking.");
        }

        if (frame.currentSession != null && frame.currentSession.isCompleted()) {
            double energy = frame.currentSession.getEnergyUsedKwh();
            double rate   = frame.currentCharger != null ? frame.currentCharger.getPricePerKwh() : 18.50;
            double total  = frame.currentSession.getTotalAmount();
            findVal(energyResult).setText(String.format("%.2f kWh", energy));
            findVal(rateResult).setText(String.format("₹%.2f/kWh", rate));
            findVal(billResult).setText(String.format("₹%.2f", total));
        }
    }
}
