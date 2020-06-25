import javax.swing.*;
import java.awt.*;

public class JOptionPaneEx {
    public static String showPasswordDialog(String message) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JPasswordField passwordTextBox = new JPasswordField(30);
        panel.add(label);
        panel.add(passwordTextBox);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, panel, "",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (option == 0) {
            return new String(passwordTextBox.getPassword());
        }
        return null;
    }

    public static int showConfirmDialog(Component component, String message) {
        return JOptionPane.showConfirmDialog(component, message, "", JOptionPane.YES_NO_OPTION);
    }
}
