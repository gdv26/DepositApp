package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.mysql.cj.api.xdevapi.Result;

import bll.OrderBLL;
import connection.ConnectionFactory;
import model.Order;
import model.Product;
import net.proteanit.sql.DbUtils;
import javax.swing.JComboBox;

public class OrdersDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField idTextField;
	private JTextField numeClientTextField;
	private JTextField denumireProdusTextField;
	private JTextField cantitateTextField;
	private JTable table;
	private JComboBox clientsComboBox;
	private JComboBox productsComboBox;
	
	public void refreshTable(){
		String refreshStatementString = "select id, numeClient, denumireProdus, cantitate from orders";
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
		finally{
			ConnectionFactory.close(refreshStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public OrdersDialog() {
		setTitle("Comenzi");
		setBounds(100, 100, 593, 481);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNumeClient = new JLabel("Nume client:");
			lblNumeClient.setBounds(38, 69, 118, 14);
			contentPanel.add(lblNumeClient);
		}
		{
			JLabel lblDenumireProdus = new JLabel("Denumire produs: ");
			lblDenumireProdus.setBounds(38, 108, 115, 14);
			contentPanel.add(lblDenumireProdus);
		}
		{
			JLabel lblCantitate = new JLabel("Cantitate: ");
			lblCantitate.setBounds(38, 141, 89, 14);
			contentPanel.add(lblCantitate);
		}
		{
			numeClientTextField = new JTextField();
			numeClientTextField.setBounds(160, 66, 86, 20);
			contentPanel.add(numeClientTextField);
			numeClientTextField.setColumns(10);
		}
		{
			denumireProdusTextField = new JTextField();
			denumireProdusTextField.setBounds(160, 105, 86, 20);
			contentPanel.add(denumireProdusTextField);
			denumireProdusTextField.setColumns(10);
		}
		{
			cantitateTextField = new JTextField();
			cantitateTextField.setBounds(160, 138, 86, 20);
			contentPanel.add(cantitateTextField);
			cantitateTextField.setColumns(10);
		}
		{
			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Order order = new Order(clientsComboBox.getSelectedItem().toString(), productsComboBox.getSelectedItem().toString(), Integer.parseInt(cantitateTextField.getText()));
					OrderBLL orderBll = new OrderBLL();
					int id = orderBll.insertOrder(order);
					//int idProdus = Integer.parseInt(idTextField.getText());
					//int cantitate = Integer.parseInt(cantitateTextField.getText());
					JOptionPane.showMessageDialog(null, "Comanda adaugata cu succes!");
					/*try {
						Method method = ProductsDialog.class.getDeclaredMethod("updateProduct", Integer.class, Integer.class);
						try {
							method.invoke(null, idProdus, cantitate);
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						}
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					}*/
					refreshTable();
				}
			});
			btnAdd.setBounds(351, 29, 89, 23);
			contentPanel.add(btnAdd);
		}
		{
			JButton btnUpdate = new JButton("Update");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String updateStatementString = "update orders set id = '"+idTextField.getText()+"', numeClient = '"+numeClientTextField.getText()+"', denumireProdus = '"+denumireProdusTextField.getText()+"', cantitate = '"+cantitateTextField.getText()+"' where id = '"+idTextField.getText()+"' ";
					Connection dbConnection = ConnectionFactory.getConnection();
					PreparedStatement updateStatement = null;
					try{
						updateStatement = dbConnection.prepareStatement(updateStatementString);
						updateStatement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Order updated successfully!");
						updateStatement.close();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Eroare la update comanda!");
					}
					finally{
						ConnectionFactory.close(updateStatement);
						ConnectionFactory.close(dbConnection);
					}
					refreshTable();
				}
			});
			btnUpdate.setBounds(351, 83, 89, 23);
			contentPanel.add(btnUpdate);
		}
		{
			JButton btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String deleteStatementString = "delete from orders where id = '"+idTextField.getText()+"'  ";
					String resetStatementString = "alter table orders drop id";
					String newStatementString = "alter table orders add id int not null auto_increment primary key";
					Connection dbConnection = ConnectionFactory.getConnection();
					PreparedStatement deleteStatement = null;
					PreparedStatement resetStatement = null;
					PreparedStatement newStatement = null;
					try{
						deleteStatement = dbConnection.prepareStatement(deleteStatementString);
						deleteStatement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Order deleted successfully!");
						deleteStatement.close();
						resetStatement = dbConnection.prepareStatement(resetStatementString);
						resetStatement.executeUpdate();
						newStatement = dbConnection.prepareStatement(newStatementString);
						newStatement.executeUpdate();
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Eroare la stergere comanda!");
					}
					finally{
						ConnectionFactory.close(deleteStatement);
						ConnectionFactory.close(dbConnection);
					}
					refreshTable();
				}
			});
			btnDelete.setBounds(351, 135, 89, 23);
			contentPanel.add(btnDelete);
		}
		{
			JButton btnShow = new JButton("Show");
			btnShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					fillClientsComboBox();
					fillProductsComboBox();
					//refreshTable();
					
					String showStatementString = "select * from orders";
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
			btnShow.setBounds(467, 29, 89, 23);
			contentPanel.add(btnShow);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 188, 557, 210);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				scrollPane.setViewportView(table);
			}
		}
		{
			JLabel lblId = new JLabel("ID: ");
			lblId.setBounds(38, 33, 46, 14);
			contentPanel.add(lblId);
		}
		{
			idTextField = new JTextField();
			idTextField.setBounds(160, 29, 86, 20);
			contentPanel.add(idTextField);
			idTextField.setColumns(10);
		}
		
		clientsComboBox = new JComboBox();
		clientsComboBox.setBounds(467, 84, 89, 23);
		contentPanel.add(clientsComboBox);
		{
			productsComboBox = new JComboBox();
			productsComboBox.setBounds(467, 138, 89, 23);
			contentPanel.add(productsComboBox);
		}
		
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
	
	public void fillClientsComboBox(){
		
		String queryString = "select * from student";
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement queryStatement = null;
		ResultSet rs = null;
		try{
			queryStatement = dbConnection.prepareStatement(queryString);
			rs = queryStatement.executeQuery();
			while(rs.next()){
				clientsComboBox.addItem(rs.getString("name"));
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Eroare in comboBoxClients!");
		}
		
		finally{
			ConnectionFactory.close(queryStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public void fillProductsComboBox(){
		
		String queryProductsString = "select * from product";
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement queryProductsStatement = null;
		ResultSet rs = null;
		try{
			queryProductsStatement = dbConnection.prepareStatement(queryProductsString);
			rs = queryProductsStatement.executeQuery();
			while(rs.next()){
				productsComboBox.addItem(rs.getString("denumire"));
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Eroare in productsComboBox!");
		}
		finally{
			ConnectionFactory.close(queryProductsStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
