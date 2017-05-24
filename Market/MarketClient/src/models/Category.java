package models;

import java.util.HashMap;
import java.util.Map;

public class Category {

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private final String name;
	private final String id;
	private Map<String, Product> products = new HashMap<String, Product>();

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
		products.put(product.getName(), product);
	}

	//-------------------------------------------------------------------------------------//

	public Map<String, Product> getProducts() {
		return products;
	}

	//-------------------------------------------------------------------------------------//

	@Override
	public String toString() {
		return "Category [id = " + id + ", name=" + name + ", products=" + products + "]";
	}

}
