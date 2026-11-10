package com.evcharge.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.evcharge.app.service.AuthService;
import com.evcharge.app.service.AuthService.AuthResult;

/**
 * Modern, dark-themed registration window for EVCharge.
 */
public class RegisterFrame extends JFrame {

    private final AuthService authService;
    private final LoginFrame parentLoginFrame;

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerBtn;
    private JButton backToLoginBtn;
    private JButton togglePwdEyeBtn;
    private JButton toggleConfirmEyeBtn;
    private JCheckBox showPasswordsCheck;

    private JPanel alertBanner;
    private JLabel alertIcon;
    private JLabel alertText;
    private JLabel statusLoadingLabel;

    public RegisterFrame(LoginFrame parentLoginFrame) {
        super("EVCharge — Create Account");
        this.parentLoginFrame = parentLoginFrame;
        this.authService = new AuthService();

        AppTheme.applyGlobalTheme();
        setIconImage(AppIcons.getImage(AppIcons.IconType.LIGHTNING, 32, AppTheme.ACCENT));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(880, 680));
        setPreferredSize(new Dimension(980, 720));

        // When closing via 'X', return to login frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (parentLoginFrame != null) {
                    parentLoginFrame.setVisible(true);
                }
            }
        });

        initUI();

        pack();
        setLocationRelativeTo(parentLoginFrame);
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
            new EmptyBorder(28, 40, 28, 40)
        ));
        card.setPreferredSize(new Dimension(480, 650));
        card.setMaximumSize(new Dimension(480, 650));

        // 1. Header
        card.add(buildHeader());
        card.add(Box.createVerticalStrut(14));

        // 2. Alert Banner
        alertBanner = buildAlertBanner();
        card.add(alertBanner);
        card.add(Box.createVerticalStrut(12));

        // 3. Form Inputs
        card.add(buildFormInputs());
        card.add(Box.createVerticalStrut(18));

        // 4. Action Buttons
        card.add(buildActions());

        // Center the card
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(card, gbc);

        // Enter key submits registration
        getRootPane().setDefaultButton(registerBtn);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(AppTheme.BG_CARD);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        JLabel sub = new JLabel("Smart Charging Platform", SwingConstants.CENTER);
        sub.setFont(AppTheme.FONT_BODY);
        sub.setForeground(AppTheme.TEXT_SEC);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel prompt = new JLabel("Create a new account to access the platform", SwingConstants.CENTER);
        prompt.setFont(AppTheme.FONT_SMALL);
        prompt.setForeground(AppTheme.TEXT_MUTED);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        prompt.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(icon);
        header.add(Box.createVerticalStrut(4));
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
        banner.setMaximumSize(new Dimension(400, 48));

        alertIcon = new JLabel(AppIcons.get(AppIcons.IconType.ALERT_CIRCLE, 16, AppTheme.ERROR));

        alertText = new JLabel("Validation message");
        alertText.setFont(AppTheme.FONT_SMALL);
        alertText.setForeground(AppTheme.ERROR);

        banner.add(alertIcon, BorderLayout.WEST);
        banner.add(alertText, BorderLayout.CENTER);
        banner.setVisible(false);

        return banner;
    }

    private JPanel buildFormInputs() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(AppTheme.BG_CARD);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(400, 310));

        // Full Name
        JLabel nameLbl = AppTheme.fieldLabel("Full Name");
        nameLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField = AppTheme.textField();
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField.setMaximumSize(new Dimension(400, 36));
        nameField.setToolTipText("Enter your full legal or business name");

        // Email Address
        JLabel emailLbl = AppTheme.fieldLabel("Email Address");
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = AppTheme.textField();
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField.setMaximumSize(new Dimension(400, 36));
        emailField.setToolTipText("Enter your email address (will be unique login ID)");

        // Password Row
        JLabel pwdLbl = AppTheme.fieldLabel("Password");
        pwdLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pwdRow = new JPanel(new BorderLayout(4, 0));
        pwdRow.setBackground(AppTheme.BG_INPUT);
        pwdRow.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1),
            new EmptyBorder(0, 0, 0, 6)
        ));
        pwdRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        pwdRow.setMaximumSize(new Dimension(400, 36));
        pwdRow.setPreferredSize(new Dimension(400, 36));

        passwordField = new JPasswordField();
        passwordField.setFont(AppTheme.FONT_BODY);
        passwordField.setForeground(AppTheme.TEXT_PRI);
        passwordField.setBackground(AppTheme.BG_INPUT);
        passwordField.setCaretColor(AppTheme.ACCENT);
        passwordField.setBorder(new EmptyBorder(6, 12, 6, 8));
        passwordField.setEchoChar('•');

        togglePwdEyeBtn = new JButton();
        togglePwdEyeBtn.setIcon(AppIcons.get(AppIcons.IconType.EYE, 16, AppTheme.TEXT_SEC));
        togglePwdEyeBtn.setContentAreaFilled(false);
        togglePwdEyeBtn.setBorderPainted(false);
        togglePwdEyeBtn.setFocusPainted(false);
        togglePwdEyeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        togglePwdEyeBtn.setToolTipText("Show / Hide password");
        togglePwdEyeBtn.addActionListener(e -> {
            boolean isHidden = passwordField.getEchoChar() != (char) 0;
            passwordField.setEchoChar(isHidden ? (char) 0 : '•');
            togglePwdEyeBtn.setIcon(AppIcons.get(isHidden ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, isHidden ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
        });

        pwdRow.add(passwordField, BorderLayout.CENTER);
        pwdRow.add(togglePwdEyeBtn, BorderLayout.EAST);

        // Confirm Password Row
        JLabel confirmLbl = AppTheme.fieldLabel("Confirm Password");
        confirmLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel confirmRow = new JPanel(new BorderLayout(4, 0));
        confirmRow.setBackground(AppTheme.BG_INPUT);
        confirmRow.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AppTheme.BORDER_COL, 1),
            new EmptyBorder(0, 0, 0, 6)
        ));
        confirmRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmRow.setMaximumSize(new Dimension(400, 36));
        confirmRow.setPreferredSize(new Dimension(400, 36));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(AppTheme.FONT_BODY);
        confirmPasswordField.setForeground(AppTheme.TEXT_PRI);
        confirmPasswordField.setBackground(AppTheme.BG_INPUT);
        confirmPasswordField.setCaretColor(AppTheme.ACCENT);
        confirmPasswordField.setBorder(new EmptyBorder(6, 12, 6, 8));
        confirmPasswordField.setEchoChar('•');

        toggleConfirmEyeBtn = new JButton();
        toggleConfirmEyeBtn.setIcon(AppIcons.get(AppIcons.IconType.EYE, 16, AppTheme.TEXT_SEC));
        toggleConfirmEyeBtn.setContentAreaFilled(false);
        toggleConfirmEyeBtn.setBorderPainted(false);
        toggleConfirmEyeBtn.setFocusPainted(false);
        toggleConfirmEyeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleConfirmEyeBtn.setToolTipText("Show / Hide confirm password");
        toggleConfirmEyeBtn.addActionListener(e -> {
            boolean isHidden = confirmPasswordField.getEchoChar() != (char) 0;
            confirmPasswordField.setEchoChar(isHidden ? (char) 0 : '•');
            toggleConfirmEyeBtn.setIcon(AppIcons.get(isHidden ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, isHidden ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
        });

        confirmRow.add(confirmPasswordField, BorderLayout.CENTER);
        confirmRow.add(toggleConfirmEyeBtn, BorderLayout.EAST);

        // Show all passwords checkbox
        showPasswordsCheck = new JCheckBox("Show Passwords");
        showPasswordsCheck.setFont(AppTheme.FONT_SMALL);
        showPasswordsCheck.setForeground(AppTheme.TEXT_SEC);
        showPasswordsCheck.setBackground(AppTheme.BG_CARD);
        showPasswordsCheck.setFocusPainted(false);
        showPasswordsCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPasswordsCheck.addActionListener(e -> {
            boolean show = showPasswordsCheck.isSelected();
            passwordField.setEchoChar(show ? (char) 0 : '•');
            confirmPasswordField.setEchoChar(show ? (char) 0 : '•');
            togglePwdEyeBtn.setIcon(AppIcons.get(show ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, show ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
            toggleConfirmEyeBtn.setIcon(AppIcons.get(show ? AppIcons.IconType.EYE_OFF : AppIcons.IconType.EYE, 16, show ? AppTheme.ACCENT : AppTheme.TEXT_SEC));
        });

        JLabel pwdHint = new JLabel("Min. 6 characters, must include letters and numbers.");
        pwdHint.setFont(AppTheme.FONT_SMALL);
        pwdHint.setForeground(AppTheme.TEXT_MUTED);
        pwdHint.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(nameLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        form.add(emailLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(emailField);
        form.add(Box.createVerticalStrut(10));

        form.add(pwdLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(pwdRow);
        form.add(Box.createVerticalStrut(10));

        form.add(confirmLbl);
        form.add(Box.createVerticalStrut(4));
        form.add(confirmRow);
        form.add(Box.createVerticalStrut(6));

        JPanel helperRow = new JPanel(new BorderLayout());
        helperRow.setBackground(AppTheme.BG_CARD);
        helperRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        helperRow.setMaximumSize(new Dimension(400, 24));
        helperRow.add(showPasswordsCheck, BorderLayout.WEST);
        helperRow.add(pwdHint, BorderLayout.EAST);
        form.add(helperRow);

        return form;
    }

    private JPanel buildActions() {
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));
        actions.setBackground(AppTheme.BG_CARD);
        actions.setAlignmentX(Component.CENTER_ALIGNMENT);
        actions.setMaximumSize(new Dimension(400, 140));

        registerBtn = AppTheme.primaryBtn("Create Account", AppIcons.get(AppIcons.IconType.PLUS, 14, AppTheme.BG_DARK));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.setMaximumSize(new Dimension(400, 42));
        registerBtn.setPreferredSize(new Dimension(400, 42));
        registerBtn.addActionListener(e -> performRegistration());

        statusLoadingLabel = new JLabel("Creating account in MySQL...");
        statusLoadingLabel.setFont(AppTheme.FONT_SMALL);
        statusLoadingLabel.setForeground(AppTheme.ACCENT);
        statusLoadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLoadingLabel.setVisible(false);

        // Divider
        JPanel divider = new JPanel(new BorderLayout(10, 0));
        divider.setBackground(AppTheme.BG_CARD);
        divider.setMaximumSize(new Dimension(400, 24));
        JLabel divText = new JLabel(" Already have an account? ");
        divText.setFont(AppTheme.FONT_SMALL);
        divText.setForeground(AppTheme.TEXT_MUTED);
        divider.add(new JSeparator() {{ setForeground(AppTheme.BORDER_COL); }}, BorderLayout.WEST);
        divider.add(divText, BorderLayout.CENTER);
        divider.add(new JSeparator() {{ setForeground(AppTheme.BORDER_COL); }}, BorderLayout.EAST);

        backToLoginBtn = AppTheme.secondaryBtn("Back to Sign In", AppIcons.get(AppIcons.IconType.USER, 14, AppTheme.TEXT_PRI));
        backToLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLoginBtn.setMaximumSize(new Dimension(400, 40));
        backToLoginBtn.setPreferredSize(new Dimension(400, 40));
        backToLoginBtn.addActionListener(e -> returnToLogin(null, null));

        actions.add(registerBtn);
        actions.add(Box.createVerticalStrut(6));
        actions.add(statusLoadingLabel);
        actions.add(Box.createVerticalStrut(10));
        actions.add(divider);
        actions.add(Box.createVerticalStrut(10));
        actions.add(backToLoginBtn);

        return actions;
    }

    private void performRegistration() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pwd = new String(passwordField.getPassword());
        String confirmPwd = new String(confirmPasswordField.getPassword());

        hideAlert();

        // Validate inputs before background call
        String validationErr = authService.validateRegistrationInputs(name, email, pwd, confirmPwd);
        if (validationErr != null) {
            showAlert(validationErr, "ERROR");
            return;
        }

        setLoadingState(true);

        SwingWorker<AuthResult, Void> worker = new SwingWorker<>() {
            @Override
            protected AuthResult doInBackground() {
                return authService.register(name, email, pwd, confirmPwd);
            }

            @Override
            protected void done() {
                setLoadingState(false);
                try {
                    AuthResult result = get();
                    if (result.isSuccess()) {
                        // Success: return to login page with pre-filled email
                        returnToLogin(email, result.getMessage());
                    } else {
                        showAlert(result.getMessage(), "ERROR");
                    }
                } catch (Exception ex) {
                    showAlert("Registration error: " + ex.getMessage(), "ERROR");
                }
            }
        };
        worker.execute();
    }

    private void returnToLogin(String prefillEmail, String successMessage) {
        if (parentLoginFrame != null) {
            if (prefillEmail != null) {
                parentLoginFrame.setEmail(prefillEmail);
            }
            if (successMessage != null) {
                parentLoginFrame.setNotification(successMessage, "SUCCESS");
            }
            parentLoginFrame.setVisible(true);
        } else {
            LoginFrame login = new LoginFrame();
            if (prefillEmail != null) login.setEmail(prefillEmail);
            if (successMessage != null) login.setNotification(successMessage, "SUCCESS");
            login.setVisible(true);
        }
        dispose();
    }

    private void setLoadingState(boolean loading) {
        registerBtn.setEnabled(!loading);
        backToLoginBtn.setEnabled(!loading);
        nameField.setEnabled(!loading);
        emailField.setEnabled(!loading);
        passwordField.setEnabled(!loading);
        confirmPasswordField.setEnabled(!loading);
        statusLoadingLabel.setVisible(loading);
        setCursor(loading ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
        if (loading) {
            registerBtn.setText("Creating Account...");
            registerBtn.setIcon(AppIcons.get(AppIcons.IconType.REFRESH, 14, AppTheme.BG_DARK));
        } else {
            registerBtn.setText("Create Account");
            registerBtn.setIcon(AppIcons.get(AppIcons.IconType.PLUS, 14, AppTheme.BG_DARK));
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
            new RegisterFrame(null).setVisible(true);
        });
    }
}
