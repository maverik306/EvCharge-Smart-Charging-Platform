package com.evcharge.app;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.evcharge.app.model.*;
import com.evcharge.app.service.BookingService;

public class BookingPanel extends JPanel {

    private final MainFrame frame;

    // Form
    private JTextField bookingIdField;
    private JComboBox<String> modeCombo;
    private JLabel formStatus;

    // Queue table
    private DefaultTableModel tableModel;
    private JTable queueTable;
    private JLabel queueSizeLabel;

    // In-memory list to mirror PriorityQueue for display
    private final List<String[]> queueRows = new ArrayList<>();

    // Current booking info
    private JLabel currentBookingInfo;
    private JLabel bookingStatusLabel;

    public BookingPanel(MainFrame frame) {
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

        g.gridx = 0; g.weightx = 0.35; g.insets = new Insets(0,0,0,16);
        body.add(buildFormCard(), g);

        g.gridx = 1; g.weightx = 0.65; g.insets = new Insets(0,0,0,0);
        body.add(buildQueueCard(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Booking & Priority Queue");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Validated bookings persisted to MySQL with EMERGENCY-first priority dispatching");
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

    // ── Booking form ──────────────────────────────────────────────────────
    private JPanel buildFormCard() {
        JPanel card = AppTheme.card("New Booking", AppIcons.get(AppIcons.IconType.BOOKING, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        bookingIdField = AppTheme.textField();
        bookingIdField.setText("BK-001");
        modeCombo = AppTheme.combo(new String[]{"NORMAL", "EMERGENCY"});

        g.gridy = 0; form.add(AppTheme.formRow("Booking ID", bookingIdField), g);
        g.gridy = 1; form.add(AppTheme.formRow("Charging Mode", modeCombo),   g);

        // Booking lifecycle controls
        JPanel lifecycleCard = new JPanel();
        lifecycleCard.setLayout(new BoxLayout(lifecycleCard, BoxLayout.Y_AXIS));
        lifecycleCard.setBackground(AppTheme.BG_CARD);
        lifecycleCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(12, 14, 12, 14)
        ));

        JLabel lcTitle = new JLabel("Active Booking Lifecycle");
        lcTitle.setFont(AppTheme.FONT_H3);
        lcTitle.setForeground(AppTheme.TEXT_PRI);
        lcTitle.setAlignmentX(LEFT_ALIGNMENT);

        currentBookingInfo = new JLabel("No active booking.");
        currentBookingInfo.setFont(AppTheme.FONT_SMALL);
        currentBookingInfo.setForeground(AppTheme.TEXT_SEC);
        currentBookingInfo.setAlignmentX(LEFT_ALIGNMENT);

        bookingStatusLabel = new JLabel("Status: —");
        bookingStatusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookingStatusLabel.setForeground(AppTheme.ACCENT);
        bookingStatusLabel.setAlignmentX(LEFT_ALIGNMENT);

        JButton confirmBtn = AppTheme.secondaryBtn("Confirm Booking", AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 14, AppTheme.TEXT_PRI));
        confirmBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        confirmBtn.setAlignmentX(LEFT_ALIGNMENT);
        confirmBtn.addActionListener(e -> confirmBooking());

        JButton startBtn = AppTheme.primaryBtn("Start Charging", AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.BG_DARK));
        startBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        startBtn.setAlignmentX(LEFT_ALIGNMENT);
        startBtn.addActionListener(e -> startCharging());

        lifecycleCard.add(lcTitle);
        lifecycleCard.add(Box.createVerticalStrut(8));
        lifecycleCard.add(currentBookingInfo);
        lifecycleCard.add(Box.createVerticalStrut(4));
        lifecycleCard.add(bookingStatusLabel);
        lifecycleCard.add(Box.createVerticalStrut(12));
        lifecycleCard.add(confirmBtn);
        lifecycleCard.add(Box.createVerticalStrut(8));
        lifecycleCard.add(startBtn);

        formStatus = new JLabel(" ");
        formStatus.setFont(AppTheme.FONT_SMALL);

        JButton addBtn = AppTheme.primaryBtn("Add to Queue", AppIcons.get(AppIcons.IconType.PLUS, 14, AppTheme.BG_DARK));
        addBtn.addActionListener(e -> addToQueue());
        JButton processBtn = AppTheme.secondaryBtn("Process Next", AppIcons.get(AppIcons.IconType.PLAY, 14, AppTheme.TEXT_PRI));
        processBtn.addActionListener(e -> processNext());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(addBtn);
        btnRow.add(processBtn);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(btnRow);
        south.add(Box.createVerticalStrut(6));
        south.add(formStatus);
        south.add(Box.createVerticalStrut(14));
        south.add(lifecycleCard);

        g.gridy = 2; form.add(AppTheme.sep(), g);
        card.add(form,  BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);
        return card;
    }

