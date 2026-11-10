package com.evcharge.app;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * High-performance, resolution-independent vector icons for Java Swing.
 * Renders crisp Lucide / FontAwesome style icons using pure Java2D vector paths.
 */
public class AppIcons {

    public enum IconType {
        DASHBOARD,
        CAR,
        CHARGER,
        BOOKING,
        SESSION,
        PAYMENT,
        REVIEW,
        DATABASE,
        USER,
        LIGHTNING,
        ANALYTICS,
        EYE,
        EYE_OFF,
        REFRESH,
        LOGOUT,
        CHECK_CIRCLE,
        ALERT_CIRCLE,
        INFO_CIRCLE,
        PLUS,
        SAVE,
        PLAY,
        SEARCH,
        LOCATION,
        CLEAR
    }

    /**
     * Creates a vector icon of the specified type, size, and fixed color.
     */
    public static Icon get(IconType type, int size, Color color) {
        return new VectorIcon(type, size, color);
    }

    /**
     * Renders a vector icon to a BufferedImage suitable for JFrame/JDialog icon images.
     */
    public static Image getImage(IconType type, int size, Color color) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        new VectorIcon(type, size, color).paintIcon(null, g2, 0, 0);
        g2.dispose();
        return img;
    }

    /**
     * Creates a vector icon that dynamically adapts its color to the component's state
     * (active, hovered, or default text color).
     */
    public static Icon getDynamic(IconType type, int size) {
        return new VectorIcon(type, size, null);
    }

    /**
     * Helper to retrieve navigation icons mapped by sidebar panel key.
     */
    public static Icon getNavIcon(String key, int size) {
        IconType type = switch (key) {
            case "Dashboard"        -> IconType.DASHBOARD;
            case "Driver & Vehicle" -> IconType.CAR;
            case "Charger"          -> IconType.CHARGER;
            case "Booking"          -> IconType.BOOKING;
            case "Session"          -> IconType.SESSION;
            case "Payment"          -> IconType.PAYMENT;
            case "Review"           -> IconType.REVIEW;
            case "Database"         -> IconType.DATABASE;
            default                 -> IconType.DASHBOARD;
        };
        return getDynamic(type, size);
    }

    /**
     * Vector icon implementation using antialiased Graphics2D paths.
     */
    public static class VectorIcon implements Icon {
        private final IconType type;
        private final int size;
        private final Color fixedColor;

        public VectorIcon(IconType type, int size, Color fixedColor) {
            this.type = type;
            this.size = size;
            this.fixedColor = fixedColor;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            // Determine drawing color based on component state if fixedColor is null
            Color drawColor = resolveColor(c);
            g2.setColor(drawColor);

            // Normalized coordinate space is 24x24
            double scale = (double) size / 24.0;
            g2.translate(x, y);
            g2.scale(scale, scale);

            float strokeWidth = 1.85f;
            g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            drawIconPath(g2, type);

            g2.dispose();
        }

        private Color resolveColor(Component c) {
            if (fixedColor != null) {
                return fixedColor;
            }
            if (c instanceof AbstractButton btn) {
                boolean active = Boolean.TRUE.equals(btn.getClientProperty("active"));
                if (active) {
                    return AppTheme.ACCENT;
                }
                if (btn.getModel().isRollover()) {
                    return AppTheme.TEXT_PRI;
                }
                return AppTheme.TEXT_SEC;
            }
            if (c != null && c.getForeground() != null) {
                return c.getForeground();
            }
            return AppTheme.ACCENT;
        }

        private void drawIconPath(Graphics2D g2, IconType type) {
            switch (type) {
                case DASHBOARD -> {
                    // Lucide layout-dashboard 4 tiles
                    g2.draw(new RoundRectangle2D.Float(3f, 3f, 7f, 9f, 2f, 2f));
                    g2.draw(new RoundRectangle2D.Float(14f, 3f, 7f, 5f, 2f, 2f));
                    g2.draw(new RoundRectangle2D.Float(14f, 12f, 7f, 9f, 2f, 2f));
                    g2.draw(new RoundRectangle2D.Float(3f, 16f, 7f, 5f, 2f, 2f));
                }
                case CAR -> {
                    // Wheels
                    g2.draw(new Ellipse2D.Float(5.5f, 15f, 4f, 4f));
                    g2.draw(new Ellipse2D.Float(14.5f, 15f, 4f, 4f));
                    // Car body
                    Path2D.Float p = new Path2D.Float();
                    p.moveTo(2.5f, 16.5f);
                    p.lineTo(5.5f, 16.5f);
                    p.moveTo(9.5f, 16.5f);
                    p.lineTo(14.5f, 16.5f);
                    p.moveTo(18.5f, 16.5f);
                    p.lineTo(21.5f, 16.5f);
                    p.lineTo(21.5f, 13f);
                    p.lineTo(19f, 12.5f);
                    p.lineTo(16f, 6.5f);
                    p.lineTo(7f, 6.5f);
                    p.lineTo(4.5f, 12.5f);
                    p.lineTo(2.5f, 13f);
                    p.closePath();
                    g2.draw(p);
                    // Windows separator
                    g2.draw(new Line2D.Float(10.5f, 6.5f, 10.5f, 12.5f));
                    g2.draw(new Line2D.Float(5f, 12.5f, 18.5f, 12.5f));
                }
                case CHARGER -> {
                    // EV Charging pillar
                    g2.draw(new RoundRectangle2D.Float(3f, 3f, 10f, 18f, 3f, 3f));
                    // Screen
                    g2.draw(new RoundRectangle2D.Float(5.5f, 6f, 5f, 3.5f, 1f, 1f));
                    // Small lightning bolt on pillar
                    Path2D.Float bolt = new Path2D.Float();
                    bolt.moveTo(8.5f, 12f);
                    bolt.lineTo(7f, 15f);
                    bolt.lineTo(8.5f, 15f);
                    bolt.lineTo(7.5f, 18f);
                    bolt.lineTo(10f, 14.5f);
                    bolt.lineTo(8.5f, 14.5f);
                    bolt.closePath();
                    g2.fill(bolt);
                    // Charging cable & holster nozzle
                    Path2D.Float cable = new Path2D.Float();
                    cable.moveTo(13f, 8f);
                    cable.curveTo(17f, 8f, 19f, 12f, 19f, 16f);
                    cable.curveTo(19f, 18.5f, 16.5f, 18.5f, 16.5f, 16f);
                    cable.lineTo(16.5f, 11f);
                    g2.draw(cable);
                    g2.draw(new Line2D.Float(15f, 11f, 18f, 11f));
                }
                case BOOKING -> {
                    // Calendar outline
                    g2.draw(new RoundRectangle2D.Float(3f, 4f, 18f, 17f, 3f, 3f));
                    // Top header line
                    g2.draw(new Line2D.Float(3f, 9.5f, 21f, 9.5f));
                    // Binder pins
                    g2.draw(new Line2D.Float(7.5f, 2f, 7.5f, 5.5f));
                    g2.draw(new Line2D.Float(16.5f, 2f, 16.5f, 5.5f));
                    // Calendar dots
                    float[] xDots = {7.5f, 12f, 16.5f};
                    float[] yDots = {13f, 17f};
                    for (float xd : xDots) {
                        for (float yd : yDots) {
                            g2.fill(new Ellipse2D.Float(xd - 1f, yd - 1f, 2f, 2f));
                        }
                    }
                }
                case SESSION -> {
                    // Battery body
                    g2.draw(new RoundRectangle2D.Float(2f, 6f, 17f, 12f, 2.5f, 2.5f));
                    // Terminal cap on right
                    Path2D.Float cap = new Path2D.Float();
                    cap.moveTo(19f, 9.5f);
                    cap.lineTo(21.5f, 9.5f);
                    cap.lineTo(21.5f, 14.5f);
                    cap.lineTo(19f, 14.5f);
                    g2.draw(cap);
                    // Center charging bolt
                    Path2D.Float b = new Path2D.Float();
                    b.moveTo(11.5f, 7.5f);
                    b.lineTo(8.5f, 12f);
                    b.lineTo(11f, 12f);
                    b.lineTo(9.5f, 16.5f);
                    b.lineTo(13.5f, 11.5f);
                    b.lineTo(11f, 11.5f);
                    b.closePath();
                    g2.fill(b);
                }
                case PAYMENT -> {
                    // Credit card
                    g2.draw(new RoundRectangle2D.Float(2f, 5f, 20f, 14f, 3f, 3f));
                    g2.draw(new Line2D.Float(2f, 9.5f, 22f, 9.5f));
                    // Chip
                    g2.draw(new RoundRectangle2D.Float(5f, 13f, 3.5f, 3f, 0.8f, 0.8f));
                    g2.draw(new Line2D.Float(11f, 15f, 18f, 15f));
                }
                case REVIEW -> {
                    // Symmetrical 5-pointed star
                    Path2D.Float s = new Path2D.Float();
                    double cx = 12, cy = 12, rOut = 9.5, rIn = 4.2;
                    for (int i = 0; i < 10; i++) {
                        double angle = -Math.PI / 2 + i * Math.PI / 5;
                        double r = (i % 2 == 0) ? rOut : rIn;
                        double px = cx + r * Math.cos(angle);
                        double py = cy + r * Math.sin(angle);
                        if (i == 0) s.moveTo(px, py);
                        else s.lineTo(px, py);
                    }
                    s.closePath();
                    g2.draw(s);
                }
                case DATABASE -> {
                    // Cylinder top
                    g2.draw(new Ellipse2D.Float(3f, 3f, 18f, 5.5f));
                    // Side lines
                    g2.draw(new Line2D.Float(3f, 5.75f, 3f, 18f));
                    g2.draw(new Line2D.Float(21f, 5.75f, 21f, 18f));
                    // Middle & bottom curve
                    g2.draw(new Arc2D.Float(3f, 9f, 18f, 5.5f, 180, 180, Arc2D.OPEN));
                    g2.draw(new Arc2D.Float(3f, 15.25f, 18f, 5.5f, 180, 180, Arc2D.OPEN));
                }
                case USER -> {
                    // Head circle
                    g2.draw(new Ellipse2D.Float(7.5f, 3.5f, 9f, 9f));
                    // Shoulders arc
                    Path2D.Float p = new Path2D.Float();
                    p.moveTo(4f, 20.5f);
                    p.curveTo(4f, 15.5f, 8f, 15.5f, 12f, 15.5f);
                    p.curveTo(16f, 15.5f, 20f, 15.5f, 20f, 20.5f);
                    g2.draw(p);
                }
                case LIGHTNING -> {
                    // Crisp sharp angular lightning bolt
                    Path2D.Float z = new Path2D.Float();
                    z.moveTo(13f, 2f);
                    z.lineTo(5.5f, 13f);
                    z.lineTo(11.5f, 13f);
                    z.lineTo(9.5f, 22f);
                    z.lineTo(18.5f, 10.5f);
                    z.lineTo(12.5f, 10.5f);
                    z.closePath();
                    g2.fill(z);
                }
                case ANALYTICS -> {
                    // Dashboard Analytics graph & metrics
                    g2.draw(new Line2D.Float(3f, 20f, 21f, 20f));
                    g2.draw(new Line2D.Float(6f, 20f, 6f, 13f));
                    g2.draw(new Line2D.Float(10.5f, 20f, 10.5f, 8f));
                    g2.draw(new Line2D.Float(15f, 20f, 15f, 14f));
                    g2.draw(new Line2D.Float(19.5f, 20f, 19.5f, 4f));
                    Path2D.Float trend = new Path2D.Float();
                    trend.moveTo(6f, 13f);
                    trend.lineTo(10.5f, 8f);
                    trend.lineTo(15f, 14f);
                    trend.lineTo(19.5f, 4f);
                    g2.draw(trend);
                }
                case EYE -> {
                    Path2D.Float eye = new Path2D.Float();
                    eye.moveTo(2f, 12f);
                    eye.curveTo(6f, 5.5f, 18f, 5.5f, 22f, 12f);
                    eye.curveTo(18f, 18.5f, 6f, 18.5f, 2f, 12f);
                    eye.closePath();
                    g2.draw(eye);
                    g2.draw(new Ellipse2D.Float(9f, 9f, 6f, 6f));
                    g2.fill(new Ellipse2D.Float(10.5f, 10.5f, 3f, 3f));
                }
                case EYE_OFF -> {
                    Path2D.Float eye = new Path2D.Float();
                    eye.moveTo(2f, 12f);
                    eye.curveTo(6f, 5.5f, 18f, 5.5f, 22f, 12f);
                    eye.curveTo(18f, 18.5f, 6f, 18.5f, 2f, 12f);
                    eye.closePath();
                    g2.draw(eye);
                    g2.draw(new Ellipse2D.Float(9.5f, 9.5f, 5f, 5f));
                    g2.draw(new Line2D.Float(3f, 3f, 21f, 21f));
                }
                case REFRESH -> {
                    g2.draw(new Arc2D.Float(3f, 3f, 18f, 18f, 30f, 280f, Arc2D.OPEN));
                    Path2D.Float arrow = new Path2D.Float();
                    arrow.moveTo(21f, 7.5f);
                    arrow.lineTo(21f, 12.5f);
                    arrow.lineTo(16f, 12.5f);
                    g2.draw(arrow);
                }
                case LOGOUT -> {
                    Path2D.Float door = new Path2D.Float();
                    door.moveTo(10f, 4f);
                    door.lineTo(4f, 4f);
                    door.lineTo(4f, 20f);
                    door.lineTo(10f, 20f);
                    g2.draw(door);
                    g2.draw(new Line2D.Float(9f, 12f, 20f, 12f));
                    Path2D.Float arrow = new Path2D.Float();
                    arrow.moveTo(16f, 8f);
                    arrow.lineTo(20f, 12f);
                    arrow.lineTo(16f, 16f);
                    g2.draw(arrow);
                }
                case CHECK_CIRCLE -> {
                    g2.draw(new Ellipse2D.Float(2.5f, 2.5f, 19f, 19f));
                    Path2D.Float c = new Path2D.Float();
                    c.moveTo(7.5f, 12f);
                    c.lineTo(10.5f, 15f);
                    c.lineTo(16.5f, 8.5f);
                    g2.draw(c);
                }
                case ALERT_CIRCLE -> {
                    g2.draw(new Ellipse2D.Float(2.5f, 2.5f, 19f, 19f));
                    g2.draw(new Line2D.Float(12f, 7f, 12f, 13f));
                    g2.fill(new Ellipse2D.Float(11f, 15.5f, 2f, 2f));
                }
                case INFO_CIRCLE -> {
                    g2.draw(new Ellipse2D.Float(2.5f, 2.5f, 19f, 19f));
                    g2.fill(new Ellipse2D.Float(11f, 7f, 2f, 2f));
                    g2.draw(new Line2D.Float(12f, 11f, 12f, 16.5f));
                }
                case PLUS -> {
                    g2.draw(new Line2D.Float(12f, 5f, 12f, 19f));
                    g2.draw(new Line2D.Float(5f, 12f, 19f, 12f));
                }
                case SAVE -> {
                    Path2D.Float f = new Path2D.Float();
                    f.moveTo(4f, 3f);
                    f.lineTo(17f, 3f);
                    f.lineTo(20f, 6f);
                    f.lineTo(20f, 21f);
                    f.lineTo(4f, 21f);
                    f.closePath();
                    g2.draw(f);
                    g2.draw(new RoundRectangle2D.Float(7f, 3f, 10f, 6f, 1f, 1f));
                    g2.draw(new RoundRectangle2D.Float(6f, 13f, 12f, 8f, 1f, 1f));
                }
                case PLAY -> {
                    Path2D.Float tri = new Path2D.Float();
                    tri.moveTo(8f, 5f);
                    tri.lineTo(18f, 12f);
                    tri.lineTo(8f, 19f);
                    tri.closePath();
                    g2.fill(tri);
                }
                case SEARCH -> {
                    g2.draw(new Ellipse2D.Float(4f, 4f, 11f, 11f));
                    g2.draw(new Line2D.Float(12.5f, 12.5f, 19.5f, 19.5f));
                }
                case LOCATION -> {
                    Path2D.Float pin = new Path2D.Float();
                    pin.moveTo(12f, 21.5f);
                    pin.curveTo(8f, 16.5f, 5f, 12.5f, 5f, 9.5f);
                    pin.curveTo(5f, 5.5f, 8f, 2.5f, 12f, 2.5f);
                    pin.curveTo(16f, 2.5f, 19f, 5.5f, 19f, 9.5f);
                    pin.curveTo(19f, 12.5f, 16f, 16.5f, 12f, 21.5f);
                    pin.closePath();
                    g2.draw(pin);
                    g2.draw(new Ellipse2D.Float(9.5f, 7f, 5f, 5f));
                }
                case CLEAR -> {
                    g2.draw(new Line2D.Float(6f, 6f, 18f, 18f));
                    g2.draw(new Line2D.Float(18f, 6f, 6f, 18f));
                }
            }
        }
    }
}
