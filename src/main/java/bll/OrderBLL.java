package bll;

import java.util.NoSuchElementException;

import dao.OrderDAO;
import model.Order;

public class OrderBLL {
	
	public Order findOrderById(int id) {
		Order order = OrderDAO.findById(id);
		if (order == null) {
			throw new NoSuchElementException("The order with id =" + id + " was not found!");
		}
		return order;
	}
	
	public int insertOrder(Order order) {
		return OrderDAO.insert(order);
	}
	
	public void updateOrders(){
		OrderDAO.updateOrders();
	}
	
	public void deleteOrders(){
		OrderDAO.deleteOrders();
	}
}
