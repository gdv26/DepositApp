package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Product;

public class ProductDAO {
	
	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO product (denumire,pret,cantitate)" + " VALUES (?,?,?)";
	private final static String findStatementString = "SELECT * FROM product where id = ?";
	private final static String updateStatementString = "update product set pret = 85 where id = 1";
	private final static String deleteStatementString = "delete from product where id = 50";

	public static Product findById(int productId) {
		
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, productId);
			rs = findStatement.executeQuery();
			rs.next();

			String denumire = rs.getString("denumire");
			int pret = rs.getInt("pret");
			int cantitate = rs.getInt("cantitate");
			toReturn = new Product(productId, denumire, pret, cantitate);
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"ProductDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	public static int insert(Product product) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, product.getDenumire());
			insertStatement.setInt(2, product.getPret());
			insertStatement.setInt(3, product.getCantitate());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
			
		} 
		catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} 
		
		finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	public static void updateProduct(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		
		try{
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.executeUpdate();
			System.out.println("Update products complete");
		}
		catch(Exception e){
			LOGGER.log(Level.WARNING, "ProductDAO:update", e.getMessage());
		}
		finally{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public static void deleteProduct(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		
		try{
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.executeUpdate();
			System.out.println("Delete product complete");
		}
		catch(Exception e){
			LOGGER.log(Level.WARNING, "ProductDAO:delete", e.getMessage());
		}
		finally{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
