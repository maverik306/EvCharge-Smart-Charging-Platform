package com.evcharge.app;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.dao.ChargerDAO;
import com.evcharge.app.model.*;

public class ChargerPanel extends JPanel {

    private final MainFrame frame;
    private final ChargerDAO chargerDAO = new ChargerDAO();

    // Charger fields
    private JTextField chargerId, pricePerKwh;
    private JComboBox<String> chargerType, chargerConnector;
    private JCheckBox available;

    // Location fields
    private JTextField locationName, locationAddress, locationRating;
    private JCheckBox locationVerified;

    // Compatibility display
    private JLabel compatLabel, statusLabel;
    private JPanel compatCard;

    public ChargerPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(AppTheme.BG_DARK);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(28, 28, 28, 28));

        add(buildHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(AppTheme.BG_DARK);

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 0, 0, 14);

        g.gridx = 0; g.gridy = 0; g.weightx = 0.45; g.weighty = 1;
        body.add(buildChargerCard(), g);

        g.gridx = 1; g.weightx = 0.35; g.insets = new Insets(0, 0, 0, 14);
        body.add(buildLocationCard(), g);

        g.gridx = 2; g.weightx = 0.2; g.insets = new Insets(0, 0, 0, 0);
        body.add(buildCompatCard(), g);

        add(body, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AppTheme.BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 22, 0));

        JLabel title = new JLabel("Charger & Location");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Configure EV chargers and charging stations connected to MySQL");
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

    private JPanel buildChargerCard() {
        JPanel card = AppTheme.card("Charger Configuration", AppIcons.get(AppIcons.IconType.CHARGER, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        chargerId        = AppTheme.textField();
        pricePerKwh      = AppTheme.textField();
        chargerType      = AppTheme.combo(new String[]{"Fast", "Slow", "Ultra-Fast"});
        chargerConnector = AppTheme.combo(new String[]{"CCS2", "Type 2", "CHAdeMO", "GB/T"});
        available        = new JCheckBox("Charger is currently available");
        available.setBackground(AppTheme.BG_CARD);
        available.setForeground(AppTheme.TEXT_PRI);
        available.setFont(AppTheme.FONT_BODY);
        available.setSelected(true);

        // Demo defaults
        chargerId.setText("GC-01");
        pricePerKwh.setText("18.50");

        g.gridy = 0; form.add(AppTheme.formRow("Charger ID",          chargerId),        g);
        g.gridy = 1; form.add(AppTheme.formRow("Charging Type",       chargerType),      g);
        g.gridy = 2; form.add(AppTheme.formRow("Connector Type",      chargerConnector), g);
        g.gridy = 3; form.add(AppTheme.formRow("Price per kWh (₹)",   pricePerKwh),      g);
        g.gridy = 4; form.add(available, g);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(AppTheme.FONT_SMALL);
        statusLabel.setBorder(new EmptyBorder(4, 0, 0, 0));

        JButton save = AppTheme.primaryBtn("Save Charger", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        save.addActionListener(e -> saveCharger());

        JButton next = AppTheme.secondaryBtn("Next: Booking", AppIcons.get(AppIcons.IconType.BOOKING, 14, AppTheme.TEXT_PRI));
        next.addActionListener(e -> frame.navigateTo("Booking"));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(save);
        btnRow.add(next);

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

    private JPanel buildLocationCard() {
        JPanel card = AppTheme.card("Charging Location", AppIcons.get(AppIcons.IconType.LOCATION, 16, AppTheme.TEXT_PRI));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(AppTheme.BG_CARD);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        g.insets = new Insets(0, 0, 14, 0);

        locationName     = AppTheme.textField();
        locationAddress  = AppTheme.textField();
        locationRating   = AppTheme.textField();
        locationVerified = new JCheckBox("Verified Location");
        locationVerified.setBackground(AppTheme.BG_CARD);
        locationVerified.setForeground(AppTheme.TEXT_PRI);
        locationVerified.setFont(AppTheme.FONT_BODY);
        locationVerified.setSelected(true);

        // Demo defaults
        locationName.setText("GreenCharge Cafe");
        locationAddress.setText("MG Road, Bengaluru");
        locationRating.setText("4.6");

        g.gridy = 0; form.add(AppTheme.formRow("Location Name",   locationName),    g);
        g.gridy = 1; form.add(AppTheme.formRow("Address",         locationAddress), g);
        g.gridy = 2; form.add(AppTheme.formRow("Rating (1–5)",    locationRating),  g);
        g.gridy = 3; form.add(locationVerified, g);

        JButton save = AppTheme.primaryBtn("Save Location", AppIcons.get(AppIcons.IconType.SAVE, 14, AppTheme.BG_DARK));
        save.addActionListener(e -> saveLocation());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(AppTheme.BG_CARD);
        btnRow.add(save);

        card.add(form,   BorderLayout.CENTER);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildCompatCard() {
        compatCard = AppTheme.card("Compatibility", AppIcons.get(AppIcons.IconType.SEARCH, 16, AppTheme.TEXT_PRI));

        compatLabel = new JLabel("<html><center>Save a charger<br>and vehicle<br>to check<br>compatibility.</center></html>");
        compatLabel.setFont(AppTheme.FONT_BODY);
        compatLabel.setForeground(AppTheme.TEXT_SEC);
        compatLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton checkBtn = AppTheme.secondaryBtn("Check Now", AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.TEXT_PRI));
        checkBtn.addActionListener(e -> checkCompatibility());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(AppTheme.BG_CARD);
        compatLabel.setAlignmentX(CENTER_ALIGNMENT);
        checkBtn.setAlignmentX(CENTER_ALIGNMENT);
        center.add(Box.createVerticalGlue());
        center.add(compatLabel);
        center.add(Box.createVerticalStrut(14));
        center.add(checkBtn);
        center.add(Box.createVerticalGlue());

        compatCard.add(center, BorderLayout.CENTER);
        return compatCard;
    }

    // ── Actions ───────────────────────────────────────────────────────────
    private void saveCharger() {
        String id = chargerId.getText().trim();
        if (id.isEmpty()) {
            statusLabel.setIcon(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 13, AppTheme.WARNING));
            statusLabel.setIconTextGap(6);
            statusLabel.setText("Charger ID is required.");
            statusLabel.setForeground(AppTheme.WARNING);
            return;
        }
        try {
            double price = Double.parseDouble(pricePerKwh.getText().trim());
            String type  = (String) chargerType.getSelectedItem();
            String conn  = (String) chargerConnector.getSelectedItem();
            int locId    = frame.currentLocation != null && frame.currentLocation.getLocationId() > 0 ?
                    frame.currentLocation.getLocationId() : 1;

            int cId = chargerDAO.saveCharger(locId, id, type, conn, price, available.isSelected());
            statusLabel.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 13, AppTheme.SUCCESS));
            statusLabel.setIconTextGap(6);
            if (cId > 0) {
                frame.currentCharger = new Charger(cId, locId, id, type, conn, price, available.isSelected());
                statusLabel.setText("Charger saved to MySQL (ID: " + cId + ", " + conn + ", ₹" + price + "/kWh)");
            } else {
                frame.currentCharger = new Charger(id, type, conn, price, available.isSelected());
                statusLabel.setText("Charger " + id + " saved (" + conn + ", ₹" + price + "/kWh)");
            }
            statusLabel.setForeground(AppTheme.SUCCESS);
            frame.setStatus("Charger: " + id + " | " + conn + " | ₹" + price + "/kWh");
            checkCompatibility();
        } catch (NumberFormatException e) {
            statusLabel.setIcon(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 13, AppTheme.ERROR));
            statusLabel.setIconTextGap(6);
            statusLabel.setText("Price must be a number.");
            statusLabel.setForeground(AppTheme.ERROR);
        }
    }

    private void saveLocation() {
        String name = locationName.getText().trim();
        if (name.isEmpty()) return;
        try {
            double rating = Double.parseDouble(locationRating.getText().trim());
            String address = locationAddress.getText().trim();
            boolean verified = locationVerified.isSelected();

            int locId = chargerDAO.saveLocation(name, address, "Station", verified, rating);
            if (locId > 0) {
                frame.currentLocation = new ChargingLocation(locId, name, address, "Station", verified, rating);
                JOptionPane.showMessageDialog(this,
                    "Location saved to MySQL (ID: " + locId + "): " + name,
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                frame.currentLocation = new ChargingLocation(name, address, verified, rating);
                JOptionPane.showMessageDialog(this,
                    "Location saved: " + name,
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Rating must be a decimal number (e.g. 4.6)",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkCompatibility() {
        if (frame.currentCharger == null || frame.currentVehicle == null) {
            compatLabel.setForeground(AppTheme.TEXT_SEC);
            compatLabel.setText("<html><center>Need both a<br>charger and<br>vehicle saved.</center></html>");
            return;
        }
        boolean compat = frame.currentCharger.isCompatibleWith(frame.currentVehicle);
        boolean avail  = frame.currentCharger.isAvailable();

        if (compat && avail) {
            compatLabel.setForeground(AppTheme.SUCCESS);
            compatLabel.setText("<html><center><b style='color:#00C87A; font-size:12px;'>COMPATIBLE</b><br><br>"
                + frame.currentCharger.getConnectorType() + "<br>matches<br>"
                + frame.currentVehicle.getConnectorType() + "<br><br>Available [Ready]</center></html>");
        } else if (!compat) {
            compatLabel.setForeground(AppTheme.ERROR);
            compatLabel.setText("<html><center><b style='color:#FF5C5C; font-size:12px;'>INCOMPATIBLE</b><br><br>"
                + frame.currentCharger.getConnectorType() + "<br>!=<br>"
                + frame.currentVehicle.getConnectorType() + "</center></html>");
        } else {
            compatLabel.setForeground(AppTheme.WARNING);
            compatLabel.setText("<html><center><b style='color:#FFB800; font-size:12px;'>UNAVAILABLE</b><br><br>"
                + "Connector matches<br>but charger is<br>not available.</center></html>");
        }
        compatCard.repaint();
    }

    public void refresh() {
        // If no charger selected yet, attempt to populate from DB
        if (frame.currentCharger == null) {
            List<Charger> list = chargerDAO.getAllChargers();
            if (!list.isEmpty()) {
                Charger first = list.get(0);
                chargerId.setText(first.getChargerId());
                pricePerKwh.setText(String.format("%.2f", first.getPricePerKwh()));
                chargerType.setSelectedItem(first.getChargingMode());
                chargerConnector.setSelectedItem(first.getConnectorType());
                available.setSelected(first.isAvailable());
                frame.currentCharger = first;
            }
        }
        if (frame.currentLocation == null) {
            List<ChargingLocation> locs = chargerDAO.getAllLocations();
            if (!locs.isEmpty()) {
                ChargingLocation first = locs.get(0);
                locationName.setText(first.getName());
                locationAddress.setText(first.getAddress());
                locationRating.setText(String.valueOf(first.getRating()));
                locationVerified.setSelected(first.isVerified());
                frame.currentLocation = first;
            }
        }
        checkCompatibility();
    }
}
