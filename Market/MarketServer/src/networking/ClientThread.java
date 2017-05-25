package networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import database.DatabaseManager;

public class ClientThread implements Runnable {

	// -------------------------------------------------------------------------------------//
	// Class fields
	// -------------------------------------------------------------------------------------//

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	private volatile boolean shutdown = false;
	DatabaseManager dbManager;

	// -------------------------------------------------------------------------------------//
	// Setup Method
	// -------------------------------------------------------------------------------------//

	public ClientThread(Socket socket) {
		this.socket = socket;
		System.out.println("Just connected to " + socket.getRemoteSocketAddress());

		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		dbManager = DatabaseManager.getInstance();
		dbManager.connect();
	}

	@Override
	public void run() {
		while (!shutdown)
			try {
				Request req = (Request) input.readObject();
				System.out.println(req);
				Response res = null;
				switch (req.getReqCode()) {
				case Request.LOGIN:
					res = getLoginResponse(req);
					System.out.println(res);
					output.writeObject(res);
					output.flush();
					break;
				case Request.REGISTER:
					res = getRegisterResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.GET_CATEGORIES:
					res = getCategoryResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.GET_PRODUCTS_BY_CATEGORY:
					res = getProductsByCategoryResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.GET_PRODUCTS:
					res = getProductsResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.CREATE_BUYING_OFFER:
					res = getCreateBuyingOfferResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.CREATE_SELLING_OFFER:
					res = getCreateSellingOfferResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.GET_BUYING_OFFERS:
					res = getBuyingOffersResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.GET_SELLING_OFFERS:
					res = getSellingOffersResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.ACCEPT_BUYING_OFFER:
					res = getAcceptBuyingOfferResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.ACCEPT_SELLING_OFFER:
					res = getAcceptSellingOfferResponse(req);
					output.writeObject(res);
					output.flush();
					break;
				case Request.DISCONNECT:
					this.close();
					break;

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

	}

	public void close() {
		try {
			input.close();
			output.close();
			socket.close();
			dbManager.disconnect();
			shutdown = true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------//
	// Functionality methods
	// -------------------------------------------------------------------------------------//

	private Response getLoginResponse(Request req) {
		String username = req.getParameters().get("username").toString();
		String password = req.getParameters().get("password").toString();
		ArrayList<HashMap<String, Object>> resultParams = dbManager.login(username, password);

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.LOGIN_APPROVED);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.LOGIN_DENIED);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getRegisterResponse(Request req) {
		String username = req.getParameters().get("username").toString();
		String password = req.getParameters().get("password").toString();
		String email = req.getParameters().get("email").toString();

		ArrayList<HashMap<String, Object>> resultParams = dbManager.register(username, password, email);
		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.REGISTER_SUCCESSFUL);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.REGISTER_ERROR);

		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getCategoryResponse(Request req) {
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getCategories();
		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_CATEGORIES);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getProductsByCategoryResponse(Request req) {
		int categoryId = Integer.parseInt(req.getParameters().get("categoryId").toString());
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getProductsByCategory(categoryId);

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_PRODUCTS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getProductsResponse(Request req) {
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getProducts();

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_PRODUCTS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getBuyingOffersResponse(Request req) {
		HashMap<String, Object> params = req.getParameters();
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getBuyingOffers((Integer) params.get("categoryId"),
				(Integer) params.get("productId"));

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_BUYING_OFFERS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getSellingOffersResponse(Request req) {
		HashMap<String, Object> params = req.getParameters();
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getSellingOffers((Integer) params.get("categoryId"),
				(Integer) params.get("productId"));

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_SELLING_OFFERS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getAcceptBuyingOfferResponse(Request req) {
		HashMap<String, Object> params = req.getParameters();
		ArrayList<HashMap<String, Object>> resultParams = dbManager//
				.acceptBuyingOffer((Integer) params.get("bOfferId"));

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_SELLING_OFFERS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;
	}

	// -------------------------------------------------------------------------------------//

	private Response getAcceptSellingOfferResponse(Request req) {
		HashMap<String, Object> params = req.getParameters();
		ArrayList<HashMap<String, Object>> resultParams = dbManager
				.acceptSellingOffer((Integer) params.get("sOfferId"));

		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_SELLING_OFFERS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);
		return res;
	}

	// -------------------------------------------------------------------------------------//

	private Response getCreateBuyingOfferResponse(Request req) {
		int quantity = (Integer) req.getParameters().get("quantity");
		int totalPrice = (Integer) req.getParameters().get("totalPrice");
		int unitPrice = (Integer) req.getParameters().get("unitPrice");
		int categoryId = (Integer) req.getParameters().get("categoryId");
		int productId = (Integer) req.getParameters().get("productId");
		int buyerId = (Integer) req.getParameters().get("buyerId");

		ArrayList<HashMap<String, Object>> resultParams = dbManager.createBuyingOffer(quantity, totalPrice, unitPrice,
				categoryId, productId, buyerId);
		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_BUYING_OFFERS);// TODO remove this
															// dummy response
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);

		return res;

	}

	private Response getCreateSellingOfferResponse(Request req) {
		int quantity = (Integer) req.getParameters().get("quantity");
		int totalPrice = (Integer) req.getParameters().get("totalPrice");
		int unitPrice = (Integer) req.getParameters().get("unitPrice");
		int categoryId = (Integer) req.getParameters().get("categoryId");
		int productId = (Integer) req.getParameters().get("productId");
		int sellerId = (Integer) req.getParameters().get("sellerId");

		ArrayList<HashMap<String, Object>> resultParams = dbManager.createSellingOffer(quantity, totalPrice, unitPrice,
				categoryId, productId, sellerId);
		Response res;
		if (resultParams.size() != 0) {
			res = new Response(Response.GET_SELLING_OFFERS);
			res.setParameters(resultParams);
		} else
			res = new Response(Response.DATABASE_ERROR);

		return res;

	}


}
