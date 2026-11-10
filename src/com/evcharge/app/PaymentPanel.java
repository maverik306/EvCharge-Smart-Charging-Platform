package com.evcharge.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.PaymentDAO;
import com.evcharge.app.model.*;
import com.evcharge.app.service.LoyaltyService;

public class PaymentPanel extends JPanel {

    private final MainFrame frame;
    private final PaymentDAO paymentDAO = new PaymentDAO();

    // Payment form
    private JComboBox<String> methodCombo;
    private JLabel statusLabel;

    // Result display
    private JLabel amountVal, methodVal, payStatusVal;
    private JLabel pointsEarned, totalPoints;
    private JPanel resultArea;

    public PaymentPanel(MainFrame frame) {
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
        body.add(buildPaymentCard(), g);

        g.gridx = 1; g.weightx = 0.62; g.insets = new Insets(0, 0, 0, 0);
        body.add(buildResultCard(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Payment & Loyalty");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Complete payment, record audit transactions in MySQL, and earn loyalty points");
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

    private JPanel buildPaymentCard() {
        JPanel card = AppTheme.card("Make Payment", AppIcons.get(AppIcons.IconType.PAYMENT, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        methodCombo = AppTheme.combo(new String[]{"UPI", "Card", "Wallet", "Cash"});

        // Payment method badges display
        JPanel methodIcons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        methodIcons.setBackground(AppTheme.BG_CARD);
        String[] methods = {"UPI", "Card", "Wallet", "Cash"};
        for (String m : methods) {
            JLabel badge = new JLabel(m, AppIcons.get(AppIcons.IconType.PAYMENT, 11, AppTheme.TEXT_MUTED), JLabel.LEFT);
            badge.setIconTextGap(4);
            badge.setFont(AppTheme.FONT_SMALL);
            badge.setForeground(AppTheme.TEXT_MUTED);
            badge.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AppTheme.BORDER_COL, 1, true),
                new EmptyBorder(4, 8, 4, 8)
            ));
            badge.setBackground(AppTheme.BG_INPUT);
            badge.setOpaque(true);
            methodIcons.add(badge);
        }

        g.gridy = 0; form.add(methodIcons,                                          g);
        g.gridy = 1; form.add(Box.createVerticalStrut(6),                           g);
        g.gridy = 2; form.add(AppTheme.formRow("Select Payment Method", methodCombo), g);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_SMALL);

        JButton payBtn = AppTheme.primaryBtn("Pay Now", AppIcons.get(AppIcons.IconType.PAYMENT, 14, AppTheme.BG_DARK));
        payBtn.addActionListener(e -> makePayment());

        JButton nextBtn = AppTheme.secondaryBtn("Next: Review", AppIcons.get(AppIcons.IconType.REVIEW, 14, AppTheme.TEXT_PRI));
        nextBtn.addActionListener(e -> frame.navigateTo("Review"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(payBtn);
        btnRow.add(nextBtn);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(btnRow);
        south.add(Box.createVerticalStrut(6));
        south.add(statusLabel);

        card.add(form,  BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildResultCard() {
        JPanel card = AppTheme.card("Payment Receipt", AppIcons.get(AppIcons.IconType.PAYMENT, 16, AppTheme.TEXT_PRI));

        resultArea = new JPanel();
        resultArea.setLayout(new BoxLayout(resultArea, BoxLayout.Y_AXIS));
        resultArea.setBackground(AppTheme.BG_CARD);

        amountVal    = receiptRow(resultArea, "Amount Paid",      "—", AppTheme.ACCENT);
        methodVal    = receiptRow(resultArea, "Payment Method",   "—", AppTheme.TEXT_PRI);
        payStatusVal = receiptRow(resultArea, "Payment Status",   "—", AppTheme.WARNING);

        receiptDivider(resultArea);

        // Loyalty points box
        JPanel loyaltyBox = new JPanel();
        loyaltyBox.setLayout(new BoxLayout(loyaltyBox, BoxLayout.Y_AXIS));
        loyaltyBox.setBackground(AppTheme.BG_INPUT);
        loyaltyBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(12, 14, 12, 14)
        ));

        JLabel lTitle = new JLabel("Loyalty Program (1 pt per ₹10)", AppIcons.get(AppIcons.IconType.REVIEW, 14, AppTheme.WARNING), JLabel.LEFT);
        lTitle.setIconTextGap(6);
        lTitle.setFont(AppTheme.FONT_H3);
        lTitle.setForeground(AppTheme.WARNING);
        lTitle.setAlignmentX(LEFT_ALIGNMENT);

        pointsEarned = new JLabel("Points earned this session: —");
        pointsEarned.setFont(AppTheme.FONT_BODY);
        pointsEarned.setForeground(AppTheme.TEXT_PRI);
        pointsEarned.setAlignmentX(LEFT_ALIGNMENT);

        totalPoints = new JLabel("Total loyalty points: —");
        totalPoints.setFont(AppTheme.FONT_BODY);
        totalPoints.setForeground(AppTheme.TEXT_SEC);
        totalPoints.setAlignmentX(LEFT_ALIGNMENT);

        loyaltyBox.add(lTitle);
        loyaltyBox.add(Box.createVerticalStrut(6));
        loyaltyBox.add(pointsEarned);
        loyaltyBox.add(Box.createVerticalStrut(3));
        loyaltyBox.add(totalPoints);

        resultArea.add(loyaltyBox);
        card.add(resultArea, BorderLayout.CENTER);
        return card;
    }

    private JLabel receiptRow(JPanel parent, String label, String initVal, Color valColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(AppTheme.BG_CARD);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.FONT_BODY);
        lbl.setForeground(AppTheme.TEXT_SEC);

        JLabel val = new JLabel(initVal);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(valColor);

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.EAST);
        parent.add(row);
        return val;
    }

