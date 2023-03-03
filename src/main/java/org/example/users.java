package org.example;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.TableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class users {

    private JFrame frmCurdOperationSwing;
    private JTextField txtid;
    private JTextField txtanimal;
    private JTextField txtspecie;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    users window = new users();
                    window.frmCurdOperationSwing.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public users() {
        initialize();
        Connect();
        loadData();
    }


    //Database Connection
    Connection con = null;
    PreparedStatement pst;
    ResultSet rs;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/firsttablesql";
            String username = "adminmysql";
            String password = "Ruralcamp_2023";
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Disconnected");
        }
    }

    // Clear All
    public void clear() {
        txtid.setText("");
        txtanimal.setText("");
        txtspecie.setText("");
    }

    // Load Table
    public void loadData() {
        try {
            pst = con.prepareStatement("Select * from users");
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmCurdOperationSwing = new JFrame();
        frmCurdOperationSwing.setTitle("CURD Operation Swing MySQL");
        frmCurdOperationSwing.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
        frmCurdOperationSwing.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("User Management System");
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 11, 259, 60);
        frmCurdOperationSwing.getContentPane().add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(20, 71, 387, 284);
        frmCurdOperationSwing.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("id");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(21, 46, 46, 24);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("animal");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(21, 81, 46, 24);
        panel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("specie");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_2.setBounds(21, 116, 46, 24);
        panel.add(lblNewLabel_1_2);

        txtid = new JTextField();
        txtid.setEditable(false);
        txtid.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtid.setBounds(78, 46, 287, 24);
        panel.add(txtid);
        txtid.setColumns(10);

        txtanimal = new JTextField();
        txtanimal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtanimal.setColumns(10);
        txtanimal.setBounds(78, 81, 287, 24);
        panel.add(txtanimal);

        txtspecie = new JTextField();
        txtspecie.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtspecie.setColumns(10);
        txtspecie.setBounds(78, 120, 287, 24);
        panel.add(txtspecie);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = txtid.getText();
                String animal = txtanimal.getText();
                String specie = txtspecie.getText();

                if (animal == null || animal.isEmpty() || animal.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter animal");
                    txtanimal.requestFocus();
                    return;
                }
                if (specie == null || specie.isEmpty() || specie.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter specie");
                    txtspecie.requestFocus();
                    return;
                }

                if (txtid.getText().isEmpty()) {
                    try {
                        String sql = "insert into users (animal,specie) values (?,?)";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, animal);
                        pst.setString(2, specie);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data insert Success");
                        clear();
                        loadData();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }


            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBounds(78, 195, 89, 23);
        panel.add(btnSave);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update Details
                String id = txtid.getText();
                String animal = txtanimal.getText();
                String specie = txtspecie.getText();

                if (animal == null || animal.isEmpty() || animal.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter Animal");
                    txtanimal.requestFocus();
                    return;
                }
                if (specie == null || specie.isEmpty() || specie.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Enter specie");
                    txtspecie.requestFocus();
                    return;
                }

                if (!txtid.getText().isEmpty()) {
                    try {
                        String sql = "update users set animal=?,specie=? where id=?";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, animal);
                        pst.setString(2, specie);
                        pst.setString(3, id);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Update Success");
                        clear();
                        loadData();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnUpdate.setBounds(177, 195, 89, 23);
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Delete Details
                String id = txtid.getText();
                if (!txtid.getText().isEmpty()) {

                    int result = JOptionPane.showConfirmDialog(null, "Sure? You want to Delete?", "Delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            String sql = "delete from users where id=?";
                            pst = con.prepareStatement(sql);
                            pst.setString(1, id);
                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Data Deleted Success");
                            clear();
                            loadData();

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }
        });
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBounds(276, 195, 89, 23);
        panel.add(btnDelete);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(417, 71, 467, 284);
        frmCurdOperationSwing.getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int index = table.getSelectedRow();
                TableModel model = table.getModel();
                // id animal specie
                txtid.setText(model.getValueAt(index, 0).toString());
                txtanimal.setText(model.getValueAt(index, 1).toString());
                txtspecie.setText(model.getValueAt(index, 2).toString());
            }
        });
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        frmCurdOperationSwing.setBounds(100, 100, 910, 522);
        frmCurdOperationSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

