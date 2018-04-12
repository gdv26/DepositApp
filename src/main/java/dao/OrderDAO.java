package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Order;

public class OrderDAO {

	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO orders (numeClient, denumireProdus, cantitate)" + " VALUES (?,?,?)";
	private final static String findStatementString = "SELECT * FROM orders where id = ?";
	private final static String updateStatementString = "update orders set numeClient = 'Daniel' where id = 2";
	private final static String deleteStatementString = "delete from orders where id = 14";

	public static Order findById(int orderId) {
		
		Order toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, orderId);
			rs = findStatement.executeQuery();
			rs.next();
			
			String numeClient = rs.getString("numeClient");
			String denumireProdus = rs.getString("denumireProdus");
			int cantitate = rs.getInt("cantitate");
			toReturn = new Order(orderId, numeClient, denumireProdus, cantitate);
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"OrderDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	public static int insert(Order order) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, order.getNumeClient());
			insertStatement.setString(2, order.getDenumireProdus());
			insertStatement.setInt(3, order.getCantitate());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
			
		} 
		catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
		} 
		
		finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	public static void updateOrders(){
	
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		
		try{
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.executeUpdate();
			System.out.println("Update orders complete");
		}
		catch(SQLException e){
			LOGGER.log(Level.WARNING, "OrderDAO:update", e.getMessage());
		}
		finally{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public static void deleteOrders(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		
		try{
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.executeUpdate();
			System.out.println("Delete from orders complete");
		}
		catch(SQLException e){
			LOGGER.log(Level.WARNING, "OrderDAO:delete", e.getMessage());
		}
		finally{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
