package userInterface;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import models.User;
import networking.RequestManager;
import networking.Response;

public class Launcher {

	// -------------------------------------------------------------------------------------//
	// Instance fields
	// -------------------------------------------------------------------------------------//

	private JComboBox<Object> categoriesComboBox;

	private RequestManager requestManager;
	private Login login;
	private User user; // TODO: fix

	// -------------------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------------------//

	public Launcher() {
		requestManager = RequestManager.getInstance();
		initialize();
	}

	// -------------------------------------------------------------------------------------//
	// Instance methods
	// -------------------------------------------------------------------------------------//

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Launcher window = new Launcher();
					window.login.getWelcomeFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// -------------------------------------------------------------------------------------//

	private void initialize() {

		login = Login.getInstance();
		login.initialize();

		login.getWelcomeFrame().addWindowListener((new WindowAdapter() {
			RequestManager requestManager;

			public WindowAdapter init(RequestManager man) {
				requestManager = man;
				return this;
			}

			@Override
			public void windowClosing(WindowEvent winEvt) {
				this.requestManager.closeConnection();
				System.exit(0);
			}
		}).init(requestManager));

		// Username and Password
		JTextField usernameLoginField = Utils.generateTextField("Username...", 185, 131, 206, 36, 10);
		login.getWelcomePanel().add(usernameLoginField);

		JPasswordField passwordField = Utils.generatePasswordField("Password...", 185, 178, 206, 36, 10);
		login.getWelcomePanel().add(passwordField);

		// Login request
		login.getSignInButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				HashMap<String, Object> parameters = new HashMap<>();
				parameters.put("username", usernameLoginField.getText());
				parameters.put("password", String.valueOf(passwordField.getPassword()));
				Response response = requestManager.sendLoginRequest(parameters);

				if (response.getResCode() == Response.DATABASE_ERROR) {
					System.out.println("Database error");
				} else if (response.getResCode() == Response.LOGIN_DENIED) {
					System.out.println("Login denied");
				} else if (response.getResCode() == Response.LOGIN_APPROVED) {

					app();
					CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
					cardLayout.show(login.getWelcomeFrame().getContentPane(), "appPanel");

				}
			}
		});

		login.getSignUpButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Register register = Register.getInstance().setWelcomeFrame(login.getWelcomeFrame());
				register.initialize();

				generateRegisterForm(register);
				CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
				cardLayout.show(login.getWelcomeFrame().getContentPane(), "registerPanel");

			}
		});

	}

	// -------------------------------------------------------------------------------------//

	private void generateRegisterForm(Register register) {

		JTextField firstNameField = Utils.generateTextField("First Name...", 201, 113, 163, 20, 10);
		register.getRegisterPanel().add(firstNameField);

		JTextField lastNameField = Utils.generateTextField("Last Name...", 201, 144, 163, 20, 10);
		register.getRegisterPanel().add(lastNameField);

		JTextField usernameField = Utils.generateTextField("Username...", 201, 175, 163, 20, 10);
		register.getRegisterPanel().add(usernameField);

		JTextField emailField = Utils.generateTextField("Email...", 201, 206, 163, 20, 10);
		register.getRegisterPanel().add(emailField);

		JPasswordField passwordField = Utils.generatePasswordField("Password...", 201, 237, 163, 20, 10);
		register.getRegisterPanel().add(passwordField);

		JPasswordField confPasswordField = Utils.generatePasswordField("Password...", 201, 68, 163, 20, 10);
		register.getRegisterPanel().add(confPasswordField);

		register.getCancelButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
				cardLayout.show(login.getWelcomeFrame().getContentPane(), "welcomePanel");
			}
		});

		// Register request
		register.getSubmitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement
			}
		});

	}

	// -------------------------------------------------------------------------------------//

	private void app() {

		login.getWelcomeFrame().setVisible(false);

		Board board = Board.getInstance();
		board.initialize();
		Navigation navigation = Navigation.getInstance();
		navigation.initialize();

		Utils.generateCategoryList(requestManager, categoriesComboBox, navigation.getNavPanel(), 20, 250, 150, 20);
		Utils.generateProductList(navigation.getNavPanel(), 20, 280, 150, 20);

		JFrame marketFrame = new JFrame();
		marketFrame.setVisible(true);
		marketFrame.setResizable(false);
		marketFrame.setTitle("Market Exchange");
		marketFrame.getContentPane().setLayout(new CardLayout(0, 0));
		marketFrame.setBackground(new Color(255, 239, 213));
		marketFrame.pack();
		marketFrame.setSize(900, 700);
		marketFrame.setLocationRelativeTo(null);
		marketFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marketFrame.addWindowListener((new WindowAdapter() {
			RequestManager requestManager;

			public WindowAdapter init(RequestManager man) {
				requestManager = man;
				return this;
			}

			@Override
			public void windowClosing(WindowEvent winEvt) {
				this.requestManager.closeConnection();
				System.exit(0);
			}
		}).init(requestManager));

		navigation.getNewOfferButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				NewOffer newOffer = NewOffer.getInstance();
				newOffer.initialize();

				addNewOffer(newOffer.getNewOfferPanel());
				CardLayout cardLayout = (CardLayout) board.getBoardPanel().getLayout();
				cardLayout.show(board.getBoardPanel(), "newOfferPanel");
			}
		});

		navigation.getBuyButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement
			}
		});

		navigation.getSellButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement
			}
		});

		// MARKET PANEL
		JSplitPane marketPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigation.getNavScrollPane(),
				board.getBoardScrollPanel());
		marketPanel.setDividerLocation(200);
		marketPanel.setDividerSize(0);
		marketFrame.getContentPane().add(marketPanel, "marketPanel");

	}

	// -------------------------------------------------------------------------------------//

	private void addNewOffer(JPanel newOfferPanel) {

		categoriesComboBox.setBounds(170, 210, 150, 20);
		categoriesComboBox.setSelectedIndex(0);
		newOfferPanel.add(categoriesComboBox);

		// select product
		Utils.generateProductList(newOfferPanel, 375, 210, 150, 20);

		// select quantity
		JTextField quantityTextField = Utils.generateTextField(" Quantity...", 198, 280, 100, 30, 10);
		newOfferPanel.add(quantityTextField);

		// price/piece
		JTextField priceTextField = Utils.generateTextField(" Price / piece ($)", 402, 280, 100, 30, 10);
		newOfferPanel.add(priceTextField);

	}

}
