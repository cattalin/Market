package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SellingOffers {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final SellingOffers sellingOffers = new SellingOffers();

	private JPanel sellingOffersPanel;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private SellingOffers() {
	}

	public static SellingOffers getInstance() {
		return sellingOffers;
	}

	//-------------------------------------------------------------------------------------//

	public void initialize(String categoryName, String productName) {

		Board board = Board.getInstance();
		String catName = categoryName.equals("Select...") ? "" : categoryName + "   >   ";
		String prodName = productName.equals("Select...") ? "all" : productName;

		sellingOffersPanel = new JPanel(null);
		sellingOffersPanel.setPreferredSize((new Dimension(700, 10000)));

		JScrollPane sellingOffersScrollP = new JScrollPane(sellingOffersPanel);
		sellingOffersScrollP.setAutoscrolls(false);
		sellingOffersScrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sellingOffersScrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sellingOffersScrollP.setPreferredSize(new Dimension(700, 10000));
		sellingOffersScrollP.setBackground(new Color(242, 242, 242));
		board.getBoardPanel().add(sellingOffersScrollP, "sellingOffersScrollP");

		JLabel category = new JLabel(catName + prodName);
		category.setBounds(20, 5, 700, 70);
		category.setFont(new Font("Verdana", Font.BOLD, 20));
		category.setForeground(new Color(255, 69, 0));
		sellingOffersPanel.add(category);

		showOffers(); //TODO: remove

	}

	//-------------------------------------------------------------------------------------//

	public void showOffers() {
		int b1 = 40, b2 = 70, b3 = 600, b4 = 100;
		JPanel offerPanel;
		sellingOffersPanel.setPreferredSize(new Dimension(700, 10 * 120 + 70));

		for (int i = 0; i < 10; i++) {
			S_offer offer = new S_offer();
			offerPanel = offer.getOfferPanel();
			offerPanel.setBounds(b1, b2, b3, b4);
			b2 += 120;
			sellingOffersPanel.add(offerPanel);
		}
	}
}
