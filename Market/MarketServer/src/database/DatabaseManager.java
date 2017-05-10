package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;



public class DatabaseManager {

	// -------------------------------------------------------------------------------------//
	// Class fields
	// -------------------------------------------------------------------------------------//

	private static DatabaseManager database = new DatabaseManager();

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/marketplace";
	static final String USER = "root";
	static final String PASS = "asdf";

	Connection connection = null;

	// -------------------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------------------//

	private DatabaseManager() {
	}

	public static DatabaseManager getInstance() {
		return database;
	}

	// -------------------------------------------------------------------------------------//
	// Instance methods
	// -------------------------------------------------------------------------------------//

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			connection.close();
			System.out.println("Disconnected.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}








	// -------------------------------------------------------------------------------------//
	// Functionality Methods
	// -------------------------------------------------------------------------------------//



	public ArrayList<HashMap<String, Object>> login(String username, String password) {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT userId, create_time, username, email, password FROM users "
							+ "WHERE (username = '" + username + "' AND password = '" + password + "' )");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("userId");
				Object createTime = rs.getTimestamp("create_time");
				String email = rs.getString("email");
				String usern = rs.getString("username");
				String pass = rs.getString("password");

				// Display values
				System.out.println(
						"Got from database: " + id + " " + createTime + " " + email + " " + usern + " " + pass);

				HashMap<String, Object> user = new HashMap<>();
				user.put("success", "true");//TODO REMOVE THIS AND REWORK THE RESPONSE CLASS
				user.put("id", id);
				user.put("createTime", createTime);
				user.put("email", email);
				user.put("username", usern);
				user.put("password", pass);

				result.add(user);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// -------------------------------------------------------------------------------------//


	public ArrayList<HashMap<String, Object>> register(String username, String password, String email) {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			/*PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO users ( username, password, email ) "
							+ "VALUES ( '" + username + "' , '" + password + "' , '" + email + "' )");*/

			String command = "INSERT INTO users" + "(username, password, email) VALUES" + "(?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, email);

			preparedStatement.executeUpdate();


			HashMap<String, Object> user = new HashMap<>();
			user.put("success", "true");//TODO REMOVE THIS AND REWORK THE RESPONSE CLASS
			user.put("email", email);
			user.put("username", username);
			user.put("password", password);
			System.out.println("successfuly inserted user");

			//			// Display values
			//			System.out.println(
			//					"Got from database: " + id + " " + createTime + " " + email + " " + usern + " " + pass);
			//
			//			HashMap<String, Object> user = new HashMap<>();
			//			user.put("success", "true");//TODO REMOVE THIS AND REWORK THE RESPONSE CLASS
			//			user.put("id", id);
			//			user.put("createTime", createTime);
			//			user.put("email", email);
			//			user.put("username", usern);
			//			user.put("password", pass)

			result.add(user);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// -------------------------------------------------------------------------------------//


	public ArrayList<HashMap<String, Object>> getCategories() {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			String command = "SELECT * FROM categories";

			PreparedStatement preparedStatement = connection.prepareStatement(command);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("categoryId");
				String name = rs.getString("name");


				HashMap<String, Object> user = new HashMap<>();
				user.put("success", "true");//TODO REMOVE THIS AND REWORK THE RESPONSE CLASS
				user.put("name", name);
				user.put("categoryId", id);
				result.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// -------------------------------------------------------------------------------------//


	public ArrayList<HashMap<String, Object>> getProductsByCategory(int categoryId) {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			//			String command = "SELECT * FROM products INNER JOIN categories ON products.categoryId = categories.categoryId"
			//					+ "WHERE categoryId=?";
			String command = "SELECT * FROM products INNER JOIN categories ON categories.categoryId = products.categoryId WHERE categories.categoryId=?";

			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setInt(1, categoryId);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int productId = rs.getInt("productId");
				String productName = rs.getString("products.name");
				String categoryName = rs.getString("categories.name");


				HashMap<String, Object> user = new HashMap<>();
				user.put("success", "true");//TODO REMOVE THIS AND REWORK THE RESPONSE CLASS
				user.put("productId", productId);
				user.put("productName", productName);
				user.put("categoryName", categoryName);
				result.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


}
