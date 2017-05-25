package models;

import java.util.ArrayList;

public class Category {

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private final String NAME;
	private final String ID;
	private ArrayList<Product> products = new ArrayList<Product>();

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Category(String id, String name) {
		ID = id;
		NAME = name;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public String getName() {
		return NAME;
	}

	//-------------------------------------------------------------------------------------//

	public void addProduct(Product product) {
		products.add(product);
	}

	//-------------------------------------------------------------------------------------//

	public ArrayList<Product> getProducts() {
		return products;
	}

	//-------------------------------------------------------------------------------------//

	@Override
	public String toString() {
		return "Category [ID = " + ID + ", NAME=" + NAME + ", products=" + products + "]";
	}

}
