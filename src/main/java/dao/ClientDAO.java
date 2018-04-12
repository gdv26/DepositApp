package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO student (name,address,email,age)"
			+ " VALUES (?,?,?,?)";
	private final static String findStatementString = "SELECT * FROM product where id = ?";
	private final static String updateStatementString = "update student set name = ? address = ? email = ? age = ? where id = ?";
	private final static String deleteStatementString = "delete from student where id = 60";
	private final static String displayClientsString = "select * from student";

	public static Client findById(int studentId) {
		
		Client toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try {
			
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setLong(1, studentId);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			String email = rs.getString("email");
			int age = rs.getInt("age");
			toReturn = new Client(studentId, name, address, email, age);
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	public static int insert(Client student) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, student.getName());
			insertStatement.setString(2, student.getAddress());
			insertStatement.setString(3, student.getEmail());
			insertStatement.setInt(4, student.getAge());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "StudentDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	public static void updateClient(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		
		try{
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.executeUpdate();
			System.out.println("Update in clients complete");
		}
		catch(SQLException e){
			LOGGER.log(Level.WARNING, "StudentDAO:update", e.getMessage());
		}
		finally{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public static void deleteClient(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		
		try{
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.executeUpdate();
			System.out.println("Delete from student complete");
		}
		catch(SQLException e){
			LOGGER.log(Level.WARNING, "StudentDAO:delete", e.getMessage());
		}
		finally{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
	
	public static void displayClients(){
		
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement displayStatement = null;
		
		try{
			displayStatement = dbConnection.prepareStatement(displayClientsString);
			displayStatement.executeQuery();
		}
		catch(SQLException e){
			LOGGER.log(Level.WARNING, "StudentDAO:display", e.getMessage());
		}
		finally{
			ConnectionFactory.close(displayStatement);
			ConnectionFactory.close(dbConnection);
		}
	}
}
