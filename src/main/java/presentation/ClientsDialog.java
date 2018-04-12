package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bll.ClientBLL;
import connection.ConnectionFactory;
import model.Client;
import net.proteanit.sql.DbUtils;

public class ClientsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField idTextField;
	private JTextField numeTextField;
	private JTextField varstaTextField;
	private JTextField adresaTextField;
	private JTextField emailTextField;
	
	private final static String showStatementString = "select * from student";
	
	public void refreshTable(){
		String refreshStatementString = "select id, name, address, email, age from student";
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement refreshStatement = null;
		ResultSet rs = null;
		try{
			refreshStatement = dbConnection.prepareStatement(refreshStatementString);
			rs = refreshStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			refreshStatement.close();
			rs.close();
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Eroare la refresh!");
		}
	}

	public ClientsDialog() {
		setTitle("Clienti");
		setBounds(100, 100, 580, 453);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 159, 544, 199);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNume = new JLabel("Nume:");
		lblNume.setBounds(29, 65, 46, 14);
		contentPanel.add(lblNume);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setBounds(251, 27, 46, 14);
		contentPanel.add(lblAdresa);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(251, 65, 46, 14);
		contentPanel.add(lblEmail);
		
		JLabel lblVarsta = new JLabel("Varsta:");
		lblVarsta.setBounds(29, 111, 46, 14);
		contentPanel.add(lblVarsta);
		
		numeTextField = new JTextField();
		numeTextField.setBounds(79, 62, 86, 20);
		contentPanel.add(numeTextField);
		numeTextField.setColumns(10);
		
		varstaTextField = new JTextField();
		varstaTextField.setBounds(79, 108, 86, 20);
		contentPanel.add(varstaTextField);
		varstaTextField.setColumns(10);
		
		adresaTextField = new JTextField();
		adresaTextField.setBounds(319, 24, 86, 20);
		contentPanel.add(adresaTextField);
		adresaTextField.setColumns(10);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(319, 62, 86, 20);
		contentPanel.add(emailTextField);
		emailTextField.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Client student = new Client(numeTextField.getText(), adresaTextField.getText(), emailTextField.getText(), Integer.parseInt(varstaTextField.getText()));
				ClientBLL studentBll = new ClientBLL();
				int id = studentBll.insertStudent(student);
				JOptionPane.showMessageDialog(null, "Client adaugat cu succes!");
				refreshTable();
			}
		});
		btnAdd.setBounds(434, 23, 72, 23);
		contentPanel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				String updateStatementString = "update student set id = '"+idTextField.getText()+"', name = '"+numeTextField.getText()+"', address = '"+adresaTextField.getText()+"', email = '"+emailTextField.getText()+"', age = '"+varstaTextField.getText()+"' where id = '"+idTextField.getText()+"' ";
				Connection dbConnection = ConnectionFactory.getConnection();
				PreparedStatement updateStatement = null;
				try{
					updateStatement = dbConnection.prepareStatement(updateStatementString);
					updateStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data updated!");
					updateStatement.close();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Eroare la update clienti!");
				}
				finally{
					ConnectionFactory.close(updateStatement);
					ConnectionFactory.close(dbConnection);
				}
				refreshTable();
			}
		});
		
		btnUpdate.setBounds(434, 107, 86, 23);
		contentPanel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String deleteStatementString = "delete from student where id = '"+idTextField.getText()+"'    ";
				String resetStatementString = "alter table student drop id";
				String newStatementString = "alter table student add id int not null auto_increment primary key";
				Connection dbConnection = ConnectionFactory.getConnection();
				PreparedStatement deleteStatement = null;
				PreparedStatement resetStatement = null;
				PreparedStatement newStatement = null; 
				try{
					deleteStatement = dbConnection.prepareStatement(deleteStatementString);
					deleteStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data deleted!");
					deleteStatement.close();
					resetStatement = dbConnection.prepareStatement(resetStatementString);
					resetStatement.executeUpdate();
					newStatement = dbConnection.prepareStatement(newStatementString);
					newStatement.executeUpdate();
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Eroare la stergere client!");
				}
				finally{
					ConnectionFactory.close(deleteStatement);
					ConnectionFactory.close(dbConnection);
				}
				refreshTable();
			}
		});
		btnDelete.setBounds(434, 61, 72, 23);
		contentPanel.add(btnDelete);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection dbConnection = ConnectionFactory.getConnection();
				PreparedStatement showStatement = null;
				ResultSet rs = null;
				
				try{
					showStatement = dbConnection.prepareStatement(showStatementString);
					rs = showStatement.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
				finally {
					ConnectionFactory.close(rs);
					ConnectionFactory.close(showStatement);
					ConnectionFactory.close(dbConnection);
				}
			}
		});
		
		btnShow.setBounds(319, 107, 72, 23);
		contentPanel.add(btnShow);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(29, 27, 46, 14);
		contentPanel.add(lblId);
		
		idTextField = new JTextField();
		idTextField.setBounds(79, 24, 86, 20);
		contentPanel.add(idTextField);
		idTextField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
