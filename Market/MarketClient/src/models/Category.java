package models;

import java.util.ArrayList;

public class Category {

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private final String name;
	private final String id;
	private ArrayList<Product> products = new ArrayList<Product>();

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//
	
	public String getId() {
		return id;
	}
	
	//-------------------------------------------------------------------------------------//

	public String getName() {
		return name;
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
		return "Category [id = " + id + ", name=" + name + ", products=" + products + "]";
	}

}
