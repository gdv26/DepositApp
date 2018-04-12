package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bll.ProductBLL;
import bll.ClientBLL;
import connection.ConnectionFactory;
import model.Product;
import model.Client;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ProductsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idTextField;
	private JTextField denumireTextField;
	private JTextField cantitateTextField;
	private JTextField pretTextField;
	private JTable table;

	private final static String showStatementString = "select * from product";
	
	public void refreshTable(){
		String refreshStatementString = "select id, denumire, pret, cantitate from product";
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

	public ProductsDialog() {
		setTitle("Produse");
		setBounds(100, 100, 588, 468);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDenumire = new JLabel("Denumire:");
			lblDenumire.setBounds(35, 67, 85, 14);
			contentPanel.add(lblDenumire);
		}
		{
			JLabel lblNewLabel = new JLabel("Pret:");
			lblNewLabel.setBounds(35, 142, 46, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblCantitate = new JLabel("Cantitate: ");
			lblCantitate.setBounds(35, 107, 74, 14);
			contentPanel.add(lblCantitate);
		}
		{
			denumireTextField = new JTextField();
			denumireTextField.setBounds(104, 64, 86, 20);
			contentPanel.add(denumireTextField);
			denumireTextField.setColumns(10);
		}
		{
			cantitateTextField = new JTextField();
			cantitateTextField.setBounds(104, 104, 86, 20);
			contentPanel.add(cantitateTextField);
			cantitateTextField.setColumns(10);
		}
		{
			pretTextField = new JTextField();
			pretTextField.setBounds(104, 139, 86, 20);
			contentPanel.add(pretTextField);
			pretTextField.setColumns(10);
		}
		{
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Product product = new Product(denumireTextField.getText(), Integer.parseInt(pretTextField.getText()), Integer.parseInt(cantitateTextField.getText()));
					ProductBLL productBll = new ProductBLL();
					int id = productBll.insertProduct(product);
					JOptionPane.showMessageDialog(null, "Produs adaugat cu succes!");
					refreshTable();
				}
			});
			
			btnAdd.setBounds(284, 36, 89, 23);
			contentPanel.add(btnAdd);
		}
		
		{
			JButton btnUpdate = new JButton("Update");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					String updateStatementString = "update product set id = '"+idTextField.getText()+"', denumire = '"+denumireTextField.getText()+"', pret = '"+pretTextField.getText()+"', cantitate = '"+cantitateTextField.getText()+"' where id = '"+idTextField.getText()+"' ";
					Connection dbConnection = ConnectionFactory.getConnection();
					PreparedStatement updateStatement = null;
					try{
						updateStatement = dbConnection.prepareStatement(updateStatementString);
						updateStatement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Product updated successfully!");
						updateStatement.close();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Eroare la update produs!");
					}
					finally{
						ConnectionFactory.close(updateStatement);
						ConnectionFactory.close(dbConnection);
					}
					refreshTable();
				}
			});
			btnUpdate.setBounds(284, 86, 89, 23);
			contentPanel.add(btnUpdate);
		}
		{
			JButton btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String deleteStatementString = "delete from product where id = '"+idTextField.getText()+"'  ";
					String resetStatementString = "alter table product drop id";
					String newStatementString = "alter table product add id int not null auto_increment primary key";
					Connection dbConnection = ConnectionFactory.getConnection();
					PreparedStatement deleteStatement = null;
					PreparedStatement resetStatement = null;
					PreparedStatement newStatement = null;
					try{
						deleteStatement = dbConnection.prepareStatement(deleteStatementString);
						deleteStatement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Produs sters cu succes!");
						deleteStatement.close();
						resetStatement = dbConnection.prepareStatement(resetStatementString);
						resetStatement.executeUpdate();
						newStatement = dbConnection.prepareStatement(newStatementString);
						newStatement.executeUpdate();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Eroare la stergere produs!");
					}
					finally{
						ConnectionFactory.close(deleteStatement);
						ConnectionFactory.close(dbConnection);
					}
					refreshTable();
				}
			});
			btnDelete.setBounds(284, 138, 89, 23);
			contentPanel.add(btnDelete);
		}
		{
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
			btnShow.setBounds(429, 86, 89, 23);
			contentPanel.add(btnShow);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 184, 552, 201);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		
		JLabel lblId = new JLabel("ID: ");
		lblId.setBounds(35, 25, 46, 14);
		contentPanel.add(lblId);
		
		idTextField = new JTextField();
		idTextField.setBounds(104, 22, 86, 20);
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
	
	public static void updateProduct(int idProdus, int cantitateProdus){
		
		JOptionPane.showMessageDialog(null, "metoda");
		Product toReturn = null;
		String findStatementString = "select * from product where id = ?";
		//String updateStatementString = "update product set id = '"+idTextField.getText()+"', denumire = '"+denumireTextField.getText()+"', pret = '"+pretTextField.getText()+"', cantitate = '"+cantitateTextField.getText()+"' where id = '"+idTextField.getText()+"' ";
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, idProdus);
			rs = findStatement.executeQuery();
			rs.next();

			String denumire = rs.getString("denumire");
			int pret = rs.getInt("pret");
			toReturn = new Product(idProdus, denumire, pret, cantitateProdus);
			
		} 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Eroare la updateProduct!");
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
