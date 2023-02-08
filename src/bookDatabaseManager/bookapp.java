package bookDatabaseManager;


import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;

public class bookapp {

	private JFrame frame;
	private JTextField textbname;
	private JTextField textedition;
	private JTextField textprice;
	private JTextField textbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bookapp window = new bookapp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	private JTable table;
	public void connect()
	{
		try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3307/project";
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url,username,password);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void printTable()
	{
		try {
			String q="Select * from BOOKAPP";
			stmt=con.prepareStatement(q);
			rs=stmt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(Exception e2)
		{
			e2.printStackTrace();
		}
	}
	public void clearAll()
	{
		textbid.setText("");
		textbname.setText("");
		textedition.setText("");
		textprice.setText("");
	}
	public bookapp() {
		initialize();
		connect();
		printTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(SystemColor.textHighlightText);
		frame.getContentPane().setBackground(SystemColor.info);
		frame.setBounds(100, 100, 945, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textbid = new JTextField();
		textbid.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) 
			{
				String id = textbid.getText();
				String q = "SELECT NAME,EDITION,PRICE FROM BOOKAPP WHERE ID=?";
				try {
					stmt = con.prepareStatement(q);
					stmt.setString(1, id);
					ResultSet rs = stmt.executeQuery();
					if(rs.next()==true)
					{
						String name=rs.getString(1);
						String edition=rs.getString(2);
						String price=rs.getString(3);
						textbname.setText(name);
						textedition.setText(edition);
						textprice.setText(price);
					}
					else
					{
						clearAll();
					}
				}
				catch(Exception e3)
				{
					e3.printStackTrace();
				}
			}
		});
		
		JLabel lblNewLabel_1_3 = new JLabel("Book ID");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1_3.setBounds(62, 76, 83, 38);
		frame.getContentPane().add(lblNewLabel_1_3);
		textbid.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textbid.setColumns(10);
		textbid.setBounds(155, 82, 51, 26);
		frame.getContentPane().add(textbid);
		
		JLabel lblNewLabel = new JLabel("Book Database Manager");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(309, 26, 302, 42);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(36, 130, 375, 217);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(23, 34, 105, 38);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1_1.setBounds(23, 95, 93, 38);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1_2.setBounds(23, 157, 93, 38);
		panel.add(lblNewLabel_1_2);
		
		textbname = new JTextField();
		textbname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textbname.setBounds(138, 40, 202, 26);
		panel.add(textbname);
		textbname.setColumns(10);
		textbname.requestFocus();
		
		textedition = new JTextField();
		textedition.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textedition.setColumns(10);
		textedition.setBounds(138, 101, 154, 26);
		panel.add(textedition);
		
		textprice = new JTextField();
		textprice.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textprice.setColumns(10);
		textprice.setBounds(138, 163, 99, 26);
		panel.add(textprice);
		
		JButton btnsave = new JButton("Save");
		btnsave.setBackground(new Color(250, 240, 230));
		btnsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String name=textbname.getText();
				String edition=textedition.getText();
				String price=textprice.getText();
				if(name.isEmpty() || edition.isEmpty() || price.isEmpty())
					JOptionPane.showMessageDialog(null, " Please enter all required data!");
				else
				{
					try {
						String q="INSERT INTO BOOKAPP(NAME,EDITION,PRICE) value(?,?,?)";
						stmt=con.prepareStatement(q);
						stmt.setString(1, name);
						stmt.setString(2, edition);
						stmt.setString(3, price);
						stmt.executeUpdate(); 
					
						JOptionPane.showMessageDialog(null, "Data is added successfully!");
						printTable();
					
						clearAll();
						textbname.requestFocus();
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		btnsave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnsave.setBounds(36, 371, 102, 38);
		frame.getContentPane().add(btnsave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(250, 240, 230));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String name=textbname.getText();
				String edition=textedition.getText();
				String price=textprice.getText();
				String id=textbid.getText();
				if(id.isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter the ID!");
				else
				{
					try {
						String q="UPDATE BOOKAPP SET NAME=?,EDITION=?,PRICE=? WHERE ID=?";
						stmt=con.prepareStatement(q);
						stmt.setString(1, name);
						stmt.setString(2, edition);
						stmt.setString(3, price);
						stmt.setString(4, id);
						stmt.executeUpdate();
						printTable();
						clearAll();
						JOptionPane.showMessageDialog(null, "Data is Updated successfully!");
					}
					catch(Exception e4)
					{
						e4.printStackTrace();
					}
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdate.setBounds(174, 371, 102, 38);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(250, 240, 230));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String id=textbid.getText();
				try {
					if(!id.isEmpty())
					{
						String q="DELETE FROM BOOKAPP WHERE ID=?";
						stmt=con.prepareStatement(q);
						stmt.setString(1, id);
						stmt.executeUpdate();
						printTable();
						clearAll();
						JOptionPane.showMessageDialog(null, "Data is deleted Successfully!");
					}
					else
						JOptionPane.showMessageDialog(null, "Please enter ID to delete the Data!");
				}
				catch(Exception e5)
				{
					e5.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDelete.setBounds(309, 371, 102, 38);
		frame.getContentPane().add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(new Color(250, 240, 230));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				clearAll();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClear.setBounds(174, 432, 102, 38);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(250, 240, 230));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				//JOptionPane.showMessageDialog(null, "Book Database Successfully closed!");
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExit.setBounds(618, 447, 102, 38);
		frame.getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(440, 117, 462, 320);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(36, 64, 212, 56);
		frame.getContentPane().add(panel_1);
	}
}
