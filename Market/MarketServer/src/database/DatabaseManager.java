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
			System.out.println("Connected to database.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			connection.close();
			System.out.println("Disconnected from database.");
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
				user.put("success", "true");// TODO REMOVE THIS AND REWORK THE
											// RESPONSE CLASS
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
			String command = "INSERT INTO users" + "(username, password, email) VALUES" + "(?,?,?)";

			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, email);

			preparedStatement.executeUpdate();

			HashMap<String, Object> user = new HashMap<>();
			user.put("success", "true");// TODO REMOVE THIS AND REWORK THE
										// RESPONSE CLASS
			user.put("email", email);
			user.put("username", username);
			user.put("password", password);
			System.out.println("successfuly inserted user");


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

				HashMap<String, Object> category = new HashMap<>();
				category.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
				// RESPONSE CLASS
				category.put("name", name);
				category.put("categoryId", id);
				result.add(category);
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
			// String command = "SELECT * FROM products INNER JOIN categories ON
			// products.categoryId = categories.categoryId"
			// + "WHERE categoryId=?";
			String command = "SELECT * FROM products INNER JOIN categories ON categories.categoryId = products.categoryId WHERE categories.categoryId=?";

			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setInt(1, categoryId);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int productId = rs.getInt("productId");
				String productName = rs.getString("products.name");
				String categoryName = rs.getString("categories.name");

				HashMap<String, Object> product = new HashMap<>();
				product.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
												// RESPONSE CLASS
				product.put("productId", productId);
				product.put("productName", productName);
				product.put("categoryName", categoryName);
				result.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// -------------------------------------------------------------------------------------//

	public ArrayList<HashMap<String, Object>> getProducts() {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			String command = "SELECT * FROM products INNER JOIN categories ON categories.categoryId = products.categoryId";

			PreparedStatement preparedStatement = connection.prepareStatement(command);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				// Retrieve by column name
				int productId = rs.getInt("productId");
				int categoryId = rs.getInt("categoryId");
				String productName = rs.getString("products.name");
				String categoryName = rs.getString("categories.name");

				HashMap<String, Object> product = new HashMap<>();
				product.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE RESPONSE CLASS
				product.put("categoryId", categoryId);
				product.put("productId", productId);
				product.put("productName", productName);
				product.put("categoryName", categoryName);
				result.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	// -------------------------------------------------------------------------------------//

	public ArrayList<HashMap<String, Object>> getBuyingOffers(int categoryId, int productId) {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			String command = "SELECT * FROM buying_offers "
					+ "INNER JOIN users ON buying_offers.buyerId = users.userId "
					+ "INNER JOIN categories ON buying_offers.categoryId = categories.categoryId "
					+ "INNER JOIN products ON buying_offers.productId = products.productId ";


			if (categoryId != 0 && productId != 0) {
				command += "WHERE ( buying_offers.categoryId=? AND buying_offers.productId=? AND isActive=1 )";
			} else if (categoryId != 0) {
				command += "WHERE buying_offers.categoryId=? AND isActive=1";
			} else if (productId != 0) {
				command += "WHERE buying_offers.productId=? AND isActive=1";
			}


			PreparedStatement preparedStatement = connection.prepareStatement(command);
			System.out.println("mere");
			if (categoryId != 0 && productId != 0) {
				preparedStatement.setInt(1, categoryId);
				preparedStatement.setInt(2, productId);
			} else if (categoryId != 0)
				preparedStatement.setInt(1, categoryId);
			else if (productId != 0)
				preparedStatement.setInt(1, productId);


			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int bOfferId = rs.getInt("bOfferId");
				int quantity = rs.getInt("quantity");
				int totalPrice = rs.getInt("totalPrice");
				int unitPrice = rs.getInt("unitPrice");
				String categoryName = rs.getString("categories.name");
				String productName = rs.getString("products.name");
				String buyer = rs.getString("username");

				System.out.println(rs);
				HashMap<String, Object> product = new HashMap<>();
				product.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
												// RESPONSE CLASS
				product.put("bOfferId", bOfferId);
				product.put("quantity", quantity);
				product.put("totalPrice", totalPrice);
				product.put("unitPrice", unitPrice);
				product.put("buyer", buyer);
				product.put("categoryName", categoryName);
				product.put("productName", productName);
				result.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}




	// -------------------------------------------------------------------------------------//

	public ArrayList<HashMap<String, Object>> getSellingOffers(int categoryId, int productId) {

		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		try {
			String command = "SELECT * FROM selling_offers "
					+ "INNER JOIN users ON selling_offers.sellerId = users.userId "
					+ "INNER JOIN categories ON selling_offers.categoryId = categories.categoryId "
					+ "INNER JOIN products ON selling_offers.productId = products.productId ";

			if (categoryId != 0 && productId != 0) {
				command += "WHERE ( selling_offers.categoryId=? AND selling_offers.productId=? AND isActive=1)";
			} else if (categoryId != 0) {
				command += "WHERE selling_offers.categoryId=? AND isActive=1";
			} else if (productId != 0) {
				command += "WHERE selling_offers.productId=? AND isActive=1";
			}


			PreparedStatement preparedStatement = connection.prepareStatement(command);
			System.out.println("mere");
			if (categoryId != 0 && productId != 0) {
				preparedStatement.setInt(1, categoryId);
				preparedStatement.setInt(2, productId);
			} else if (categoryId != 0)
				preparedStatement.setInt(1, categoryId);
			else if (productId != 0)
				preparedStatement.setInt(1, productId);


			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int sOfferId = rs.getInt("sOfferId");
				int quantity = rs.getInt("quantity");
				int totalPrice = rs.getInt("totalPrice");
				int unitPrice = rs.getInt("unitPrice");
				String categoryName = rs.getString("categories.name");
				String productName = rs.getString("products.name");
				String seller = rs.getString("username");

				System.out.println(rs);
				HashMap<String, Object> product = new HashMap<>();
				product.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
												// RESPONSE CLASS
				product.put("sOfferId", sOfferId);
				product.put("quantity", quantity);
				product.put("totalPrice", totalPrice);
				product.put("unitPrice", unitPrice);
				product.put("seller", seller);
				product.put("categoryName", categoryName);
				product.put("productName", productName);
				result.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}





	public ArrayList<HashMap<String, Object>> createBuyingOffer(int quantity, int totalPrice, int unitPrice,
			int categoryId, int productId, int buyerId) {
		ArrayList<HashMap<String, Object>> result = new ArrayList<>();


		if (totalPrice == quantity * unitPrice) {
			try {
				String command = "INSERT INTO buying_offers"
						+ "(quantity, totalPrice, unitPrice, categoryId, productId, buyerId) VALUES" + "(?,?,?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(command);
				preparedStatement.setInt(1, quantity);
				preparedStatement.setInt(2, totalPrice);
				preparedStatement.setInt(3, unitPrice);
				preparedStatement.setInt(4, categoryId);
				preparedStatement.setInt(5, productId);
				preparedStatement.setInt(6, buyerId);

				preparedStatement.executeUpdate();

				HashMap<String, Object> response = new HashMap<>();
				response.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
				// RESPONSE CLASS
				response.put("quantity", quantity);
				response.put("totalPrice", totalPrice);
				response.put("unitPrice", unitPrice);
				response.put("categoryId", categoryId);
				response.put("productId", productId);
				response.put("buyerId", buyerId);
				System.out.println("successfuly inserted buying offer");


				result.add(response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			HashMap<String, Object> response = new HashMap<>();
			response.put("success", "false");// TODO REMOVE THIS AND REWORK THE
			result.add(response);
		}

		return result;
	}

	public ArrayList<HashMap<String, Object>> createSellingOffer(int quantity, int totalPrice, int unitPrice,
			int categoryId, int productId, int sellerId) {
		ArrayList<HashMap<String, Object>> result = new ArrayList<>();


		if (totalPrice == quantity * unitPrice) {
			try {
				String command = "INSERT INTO selling_offers"
						+ "(quantity, totalPrice, unitPrice, categoryId, productId, sellerId) VALUES" + "(?,?,?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(command);
				preparedStatement.setInt(1, quantity);
				preparedStatement.setInt(2, totalPrice);
				preparedStatement.setInt(3, unitPrice);
				preparedStatement.setInt(4, categoryId);
				preparedStatement.setInt(5, productId);
				preparedStatement.setInt(6, sellerId);

				preparedStatement.executeUpdate();

				HashMap<String, Object> response = new HashMap<>();
				response.put("success", "true");// TODO REMOVE THIS AND REWORK
												// THE
				// RESPONSE CLASS
				response.put("quantity", quantity);
				response.put("totalPrice", totalPrice);
				response.put("unitPrice", unitPrice);
				response.put("categoryId", categoryId);
				response.put("productId", productId);
				response.put("sellerId", sellerId);
				System.out.println("successfuly inserted selling offer");


				result.add(response);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			HashMap<String, Object> response = new HashMap<>();
			response.put("success", "false");// TODO REMOVE THIS AND REWORK THE
			result.add(response);
		}

		return result;
	}

}
