package com.evcharge.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.ReviewDAO;
import com.evcharge.app.model.*;

public class ReviewPanel extends JPanel {

    private final MainFrame frame;
    private final ReviewDAO reviewDAO = new ReviewDAO();

    private int selectedRating = 5;
    private JButton[] starButtons;
    private JTextArea commentArea;
    private JLabel statusLabel, validationLabel;
    private JPanel reviewResultCard;
    private JLabel resultRating, resultComment, resultBooking;

    public ReviewPanel(MainFrame frame) {
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

        g.gridx = 0; g.weightx = 0.45; g.insets = new Insets(0, 0, 0, 18);
        body.add(buildReviewForm(), g);

        g.gridx = 1; g.weightx = 0.55; g.insets = new Insets(0, 0, 0, 0);
        body.add(buildResultCard(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Leave a Review");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Rate and review your charging experience — only for COMPLETED sessions (persisted to MySQL)");
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

    private JPanel buildReviewForm() {
        JPanel card = AppTheme.card("Your Review", AppIcons.get(AppIcons.IconType.REVIEW, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        // Star rating row
        JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        starsPanel.setBackground(AppTheme.BG_CARD);
        starButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            final int star = i + 1;
            JButton btn = new JButton();
            btn.setIcon(AppIcons.get(AppIcons.IconType.REVIEW, 26, AppTheme.WARNING));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setMargin(new Insets(0, 2, 0, 2));
            btn.addActionListener(e -> {
                selectedRating = star;
                updateStars(star);
            });
            btn.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { updateStars(star); }
                @Override public void mouseExited(MouseEvent e)  { updateStars(selectedRating); }
            });
            starButtons[i] = btn;
            starsPanel.add(btn);
        }

        // Comment area
        commentArea = new JTextArea(4, 20);
        commentArea.setFont(AppTheme.FONT_BODY);
        commentArea.setForeground(AppTheme.TEXT_PRI);
        commentArea.setBackground(AppTheme.BG_INPUT);
        commentArea.setCaretColor(AppTheme.ACCENT);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setText("Fast charging and clean rest area.");
        commentArea.setBorder(new EmptyBorder(8, 12, 8, 12));

        JScrollPane commentScroll = new JScrollPane(commentArea);
        commentScroll.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_COL, 1));
        commentScroll.setBackground(AppTheme.BG_INPUT);

        // Rating label
        JLabel ratingLabel = new JLabel("Rating (1–5 Stars)");
        ratingLabel.setFont(AppTheme.FONT_LABEL);
        ratingLabel.setForeground(AppTheme.TEXT_SEC);

        JLabel commentLabel = new JLabel("Comment");
        commentLabel.setFont(AppTheme.FONT_LABEL);
        commentLabel.setForeground(AppTheme.TEXT_SEC);

        g.gridy = 0; form.add(ratingLabel,   g);
        g.gridy = 1; form.add(starsPanel,    g);
        g.gridy = 2; form.add(commentLabel,  g);
        g.gridy = 3; form.add(commentScroll, g);

