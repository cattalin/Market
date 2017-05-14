package models;

public class Product {

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private final String NAME;
	private final String CATEGORY_NAME;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Product(String name, String categoryName) {

		NAME = name;
		CATEGORY_NAME = categoryName;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public String getName() {
		return NAME;
	}

	//-------------------------------------------------------------------------------------//

	public String getCategoryName() {
		return CATEGORY_NAME;
	}

	//-------------------------------------------------------------------------------------//

	@Override
	public String toString() {
		return "Product [NAME=" + NAME + ", CATEGORY_NAME=" + CATEGORY_NAME + "]";
	}

}
