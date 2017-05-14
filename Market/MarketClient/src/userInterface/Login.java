package userInterface;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final Login login = new Login();

	private JFrame welcomeFrame;
	private JPanel welcomePanel;
	private JButton signInButton;
	private JButton signUpButton;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private Login() {
	};

	public static Login getInstance() {
		return login;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public void initialize() {

		welcomeFrame = new JFrame();
		welcomeFrame.setResizable(false);
		welcomeFrame.setTitle("Market Exchange");
		welcomeFrame.getContentPane().setBackground(new Color(255, 255, 255));
		welcomeFrame.getContentPane().setLayout(new CardLayout(0, 0));
		welcomeFrame.setSize(568, 423);
		welcomeFrame.setLocationRelativeTo(null);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//WELCOME PANEL
		welcomePanel = new JPanel(null);
		welcomePanel.setBackground(Color.WHITE);
		welcomeFrame.getContentPane().add(welcomePanel, "welcomePanel");

		addLoginForm();
	}

	//-------------------------------------------------------------------------------------//

	private void addLoginForm() {

		JLabel welcomeIcon = new JLabel("");
		welcomeIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\marketlogo.png"));
		welcomeIcon.setBounds(250, 37, 70, 70);
		welcomePanel.add(welcomeIcon);

		//Sign In
		signInButton = new JButton("Sign In");
		signInButton.setBounds(185, 225, 206, 30);
		signInButton.setForeground(Color.BLACK);
		signInButton.setBackground(new Color(255, 69, 0));
		welcomePanel.add(signInButton);

		//Sign Up
		JLabel registerLabel = new JLabel("Don't have an accout?");
		registerLabel.setBounds(185, 266, 115, 15);
		registerLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 11));
		welcomePanel.add(registerLabel);

		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(310, 263, 81, 23);
		signUpButton.setForeground(new Color(0, 128, 0));
		welcomePanel.add(signUpButton);

	}

	//-------------------------------------------------------------------------------------//

	public JFrame getWelcomeFrame() {
		return welcomeFrame;
	}

	//-------------------------------------------------------------------------------------//

	public JPanel getWelcomePanel() {
		return welcomePanel;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getSignInButton() {
		return signInButton;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getSignUpButton() {
		return signUpButton;
	}

}
