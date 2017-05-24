package userInterface;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuyingOffers {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final BuyingOffers buyingOffers = new BuyingOffers();

	private JPanel buyingOffersPanel;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private BuyingOffers() {
	}

	public static BuyingOffers getInstance() {
		return buyingOffers;
	}

	//-------------------------------------------------------------------------------------//

	public void initialize(String categoryName, String productName) {

		Board board = Board.getInstance();
		String catName = categoryName.equals("Select...") ? "" : categoryName + "   >   ";
		String prodName = productName.equals("Select...") ? "all" : productName;

		buyingOffersPanel = new JPanel(null);
		buyingOffersPanel.setBackground(new Color(242, 242, 242));
		buyingOffersPanel.setSize(700, 700);
		board.getBoardPanel().add(buyingOffersPanel, "buyingOffersPanel");

		JLabel category = new JLabel(catName + prodName);
		category.setBounds(20, 5, 700, 70);
		category.setFont(new Font("Verdana", Font.BOLD, 20));
		category.setForeground(new Color(255, 69, 0));
		buyingOffersPanel.add(category);

	}

	public void showOffers() {

	}

}
