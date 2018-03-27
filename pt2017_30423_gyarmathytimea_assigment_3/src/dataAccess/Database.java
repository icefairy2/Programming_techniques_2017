package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection class to database
 * @author Timi
 *
 */
public class Database {
	private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse?autoReconnect=true&useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "";

	private static Database instance = new Database();

	private Database() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getConnection() {
		return instance.connect();
	}

	public static void close(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
			}
	}

	public static void close(Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occured while trying to close the resultSet");
			}
		}
	}
}
