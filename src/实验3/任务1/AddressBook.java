package 实验3.任务1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddressBook {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/contacts";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1024";

    public static void main(String[] args) {
        // 创建主窗口
        JFrame frame = new JFrame("通讯录");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("操作");

        // 创建菜单项
        JMenuItem addContactItem = new JMenuItem("添加联系人");
        JMenuItem modifyContactItem = new JMenuItem("修改联系人");
        JMenuItem deleteContactItem = new JMenuItem("删除联系人");
        JMenuItem showContactItem = new JMenuItem("查看联系人");
        JMenuItem exitItem = new JMenuItem("退出");

        // 添加菜单项到菜单
        menu.add(addContactItem);
        menu.add(modifyContactItem);
        menu.add(deleteContactItem);
        menu.add(showContactItem);
        menu.addSeparator();
        menu.add(exitItem);

        // 设置菜单到菜单栏
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // 添加菜单项的事件监听器
        addContactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        modifyContactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyContact();
            }
        });

        deleteContactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        showContactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContact();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 显示窗口
        frame.setVisible(true);
    }

    // 添加联系人
    private static void addContact() {
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "添加联系人", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO contact (name, address, phone) VALUES (?, ?, ?)")) {

                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, phone);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "成功添加联系人!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 修改联系人
    private static void modifyContact() {
        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("姓名:"));
        panel.add(nameField);
        panel.add(new JLabel("地址:"));
        panel.add(addressField);
        panel.add(new JLabel("电话:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "修改联系人", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("UPDATE contact SET name=?, address=?, phone=? WHERE id=?")) {

                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, phone);
                stmt.setInt(4, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "成功修改联系人!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 删除联系人
    private static void deleteContact() {
        JTextField idField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, panel, "删除联系人", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM contact WHERE id=?")) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "成功删除联系人!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 查看联系人
    private static void showContact() {
        StringBuilder contacts = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contact")) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                contacts.append(resultSet.getString("id")).append(" ")
                        .append(resultSet.getString("name")).append(" ")
                        .append(resultSet.getString("address")).append(" ")
                        .append(resultSet.getString("phone")).append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, contacts.toString(), "联系人列表", JOptionPane.INFORMATION_MESSAGE);
    }
}