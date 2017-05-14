package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import models.Category;
import models.Product;
import networking.RequestManager;
import networking.Response;

class Utils {

	public static HashMap<String, Category> generateCategoryList(RequestManager requestManager) {

		Response response = requestManager.sendGetCategoriesRequest();
		HashMap<String, Category> categories = new HashMap<String, Category>();

		if (response.getResCode() == Response.GET_CATEGORIES) {

			for (HashMap<String, Object> currentCategory : response.getParameters()) {
				Category category = new Category(currentCategory.get("categoryId").toString(), currentCategory.get("name").toString());
				categories.put(currentCategory.get("categoryId").toString(), category);
			}

			return categories;
		}

		System.out.println("error!!");
		return null;
	}

	//-------------------------------------------------------------------------------------//

	public static void generateProductList(RequestManager requestManager, HashMap<String, Category> categories) {

		Response response = requestManager.sendGetCategoriesRequest();

		if (response.getResCode() == Response.GET_PRODUCTS) {

			for (HashMap<String, Object> currentProduct : response.getParameters()) {

				Product product = new Product(currentProduct.get("productId").toString(), //
						currentProduct.get("name").toString(), //
						currentProduct.get("categoryId").toString());

				Category category = categories.get(currentProduct.get("categoryId").toString());
				category.addProduct(product);

			}

		}

		System.out.println("error!!");
		return;
	}

	//-------------------------------------------------------------------------------------//

	public static JPasswordField generatePasswordField(String text, int b1, int b2, int b3, int b4, int columns) {

		JPasswordField textField = new JPasswordField();

		textField.setText(text);
		textField.setColumns(columns);
		textField.setBounds(b1, b2, b3, b4);
		textField.setBackground(new Color(220, 220, 220));
		textField.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});

		return textField;

	}

	//-------------------------------------------------------------------------------------//

	public static JTextField generateTextField(String text, int b1, int b2, int b3, int b4, int columns) {

		JTextField textField = new JTextField();

		textField.setText(text);
		textField.setColumns(columns);
		textField.setBounds(b1, b2, b3, b4);
		textField.setBackground(new Color(220, 220, 220));
		textField.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});

		return textField;

	}

	//-------------------------------------------------------------------------------------//

	public static String[] parseAllCategories(HashMap<String, Category> categories) {

		String[] categoriesArray = new String[categories.size()];
		int i = 0;
		for (Category current : categories.values()) {
			categoriesArray[i] = current.getName();
			i++;
		}

		return categoriesArray;
	}

	//-------------------------------------------------------------------------------------//

	public static String[] parseAllProducts(HashMap<String, Category> categories) {

		String[] productsArray = new String[100];

		int i = 0;
		for (Category current : categories.values()) {
			for (Product product : current.getProducts()) {
				productsArray[i] = product.getName();
				i++;
			}
		}

		return productsArray;
	}

	//		categoriesComboBox = new JComboBox<Object>(categoriesArray);
	//		categoriesComboBox.setBounds(b1, b2, b3, b4);
	//		categoriesComboBox.setSelectedIndex(0);
	//		panel.add(categoriesComboBox);

}
