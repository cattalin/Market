package userInterface;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Navigation {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final Navigation navigation = new Navigation();

	private JScrollPane navScrollPane;
	private JPanel navPanel;
	private JButton newOfferButton;
	private JButton buyButton;
	private JButton sellButton;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private Navigation() {
	}

	public static Navigation getInstance() {
		return navigation;
	}

	//-------------------------------------------------------------------------------------//

	public void initialize() {

		navPanel = new JPanel(null);
		navScrollPane = new JScrollPane(navPanel);

		navPanel.setBackground(new Color(70, 100, 105));
		navPanel.setSize(200, 700);

		JLabel greetingsMessage = new JLabel("Welcome,");
		greetingsMessage.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		greetingsMessage.setForeground(Color.WHITE);
		greetingsMessage.setBounds(50, 10, 100, 100);
		navPanel.add(greetingsMessage);

		JLabel firstName = new JLabel("FETITOIDUL"); //TODO: get user's first name and send it here as parameter
		firstName.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		firstName.setForeground(Color.WHITE);
		firstName.setBounds(50, 35, 100, 100);
		navPanel.add(firstName);

		newOfferButton = new JButton("New Offer");
		newOfferButton.setBounds(20, 150, 150, 30);
		newOfferButton.setForeground(Color.WHITE);
		newOfferButton.setBackground(new Color(230, 138, 0));
		navPanel.add(newOfferButton);

		JLabel searchLabel = new JLabel("Search");
		searchLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setBounds(20, 170, 100, 100);
		navPanel.add(searchLabel);

		buyButton = new JButton("Buy");
		buyButton.setBounds(20, 320, 70, 23);
		buyButton.setBackground(new Color(0, 128, 0));
		buyButton.setForeground(Color.WHITE);
		navPanel.add(buyButton);

		sellButton = new JButton("Sell");
		sellButton.setBounds(100, 320, 70, 23);
		sellButton.setBackground(new Color(0, 128, 0));
		sellButton.setForeground(Color.WHITE);
		navPanel.add(sellButton);

	}

	//-------------------------------------------------------------------------------------//

	public JScrollPane getNavScrollPane() {
		return navScrollPane;
	}

	//-------------------------------------------------------------------------------------//

	public JPanel getNavPanel() {
		return navPanel;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getNewOfferButton() {
		return newOfferButton;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getBuyButton() {
		return buyButton;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getSellButton() {
		return sellButton;
	}

}
