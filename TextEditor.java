import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new OpenMenuItemListener());
        fileMenu.add(openMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new SaveMenuItemListener());
        fileMenu.add(saveMenuItem);

        JMenuItem printMenuItem = new JMenuItem("Print");
        printMenuItem.addActionListener(new PrintMenuItemListener());
        fileMenu.add(printMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemListener());
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.addActionListener(new CutMenuItemListener());
        editMenu.add(cutMenuItem);

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.addActionListener(new CopyMenuItemListener());
        editMenu.add(copyMenuItem);

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.addActionListener(new PasteMenuItemListener());
        editMenu.add(pasteMenuItem);

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        JButton saveAndSubmitButton = new JButton("Save and Submit");
        saveAndSubmitButton.addActionListener(new SaveAndSubmitButtonListener());
        add(saveAndSubmitButton, BorderLayout.SOUTH);
    }

    private class OpenMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnValue = fileChooser.showOpenDialog(TextEditor.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    reader.close();
                    textArea.setText(content.toString());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class SaveMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnValue = fileChooser.showSaveDialog(TextEditor.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(textArea.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(TextEditor.this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class PrintMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(TextEditor.this, "Error printing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ExitMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class CutMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.cut();
        }
    }

    private class CopyMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.copy();
        }
    }

    private class PasteMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.paste();
        }
    }

    private class SaveAndSubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnValue = fileChooser.showSaveDialog(TextEditor.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(textArea.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(TextEditor.this, "File saved and submitted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    textArea.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(TextEditor.this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        TextEditor textEditor = new TextEditor();
        textEditor.setVisible(true);
    }
}
