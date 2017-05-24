package models;

public class Product {

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private final String productId;
	private final String productName;
	private final String categoryId;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Product( String productId,String productName, String categoryId) {

		this.productId=productId;
		this.productName = productName;
		this.categoryId = categoryId;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//
	
	public String getId() {
		return productId;
	}
	
	//-------------------------------------------------------------------------------------//

	public String getName() {
		return productName;
	}

	//-------------------------------------------------------------------------------------//

	public String getCategoryId() {
		return categoryId;
	}
	
	//-------------------------------------------------------------------------------------//


	@Override
	public String toString() {
		return "Product [id=" + productId + ", name=" + productName + ", categoryId=" + categoryId + "]";
	}

	
}
