package userInterface;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Register {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final Register register = new Register();

	private JPanel registerPanel;
	private JFrame welcomeFrame;
	private JButton cancelSubmit;
	private JButton submitButton;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private Register() {
	};

	public static Register getInstance() {
		return register;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public void initialize() {

		registerPanel = new JPanel(null);
		registerPanel.setBackground(Color.WHITE);
		welcomeFrame.getContentPane().add(registerPanel, "registerPanel");

		JLabel registerIcon = new JLabel("");
		registerIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\register.png"));
		registerIcon.setBounds(248, 22, 70, 70);
		registerPanel.add(registerIcon);

		//Submit
		submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(0, 128, 0));
		submitButton.setForeground(new Color(255, 255, 255));
		submitButton.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		submitButton.setBounds(201, 299, 163, 29);
		registerPanel.add(submitButton);

		//Cancel
		cancelSubmit = new JButton("Cancel");
		cancelSubmit.setForeground(Color.RED);
		cancelSubmit.setBounds(239, 339, 89, 23);
		registerPanel.add(cancelSubmit);
	}

	//-------------------------------------------------------------------------------------//

	public JPanel getRegisterPanel() {
		return registerPanel;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getCancelButton() {
		return cancelSubmit;
	}

	//-------------------------------------------------------------------------------------//

	public JButton getSubmitButton() {
		return submitButton;
	}

	//-------------------------------------------------------------------------------------//

	public Register setWelcomeFrame(JFrame welcomeFrame) {
		this.welcomeFrame = welcomeFrame;
		return register;
	}

}