    // ── Queue table ───────────────────────────────────────────────────────
    private JPanel buildQueueCard() {
        JPanel card = AppTheme.card("");

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_CARD);

        JLabel title = new JLabel("Priority Queue");
        title.setFont(AppTheme.FONT_H3);
        title.setForeground(AppTheme.TEXT_PRI);

        queueSizeLabel = new JLabel("0 bookings");
        queueSizeLabel.setFont(AppTheme.FONT_SMALL);
        queueSizeLabel.setForeground(AppTheme.TEXT_SEC);

        header.add(title,          BorderLayout.WEST);
        header.add(queueSizeLabel, BorderLayout.EAST);

        // Table
        String[] cols = {"Booking ID", "Driver", "Vehicle", "Mode", "Battery %"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        queueTable = new JTable(tableModel);
        queueTable.setFont(AppTheme.FONT_BODY);
        queueTable.setRowHeight(38);
        queueTable.setShowVerticalLines(false);
        queueTable.setGridColor(AppTheme.BORDER_COL);
        queueTable.getTableHeader().setFont(AppTheme.FONT_LABEL);
        queueTable.getTableHeader().setBackground(AppTheme.BG_SIDEBAR);
        queueTable.getTableHeader().setForeground(AppTheme.TEXT_SEC);
        queueTable.setBackground(AppTheme.BG_CARD);
        queueTable.setForeground(AppTheme.TEXT_PRI);
        queueTable.setSelectionBackground(AppTheme.ACCENT_DIM);
        queueTable.setSelectionForeground(AppTheme.ACCENT);
        queueTable.setFillsViewportHeight(true);
        queueTable.setIntercellSpacing(new Dimension(0, 0));

        // Custom renderer to colour EMERGENCY rows
        DefaultTableCellRenderer modeRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                String mode = (String) t.getValueAt(row, 3);
                if ("EMERGENCY".equals(mode)) {
                    setForeground(AppTheme.ERROR);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else {
                    setForeground(AppTheme.TEXT_PRI);
                    setFont(AppTheme.FONT_BODY);
                }
                setBackground(sel ? AppTheme.ACCENT_DIM : AppTheme.BG_CARD);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return this;
            }
        };
        for (int i = 0; i < queueTable.getColumnCount(); i++)
            queueTable.getColumnModel().getColumn(i).setCellRenderer(modeRenderer);

        JScrollPane scroll = AppTheme.scrollPane(queueTable);
        scroll.getViewport().setBackground(AppTheme.BG_CARD);

        // Hint
        JLabel hint = new JLabel("EMERGENCY bookings jump to the front, followed by lowest battery percentage.",
                AppIcons.get(AppIcons.IconType.INFO_CIRCLE, 13, AppTheme.TEXT_MUTED), JLabel.LEFT);
        hint.setIconTextGap(6);
        hint.setFont(AppTheme.FONT_SMALL);
        hint.setForeground(AppTheme.TEXT_MUTED);
        hint.setBorder(new EmptyBorder(10, 0, 0, 0));

