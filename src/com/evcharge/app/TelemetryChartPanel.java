package com.evcharge.app;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelemetryChartPanel extends JPanel {

    private final MainFrame frame;
    private final List<Double> powerPoints = new ArrayList<>();
    private final List<Integer> batteryPoints = new ArrayList<>();
    private static final int MAX_POINTS = 32;

    private boolean isCharging = false;
    private double currentKw = 0.0;
    private int currentBatteryPct = 0;
    private double totalEnergyDelivered = 0.0;

    private JLabel kwLabel;
    private JLabel batteryLabel;
    private JLabel energyLabel;
    private JLabel statusPill;

    private Timer telemetryTimer;

    public TelemetryChartPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(AppTheme.BG_CARD);
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(16, 18, 16, 18)
        ));

        // Initialize historical buffer (idle baseline)
        for (int i = 0; i < MAX_POINTS; i++) {
            powerPoints.add(0.0);
            batteryPoints.add(0);
        }

        add(buildHeader(), BorderLayout.NORTH);
        add(new ChartCanvas(), BorderLayout.CENTER);

        initTimer();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_CARD);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(AppTheme.BG_CARD);

        JLabel title = new JLabel("Live Charging Telemetry (OCPP / BMS Stream)",
                AppIcons.get(AppIcons.IconType.ANALYTICS, 18, AppTheme.ACCENT), JLabel.LEFT);
        title.setIconTextGap(8);
        title.setFont(AppTheme.FONT_H3);
        title.setForeground(AppTheme.TEXT_PRI);

        JLabel sub = new JLabel("Read-only sensor telemetry: power output (kW) & vehicle battery percentage (%)");
        sub.setFont(AppTheme.FONT_SMALL);
        sub.setForeground(AppTheme.TEXT_SEC);

        left.add(title);
        left.add(Box.createVerticalStrut(2));
        left.add(sub);

        // Readout pills
        JPanel pills = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pills.setBackground(AppTheme.BG_CARD);

        statusPill   = createPill("IDLE", null, AppTheme.TEXT_MUTED, AppTheme.BORDER_COL);
        kwLabel      = createPill("0.0 kW", AppIcons.get(AppIcons.IconType.LIGHTNING, 13, AppTheme.ACCENT), AppTheme.ACCENT, AppTheme.ACCENT_DIM);
        batteryLabel = createPill("0%", AppIcons.get(AppIcons.IconType.SESSION, 13, AppTheme.BLUE), AppTheme.BLUE, new Color(99, 132, 255, 35));
        energyLabel  = createPill("0.0 kWh", AppIcons.get(AppIcons.IconType.ANALYTICS, 13, AppTheme.PURPLE), AppTheme.PURPLE, new Color(167, 119, 255, 35));

        pills.add(statusPill);
        pills.add(kwLabel);
        pills.add(batteryLabel);
        pills.add(energyLabel);

        header.add(left, BorderLayout.WEST);
        header.add(pills, BorderLayout.EAST);
        return header;
    }

    private JLabel createPill(String text, Icon icon, Color fg, Color bg) {
        JLabel pill = new JLabel(text, icon, JLabel.LEFT);
        pill.setIconTextGap(6);
        pill.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pill.setForeground(fg);
        pill.setBackground(bg);
        pill.setOpaque(true);
        pill.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(fg, 1, true),
            new EmptyBorder(4, 10, 4, 10)
        ));
        return pill;
    }

    private void initTimer() {
        telemetryTimer = new Timer(900, e -> tickTelemetry());
        telemetryTimer.start();
    }

    private void tickTelemetry() {
        if (frame.currentVehicle != null) {
            currentBatteryPct = frame.currentVehicle.getCurrentBatteryPercentage();
        } else {
            currentBatteryPct = 0;
        }

        if (isCharging && frame.currentVehicle != null) {
            // Realistic fast-charging fluctuations
            double baseKw = 48.0;
            if (frame.currentCharger != null && "Slow".equalsIgnoreCase(frame.currentCharger.getChargingMode())) {
                baseKw = 7.4;
            } else if (frame.currentCharger != null && "Ultra-Fast".equalsIgnoreCase(frame.currentCharger.getChargingMode())) {
                baseKw = 120.0;
            }
            double noise = (Math.random() - 0.5) * 3.5;
            currentKw = Math.max(1.0, Math.round((baseKw + noise) * 10.0) / 10.0);

            // Energy delivered increments
            totalEnergyDelivered += (currentKw * (0.9 / 3600.0));

            // Battery increases gradually
            if (Math.random() > 0.45 && currentBatteryPct < 100) {
                currentBatteryPct++;
                frame.currentVehicle.setCurrentBatteryPercentage(currentBatteryPct);
            }

            statusPill.setText("● CHARGING");
            statusPill.setForeground(AppTheme.ACCENT);
            statusPill.setBackground(AppTheme.ACCENT_DIM);
        } else {
            currentKw = 0.0;
            statusPill.setText("○ IDLE");
            statusPill.setForeground(AppTheme.TEXT_MUTED);
            statusPill.setBackground(new Color(255, 255, 255, 10));
        }

        // Shift buffers
        powerPoints.remove(0);
        powerPoints.add(currentKw);

        batteryPoints.remove(0);
        batteryPoints.add(currentBatteryPct);

        // Update pills
        kwLabel.setText(String.format("%.1f kW", currentKw));
        batteryLabel.setText(frame.currentVehicle != null ? String.format("%d%%", currentBatteryPct) : "—");
        energyLabel.setText(String.format("%.2f kWh", totalEnergyDelivered));

        repaint();
    }

    public void setCharging(boolean charging) {
        this.isCharging = charging;
        if (!charging) {
            currentKw = 0.0;
        }
    }

    public boolean isCharging() {
        return isCharging;
    }

    public double getTotalEnergyDelivered() {
        return Math.max(0.0, Math.round(totalEnergyDelivered * 100.0) / 100.0);
    }

    public void resetTelemetry(int startingBattery) {
        this.currentBatteryPct = startingBattery;
        this.totalEnergyDelivered = 0.0;
        this.currentKw = 0.0;
        for (int i = 0; i < MAX_POINTS; i++) {
            powerPoints.set(i, 0.0);
            batteryPoints.set(i, currentBatteryPct);
        }
        repaint();
    }

    // ── Custom Drawing Canvas ──────────────────────────────────────────────
    private class ChartCanvas extends JPanel {
        ChartCanvas() {
            setPreferredSize(new Dimension(0, 160));
            setBackground(AppTheme.BG_INPUT);
            setBorder(new LineBorder(AppTheme.BORDER_COL, 1, true));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int padL = 45;
            int padR = 45;
            int padT = 16;
            int padB = 26;

            int plotW = w - padL - padR;
            int plotH = h - padT - padB;

            // Background fill
            g2.setColor(AppTheme.BG_INPUT);
            g2.fillRect(0, 0, w, h);

            // Draw horizontal grid lines & labels
            g2.setColor(new Color(255, 255, 255, 12));
            for (int i = 0; i <= 4; i++) {
                int y = padT + (plotH * i / 4);
                g2.drawLine(padL, y, w - padR, y);

                // Left Y axis (kW)
                int kwVal = 60 - (i * 15);
                g2.setFont(AppTheme.FONT_SMALL);
                g2.setColor(new Color(0, 200, 122, 140));
                g2.drawString(kwVal + "k", 10, y + 4);

                // Right Y axis (%)
                int pctVal = 100 - (i * 25);
                g2.setColor(new Color(0, 210, 255, 140));
                g2.drawString(pctVal + "%", w - 38, y + 4);
                g2.setColor(new Color(255, 255, 255, 12));
            }

            int n = powerPoints.size();
            if (n < 2) {
                g2.dispose();
                return;
            }

            double stepX = (double) plotW / (n - 1);

            // 1. Draw Power Curve (Neon Green #00C87A)
            Path2D.Double powerPath = new Path2D.Double();
            Path2D.Double powerFill = new Path2D.Double();
            powerFill.moveTo(padL, padT + plotH);

            for (int i = 0; i < n; i++) {
                double x = padL + (i * stepX);
                double val = powerPoints.get(i);
                double clampedKw = Math.min(60.0, Math.max(0.0, val));
                double y = padT + plotH - (clampedKw / 60.0 * plotH);

                if (i == 0) {
                    powerPath.moveTo(x, y);
                    powerFill.lineTo(x, y);
                } else {
                    powerPath.lineTo(x, y);
                    powerFill.lineTo(x, y);
                }
            }
            powerFill.lineTo(padL + plotW, padT + plotH);
            powerFill.closePath();

            // Gradient area fill for power
            GradientPaint greenGrad = new GradientPaint(
                0, padT, new Color(0, 200, 122, 50),
                0, padT + plotH, new Color(0, 200, 122, 0)
            );
            g2.setPaint(greenGrad);
            g2.fill(powerFill);

            // Power stroke
            g2.setColor(AppTheme.ACCENT);
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(powerPath);

            // 2. Draw Battery Percentage Curve (Cyan #00D2FF)
            Path2D.Double batPath = new Path2D.Double();
            for (int i = 0; i < n; i++) {
                double x = padL + (i * stepX);
                double pct = batteryPoints.get(i);
                double clampedPct = Math.min(100.0, Math.max(0.0, pct));
                double y = padT + plotH - (clampedPct / 100.0 * plotH);

                if (i == 0) batPath.moveTo(x, y);
                else batPath.lineTo(x, y);
            }

            g2.setColor(new Color(0, 210, 255));
            g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(batPath);

            // Draw current endpoint indicator dots
            double lastX = padL + ((n - 1) * stepX);
            double lastKwY = padT + plotH - (Math.min(60.0, currentKw) / 60.0 * plotH);
            double lastBatY = padT + plotH - (Math.min(100.0, currentBatteryPct) / 100.0 * plotH);

            // Glowing circles
            g2.setColor(AppTheme.ACCENT);
            g2.fillOval((int) lastX - 4, (int) lastKwY - 4, 8, 8);
            g2.setColor(Color.WHITE);
            g2.fillOval((int) lastX - 2, (int) lastKwY - 2, 4, 4);

            g2.setColor(new Color(0, 210, 255));
            g2.fillOval((int) lastX - 4, (int) lastBatY - 4, 8, 8);
            g2.setColor(Color.WHITE);
            g2.fillOval((int) lastX - 2, (int) lastBatY - 2, 4, 4);

            // Legend at bottom
            g2.setFont(AppTheme.FONT_SMALL);
            g2.setColor(AppTheme.ACCENT);
            g2.drawString("━ Power Output (kW)", padL, h - 6);
            g2.setColor(new Color(0, 210, 255));
            g2.drawString("━ Battery Level (%)", padL + 140, h - 6);

            g2.dispose();
        }
    }
}
