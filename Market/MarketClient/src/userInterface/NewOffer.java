package userInterface;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewOffer {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final NewOffer newOffer = new NewOffer();
	private JPanel newOfferPanel;
	private JButton buyingOffer;
	private JButton sellingOffer;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private NewOffer() {
	};

	public static NewOffer getInstance() {
		return newOffer;
	}

	//-------------------------------------------------------------------------------------//

	public void initialize() {

		Board board = Board.getInstance();

		newOfferPanel = new JPanel(null);
		newOfferPanel.setBackground(new Color(242, 242, 242));
		newOfferPanel.setSize(700, 700);
		board.getBoardPanel().add(newOfferPanel, "newOfferPanel");

		JLabel newOfferIcon = new JLabel("");
		newOfferIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\rsz_newOffer.png")); //TODO: put this in resources folder (other icons too)
		newOfferIcon.setBounds(315, 100, 70, 70);
		newOfferPanel.add(newOfferIcon);

		buyingOffer = new JButton("I want to buy");
		buyingOffer.setBackground(new Color(0, 128, 0));
		buyingOffer.setForeground(Color.WHITE);
		buyingOffer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		buyingOffer.setBounds(170, 360, 150, 29);
		newOfferPanel.add(buyingOffer);

		sellingOffer = new JButton("I want to sell");
		sellingOffer.setBackground(new Color(0, 128, 0));
		sellingOffer.setForeground(Color.WHITE);
		sellingOffer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		sellingOffer.setBounds(375, 362, 150, 29);
		newOfferPanel.add(sellingOffer);

	}

	//-------------------------------------------------------------------------------------//

	public JPanel getNewOfferPanel() {
		return newOfferPanel;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getBuyingButton() {
		return buyingOffer;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getSellingButton() {
		return sellingOffer;
	}
}
