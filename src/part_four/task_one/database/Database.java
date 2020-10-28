package part_four.task_one.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import part_four.task_one.interfaces.PropertiesGetter;

/**
 * Class connected to database. Use for execute SQL query.
 * 
 * @author Denis Samuilik
 */

public class Database implements PropertiesGetter {

	/**
	 * <P>
	 * default table name.
	 * </P>
	 */
	protected final String tableName = "students";

	/**
	 * <P>
	 * string query for check exist default table.
	 * </P>
	 */
	private final String checkTableQuery = "SHOW TABLES LIKE '%s'";

	/**
	 * <P>
	 * string query for create default database.
	 * </P>
	 * 
	 * @see Database#setDefaultQuery
	 **/
	private final String createTableQuery;

	/**
	 * <P>
	 * A connection (session) with a specific database. SQL statements are executed
	 * and results are returned within the context of a connection.
	 * </P>
	 * 
	 * @see Database#getConnection()
	 */
	protected Connection connection;

	/**
	 *
	 * <P>
	 * The object used for executing a static SQL statement and returning the
	 * results it produces.
	 * </P>
	 */
	private Statement state;

	/**
	 * <P>
	 * String array with default users in default table.
	 * </P>
	 * 
	 * @see Database#createTableQuery
	 */
	private static String[] defaultStudends = { "1,\"Иванов Андрей\",255,4,6,7,5", "2,\"Малинина Ожешка\",256,5,7,8,6",
			"3,\"Сидоров Павел\",240,6,6,7,5", "4,\"Кучинина Полина\",254,4,5,8,7", "5,\"Андреевич Ольга\",234,5,6,8,7",
			"6,\"Печорский Василий\",224,7,9,3,8", };

	/**
	 * <p>
	 * Default column names.
	 * </p>
	 * 
	 * @see Database#setDefaultQuery()
	 */
	private final String[] columnNames = new String[] { "Id", "Name", "Group", "MarkMath", "MarkLiterature",
			"MarkEnglish", "MarkProgramming" };

	/**
	 * <p>
	 * Default columns params.
	 * </p>
	 * 
	 * @see Database#setDefaultQuery()
	 */
	private final String[] columnParams = new String[] { "INT PRIMARY KEY AUTO_INCREMENT", "VARCHAR(200)", "VARCHAR(30)",
			"INT(1)", "INT(1)", "INT(1)", "INT(1)" };

	/**
	 * <p>
	 * Check int fields value in table.
	 * </p>
	 * 
	 * @see Database#setDefaultQuery()
	 */
	private final String[] checkValue = new String[] { null, null, null, " CHECK(`#COLUMN#` >= 0 AND `#COLUMN#` <= 10)",
			" CHECK(`#COLUMN#` >= 0 AND `#COLUMN#` <= 10)", " CHECK(`#COLUMN#` >= 0 AND `#COLUMN#` <= 10)",
			" CHECK(`#COLUMN#` >= 0 AND `#COLUMN#` <= 10)", };

	/**
	 * Public contsructor for create instance.
	 * 
	 * @exception SQLException if a database access error occurs
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 *                     This class is the general class of exceptions produced by
	 *                     failed or interrupted I/O operations.
	 */
	public Database() throws SQLException, IOException {
		createTableQuery = setDefaultQuery();
		getConnection();
	}

	/**
	 * Build query for create default table
	 * 
	 * @return query string
	 */
	private String setDefaultQuery() {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("CREATE TABLE " + tableName + " (");
		for (int i = 0; i < columnNames.length; i++) {
			String checked = "";
			if (checkValue[i] != null) {
				checked = checkValue[i].replaceAll("#COLUMN#", columnNames[i]);
			}
			queryBuilder.append(
					"`" + columnNames[i] + "` " + columnParams[i] + checked + (i + 1 != columnNames.length ? "," : ""));
		}
		queryBuilder.append(")");
		return queryBuilder.toString();
	}

