package dataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for implementing generic data access actions to the database:
 * Create, Retrieve, Update, Delete
 * @author Timi
 *
 * @param <T> generic type of object on which the action takes place
 */
public class AbstractDA<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDA.class.getName());
	
	private final Class<T> type;
	
	public AbstractDA(Class<T> entityClass)
	{
	      this.type = entityClass;
	}
	
	/**
	 * Create SQL queries
	 */
	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	protected String createInsertQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
	    sb.append(type.getSimpleName());
	    sb.append("(");
		for (Field field : type.getDeclaredFields()) {
			sb.append(field.getName());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
	    sb.append(") ");
		sb.append(" VALUES");
		sb.append(" (");
		for (@SuppressWarnings("unused") Field field : type.getDeclaredFields()) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	private String createUpdateQuery(String fieldName){
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		for (Field field : type.getDeclaredFields()) {
			sb.append(field.getName());
			sb.append("=?, ");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append("WHERE ");
		sb.append(fieldName);
		sb.append(" =?");
		return sb.toString();
	}
	
	private String createDeleteQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}
	
	/**
	 * Retrieve all data
	 * @return list with the objects
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		List<T> list = new ArrayList<T>();
		String selectAllQuery = "SELECT * FROM "+ type.getSimpleName() + ";";
		
		try{
			connection = Database.getConnection();
			statement = connection.prepareStatement(selectAllQuery);
			rs = statement.executeQuery();
			list = createObjects(rs);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "AbstractDA:findAll " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Retrieve id specific data
	 * @param id specified id
	 * @return object modeled upon the data
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = Database.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "AbstractDA:findById " + e.getMessage());
		} finally {
			Database.close(resultSet);
			Database.close(statement);
			Database.close(connection);
		}
		return null;
	}

	/**
	 * Create a list of objects based upon the retrieved data
	 * @param resultSet
	 * @return
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Create
	 * @param t object to be inserted
	 * @return inserted id in case of success, -1 in case of error
	 */
	public int insert(T t) {
		Connection connection = Database.getConnection();

		String query = createInsertQuery();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			for (Field field : type.getDeclaredFields()) {
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
				Method method = propertyDescriptor.getReadMethod();
				
				if (field.getType().isAssignableFrom(Integer.TYPE)) 
		         {
		        	 insertStatement.setInt(i, Integer.parseInt(method.invoke(t).toString()));
		         }
		         else if (field.getType().isAssignableFrom(Double.TYPE)) 
		         {
		        	 insertStatement.setDouble(i, Double.parseDouble(method.invoke(t).toString()));
			     }
		         else if (field.getType().isAssignableFrom(String.class)) 
		         {
		        	 insertStatement.setString(i, method.invoke(t).toString());
			     }
		         else if (field.getType().isAssignableFrom(Date.class)) 
		         {
		        	 insertStatement.setDate(i, (java.sql.Date) method.invoke(t));
			     }
		         else if (field.getType().isAssignableFrom(Boolean.TYPE)) 
		         {
		        	 insertStatement.setBoolean(i, Boolean.parseBoolean(method.invoke(t).toString()));
			     }
				
				insertStatement.setString(i, method.invoke(t).toString());
				i++;
			}
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:insert " + e.getMessage());
			e.printStackTrace();
		} catch (IntrospectionException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:insert " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:insert " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:insert " + e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:insert " + e.getMessage());
			e.printStackTrace();
		} finally {
			Database.close(insertStatement);
			Database.close(connection);
		}
		return insertedId;
	}

	/**
	 * Delete (id specific)
	 * @param id object id to be deleted
	 * @return -1 in case of error, positive number in case of success
	 */
	public int deleteById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery("id");
		try {
			connection = Database.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			return statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "AbstractDA:deleteById " + e.getMessage());
		} finally {
			Database.close(resultSet);
			Database.close(statement);
			Database.close(connection);
		}		
		return -1;
	}
	
	/**
	 * Update (id specific)
	 * @param id id of object to be updated
	 * @param t object with updated information
	 * @return 1 in case of success, -1 in case of error
	 */
	public int updateById(int id, T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createUpdateQuery("id");
		try{
			connection = Database.getConnection();
			statement = connection.prepareStatement(query);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			for (Field field : type.getDeclaredFields()) {	
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
				Method method = propertyDescriptor.getReadMethod();
	
				if (field.getType().isAssignableFrom(Integer.TYPE)) 
		         {
		        	 statement.setInt(i, Integer.parseInt(method.invoke(t).toString()));
		         }
		         else if (field.getType().isAssignableFrom(Double.TYPE)) 
		         {
		        	 statement.setDouble(i, Double.parseDouble(method.invoke(t).toString()));
			     }
		         else if (field.getType().isAssignableFrom(String.class)) 
		         {
		        	 statement.setString(i, method.invoke(t).toString());
			     }
		         else if (field.getType().isAssignableFrom(Date.class)) 
		         {
		        	 statement.setDate(i, (java.sql.Date) method.invoke(t));
			     }
		         else if (field.getType().isAssignableFrom(Boolean.TYPE)) 
		         {
		        	 statement.setBoolean(i, Boolean.parseBoolean(method.invoke(t).toString()));
			     }
				
				statement.setString(i, method.invoke(t).toString());
				i++;
			}
			statement.setInt(i, id);
			statement.executeUpdate();
			return 1;
		}catch (SQLException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (IntrospectionException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			LOGGER.log(Level.WARNING, "AbstractDA:update " + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
}
