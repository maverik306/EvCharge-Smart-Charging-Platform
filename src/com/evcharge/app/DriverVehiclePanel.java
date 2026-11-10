package com.evcharge.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.DriverDAO;
import com.evcharge.app.dao.VehicleDAO;
import com.evcharge.app.model.*;

public class DriverVehiclePanel extends JPanel {

    private final MainFrame frame;

    // Driver fields
    private JTextField driverName, driverPhone, driverEmail, loyaltyField;

    // Vehicle fields
    private JTextField vehicleModel, batteryCapacity, batteryPercent;
    private JComboBox<String> connectorType;

    // Status
    private JLabel driverStatus, vehicleStatus;

    public DriverVehiclePanel(MainFrame frame) {
        this.frame = frame;
        setBackground(AppTheme.BG_DARK);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(28, 28, 28, 28));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridLayout(1, 2, 20, 0));
        body.setBackground(AppTheme.BG_DARK);
        body.add(buildDriverCard());
        body.add(buildVehicleCard());

        JScrollPane scroll = AppTheme.scrollPane(body);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.BG_DARK);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Driver & Vehicle Setup");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Configure driver profile and EV specifications to begin charging operations");
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

    // ── Driver card ───────────────────────────────────────────────────────
    private JPanel buildDriverCard() {
        JPanel card = AppTheme.card("Driver Profile", AppIcons.get(AppIcons.IconType.USER, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        driverName   = AppTheme.textField();
        driverPhone  = AppTheme.textField();
        driverEmail  = AppTheme.textField();
        loyaltyField = AppTheme.textField();
        loyaltyField.setText("0");
        loyaltyField.setToolTipText("Optional starting loyalty points");

        g.gridy = 0; form.add(AppTheme.formRow("Full Name",            driverName),   g);
        g.gridy = 1; form.add(AppTheme.formRow("Phone Number",         driverPhone),  g);
        g.gridy = 2; form.add(AppTheme.formRow("Email Address",        driverEmail),  g);
        g.gridy = 3; form.add(AppTheme.formRow("Starting Loyalty Pts", loyaltyField), g);

        driverStatus = new JLabel(" ");
        driverStatus.setFont(AppTheme.FONT_SMALL);
        driverStatus.setBorder(new EmptyBorder(4, 0, 0, 0));

        JButton save = AppTheme.primaryBtn("Save Driver", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        save.addActionListener(e -> saveDriver());

        JButton clear = AppTheme.secondaryBtn("Clear", AppIcons.get(AppIcons.IconType.CLEAR, 14, AppTheme.TEXT_PRI));
        clear.setPreferredSize(new Dimension(100, 40));
        clear.addActionListener(e -> clearDriver());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(save);
        btnRow.add(clear);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(btnRow);
        south.add(Box.createVerticalStrut(6));
        south.add(driverStatus);

        card.add(form,  BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);
        return card;
    }

    // ── Vehicle card ──────────────────────────────────────────────────────
    private JPanel buildVehicleCard() {
        JPanel card = AppTheme.card("Vehicle Details", AppIcons.get(AppIcons.IconType.CAR, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        vehicleModel    = AppTheme.textField();
        batteryCapacity = AppTheme.textField();
        batteryPercent  = AppTheme.textField();
        connectorType   = AppTheme.combo(new String[]{"CCS2", "Type 2", "CHAdeMO", "GB/T"});

        g.gridy = 0; form.add(AppTheme.formRow("Vehicle Model",          vehicleModel),    g);
        g.gridy = 1; form.add(AppTheme.formRow("Battery Capacity (kWh)", batteryCapacity), g);
        g.gridy = 2; form.add(AppTheme.formRow("Current Battery %",      batteryPercent),  g);
        g.gridy = 3; form.add(AppTheme.formRow("Connector Type",         connectorType),   g);

        vehicleStatus = new JLabel(" ");
        vehicleStatus.setFont(AppTheme.FONT_SMALL);
        vehicleStatus.setBorder(new EmptyBorder(4, 0, 0, 0));

        JButton save = AppTheme.primaryBtn("Save Vehicle", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        save.addActionListener(e -> saveVehicle());

        JButton next = AppTheme.secondaryBtn("Next: Charger →");
        next.addActionListener(e -> frame.navigateTo("Charger"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(save);
        btnRow.add(next);

        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setBackground(AppTheme.BG_CARD);
        south.add(btnRow);
        south.add(Box.createVerticalStrut(6));
        south.add(vehicleStatus);

        card.add(form,  BorderLayout.CENTER);
        card.add(south, BorderLayout.SOUTH);
        return card;
    }

    public void refresh() {
        if (frame.currentDriver != null) {
            driverName.setText(frame.currentDriver.getName() != null ? frame.currentDriver.getName() : "");
            String p = frame.currentDriver.getPhoneNumber();
            driverPhone.setText(p != null && !p.startsWith("NA-") ? p : "");
            driverEmail.setText(frame.currentDriver.getEmail() != null ? frame.currentDriver.getEmail() : "");
            loyaltyField.setText(String.valueOf(frame.currentDriver.getLoyaltyPoints()));
        } else {
            driverName.setText("");
            driverPhone.setText("");
            driverEmail.setText("");
            loyaltyField.setText("0");
        }

        if (frame.currentVehicle != null) {
            vehicleModel.setText(frame.currentVehicle.getModel() != null ? frame.currentVehicle.getModel() : "");
            batteryCapacity.setText(String.valueOf(frame.currentVehicle.getBatteryCapacityKwh()));
            batteryPercent.setText(String.valueOf(frame.currentVehicle.getCurrentBatteryPercentage()));
            connectorType.setSelectedItem(frame.currentVehicle.getConnectorType());
        } else {
            vehicleModel.setText("");
            batteryCapacity.setText("");
            batteryPercent.setText("");
        }
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void saveDriver() {
        String name  = driverName.getText().trim();
        String phone = driverPhone.getText().trim();
        String email = driverEmail.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            showStatus(driverStatus, "Please fill name and email fields.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        if (phone.isEmpty()) {
            phone = (frame.currentUser != null) ? "NA-" + frame.currentUser.getUserId() : "Not Provided";
        }

        int pts = 0;
        try {
            pts = Integer.parseInt(loyaltyField.getText().trim());
        } catch (NumberFormatException ignored) {}

        // Persist to MySQL
        DriverDAO dao = new DriverDAO();
        int dbId = dao.saveDriver(name, phone, email);
        if (dbId > 0) {
            if (pts > 0) {
                dao.updateLoyaltyPoints(dbId, pts);
            }
            frame.currentDriver = new Driver(dbId, name, phone, email, pts);
            showStatus(driverStatus, "Driver saved to MySQL (ID: " + dbId + "): " + name, AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
        } else {
            frame.currentDriver = new Driver(name, phone, email);
            if (pts > 0) frame.currentDriver.addLoyaltyPoints(pts);
            showStatus(driverStatus, "Driver saved in-memory: " + name, AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
        }

        frame.setStatus("Driver set: " + name + " • " + email);
        if (frame.dashboardPanel != null) {
            frame.dashboardPanel.refresh();
        }
    }

    private void clearDriver() {
        driverName.setText(""); driverPhone.setText(""); driverEmail.setText(""); loyaltyField.setText("0");
        frame.currentDriver = null;
        showStatus(driverStatus, "Driver cleared.", AppTheme.TEXT_SEC, null);
        frame.setStatus("Driver cleared.");
        if (frame.dashboardPanel != null) {
            frame.dashboardPanel.refresh();
        }
    }

    private void saveVehicle() {
        if (frame.currentDriver == null) {
            showStatus(vehicleStatus, "Please save a driver profile first.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        String model = vehicleModel.getText().trim();
        String connector = (String) connectorType.getSelectedItem();

        if (model.isEmpty()) {
            showStatus(vehicleStatus, "Vehicle model is required.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
            return;
        }

        try {
            double cap = Double.parseDouble(batteryCapacity.getText().trim());
            int    pct = Integer.parseInt(batteryPercent.getText().trim());

            if (cap <= 0) {
                showStatus(vehicleStatus, "Battery capacity must be greater than 0 kWh.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
                return;
            }
            if (pct < 0 || pct > 100) {
                showStatus(vehicleStatus, "Battery percentage must be between 0 and 100.", AppTheme.WARNING, AppIcons.IconType.ALERT_CIRCLE);
                return;
            }

            int dId = frame.currentDriver != null && frame.currentDriver.getDriverId() > 0 ?
                    frame.currentDriver.getDriverId() : 1;

            VehicleDAO dao = new VehicleDAO();
            int vId = dao.saveVehicle(dId, model, cap, connector, pct);

            if (vId > 0) {
                frame.currentVehicle = new Vehicle(vId, dId, model, cap, connector, pct);
                showStatus(vehicleStatus, "Vehicle saved to MySQL (ID: " + vId + "): " + model, AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
            } else {
                frame.currentVehicle = new Vehicle(model, cap, connector, pct);
                showStatus(vehicleStatus, "Vehicle saved in-memory: " + model, AppTheme.SUCCESS, AppIcons.IconType.CHECK_CIRCLE);
            }

            frame.setStatus("Vehicle: " + model + " | " + connector + " | " + pct + "% battery");
            if (frame.dashboardPanel != null) {
                frame.dashboardPanel.refresh();
            }
        } catch (NumberFormatException e) {
            showStatus(vehicleStatus, "Capacity and battery% must be valid numbers.", AppTheme.ERROR, AppIcons.IconType.ALERT_CIRCLE);
        }
    }

    private void showStatus(JLabel lbl, String msg, Color color, AppIcons.IconType iconType) {
        lbl.setText(msg);
        lbl.setForeground(color);
        lbl.setIcon(iconType != null ? AppIcons.get(iconType, 13, color) : null);
        lbl.setIconTextGap(6);
    }
}
