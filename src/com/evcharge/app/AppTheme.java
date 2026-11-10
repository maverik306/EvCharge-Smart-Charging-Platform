package com.evcharge.app;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class AppTheme {

    // ── Colors ────────────────────────────────────────────────────────────
    public static final Color BG_DARK      = new Color(15, 17, 23);
    public static final Color BG_CARD      = new Color(24, 27, 38);
    public static final Color BG_SIDEBAR   = new Color(11, 13, 18);
    public static final Color BG_INPUT     = new Color(20, 23, 33);
    public static final Color ACCENT       = new Color(0, 200, 122);
    public static final Color ACCENT_DIM   = new Color(0, 200, 122, 45);
    public static final Color ACCENT_DARK  = new Color(0, 140, 85);
    public static final Color TEXT_PRI     = new Color(232, 234, 240);
    public static final Color TEXT_SEC     = new Color(139, 143, 168);
    public static final Color TEXT_MUTED   = new Color(70, 75, 100);
    public static final Color BORDER_COL   = new Color(40, 44, 60);
    public static final Color SUCCESS      = new Color(0, 200, 122);
    public static final Color WARNING      = new Color(255, 178, 0);
    public static final Color ERROR        = new Color(255, 75, 75);
    public static final Color BLUE         = new Color(99, 132, 255);
    public static final Color PURPLE       = new Color(167, 119, 255);

    // ── Fonts ─────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD,  24);
    public static final Font FONT_H2       = new Font("Segoe UI", Font.BOLD,  17);
    public static final Font FONT_H3       = new Font("Segoe UI", Font.BOLD,  14);
    public static final Font FONT_BODY     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD,  11);
    public static final Font FONT_NAV      = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_MONO     = new Font("Consolas",  Font.PLAIN, 12);

    // ── Global UIManager setup ────────────────────────────────────────────
    public static void applyGlobalTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        UIManager.put("Panel.background",           BG_DARK);
        UIManager.put("Label.foreground",            TEXT_PRI);
        UIManager.put("Label.font",                  FONT_BODY);
        UIManager.put("Table.background",            BG_CARD);
        UIManager.put("Table.foreground",            TEXT_PRI);
        UIManager.put("Table.gridColor",             BORDER_COL);
        UIManager.put("Table.selectionBackground",   ACCENT_DIM);
        UIManager.put("Table.selectionForeground",   ACCENT);
        UIManager.put("TableHeader.background",      BG_SIDEBAR);
        UIManager.put("TableHeader.foreground",      TEXT_SEC);
        UIManager.put("TableHeader.font",            FONT_LABEL);
        UIManager.put("ScrollPane.background",       BG_DARK);
        UIManager.put("ScrollBar.background",        BG_CARD);
        UIManager.put("ScrollBar.thumb",             BORDER_COL);
        UIManager.put("ScrollBar.track",             BG_CARD);
        UIManager.put("ScrollBar.width",             8);
        UIManager.put("TabbedPane.background",       BG_DARK);
        UIManager.put("TabbedPane.foreground",       TEXT_PRI);
        UIManager.put("ToolTip.background",          BG_CARD);
        UIManager.put("ToolTip.foreground",          TEXT_PRI);
        UIManager.put("OptionPane.background",       BG_CARD);
        UIManager.put("OptionPane.messageForeground",TEXT_PRI);
    }

    // ── Component factories ───────────────────────────────────────────────

    /** Styled card panel with optional title */
    public static JPanel card(String title) {
        return card(title, null);
    }

    /** Styled card panel with title and vector icon */
    public static JPanel card(String title, Icon icon) {
        JPanel p = new JPanel(new BorderLayout(0, 14));
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COL, 1, true),
            new EmptyBorder(18, 20, 18, 20)
        ));
        if (title != null && !title.isEmpty()) {
            JLabel lbl = new JLabel(title);
            if (icon != null) {
                lbl.setIcon(icon);
                lbl.setIconTextGap(10);
            }
            lbl.setFont(FONT_H3);
            lbl.setForeground(TEXT_PRI);
            lbl.setBorder(new EmptyBorder(0, 0, 6, 0));
            p.add(lbl, BorderLayout.NORTH);
        }
        return p;
    }

    /** Full-width dark text input */
    public static JTextField textField() {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_PRI);
        f.setBackground(BG_INPUT);
        f.setCaretColor(ACCENT);
        f.setSelectionColor(ACCENT_DIM);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COL, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        return f;
    }

    /** Dark combo box */
    public static JComboBox<String> combo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(FONT_BODY);
        c.setForeground(TEXT_PRI);
        c.setBackground(BG_INPUT);
        c.setBorder(BorderFactory.createLineBorder(BORDER_COL, 1));
        c.setPreferredSize(new Dimension(100, 36));
        return c;
    }

    /** Green primary action button */
    public static JButton primaryBtn(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed()  ? ACCENT_DARK
                         : getModel().isRollover() ? new Color(0, 215, 135)
                         : ACCENT;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(BG_DARK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(170, 40));
        return btn;
    }

    /** Green primary action button with icon */
    public static JButton primaryBtn(String text, Icon icon) {
        JButton btn = primaryBtn(text);
        if (icon != null) {
            btn.setIcon(icon);
            btn.setIconTextGap(8);
        }
        return btn;
    }

    /** Ghost secondary button */
    public static JButton secondaryBtn(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? BORDER_COL : BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER_COL);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(TEXT_PRI);
        btn.setFont(FONT_BODY);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(170, 40));
        return btn;
    }

    /** Ghost secondary button with icon */
    public static JButton secondaryBtn(String text, Icon icon) {
        JButton btn = secondaryBtn(text);
        if (icon != null) {
            btn.setIcon(icon);
            btn.setIconTextGap(8);
        }
        return btn;
    }

    /** Small colored badge label */
    public static JLabel badge(String text, Color bg) {
        JLabel l = new JLabel(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        l.setForeground(Color.WHITE);
        l.setBorder(new EmptyBorder(3, 10, 3, 10));
        l.setOpaque(false);
        return l;
    }

    /** Simple field label (uppercase small caps style) */
    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_SEC);
        return l;
    }

    /** Separator line */
    public static JSeparator sep() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDER_COL);
        s.setBackground(BG_DARK);
        return s;
    }

    /** Stat card — icon + value + label */
    public static JPanel statCard(String emoji, String value, String label, Color valueColor) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COL, 1, true),
            new EmptyBorder(16, 20, 16, 20)
        ));

        JLabel ico = new JLabel(emoji);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        ico.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        val.setForeground(valueColor);
        val.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_SEC);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(ico);
        p.add(Box.createVerticalStrut(8));
        p.add(val);
        p.add(Box.createVerticalStrut(2));
        p.add(lbl);
        return p;
    }

    /** Scrollable console/log text area */
    public static JTextArea consoleArea() {
        JTextArea ta = new JTextArea();
        ta.setFont(FONT_MONO);
        ta.setForeground(ACCENT);
        ta.setBackground(new Color(10, 12, 17));
        ta.setCaretColor(ACCENT);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new EmptyBorder(12, 14, 12, 14));
        return ta;
    }

    /** Styled scroll pane wrapping any component */
    public static JScrollPane scrollPane(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(new LineBorder(BORDER_COL, 1));
        sp.getViewport().setBackground(c.getBackground());
        sp.setBackground(c.getBackground());
        sp.getVerticalScrollBar().setBackground(BG_CARD);
        sp.getHorizontalScrollBar().setBackground(BG_CARD);
        return sp;
    }

    /** Row with label + field (for forms) */
    public static JPanel formRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(10, 4));
        row.setBackground(BG_CARD);
        row.setOpaque(false);
        JLabel lbl = fieldLabel(labelText);
        row.add(lbl, BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }
}