        card.add(header, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(hint,   BorderLayout.SOUTH);
        return card;
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void addToQueue() {
        if (frame.currentDriver == null || frame.currentVehicle == null || frame.currentCharger == null) {
            showStatus("Please save a Driver, Vehicle, and Charger first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        String id   = bookingIdField.getText().trim();
        String mode = (String) modeCombo.getSelectedItem();
        if (id.isEmpty()) {
            showStatus("Booking ID is required.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        Booking b = new Booking(id, frame.currentDriver, frame.currentVehicle, frame.currentCharger, mode);

        // Validate compatibility & availability and save to MySQL
        BookingService service = new BookingService();
        boolean ok = service.createBooking(b);
        if (!ok) {
            showStatus("Validation failed! Check connector compatibility or charger availability.", AppTheme.ERROR, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        frame.bookingQueue.addBooking(b);

        String bat = frame.currentVehicle.getCurrentBatteryPercentage() + "%";
        queueRows.add(new String[]{id, frame.currentDriver.getName(),
                frame.currentVehicle.getModel(), mode, bat});
        rebuildTable();

        showStatus("Booking " + id + " saved to MySQL & queued (" + mode + ")", AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
        frame.setStatus("Booking " + id + " queued | mode: " + mode);

        // Auto-increment booking ID for convenience
        try {
            String prefix = id.replaceAll("\\d+$", "");
            int num = Integer.parseInt(id.replaceAll("\\D+", ""));
            bookingIdField.setText(prefix + String.format("%03d", num + 1));
        } catch (Exception ignored) {}
    }

    private void processNext() {
        Booking next = new BookingService().dispatchNext(frame.bookingQueue);
        if (next == null) {
            showStatus("Queue is empty.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        frame.currentBooking = next;

        // Remove from display list
        queueRows.removeIf(r -> r[0].equals(next.getBookingId()));
        rebuildTable();

        updateLifecycleDisplay();
        showStatus("Processing: " + next.getBookingId() + " [" + next.getChargingMode() + "] (CONFIRMED in DB)", AppTheme.ACCENT, AppIcons.IconType.PLAY);
        frame.setStatus("Active booking: " + next.getBookingId() + " CONFIRMED");
    }

    private void confirmBooking() {
        if (frame.currentBooking == null) {
            showStatus("No active booking. Process one from the queue first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        new BookingService().confirmBooking(frame.currentBooking);
        updateLifecycleDisplay();
        showStatus("Booking CONFIRMED in MySQL database", AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
        frame.setStatus("Booking " + frame.currentBooking.getBookingId() + " CONFIRMED");
    }

    private void startCharging() {
        if (frame.currentBooking == null) {
            showStatus("No active booking.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        if (!"CONFIRMED".equals(frame.currentBooking.getStatus())) {
            showStatus("Confirm the booking before starting charging.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }
        frame.currentSession = new BookingService().startCharging(frame.currentBooking);
        updateLifecycleDisplay();
        showStatus("Charging started! Session created in MySQL", AppTheme.ACCENT, AppIcons.IconType.LIGHTNING);
        frame.setStatus("Charging: " + frame.currentBooking.getBookingId());

        // Navigate to Session
        Timer delay = new Timer(900, e -> frame.navigateTo("Session"));
        delay.setRepeats(false);
        delay.start();
    }

    private void rebuildTable() {
        tableModel.setRowCount(0);
        for (String[] row : queueRows)
            tableModel.addRow(row);
        queueSizeLabel.setText(queueRows.size() + " booking" + (queueRows.size() == 1 ? "" : "s"));
    }

    private void updateLifecycleDisplay() {
        if (frame.currentBooking == null) return;
        currentBookingInfo.setText(frame.currentBooking.getBookingId()
            + "  •  " + frame.currentBooking.getDriver().getName()
            + "  •  " + frame.currentBooking.getChargingMode());
        String st = frame.currentBooking.getStatus();
        bookingStatusLabel.setText("Status: " + st);
        bookingStatusLabel.setForeground(
            "COMPLETED".equals(st) ? AppTheme.SUCCESS :
            "CHARGING".equals(st)  ? AppTheme.ACCENT   :
            "CONFIRMED".equals(st) ? AppTheme.BLUE      : AppTheme.WARNING
        );
    }

    private void showStatus(String msg, Color color, AppIcons.IconType iconType) {
        formStatus.setText(msg);
        formStatus.setForeground(color);
        formStatus.setIcon(iconType != null ? AppIcons.get(iconType, 13, color) : null);
        formStatus.setIconTextGap(6);
    }

    public void refresh() {
        updateLifecycleDisplay();
    }
}
