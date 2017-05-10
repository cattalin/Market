package userInterface;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Market {

	//-------------------------------------------------------------------------------------//
	//Class fields
	//-------------------------------------------------------------------------------------//

	private static final String SERVER_NAME = "79.115.29.173";
	private static final int PORT = 3000;

	//-------------------------------------------------------------------------------------//
	//Instance fields
	//-------------------------------------------------------------------------------------//

	private JFrame welcomeFrame, marketFrame;
	private JPanel welcomePanel;
	private JPanel registerPanel;
	private JSplitPane marketPanel;

	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	public Market() {

		System.out.println("Connecting to " + SERVER_NAME + " on port " + PORT);
		openConnection();

	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Market window = new Market();
					window.welcomeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//-------------------------------------------------------------------------------------//

	private void openConnection() {

		try {
			clientSocket = new Socket(SERVER_NAME, PORT);
			System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

			OutputStream outToServer = clientSocket.getOutputStream();
			out = new ObjectOutputStream(outToServer);
			InputStream inFromServer = clientSocket.getInputStream();
			in = new ObjectInputStream(inFromServer);

			initialize();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//-------------------------------------------------------------------------------------//

	private void closeConnection() {

		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//-------------------------------------------------------------------------------------//

	private void initialize() {

		//MAIN FRAME
		welcomeFrame = new JFrame();
		welcomeFrame.setResizable(false);
		welcomeFrame.setTitle("Market Exchange");
		welcomeFrame.getContentPane().setBackground(new Color(255, 255, 255));
		welcomeFrame.getContentPane().setLayout(new CardLayout(0, 0));
		welcomeFrame.setSize(568, 423);
		welcomeFrame.setLocationRelativeTo(null);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		welcomeFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				closeConnection();
				System.exit(0);
			}
		});

		//WELCOME PANEL
		welcomePanel = new JPanel(null);
		welcomePanel.setBackground(Color.WHITE);
		welcomeFrame.getContentPane().add(welcomePanel, "welcomePanel");

		generateLoginForm();

	}

	//					LOGIN
	//-------------------------------------------------------------------------------------//

	private void generateLoginForm() {

		JLabel welcomeIcon = new JLabel("");
		welcomeIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\marketlogo.png"));
		welcomeIcon.setBounds(250, 37, 70, 70);
		welcomePanel.add(welcomeIcon);

		JTextField usernameLoginField = generateTextField("Username...", 185, 131, 206, 36, 10);
		welcomePanel.add(usernameLoginField);

		JPasswordField passwordField = generatePasswordField("Password...", 185, 178, 206, 36, 10);
		welcomePanel.add(passwordField);

		//Sign In
		JButton signInButton = new JButton("Sign In");
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				app();
				CardLayout cardLayout = (CardLayout) welcomeFrame.getContentPane().getLayout();
				cardLayout.show(welcomeFrame.getContentPane(), "appPanel");
			}
		});

		signInButton.setBounds(185, 225, 206, 30);
		signInButton.setForeground(Color.BLACK);
		signInButton.setBackground(new Color(255, 69, 0));
		welcomePanel.add(signInButton);

		//Sign Up
		JLabel registerLabel = new JLabel("Don't have an accout?");
		registerLabel.setBounds(185, 266, 115, 15);
		registerLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 11));
		welcomePanel.add(registerLabel);

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				generateRegisterForm();
				CardLayout cardLayout = (CardLayout) welcomeFrame.getContentPane().getLayout();
				cardLayout.show(welcomeFrame.getContentPane(), "registerPanel");
			}
		});
		signUpButton.setBounds(310, 263, 81, 23);
		signUpButton.setForeground(new Color(0, 128, 0));
		welcomePanel.add(signUpButton);

	}

	//				REGISTER
	//-------------------------------------------------------------------------------------//

	private void generateRegisterForm() {

		//SIGN IN PANEL
		registerPanel = new JPanel(null);
		registerPanel.setBackground(Color.WHITE);
		welcomeFrame.getContentPane().add(registerPanel, "registerPanel");

		//Register form
		JLabel registerIcon = new JLabel("");
		registerIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\register.png"));
		registerIcon.setBounds(248, 22, 70, 70);
		registerPanel.add(registerIcon);

		JTextField firstNameField = generateTextField("First Name...", 201, 113, 163, 20, 10);
		registerPanel.add(firstNameField);

		JTextField lastNameField = generateTextField("Last Name...", 201, 144, 163, 20, 10);
		registerPanel.add(lastNameField);

		JTextField usernameField = generateTextField("Username...", 201, 175, 163, 20, 10);
		registerPanel.add(usernameField);

		JPasswordField passwordField = generatePasswordField("Password...", 201, 206, 163, 20, 10);
		registerPanel.add(passwordField);

		JPasswordField confPasswordField = generatePasswordField("Password...", 201, 237, 163, 20, 10);
		registerPanel.add(confPasswordField);

		//Submit
		JButton submitButton = new JButton("Submit");
		submitButton.setBackground(new Color(0, 128, 0));
		submitButton.setForeground(new Color(255, 255, 255));
		submitButton.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		submitButton.setBounds(201, 268, 163, 29);
		registerPanel.add(submitButton);

		//Cancel
		JButton cancelSubmit = new JButton("Cancel");
		cancelSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) welcomeFrame.getContentPane().getLayout();
				cardLayout.show(welcomeFrame.getContentPane(), "welcomePanel");
			}
		});
		cancelSubmit.setForeground(Color.RED);
		cancelSubmit.setBounds(239, 308, 89, 23);
		registerPanel.add(cancelSubmit);

	}

	//				APP
	//-------------------------------------------------------------------------------------//

	private void app() {

		welcomeFrame.setVisible(false);

		//MARKET FRAME
		marketFrame = new JFrame();
		marketFrame.setVisible(true);
		marketFrame.setResizable(false);
		marketFrame.setTitle("Market Exchange");
		marketFrame.getContentPane().setLayout(new CardLayout(0, 0));
		marketFrame.setBackground(new Color(255, 239, 213));
		marketFrame.pack();
		marketFrame.setSize(900, 700);
		marketFrame.setLocationRelativeTo(null);
		marketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marketFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				closeConnection();
				System.exit(0);
			}
		});

		//navigation
		JPanel navPanel = new JPanel(null);
		JScrollPane navScrollPanel = new JScrollPane(navPanel);

		//board
		JPanel boardPanel = new JPanel(new CardLayout());
		JScrollPane boardScrollPanel = new JScrollPane(boardPanel);
		boardPanel.setBackground(new Color(242, 242, 242));
		boardPanel.setSize(700, 700);

		//start
		JPanel startPanel = new JPanel(null);
		startPanel.setBackground(new Color(242, 242, 242));
		startPanel.setSize(700, 700);
		boardPanel.add(startPanel, " startPanel");

		//new offer
		JPanel newOfferPanel = new JPanel(null);
		newOfferPanel.setBackground(new Color(242, 242, 242));
		newOfferPanel.setSize(700, 700);
		boardPanel.add(newOfferPanel, "newOfferPanel");
		newOfferPanel.setVisible(false);

		generateNavigationPanel(navPanel, newOfferPanel, boardPanel);

		//MARKET PANEL
		marketPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navScrollPanel, boardScrollPanel);
		marketPanel.setDividerLocation(200);
		marketPanel.setDividerSize(0);
		marketFrame.getContentPane().add(marketPanel, "marketPanel");

	}

	//-------------------------------------------------------------------------------------//

	private void generateNavigationPanel(JPanel navPanel, JPanel newOfferPanel, JPanel boardPanel) {

		navPanel.setBackground(new Color(70, 100, 105));
		navPanel.setSize(200, 700);

		//greetings
		JLabel greetingsMessage = new JLabel("Welcome,");
		greetingsMessage.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		greetingsMessage.setForeground(Color.WHITE);
		greetingsMessage.setBounds(50, 10, 100, 100);
		navPanel.add(greetingsMessage);

		JLabel firstName = new JLabel("FETITOIDUL"); //TODO: get user's first name
		firstName.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		firstName.setForeground(Color.WHITE);
		firstName.setBounds(50, 35, 100, 100);
		navPanel.add(firstName);

		//new offer button
		JButton newOfferButton = new JButton("New Offer");
		newOfferButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				addNewOffer(newOfferPanel);
				CardLayout cardLayout = (CardLayout) boardPanel.getLayout();
				cardLayout.show(boardPanel, "newOfferPanel");
			}
		});

		newOfferButton.setBounds(20, 150, 150, 30);
		newOfferButton.setForeground(Color.WHITE);
		newOfferButton.setBackground(new Color(230, 138, 0));
		navPanel.add(newOfferButton);

		//search
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 17));
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setBounds(20, 170, 100, 100);
		navPanel.add(searchLabel);

		//select category
		generateCategoryList(navPanel, 20, 250, 150, 20);

		//select product
		generateProductList(navPanel, 20, 280, 150, 20);

		//Buy button
		JButton buyButton = new JButton("Buy");
		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		buyButton.setBounds(20, 320, 70, 23);
		buyButton.setBackground(new Color(0, 128, 0));
		buyButton.setForeground(Color.WHITE);
		navPanel.add(buyButton);

		//Sell button
		JButton sellButton = new JButton("Sell");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		sellButton.setBounds(100, 320, 70, 23);
		sellButton.setBackground(new Color(0, 128, 0));
		sellButton.setForeground(Color.WHITE);
		navPanel.add(sellButton);

	}

	//-------------------------------------------------------------------------------------//

	private void addNewOffer(JPanel newOfferPanel) {

		JLabel newOfferIcon = new JLabel("");
		newOfferIcon.setIcon(new ImageIcon("C:\\Users\\Alina\\Desktop\\images\\rsz_newOffer.png"));
		newOfferIcon.setBounds(315, 100, 70, 70);
		newOfferPanel.add(newOfferIcon);

		//select category
		generateCategoryList(newOfferPanel, 170, 210, 150, 20);

		//select product
		generateProductList(newOfferPanel, 375, 210, 150, 20);

		//select quantity
		JTextField quantityTextField = generateTextField(" Quantity...", 198, 280, 100, 30, 10);
		newOfferPanel.add(quantityTextField);

		//price/piece
		JTextField priceTextField = generateTextField(" Price / piece ($)", 402, 280, 100, 30, 10);
		newOfferPanel.add(priceTextField);

		//new buying offer
		JButton buyingOffer = new JButton("I want to buy");
		buyingOffer.setBackground(new Color(0, 128, 0));
		buyingOffer.setForeground(Color.WHITE);
		buyingOffer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		buyingOffer.setBounds(170, 360, 150, 29);
		newOfferPanel.add(buyingOffer);

		//new selling offer
		JButton sellingOffer = new JButton("I want to sell");
		sellingOffer.setBackground(new Color(0, 128, 0));
		sellingOffer.setForeground(Color.WHITE);
		sellingOffer.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 14));
		sellingOffer.setBounds(375, 362, 150, 29);
		newOfferPanel.add(sellingOffer);

	}

	//-------------------------------------------------------------------------------------//

	private void generateProductList(JPanel panel, int b1, int b2, int b3, int b4) {

		String[] products = { "Products", "Durex", "Love", "pentru sex" };
		JComboBox<Object> productsComboBox = new JComboBox<Object>(products);
		productsComboBox.setBounds(b1, b2, b3, b4);
		productsComboBox.setSelectedIndex(0);
		panel.add(productsComboBox);
	}

	//-------------------------------------------------------------------------------------//

	private void generateCategoryList(JPanel panel, int b1, int b2, int b3, int b4) {

		String[] categories = { "Category", "tigari", "multe", "de 10", "cu bila", "dupa sex" };
		JComboBox<Object> categoriesComboBox = new JComboBox<Object>(categories);
		categoriesComboBox.setBounds(b1, b2, b3, b4);
		categoriesComboBox.setSelectedIndex(0);
		panel.add(categoriesComboBox);
	}

	//-------------------------------------------------------------------------------------//

	private JPasswordField generatePasswordField(String text, int b1, int b2, int b3, int b4, int columns) {

		JPasswordField textField = new JPasswordField();

		textField.setText(text);
		textField.setColumns(columns);
		textField.setBounds(b1, b2, b3, b4);
		textField.setBackground(new Color(220, 220, 220));
		textField.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
			}
		});

		return textField;

	}

	//-------------------------------------------------------------------------------------//

	private JTextField generateTextField(String text, int b1, int b2, int b3, int b4, int columns) {

		JTextField textField = new JTextField();

		textField.setText(text);
		textField.setColumns(columns);
		textField.setBounds(b1, b2, b3, b4);
		textField.setBackground(new Color(220, 220, 220));
		textField.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 13));
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField.getText().equals(text))
					textField.setText("");
			}
		});

		return textField;

	}

}
