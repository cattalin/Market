package userInterface;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class S_offer {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private String sellerName;
	private String categoryName;
	private String productName;
	private String quantity;
	private String unitPrice;

	private JPanel offerPanel;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public S_offer() {

		offerPanel = new JPanel(null);
		offerPanel.setBackground(new Color(51, 61, 79));
		Border blackline = BorderFactory.createLineBorder(Color.white);

		TitledBorder title;
		title = BorderFactory.createTitledBorder(blackline, "IT   >   " + "Laptop"); //TODO:
		title.setTitleFont(new Font("Verdana", Font.BOLD, 13));
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleColor((Color.WHITE));
		offerPanel.setBorder(title);

		//		JLabel title = new JLabel("IT   >   " + "Laptop"); //TODO:
		//		title.setBounds(10, 15, 400, 15);
		//		title.setFont(new Font("Verdana", Font.BOLD, 15));
		//		title.setForeground(Color.BLACK);
		//		offerPanel.add(title);

		JLabel sellerName = new JLabel("by " + "username"); //TODO:
		sellerName.setBounds(10, 40, 100, 15);
		sellerName.setFont(new Font("Verdana", Font.BOLD, 13));
		sellerName.setForeground(Color.WHITE);
		offerPanel.add(sellerName);

	}

	//-------------------------------------------------------------------------------------//

	public JPanel getOfferPanel() {
		return offerPanel;
	}

	//-------------------------------------------------------------------------------------//

	public String getSellerName() {
		return sellerName;
	}

	//-------------------------------------------------------------------------------------//

	public S_offer setSellerName(String sellerName) {
		this.sellerName = sellerName;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public String getCategoryName() {
		return categoryName;
	}

	//-------------------------------------------------------------------------------------//

	public S_offer setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public String getProductName() {
		return productName;
	}

	//-------------------------------------------------------------------------------------//

	public S_offer setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getQuantity() {
		return quantity;
	}

	//-------------------------------------------------------------------------------------//

	public S_offer setQuantity(String quantity) {
		this.quantity = quantity;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public String getUnitPrice() {
		return unitPrice;
	}

	//-------------------------------------------------------------------------------------//

	public S_offer setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

}
