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

import models.Category;
import models.User;
import networking.RequestManager;
import networking.Response;

public class Launcher {

	// -------------------------------------------------------------------------------------//
	// Instance fields
	// -------------------------------------------------------------------------------------//

	private HashMap<String, Category> categories;

	private RequestManager requestManager;
	private User user;

	private Login login;
	private Register register;
	private Board board;
	private Navigation navigation;
	private NewOffer newOffer;

	// -------------------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------------------//

	public Launcher() {

		requestManager = RequestManager.getInstance();
		navigation = Navigation.getInstance();
		register = Register.getInstance();
		newOffer = NewOffer.getInstance();
		login = Login.getInstance();
		board = Board.getInstance();

		initialize();
	}

	// -------------------------------------------------------------------------------------//
	// Instance methods
	// -------------------------------------------------------------------------------------//

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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

		login.initialize();
		register.setWelcomeFrame(login.getWelcomeFrame()).initialize();
		generateRegisterForm();

		login.getWelcomeFrame().addWindowListener((new WindowAdapter() {
			RequestManager requestManager;

			public WindowAdapter init(RequestManager man) {
				requestManager = man;
				return this;
			}

			public void windowClosing(WindowEvent winEvt) {
				this.requestManager.closeConnection();
				System.exit(0);
			}
		}).init(requestManager));

		JTextField usernameLoginField = Utils.generateTextField("Username...", 185, 131, 206, 36, 10);
		login.getWelcomePanel().add(usernameLoginField);

		JPasswordField passwordField = Utils.generatePasswordField("Password...", 185, 178, 206, 36, 10);
		login.getWelcomePanel().add(passwordField);

		login.getSignInButton().addActionListener(new ActionListener() {
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

					HashMap<String, Object> userData = response.getParameters().get(0);
					user.setId(userData.get("id").toString()).setUsername(userData.get("username").toString()).setEmail(userData.get("email").toString());

					login.getWelcomeFrame().setVisible(false);
					app();
					CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
					cardLayout.show(login.getWelcomeFrame().getContentPane(), "appPanel");

				}
			}
		});

		login.getSignUpButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
				cardLayout.show(login.getWelcomeFrame().getContentPane(), "registerPanel");
			}
		});

	}

	// -------------------------------------------------------------------------------------//

	private void generateRegisterForm() {

		JTextField usernameField = Utils.generateTextField("Username...", 201, 113, 163, 20, 10);
		register.getRegisterPanel().add(usernameField);

		JTextField emailField = Utils.generateTextField("Email...", 201, 144, 163, 20, 10);
		register.getRegisterPanel().add(emailField);

		JTextField confEmailField = Utils.generateTextField("Confirm Email...", 201, 175, 163, 20, 10);
		register.getRegisterPanel().add(confEmailField);

		JPasswordField passwordField = Utils.generatePasswordField("Password...", 201, 206, 163, 20, 10);
		register.getRegisterPanel().add(passwordField);

		JPasswordField confPasswordField = Utils.generatePasswordField("Password...", 201, 237, 163, 20, 10);
		register.getRegisterPanel().add(confPasswordField);

		register.getCancelButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
				cardLayout.show(login.getWelcomeFrame().getContentPane(), "welcomePanel");
			}
		});

		register.getSubmitButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				HashMap<String, Object> parameters = new HashMap<>();
				parameters.put("username", usernameField.getText());
				parameters.put("email", emailField.getText());
				parameters.put("password", String.valueOf((passwordField).getPassword()));
				Response response = requestManager.sendRegisterRequest(parameters);

				if (response.getResCode() == Response.DATABASE_ERROR) {
					System.out.println("Database error.");
				} else if (response.getResCode() == Response.REGISTER_ERROR) {
					System.out.println("Register error.");
				} else if (response.getResCode() == Response.REGISTER_SUCCESSFUL) {
					System.out.println("Succes!");
				}
			}
		});

	}

	// -------------------------------------------------------------------------------------//

	private void app() {

		board.initialize();
		navigation.initialize();

		categories = Utils.generateCategoryList(requestManager);
		Utils.generateProductList(requestManager, categories);

		String[] allCategories = Utils.parseAllCategories(categories);
		String[] allProducts = Utils.parseAllProducts(categories);

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

			public void windowClosing(WindowEvent winEvt) {
				this.requestManager.closeConnection();
				System.exit(0);
			}
		}).init(requestManager));

		JComboBox<Object> categoriesComboBox = new JComboBox<Object>(allCategories);
		//categoriesComboBox.setBounds(b1, b2, b3, b4); //TODO: set bounds
		categoriesComboBox.setSelectedIndex(0);
		navigation.getNavPanel().add(categoriesComboBox);

		JComboBox<Object> productsComboBox = new JComboBox<Object>(allProducts);
		//productsComboBox.setBounds(b1, b2, b3, b4); //TODO: set bounds
		productsComboBox.setSelectedIndex(0);
		navigation.getNavPanel().add(productsComboBox);

		navigation.getNewOfferButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				newOffer.initialize();

				addNewOffer(newOffer.getNewOfferPanel(), allCategories, allProducts);
				CardLayout cardLayout = (CardLayout) board.getBoardPanel().getLayout();
				cardLayout.show(board.getBoardPanel(), "newOfferPanel");
			}
		});

		navigation.getBuyButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: implement
			}
		});

		navigation.getSellButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: implement
			}
		});

		// MARKET PANEL
		JSplitPane marketPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigation.getNavScrollPane(), board.getBoardScrollPanel());
		marketPanel.setDividerLocation(200);
		marketPanel.setDividerSize(0);
		marketFrame.getContentPane().add(marketPanel, "marketPanel");

	}

	// -------------------------------------------------------------------------------------//

	private void addNewOffer(JPanel newOfferPanel, String[] allCategories, String[] allProducts) {

		JComboBox<Object> categoriesComboBox = new JComboBox<Object>(allCategories);
		categoriesComboBox.setBounds(170, 210, 150, 20);
		categoriesComboBox.setSelectedIndex(0);
		newOfferPanel.add(categoriesComboBox);

		JComboBox<Object> productsComboBox = new JComboBox<Object>(allProducts);
		productsComboBox.setBounds(170, 250, 150, 20);
		productsComboBox.setSelectedIndex(0);
		newOfferPanel.add(productsComboBox);

		// select quantity
		JTextField quantityTextField = Utils.generateTextField(" Quantity...", 198, 280, 100, 30, 10);
		newOfferPanel.add(quantityTextField);

		// price/piece
		JTextField priceTextField = Utils.generateTextField(" Price / piece ($)", 402, 280, 100, 30, 10);
		newOfferPanel.add(priceTextField);

	}

}
