package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class RequestManager {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private static final RequestManager requestManager = new RequestManager();

	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private static final String SERVER_NAME = "localhost";
	private static final int PORT = 3000;

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private RequestManager() {
		try {
			System.out.println("Connecting to " + SERVER_NAME + " on port " + PORT);
			clientSocket = new Socket(SERVER_NAME, PORT);
			OutputStream outToServer = clientSocket.getOutputStream();
			out = new ObjectOutputStream(outToServer);
			InputStream inFromServer = clientSocket.getInputStream();
			in = new ObjectInputStream(inFromServer);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static RequestManager getInstance() {
		return requestManager;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public Response sendRegisterRequest(HashMap<String, Object> parameters) {

		Request req = new Request(Request.REGISTER);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendLoginRequest(HashMap<String, Object> parameters) {

		System.out.println("Logging in: " + parameters.get("username"));
		Request req = new Request(Request.LOGIN);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendGetCategoriesRequest() {

		Request req = new Request(Request.GET_CATEGORIES);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sentGetProductsRequest() {

		Request req = new Request(Request.GET_PRODUCTS);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sentGetProductsByCategoryRequest(HashMap<String, Object> parameters) {

		System.out.println("Getting products by categoryId= " + parameters.get("categoryId"));
		Request req = new Request(Request.GET_PRODUCTS_BY_CATEGORY);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendBuyingOfferRequest(HashMap<String, Object> parameters) {

		Request req = new Request(Request.CREATE_BUYING_OFFER);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendSellingOfferRequest(HashMap<String, Object> parameters) {

		Request req = new Request(Request.CREATE_SELLING_OFFER);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendBuyingOffersRequest(HashMap<String, Object> parameters) {

		Request req = new Request(Request.GET_BUYING_OFFERS);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendSellingOffersRequest(HashMap<String, Object> parameters) {

		Request req = new Request(Request.GET_SELLING_OFFERS);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public Response sendAcceptOfferRequest(HashMap<String, Object> parameters, String type) {

		Request req = null;

		if (!type.equals("Buy"))
			req = new Request(Request.ACCEPT_BUYING_OFFER);
		else
			req = new Request(Request.ACCEPT_SELLING_OFFER);

		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			Response res = (Response) in.readObject();
			return res;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	//-------------------------------------------------------------------------------------//

	public void sendDisconectRequest() {

		Request req = new Request(Request.DISCONNECT);

		try {
			out.writeObject(req);
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	//-------------------------------------------------------------------------------------//

	public void closeConnection() {
		try {
			in.close();
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
