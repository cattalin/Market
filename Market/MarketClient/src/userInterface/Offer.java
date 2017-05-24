package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import networking.RequestManager;
import networking.Response;

public class Offer {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private RequestManager requestManager;

	private String offerId;
	private String userName;
	private String categoryName;
	private String productName;
	private String quantity;
	private String unitPrice;
	private String action;

	private JPanel offerPanel;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Offer() {
		requestManager = RequestManager.getInstance();
	}

	//-------------------------------------------------------------------------------------//

	public void initialize() {

		offerPanel = new JPanel(null);
		offerPanel.setBackground(new Color(51, 61, 79));
		offerPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

		JLabel sellerName = new JLabel("by " + userName + " in " + categoryName + " > " + productName);
		sellerName.setBounds(20, 10, 500, 20);
		sellerName.setFont(new Font("Times New Roman", Font.BOLD, 13));
		sellerName.setForeground(Color.WHITE);
		offerPanel.add(sellerName);

		JLabel quantityLabel = new JLabel("Quantity: " + quantity);
		quantityLabel.setBounds(20, 35, 150, 15);
		quantityLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		quantityLabel.setForeground(new Color(230, 138, 0));
		offerPanel.add(quantityLabel);

		JLabel itemPriceLabel = new JLabel("Unit Price: " + unitPrice);
		itemPriceLabel.setBounds(20, 55, 150, 15);
		itemPriceLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		itemPriceLabel.setForeground(new Color(230, 138, 0));
		offerPanel.add(itemPriceLabel);

		JLabel totalPriceLabel = new JLabel("Total Price: " + Integer.parseInt(quantity) * Integer.parseInt(unitPrice));
		totalPriceLabel.setBounds(20, 75, 150, 15);
		totalPriceLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		totalPriceLabel.setForeground(new Color(230, 138, 0));
		offerPanel.add(totalPriceLabel);

		JButton buyButton = new JButton(action);
		buyButton.setBounds(470, 35, 100, 30);
		buyButton.setBackground(new Color(0, 128, 0));
		buyButton.setForeground(Color.WHITE);
		offerPanel.add(buyButton);
		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				HashMap<String, Object> parameters = new HashMap<>();
				parameters.put("", "");

				Response response = requestManager.sendAcceptOfferRequest(parameters);

			}
		});

	}

	//-------------------------------------------------------------------------------------//

	public JPanel getOfferPanel() {
		return offerPanel;
	}
	//-------------------------------------------------------------------------------------//

	public Offer setOfferId(String offerId) {
		this.offerId = offerId;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setQuantity(String quantity) {
		this.quantity = quantity;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	//-------------------------------------------------------------------------------------//

	public Offer setAction(String action) {
		this.action = action;
		return this;
	}

}