    private void receiptDivider(JPanel parent) {
        JSeparator sep = AppTheme.sep();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        parent.add(Box.createVerticalStrut(6));
        parent.add(sep);
        parent.add(Box.createVerticalStrut(6));
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void makePayment() {
        if (frame.currentSession == null || !frame.currentSession.isCompleted()) {
            show("Complete the charging session first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        if (frame.currentPayment != null && "PAID".equals(frame.currentPayment.getPaymentStatus())) {
            show("Payment already processed.", AppTheme.BLUE, AppIcons.IconType.INFO_CIRCLE);
            return;
        }

        String method = (String) methodCombo.getSelectedItem();
        frame.currentPayment = new Payment("PAY-" + (frame.currentBooking != null ? frame.currentBooking.getBookingId() : "001"), frame.currentSession);
        frame.currentPayment.makePayment(method);

        double amount = frame.currentPayment.getAmount();
        amountVal.setText(String.format("₹%.2f", amount));
        methodVal.setText(method);

        if ("PAID".equals(frame.currentPayment.getPaymentStatus())) {
            payStatusVal.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 13, AppTheme.SUCCESS));
            payStatusVal.setIconTextGap(5);
            payStatusVal.setText("PAID");
            payStatusVal.setForeground(AppTheme.SUCCESS);

            // Persist payment to MySQL
            String dbMethod = "UPI";
            if ("Card".equalsIgnoreCase(method)) dbMethod = "CARD";
            else if ("Wallet".equalsIgnoreCase(method)) dbMethod = "WALLET";
            else if ("Cash".equalsIgnoreCase(method)) dbMethod = "CASH";

            int sId = frame.currentSession.getId() > 0 ? frame.currentSession.getId() : 1;
            int payId = paymentDAO.recordPayment(sId, dbMethod, amount, "PAID");
            if (payId > 0) {
                frame.currentPayment.setId(payId);
            }

            // Award loyalty and persist transaction
            LoyaltyService ls = new LoyaltyService();
            int earned = ls.awardPoints(frame.currentDriver, frame.currentPayment);
            pointsEarned.setText("Points earned this session: +" + earned);
            totalPoints.setText("Total loyalty points: " + (frame.currentDriver != null ? frame.currentDriver.getLoyaltyPoints() : earned));

            show("Payment successful via " + method + " | +" + earned + " pts earned (Saved to MySQL)", AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
            frame.setStatus("Paid ₹" + String.format("%.2f", amount) + " via " + method + " | Points: " + (frame.currentDriver != null ? frame.currentDriver.getLoyaltyPoints() : earned));
        } else {
            show("Payment failed — session may not be completed.", AppTheme.ERROR, AppIcons.IconType.ALERT_CIRCLE);
        }
    }

    private void show(String msg, Color c, AppIcons.IconType iconType) {
        statusLabel.setText(msg);
        statusLabel.setForeground(c);
        statusLabel.setIcon(iconType != null ? AppIcons.get(iconType, 13, c) : null);
        statusLabel.setIconTextGap(6);
    }

    public void refresh() {
        if (frame.currentSession != null && frame.currentSession.isCompleted()) {
            amountVal.setText(String.format("₹%.2f", frame.currentSession.getTotalAmount()));
        }
        if (frame.currentPayment != null) {
            methodVal.setText(frame.currentPayment.getPaymentMethod() != null ?
                frame.currentPayment.getPaymentMethod() : "—");
            String st = frame.currentPayment.getPaymentStatus();
            if ("PAID".equals(st)) {
                payStatusVal.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 13, AppTheme.SUCCESS));
                payStatusVal.setIconTextGap(5);
                payStatusVal.setText("PAID");
                payStatusVal.setForeground(AppTheme.SUCCESS);
            } else {
                payStatusVal.setIcon(null);
                payStatusVal.setText(st);
                payStatusVal.setForeground(AppTheme.WARNING);
            }
        }
        if (frame.currentDriver != null) {
            totalPoints.setText("Total loyalty points: " + frame.currentDriver.getLoyaltyPoints());
        }
    }
}
