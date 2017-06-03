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
import models.Product;
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
	private SellingOffers sellingOffers;
	private BuyingOffers buyingOffers;

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
		user = User.getInstance();
		sellingOffers = SellingOffers.getInstance();
		buyingOffers = BuyingOffers.getInstance();

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
				requestManager.sendDisconectRequest();
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

					user.setId(userData.get("id").toString()).setUsername(userData.get("username").toString())
							.setEmail(userData.get("email").toString());

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
					CardLayout cardLayout = (CardLayout) login.getWelcomeFrame().getContentPane().getLayout();
					cardLayout.show(login.getWelcomeFrame().getContentPane(), "welcomePanel");
				}

			}
		});

	}

	// -------------------------------------------------------------------------------------//

	private void app() {

		login.getWelcomeFrame().setVisible(false);

		board.initialize();
		navigation.initialize(user.getUsername());

		categories = Utils.generateCategoryList(requestManager);
		Utils.generateProductList(requestManager, categories);

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
				requestManager.sendDisconectRequest();

				this.requestManager.closeConnection();
				System.exit(0);
			}
		}).init(requestManager));

		JComboBox<Object> categoriesComboBox = new JComboBox<Object>();
		categoriesComboBox.setBounds(20, 240, 150, 25);
		categoriesComboBox.insertItemAt("Select...", 0);
		categoriesComboBox.setSelectedIndex(0);
		navigation.getNavPanel().add(categoriesComboBox);
		int i = 0;
		for (Category category : categories.values())
			categoriesComboBox.insertItemAt(category.getName(), i + 1);

		JComboBox<Object> productsComboBox = new JComboBox<Object>();
		productsComboBox.setBounds(20, 280, 150, 25);
		productsComboBox.insertItemAt("Select...", 0);
		productsComboBox.setSelectedIndex(0);
		navigation.getNavPanel().add(productsComboBox);
		i = 1;
		for (Category category : categories.values())
			for (Product product : category.getProducts().values())
				productsComboBox.insertItemAt(product.getName(), i);

		categoriesComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				productsComboBox.removeAllItems();

				if (categoriesComboBox.getSelectedIndex() != 0) {
					productsComboBox.insertItemAt("Select...", 0);

					int i = 1;
					for (Product product : categories.get(categoriesComboBox.getSelectedItem().toString()).getProducts()
							.values()) {
						productsComboBox.insertItemAt(product.getName(), i++);
					}
					productsComboBox.setSelectedIndex(0);

				} else {
					productsComboBox.insertItemAt("Select...", 0);
					int i = 1;
					for (Category category : categories.values())
						for (Product product : category.getProducts().values())
							productsComboBox.insertItemAt(product.getName(), i++);
					productsComboBox.setSelectedIndex(0);

				}
			}
		});

		navigation.getNewOfferButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				newOffer.initialize();

				addNewOffer(newOffer.getNewOfferPanel());
				CardLayout cardLayout = (CardLayout) board.getBoardPanel().getLayout();
				cardLayout.show(board.getBoardPanel(), "newOfferPanel");
			}
		});

		navigation.getBuyButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category categoryChosen = categories.get(categoriesComboBox.getSelectedItem());

				Product productChosen = null;
				if (categoryChosen != null)
					productChosen = categoryChosen.getProducts().get(productsComboBox.getSelectedItem());

				int categoryId = categoryChosen == null ? 0 : Integer.parseInt(categoryChosen.getId());
				int productId = productChosen == null ? 0 : Integer.parseInt(productChosen.getId());

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("categoryId", categoryId);
				parameters.put("productId", productId);

				Response response = requestManager.sendSellingOffersRequest(parameters);
				if (response.getResCode() == Response.GET_SELLING_OFFERS) {
					buyingOffers.setParameters(response.getParameters());
					buyingOffers.initialize(categoriesComboBox.getSelectedItem().toString(),
							productsComboBox.getSelectedItem().toString());
				} else {
					buyingOffers.setParameters(null);
					buyingOffers.initialize(categoriesComboBox.getSelectedItem().toString(),
							productsComboBox.getSelectedItem().toString());
				}

				CardLayout cardLayout = (CardLayout) board.getBoardPanel().getLayout();
				cardLayout.show(board.getBoardPanel(), "sellingOffersScrollP");
			}
		});

		navigation.getSellButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category categoryChosen = categories.get(categoriesComboBox.getSelectedItem());

				Product productChosen = null;
				if (categoryChosen != null)
					productChosen = categoryChosen.getProducts().get(productsComboBox.getSelectedItem());

				int categoryId = categoryChosen == null ? 0 : Integer.parseInt(categoryChosen.getId());
				int productId = productChosen == null ? 0 : Integer.parseInt(productChosen.getId());

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("categoryId", categoryId);
				parameters.put("productId", productId);

				Response response = requestManager.sendBuyingOffersRequest(parameters);

				if (response.getResCode() == Response.GET_BUYING_OFFERS) {
					sellingOffers.setParameters(response.getParameters());
					sellingOffers.initialize(categoriesComboBox.getSelectedItem().toString(),
							productsComboBox.getSelectedItem().toString());
				} else {
					sellingOffers.setParameters(null);
					sellingOffers.initialize(categoriesComboBox.getSelectedItem().toString(),
							productsComboBox.getSelectedItem().toString());
				}

				CardLayout cardLayout = (CardLayout) board.getBoardPanel().getLayout();
				cardLayout.show(board.getBoardPanel(), "sellingOffersScrollP");
			}
		});

		// MARKET PANEL
		JSplitPane marketPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigation.getNavScrollPane(),
				board.getBoardPanel());
		marketPanel.setDividerLocation(200);
		marketPanel.setDividerSize(0);
		marketFrame.getContentPane().add(marketPanel, "marketPanel");

	}

	// -------------------------------------------------------------------------------------//

	private void addNewOffer(JPanel newOfferPanel) {

		JComboBox<Object> categoriesComboBox = new JComboBox<Object>();
		categoriesComboBox.setBounds(170, 210, 150, 20);
		categoriesComboBox.insertItemAt("Select...", 0);
		categoriesComboBox.setSelectedIndex(0);
		newOfferPanel.add(categoriesComboBox);
		int i = 1;
		for (Category category : categories.values())
			categoriesComboBox.insertItemAt(category.getName(), i);

		JComboBox<Object> productsComboBox = new JComboBox<Object>();
		productsComboBox.setBounds(373, 210, 150, 20);
		productsComboBox.insertItemAt("Select...", 0);
		productsComboBox.setSelectedIndex(0);
		newOfferPanel.add(productsComboBox);

		i = 1;
		for (Category category : categories.values())
			for (Product product : category.getProducts().values())
				productsComboBox.insertItemAt(product.getName(), i);

		categoriesComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				productsComboBox.removeAllItems();

				if (categoriesComboBox.getSelectedIndex() != 0) {
					productsComboBox.insertItemAt("Select...", 0);

					int i = 1;
					for (Product product : categories.get(categoriesComboBox.getSelectedItem().toString()).getProducts()
							.values()) {
						productsComboBox.insertItemAt(product.getName(), i++);
					}
					productsComboBox.setSelectedIndex(0);

				} else {
					productsComboBox.insertItemAt("Select...", 0);
					int i = 1;
					for (Category category : categories.values())
						for (Product product : category.getProducts().values())
							productsComboBox.insertItemAt(product.getName(), i++);
					productsComboBox.setSelectedIndex(0);

				}
			}
		});

		// select quantity
		JTextField quantityTextField = Utils.generateTextField(" Quantity...", 198, 280, 100, 30, 10);
		newOfferPanel.add(quantityTextField);

		// price/piece
		JTextField priceTextField = Utils.generateTextField(" Price / piece ($)", 402, 280, 100, 30, 10);
		newOfferPanel.add(priceTextField);

		newOffer.getBuyingButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category categoryChosen = categories.get(categoriesComboBox.getSelectedItem());
				Product productChosen = categoryChosen.getProducts().get(productsComboBox.getSelectedItem());
				int quantity = Integer.parseInt(quantityTextField.getText());
				int unitPrice = Integer.parseInt(priceTextField.getText());

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("categoryId", Integer.parseInt(categoryChosen.getId()));
				parameters.put("productId", Integer.parseInt(productChosen.getId()));
				parameters.put("quantity", quantity);
				parameters.put("unitPrice", unitPrice);
				parameters.put("totalPrice", quantity * unitPrice);
				parameters.put("buyerId", Integer.parseInt(user.getId()));

				Response response = requestManager.sendBuyingOfferRequest(parameters);
				//TODO: process response

			}
		});

		newOffer.getSellingButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Category categoryChosen = categories.get(categoriesComboBox.getSelectedItem());
				Product productChosen = categoryChosen.getProducts().get(productsComboBox.getSelectedItem());
				int quantity = Integer.parseInt(quantityTextField.getText());
				int unitPrice = Integer.parseInt(priceTextField.getText());

				HashMap<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("categoryId", Integer.parseInt(categoryChosen.getId()));
				parameters.put("productId", Integer.parseInt(productChosen.getId()));
				parameters.put("quantity", quantity);
				parameters.put("unitPrice", unitPrice);
				parameters.put("totalPrice", quantity * unitPrice);
				parameters.put("sellerId", Integer.parseInt(user.getId()));

				Response response = requestManager.sendSellingOfferRequest(parameters);
				//TODO: process response
			}
		});

	}

}
