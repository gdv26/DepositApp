package presentation;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import bll.OrderBLL;
import bll.ProductBLL;
import bll.ClientBLL;
import model.Order;
import model.Product;
import model.Client;
import start.ReflectionExample;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frmWarehouseMa;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmWarehouseMa.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public GUI() {
		initialize();
	}

	private void initialize() {
		
		frmWarehouseMa = new JFrame();
		frmWarehouseMa.setTitle("Order Management Application");
		frmWarehouseMa.setBounds(100, 100, 450, 300);
		frmWarehouseMa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWarehouseMa.getContentPane().setLayout(null);
		
		JButton btnClienti = new JButton("Clienti");
		btnClienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	
				try {
					ClientsDialog dialog = new ClientsDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnClienti.setBounds(117, 21, 190, 56);
		frmWarehouseMa.getContentPane().add(btnClienti);
		
		JButton btnProduse = new JButton("Produse");
		btnProduse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					ProductsDialog dialog = new ProductsDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnProduse.setBounds(117, 87, 190, 56);
		frmWarehouseMa.getContentPane().add(btnProduse);
		
		JButton btnComenzi = new JButton("Comenzi");
		btnComenzi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					OrdersDialog dialog = new OrdersDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnComenzi.setBounds(117, 154, 190, 56);
		frmWarehouseMa.getContentPane().add(btnComenzi);
	}
}