        // Rule indicator
        validationLabel = new JLabel("Review requires a COMPLETED booking.",
                AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 13, AppTheme.WARNING), JLabel.LEFT);
        validationLabel.setIconTextGap(6);
        validationLabel.setFont(AppTheme.FONT_SMALL);
        validationLabel.setForeground(AppTheme.WARNING);
        validationLabel.setBorder(new EmptyBorder(4, 0, 0, 0));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_SMALL);

        JButton submitBtn = AppTheme.primaryBtn("Submit Review", AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 14, AppTheme.BG_DARK));
        submitBtn.addActionListener(e -> submitReview());

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(submitBtn);
        south.add(Box.createVerticalStrut(6));
        south.add(validationLabel);
        south.add(Box.createVerticalStrut(4));
        south.add(statusLabel);

        card.add(form,  BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildResultCard() {
        reviewResultCard = AppTheme.card("Review Summary", AppIcons.get(AppIcons.IconType.REVIEW, 16, AppTheme.TEXT_PRI));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(AppTheme.BG_CARD);

        resultRating = new JLabel("No review submitted yet.");
        resultRating.setFont(new Font("Segoe UI", Font.BOLD, 26));
        resultRating.setForeground(AppTheme.TEXT_SEC);
        resultRating.setAlignmentX(LEFT_ALIGNMENT);

        resultBooking = new JLabel("—");
        resultBooking.setFont(AppTheme.FONT_SMALL);
        resultBooking.setForeground(AppTheme.TEXT_MUTED);
        resultBooking.setAlignmentX(LEFT_ALIGNMENT);

        resultComment = new JLabel("");
        resultComment.setFont(AppTheme.FONT_BODY);
        resultComment.setForeground(AppTheme.TEXT_PRI);
        resultComment.setAlignmentX(LEFT_ALIGNMENT);
        resultComment.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(12, 14, 12, 14)
        ));
        resultComment.setBackground(AppTheme.BG_INPUT);
        resultComment.setOpaque(true);

        JLabel note = new JLabel("<html><b>Business Rule:</b><br>"
            + "A review is only valid if:<br>"
            + "• Booking status = COMPLETED<br>"
            + "• Rating between 1 and 5</html>");
        note.setFont(AppTheme.FONT_SMALL);
        note.setForeground(AppTheme.TEXT_MUTED);
        note.setBorder(new EmptyBorder(16, 0, 0, 0));
        note.setAlignmentX(LEFT_ALIGNMENT);

        inner.add(resultRating);
        inner.add(Box.createVerticalStrut(4));
        inner.add(resultBooking);
        inner.add(Box.createVerticalStrut(12));
        inner.add(resultComment);
        inner.add(Box.createVerticalGlue());
        inner.add(note);

        reviewResultCard.add(inner, BorderLayout.CENTER);
        return reviewResultCard;
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void submitReview() {
        if (frame.currentBooking == null || frame.currentDriver == null) {
            showStatus("Complete a booking first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        ChargingLocation loc = frame.currentLocation != null ?
            frame.currentLocation : new ChargingLocation(1, "GreenCharge Cafe", "MG Road, Bengaluru", "Cafe", true, 4.6);

        Review review = new Review(
            "REV-" + frame.currentBooking.getBookingId(),
            frame.currentDriver,
            loc,
            frame.currentBooking,
            selectedRating,
            commentArea.getText().trim()
        );

        if (review.isValidReview()) {
            // Save to MySQL
            int dId = frame.currentDriver.getDriverId() > 0 ? frame.currentDriver.getDriverId() : 1;
            int lId = loc.getLocationId() > 0 ? loc.getLocationId() : 1;
            int bId = frame.currentBooking.getId() > 0 ? frame.currentBooking.getId() : 1;
            reviewDAO.submitReview(dId, lId, bId, selectedRating, commentArea.getText().trim());

            // Update result display
            resultRating.setIcon(AppIcons.get(AppIcons.IconType.REVIEW, 22, AppTheme.WARNING));
            resultRating.setIconTextGap(8);
            resultRating.setText(selectedRating + " / 5 Stars");
            resultRating.setForeground(AppTheme.WARNING);
            resultBooking.setText("Booking: " + frame.currentBooking.getBookingId()
                + "  •  " + frame.currentDriver.getName() + " (Saved to MySQL)");
            resultComment.setText("<html>" + commentArea.getText().trim() + "</html>");

            validationLabel.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 13, AppTheme.SUCCESS));
            validationLabel.setText("Review accepted & persisted to MySQL!");
            validationLabel.setForeground(AppTheme.SUCCESS);
            showStatus(selectedRating + " star review submitted!", AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
            frame.setStatus("Review submitted: " + selectedRating + " stars | " + frame.currentBooking.getBookingId());
        } else {
            validationLabel.setIcon(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 13, AppTheme.ERROR));
            validationLabel.setText("Review invalid — booking not COMPLETED or rating out of range.");
            validationLabel.setForeground(AppTheme.ERROR);
            showStatus("Review invalid. Make sure booking is COMPLETED and rating is 1–5.", AppTheme.ERROR, AppIcons.IconType.ALERT_CIRCLE);
        }
    }

    private void updateStars(int rating) {
        for (int j = 0; j < 5; j++) {
            Color starCol = j < rating ? AppTheme.WARNING : AppTheme.BORDER_COL;
            starButtons[j].setIcon(AppIcons.get(AppIcons.IconType.REVIEW, 26, starCol));
        }
    }

    private void showStatus(String msg, Color c, AppIcons.IconType iconType) {
        statusLabel.setText(msg);
        statusLabel.setForeground(c);
        statusLabel.setIcon(iconType != null ? AppIcons.get(iconType, 13, c) : null);
        statusLabel.setIconTextGap(6);
    }

    public void refresh() {
        if (frame.currentBooking != null && "COMPLETED".equals(frame.currentBooking.getStatus())) {
            validationLabel.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 13, AppTheme.SUCCESS));
            validationLabel.setText("Booking is COMPLETED — review can be submitted.");
            validationLabel.setForeground(AppTheme.SUCCESS);
        } else {
            validationLabel.setIcon(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 13, AppTheme.WARNING));
            validationLabel.setText("Review requires a COMPLETED booking.");
            validationLabel.setForeground(AppTheme.WARNING);
        }
    }
}
