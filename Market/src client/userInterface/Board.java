package userInterface;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class Board {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final Board board = new Board();

	private JPanel boardPanel;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private Board() {
	};

	public static Board getInstance() {
		return board;
	}

	//-------------------------------------------------------------------------------------//

	public void initialize() {

		boardPanel = new JPanel(new CardLayout());
		boardPanel.setBackground(new Color(242, 242, 242));
		boardPanel.setSize(700, 700);

		JPanel startPanel = new JPanel(null);
		startPanel.setBackground(new Color(242, 242, 242));
		startPanel.setSize(700, 700);
		boardPanel.add(startPanel, " startPanel");

		//TODO: think what to add here
	}

	//-------------------------------------------------------------------------------------//

	public JPanel getBoardPanel() {
		return boardPanel;
	}

}
