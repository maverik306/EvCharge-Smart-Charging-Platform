package com.evcharge.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.model.User;
import com.evcharge.app.service.AuthService;
import com.evcharge.app.service.AuthService.AuthResult;

/**
 * Modern, dark-themed login window for EVCharge.
 */
public class LoginFrame extends JFrame {

    private final AuthService authService;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton createAccountBtn;
    private JButton toggleEyeBtn;
    private JCheckBox showPasswordCheck;

    private JPanel alertBanner;
    private JLabel alertIcon;
    private JLabel alertText;
    private JLabel statusLoadingLabel;

    public LoginFrame() {
        super("EVCharge — Smart Charging Platform");
        this.authService = new AuthService();

        AppTheme.applyGlobalTheme();
        setIconImage(AppIcons.getImage(AppIcons.IconType.LIGHTNING, 32, AppTheme.ACCENT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 620));
        setPreferredSize(new Dimension(960, 680));

        initUI();

        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        getContentPane().setBackground(AppTheme.BG_DARK);
        setLayout(new GridBagLayout());

        // Center card container
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(AppTheme.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1, true),
            new EmptyBorder(32, 40, 32, 40)
        ));
        card.setPreferredSize(new Dimension(460, 560));
        card.setMaximumSize(new Dimension(460, 560));

        // 1. Header (Logo + Brand + Subtitle)
        card.add(buildHeader());
        card.add(Box.createVerticalStrut(20));

        // 2. Alert Banner (Hidden by default)
        alertBanner = buildAlertBanner();
        card.add(alertBanner);
        card.add(Box.createVerticalStrut(16));

        // 3. Form Inputs (Email & Password)
        card.add(buildFormInputs());
        card.add(Box.createVerticalStrut(20));

        // 4. Action Buttons (Sign In + Create Account link)
        card.add(buildActions());

        // Add centered card to frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(card, gbc);

        // Enter key submits login
        getRootPane().setDefaultButton(loginBtn);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppTheme.BG_CARD);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Icon badge
        JLabel icon = new JLabel(AppIcons.get(AppIcons.IconType.LIGHTNING, 36, AppTheme.ACCENT));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title brand row centered
        JPanel brandRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        brandRow.setBackground(AppTheme.BG_CARD);
        brandRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        brandRow.setMaximumSize(new Dimension(380, 36));

        JLabel evLabel = new JLabel("EV");
        evLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        evLabel.setForeground(AppTheme.ACCENT);

        JLabel chargeLabel = new JLabel("Charge");
        chargeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        chargeLabel.setForeground(AppTheme.TEXT_PRI);

        brandRow.add(evLabel);
        brandRow.add(chargeLabel);

        // Subtitle
        JLabel sub = new JLabel("Smart Charging Platform", SwingConstants.CENTER);
        sub.setFont(AppTheme.FONT_BODY);
        sub.setForeground(AppTheme.TEXT_SEC);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel prompt = new JLabel("Sign in to manage charging operations", SwingConstants.CENTER);
        prompt.setFont(AppTheme.FONT_SMALL);
        prompt.setForeground(AppTheme.TEXT_MUTED);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        prompt.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(icon);
        header.add(Box.createVerticalStrut(6));
        header.add(brandRow);
        header.add(Box.createVerticalStrut(4));
        header.add(sub);
        header.add(Box.createVerticalStrut(4));
        header.add(prompt);

        return header;
    }

    private JPanel buildAlertBanner() {
        JPanel banner = new JPanel(new BorderLayout(10, 0));
        banner.setBackground(new Color(255, 75, 75, 25));
        banner.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.ERROR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        banner.setAlignmentX(Component.CENTER_ALIGNMENT);
        banner.setMaximumSize(new Dimension(380, 48));

        alertIcon = new JLabel(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 16, AppTheme.ERROR));

        alertText = new JLabel("Validation error message");
        alertText.setFont(AppTheme.FONT_SMALL);
        alertText.setForeground(AppTheme.ERROR);

        banner.add(alertIcon, BorderLayout.WEST);
        banner.add(alertText, BorderLayout.CENTER);
        banner.setVisible(false); // Initially hidden

        return banner;
    }

    private JPanel buildFormInputs() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(AppTheme.BG_CARD);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(380, 190));

        // Email Label & Field
        JLabel emailLbl = AppTheme.fieldLabel("Email Address");
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField = AppTheme.textField();
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setMaximumSize(new Dimension(380, 38));
        emailField.setToolTipText("Enter your registered email address");

        // Password Label & Field
        JLabel pwdLbl = AppTheme.fieldLabel("Password");
        pwdLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pwdRow = new JPanel(new BorderLayout(4, 0));
        pwdRow.setBackground(AppTheme.BG_INPUT);
        pwdRow.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1),
            new EmptyBorder(0, 0, 0, 6)
        ));
        pwdRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pwdRow.setMaximumSize(new Dimension(380, 38));
        pwdRow.setPreferredSize(new Dimension(380, 38));

        passwordField = new JPasswordField();
        passwordField.setFont(AppTheme.FONT_BODY);
        passwordField.setForeground(AppTheme.TEXT_PRI);
        passwordField.setBackground(AppTheme.BG_INPUT);
        passwordField.setCaretColor(AppTheme.ACCENT);
        passwordField.setBorder(new EmptyBorder(8, 12, 8, 8));
        passwordField.setEchoChar('•');

        toggleEyeBtn = new JButton();
        toggleEyeBtn.setIcon(AppIcons.get(AppIcons.IconType.EYE, 16, AppTheme.TEXT_SEC));
        toggleEyeBtn.setContentAreaFilled(false);
        toggleEyeBtn.setBorderPainted(false);
        toggleEyeBtn.setFocusPainted(false);
        toggleEyeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleEyeBtn.setToolTipText("Toggle password visibility");
        toggleEyeBtn.addActionListener(e -> togglePasswordVisibility());

        pwdRow.add(passwordField, BorderLayout.CENTER);
        pwdRow.add(toggleEyeBtn, BorderLayout.EAST);

        // Show password checkbox
        showPasswordCheck = new JCheckBox("Show Password");
        showPasswordCheck.setFont(AppTheme.FONT_SMALL);
        showPasswordCheck.setForeground(AppTheme.TEXT_SEC);
        showPasswordCheck.setBackground(AppTheme.BG_CARD);
        showPasswordCheck.setFocusPainted(false);
        showPasswordCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPasswordCheck.addActionListener(e -> {
            boolean show = showPasswordCheck.isSelected();
            passwordField.setEchoChar(show ? (char) 0 : '•');
            toggleEyeBtn.setIcon(AppIcons.get(show ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, show ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
        });

        form.add(emailLbl);
        form.add(Box.createVerticalStrut(6));
        form.add(emailField);
        form.add(Box.createVerticalStrut(14));
        form.add(pwdLbl);
        form.add(Box.createVerticalStrut(6));
        form.add(pwdRow);
        form.add(Box.createVerticalStrut(6));
        form.add(showPasswordCheck);

        return form;
    }

    private JPanel buildActions() {
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setBackground(AppTheme.BG_CARD);
        actions.setAlignmentX(Component.CENTER_ALIGNMENT);
        actions.setMaximumSize(new Dimension(380, 150));

        // Login Button
        loginBtn = AppTheme.primaryBtn("Sign In", AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.BG_DARK));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(380, 42));
        loginBtn.setPreferredSize(new Dimension(380, 42));
        loginBtn.addActionListener(e -> performLogin());

        // Loading indicator
        statusLoadingLabel = new JLabel("Verifying credentials with MySQL...");
        statusLoadingLabel.setFont(AppTheme.FONT_SMALL);
        statusLoadingLabel.setForeground(AppTheme.ACCENT);
        statusLoadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLoadingLabel.setVisible(false);

        // Separator
        JPanel divider = new JPanel(new BorderLayout(10, 0));
        divider.setBackground(AppTheme.BG_CARD);
        divider.setMaximumSize(new Dimension(380, 24));
        JLabel divText = new JLabel(" Don't have an account? ");
        divText.setFont(AppTheme.FONT_SMALL);
        divText.setForeground(AppTheme.TEXT_MUTED);
        divider.add(new JSeparator() {{ setForeground(AppTheme.BORDER_COL); }}, BorderLayout.WEST);
        divider.add(divText, BorderLayout.CENTER);
        divider.add(new JSeparator() {{ setForeground(AppTheme.BORDER_COL); }}, BorderLayout.EAST);

        // Create Account Button
        createAccountBtn = AppTheme.secondaryBtn("Create Account", AppIcons.get(AppIcons.IconType.PLUS, 14, AppTheme.TEXT_PRI));
        createAccountBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountBtn.setMaximumSize(new Dimension(380, 40));
        createAccountBtn.setPreferredSize(new Dimension(380, 40));
        createAccountBtn.addActionListener(e -> openRegisterPage());

        actions.add(loginBtn);
        actions.add(Box.createVerticalStrut(6));
        actions.add(statusLoadingLabel);
        actions.add(Box.createVerticalStrut(10));
        actions.add(divider);
        actions.add(Box.createVerticalStrut(10));
        actions.add(createAccountBtn);

        return actions;
    }

    private void togglePasswordVisibility() {
        boolean isHidden = passwordField.getEchoChar() != (char) 0;
        passwordField.setEchoChar(isHidden ? (char) 0 : '•');
        showPasswordCheck.setSelected(isHidden);
        toggleEyeBtn.setIcon(AppIcons.get(isHidden ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, isHidden ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
    }

    /**
     * Executes the login process asynchronously to ensure UI remains responsive.
     */
    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        hideAlert();

        // 1. Basic client-side validation
        if (email.isEmpty()) {
            showAlert("Please enter your email address.", "ERROR");
            emailField.requestFocus();
            return;
        }
        if (!authService.isValidEmail(email)) {
            showAlert("Please enter a valid email address.", "ERROR");
            emailField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showAlert("Please enter your password.", "ERROR");
            passwordField.requestFocus();
            return;
        }

        // 2. Set loading state
        setLoadingState(true);

        // 3. Authenticate in background thread
        SwingWorker<AuthResult, Void> worker = new SwingWorker<>() {
            @Override
            protected AuthResult doInBackground() {
                return authService.authenticate(email, password);
            }

            @Override
            protected void done() {
                setLoadingState(false);
                try {
                    AuthResult result = get();
                    if (result.isSuccess()) {
                        onLoginSuccess(result.getUser());
                    } else {
                        showAlert(result.getMessage(), "ERROR");
                    }
                } catch (Exception ex) {
                    showAlert("Authentication service error: " + ex.getMessage(), "ERROR");
                }
            }
        };
        worker.execute();
    }

    private void onLoginSuccess(User user) {
        // Open the existing EVCharge dashboard
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(user);
            mainFrame.setVisible(true);
            mainFrame.showToast("Welcome back, " + user.getFullName() + "!", "SUCCESS");

            // Close login window
            dispose();
        });
    }

    private void openRegisterPage() {
        RegisterFrame regFrame = new RegisterFrame(this);
        regFrame.setVisible(true);
        this.setVisible(false);
    }

    private void setLoadingState(boolean loading) {
        loginBtn.setEnabled(!loading);
        createAccountBtn.setEnabled(!loading);
        emailField.setEnabled(!loading);
        passwordField.setEnabled(!loading);
        statusLoadingLabel.setVisible(loading);
        setCursor(loading ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
        if (loading) {
            loginBtn.setText("Authenticating...");
            loginBtn.setIcon(AppIcons.get(AppIcons.IconType.REFRESH, 14, AppTheme.BG_DARK));
        } else {
            loginBtn.setText("Sign In");
            loginBtn.setIcon(AppIcons.get(AppIcons.IconType.LIGHTNING, 14, AppTheme.BG_DARK));
        }
    }

    public void setNotification(String message, String type) {
        showAlert(message, type);
    }

    public void setEmail(String email) {
        if (emailField != null) {
            emailField.setText(email);
            passwordField.requestFocus();
        }
    }

    private void showAlert(String message, String type) {
        if ("SUCCESS".equalsIgnoreCase(type)) {
            alertBanner.setBackground(new Color(0, 200, 122, 28));
            alertBanner.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AppTheme.SUCCESS, 1, true),
                new EmptyBorder(8, 12, 8, 12)
            ));
            alertIcon.setIcon(AppIcons.get(AppIcons.IconType.CHECK_CIRCLE, 16, AppTheme.SUCCESS));
            alertIcon.setText("");
            alertText.setForeground(AppTheme.SUCCESS);
        } else if ("INFO".equalsIgnoreCase(type)) {
            alertBanner.setBackground(new Color(99, 132, 255, 28));
            alertBanner.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AppTheme.BLUE, 1, true),
                new EmptyBorder(8, 12, 8, 12)
            ));
            alertIcon.setIcon(AppIcons.get(AppIcons.IconType.INFO_CIRCLE, 16, AppTheme.BLUE));
            alertIcon.setText("");
            alertText.setForeground(AppTheme.BLUE);
        } else {
            alertBanner.setBackground(new Color(255, 75, 75, 28));
            alertBanner.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(AppTheme.ERROR, 1, true),
                new EmptyBorder(8, 12, 8, 12)
            ));
            alertIcon.setIcon(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 16, AppTheme.ERROR));
            alertIcon.setText("");
            alertText.setForeground(AppTheme.ERROR);
        }
        alertText.setText(message);
        alertBanner.setVisible(true);
        revalidate();
        repaint();
    }

    private void hideAlert() {
        alertBanner.setVisible(false);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppTheme.applyGlobalTheme();
            new LoginFrame().setVisible(true);
        });
    }
}
