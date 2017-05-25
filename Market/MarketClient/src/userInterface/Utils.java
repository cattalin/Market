package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import models.Category;
import networking.RequestManager;
import networking.Response;

class Utils {

	public static void generateCategoryList(RequestManager requestManager, JComboBox<Object> categoriesComboBox, JPanel panel, int b1, int b2, int b3, int b4) {

		HashMap<String, Object> parameters = new HashMap<>();
		Response response = requestManager.sendGetCategoriesRequest(parameters);

		if (response.getResCode() == Response.GET_CATEGORIES) {
			ArrayList<Category> categories = new ArrayList<Category>();

			for (HashMap<String, Object> currentCategory : response.getParameters()) {

				String categoryID = currentCategory.get("categoryId").toString();
				String categoryName = currentCategory.get("name").toString();
				Category category = new Category(categoryID, categoryName);
				categories.add(category);
			}

			String[] categoriesArray = new String[categories.size()];
			for (int j = 0; j < categories.size(); j++) {
				categoriesArray[j] = categories.get(j).getName();
			}

			categoriesComboBox = new JComboBox<Object>(categoriesArray);
			categoriesComboBox.setBounds(b1, b2, b3, b4);
			categoriesComboBox.setSelectedIndex(0);
			panel.add(categoriesComboBox);

		} else {
			System.out.println("error!!");
			return;
		}
	}

	//-------------------------------------------------------------------------------------//

	public static void generateProductList(JPanel panel, int b1, int b2, int b3, int b4) {

		//TODO: implement

		String[] products = { "Products", "Durex", "Love", "pentru sex" };
		JComboBox<Object> productsComboBox = new JComboBox<Object>(products);
		productsComboBox.setBounds(b1, b2, b3, b4);
		productsComboBox.setSelectedIndex(0);
		panel.add(productsComboBox);
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
				if (textField.getText().equals(text))
					textField.setText("");
			}
		});

		return textField;

	}

}
