import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainForm extends JFrame {

    private JPanel root;
    private JRadioButton zipButton;
    private JRadioButton secretButton;
    private JPanel imagePanel;
    private ButtonGroup group;

    public MainForm() throws IOException {

        setContentPane(root);
        configureDragNDrop();
        configureButtonGroup();
        configureButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ImageIO.read(new File("default.png")));
       setSize(800, 500);
        setVisible(true);
        setResizable(false);

    }

    private void configureButtonGroup() {
        group = new ButtonGroup();
        group.add(zipButton);
        group.add(secretButton);
        zipButton.setSelected(true);
    }

    private void configureButtons() {
        zipButton.setIcon(new ImageIcon("zip-white.png"));
        zipButton.setSelectedIcon(new ImageIcon("zip-white-selected.png"));
        secretButton.setIcon(new ImageIcon("secret-white.png"));
        secretButton.setSelectedIcon(new ImageIcon("secret-white-selected.png"));
        zipButton.setSelected(true);
    }

    private void configureDragNDrop() {
        imagePanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent event) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    onDropFiles((List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private BaseCipher createCipher(String password) {
        if (zipButton.isSelected()) {
            return new ZipCipher(password);
        }

        return new Cipher(password);
    }

    private void onDropFiles(List<File> files) {
        BaseCipher cipher = createCipher(null);
        for (File file : files) {
            if (file.getAbsolutePath().endsWith(cipher.getEncodesFileExtension())) {
                decodeFile(file);
            } else {
                encodeFile(file);
            }
        }
    }

    private void encodeFile(File file) {
        String password = JOptionPaneEx.showPasswordDialog("Podaj hasło do zaszyfrowania pliku" + file.getName());

        if (password != null && password.length() > 0) {
            BaseCipher cipher = createCipher(password);

            String resultFilePath = file.getParent() + "\\" + file.getName() + cipher.getEncodesFileExtension();

            try {
                boolean canProcess = true;
                if (filExists(resultFilePath)) {
                    if (JOptionPaneEx.showConfirmDialog(this,
                            String.format("Plik %s istnieje. Czy chcesz do nadpisać?", resultFilePath)) != JOptionPane.YES_OPTION) {
                        canProcess = false;
                    }
                }
                if (canProcess) {
                    cipher.encode(file.getAbsolutePath(), resultFilePath);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void decodeFile(File file) {

        String password = JOptionPaneEx.showPasswordDialog("Podaj hasło do odszyfrowania pliku" + file.getName());

        if (password != null && password.length() > 0) {
            BaseCipher cipher = createCipher(password);

            String resultFilePath = file.getAbsolutePath().replaceAll(cipher.getEncodesFileExtension() + "$", "");

            try {

                boolean canProcess = true;
                if (filExists(resultFilePath)) {
                    if (JOptionPaneEx.showConfirmDialog(this,
                            String.format("Plik %s istnieje. Czy chcesz do nadpisać?", resultFilePath)) != JOptionPane.YES_OPTION) {

                        canProcess = false;
                    }
                }
                if (canProcess) {
                    cipher.decode(file.getAbsolutePath(), resultFilePath);
                }
            } catch (InvalidPassword e) {
                JOptionPane.showMessageDialog(this, "Podane hasło jest nieprawidłowe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private boolean filExists(String filePath) {
        return new File(filePath).exists();
    }

    private void createUIComponents() {
        try {
            imagePanel = new ImagePanel("dragNdrop.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
