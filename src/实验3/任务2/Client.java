package 实验3.任务2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Client {
    private Server server;
    private JFrame frame;
    private JTable table;

    public Client() {
        server = new Server();
        frame = new JFrame("个人通讯录");
        table = new JTable();
        setupGUI();
    }

    private void setupGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a panel to hold the table and buttons
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add buttons to the panel
        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    server.deleteContact(id);
                    loadContacts();
                }
            }
        });
        buttonPanel.add(deleteButton);

        JButton updateButton = new JButton("更新");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    String name = (String) table.getValueAt(selectedRow, 1);
                    String address = (String) table.getValueAt(selectedRow, 2);
                    String phone = (String) table.getValueAt(selectedRow, 3);
                    showUpdateDialog(id, name, address, phone);
                }
            }
        });
        buttonPanel.add(updateButton);

        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });
        buttonPanel.add(addButton);

        // Populate the table with data from the server
        loadContacts();

        // Display the frame
        frame.setVisible(true);
    }

    private void loadContacts() {
        List<Contact> contacts = server.getAllContacts();
        String[] columnNames = {"ID", "姓名", "地址", "电话"};
        Object[][] data = new Object[contacts.size()][4];
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            data[i][0] = contact.getId();
            data[i][1] = contact.getName();
            data[i][2] = contact.getAddress();
            data[i][3] = contact.getPhone();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void showAddDialog() {
        JFrame addDialog = new JFrame("添加联系人");
        addDialog.setSize(400, 300);
        addDialog.setLayout(new GridLayout(4, 2));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        addDialog.add(idLabel);
        addDialog.add(idField);

        JLabel nameLabel = new JLabel("姓名:");
        JTextField nameField = new JTextField();
        addDialog.add(nameLabel);
        addDialog.add(nameField);

        JLabel addressLabel = new JLabel("地址:");
        JTextField addressField = new JTextField();
        addDialog.add(addressLabel);
        addDialog.add(addressField);

        JLabel phoneLabel = new JLabel("电话:");
        JTextField phoneField = new JTextField();
        addDialog.add(phoneLabel);
        addDialog.add(phoneField);

        JButton confirmButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setAddress(address);
                contact.setPhone(phone);
                server.addContact(contact);
                addDialog.dispose();
                loadContacts();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog.dispose();
            }
        });
        addDialog.add(confirmButton);
        addDialog.add(cancelButton);
        addDialog.setVisible(true);
    }

    private void showUpdateDialog(int id, String name, String address, String phone) {
        JFrame updateDialog = new JFrame("修改联系人");
        updateDialog.setSize(400, 300);
        updateDialog.setLayout(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(String.valueOf(id));
        updateDialog.add(idLabel);
        updateDialog.add(idField);

        JLabel nameLabel = new JLabel("姓名:");
        JTextField nameField = new JTextField(name);
        updateDialog.add(nameLabel);
        updateDialog.add(nameField);

        JLabel addressLabel = new JLabel("地址:");
        JTextField addressField = new JTextField(address);
        updateDialog.add(addressLabel);
        updateDialog.add(addressField);

        JLabel phoneLabel = new JLabel("电话:");
        JTextField phoneField = new JTextField(phone);
        updateDialog.add(phoneLabel);
        updateDialog.add(phoneField);

        JButton confirmButton = new JButton("确定");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int updatedId = Integer.parseInt(idField.getText());
                String updatedName = nameField.getText();
                String updatedAddress = addressField.getText();
                String updatedPhone = phoneField.getText();
                Contact updatedContact = new Contact();
                updatedContact.setId(updatedId);
                updatedContact.setName(updatedName);
                updatedContact.setAddress(updatedAddress);
                updatedContact.setPhone(updatedPhone);
                server.updateContact(updatedContact);
                updateDialog.dispose();
                loadContacts();
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDialog.dispose();
            }
        });
        updateDialog.add(confirmButton);
        updateDialog.add(cancelButton);
        updateDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