	/**
	 * Create connection @see {@link Database#connection}
	 * 
	 * @throws SQLException {@inheritDoc}
	 * @throws IOException  {@inheritDoc}
	 */
	private void getConnection() throws SQLException, IOException {
		try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection succesfull!");
        }
        catch(Exception ex){
            System.out.println("Connection failed...");             
            System.out.println(ex);
        }
		Properties props = new Properties();
		getProperties("database.properties", props);

		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		try {
			connection = DriverManager.getConnection(url, username, password);
			state = connection.createStatement();
			System.out.println(state);
			checkAndCreateDefaultTable();
		} catch (Exception e) {
			if (connection != null) {
				connection.close();
			}
			e.printStackTrace();
		}

	}

	/**
	 * Close connection @see {@link Database#connection}
	 * 
	 * @throws SQLException {@inheritDoc}
	 */
	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * Check default table if not exist create
	 * 
	 * @throws SQLException {@inheritDoc}
	 **/
	private void checkAndCreateDefaultTable() throws SQLException {
		ResultSet result = state.executeQuery(String.format(checkTableQuery, tableName));
		if (!result.next()) {
			state.executeUpdate(createTableQuery);
			addDefaulUserToTable();
		}
	}

	/**
	 * Add users into empty default table {@link Database#defaultStudends}
	 * 
	 * @throws SQLException {@inheritDoc}
	 */
	private void addDefaulUserToTable() throws SQLException {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("INSERT INTO " + tableName + "(`" + String.join("`,`", columnNames) + "`) ");
		queryBuilder.append("VALUES ");
		for (int i = 0; i < defaultStudends.length; i++) {
			String stud = defaultStudends[i];
			queryBuilder.append("(" + stud + ")" + ((i + 1) != defaultStudends.length ? "," : ""));
		}
		state.executeUpdate(queryBuilder.toString());
	}

	/**
	 * Convert ResultSet to List for insert into JTable
	 * 
	 * @param dbResult - result executeQuery from DB @see
	 *                 {@link Statement#executeQuery(String)
	 *                 Statement.executeQuery(String)}
	 * @throws SQLException {@inheritDoc}
	 */
	private List<String[]> convertDbResult(ResultSet dbResult) throws SQLException {
		List<String[]> result = new ArrayList<>();
		ResultSetMetaData dbResultMeta = dbResult.getMetaData();
		while (dbResult.next()) {
			List<String> row = new ArrayList<>();
			for (int i = 1; i <= dbResultMeta.getColumnCount(); i++) {
				row.add(dbResult.getObject(i).toString());
			}
			if (row.size() > 0) {
				String[] tmpRow = new String[row.size()];
				row.toArray(tmpRow);
				result.add(tmpRow);
			}
		}
		return result;
	}

	/**
	 * Select all from table
	 * 
	 * @return List converted result
	 * @throws SQLException {@inheritDoc}
	 */
	public List<String[]> showAll() throws SQLException {
		ResultSet dbResult = state.executeQuery("SELECT * FROM " + tableName);
		return convertDbResult(dbResult);
	}

	/**
	 * Get columnNames used for draw JTable
	 * 
	 * @return columnNames
	 */
	public String[] getColumnNames() {
		return Arrays.copyOf(columnNames, columnNames.length);
	}

	/**
	 * Get columnParams used for draw JTable
	 * 
	 * @return columnParams
	 */
	public String[] getColumnParams() {
		return Arrays.copyOf(columnParams, columnParams.length);
	}

	/**
	 * Add new values into table
	 * 
	 * @param values string array from inputs values
	 * @throws SQLException {@inheritDoc}
	 */
	public void addValues(String[] values) throws SQLException {
		String[] colNamesWoId = new String[columnNames.length - 1];
		String tmpVal = "";

		System.arraycopy(columnNames, 1, colNamesWoId, 0, columnNames.length - 1);
		for (int i = 0; i < colNamesWoId.length; i++) {
			tmpVal += "?" + (i + 1 != colNamesWoId.length ? ", " : "");
		}
		String sql = "INSERT INTO " + tableName + " (`" + String.join("`,`", colNamesWoId) + "`) Values (" + tmpVal
				+ ")";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		for (int i = 0; i < values.length; i++) {
			preparedStatement.setString(i + 1, values[i]);
		}

		int rows = preparedStatement.executeUpdate();
	}

	/**
	 * Deleted selected rows by 'Id' column values, use into main frame.
	 * 
	 * @param values string array from id value
	 * 
	 * @throws SQLException {@inheritDoc}
	 */
	public void deleteSelectedRowQuery(String[] values) throws SQLException {
		String[] tmpVal = new String[values.length];
		for (int i = 0; i < tmpVal.length; i++) {
			tmpVal[i] = "`Id`= ?";
		}
		String sql = "DELETE FROM " + tableName + " WHERE " + String.join(" or ", tmpVal);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		for (int i = 0; i < values.length; i++) {
			preparedStatement.setString(i + 1, values[i]);
		}
		int rows = preparedStatement.executeUpdate();
	}

	/**
	 * Delete row from table by conditions, user into popup frame
	 * 
	 * @param condition string sql condition use with 'WHERE'
	 * @param values    string array with string value for delete
	 * 
	 * @throws SQLException {@inheritDoc}
	 */
	public void deleteByCondition(String condition, String[] values) throws SQLException {

		String sql = String.format("DELETE FROM %s WHERE %s ?", tableName, condition);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setString(i + 1, values[i]);
		}
		preparedStatement.executeUpdate();

	}

	/**
	 * Select row by sql condition
	 * 
	 * @param condition    string sql condition use with 'WHERE'
	 * @param values       string array with values for select
	 * @param sort         string use for order by
	 * @param selectColumn if need select specific column
	 * @return converted result for build table
	 * @throws SQLException {@inheritDoc}
	 */
	public List<String[]> selectByCondition(String condition, String[] values, String sort, String... selectColumn)
			throws SQLException {
		String columns = "*";
		if (selectColumn.length > 0) {
			columns = String.join(",", selectColumn);
		}

		String sql = String.format("SELECT %s FROM %s WHERE %s ? %s", columns, tableName, condition, sort);
		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		for (int i = 0; i < values.length; i++) {
			preparedStatement.setString(i + 1, values[i]);
		}
		ResultSet resultDb = preparedStatement.executeQuery();
		return convertDbResult(resultDb);

	}
}
