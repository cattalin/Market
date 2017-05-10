package networking;

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
	DatabaseManager dbManager;

	// -------------------------------------------------------------------------------------//
	// Setup Method
	// -------------------------------------------------------------------------------------//

	public ClientThread(Socket socket) {
		this.socket = socket;
		System.out.println("Just connected to " + socket.getRemoteSocketAddress());
		dbManager = DatabaseManager.getInstance();
	}

	@Override
	public void run() {
		//while (true) {
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			try {
				Request req = (Request) input.readObject();
				System.out.println(req);
				Response res = null;
				switch (req.getReqCode()) {
				case Request.LOGIN:
					res = getLoginResponse(req);
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

				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			//output.writeObject("Thank you for connecting to " + socket.getLocalSocketAddress() + "\nGoodbye!");

		} catch (IOException e) {
			e.printStackTrace();
		}
		//}
	}

	public void close() {
		try {
			input.close();
			output.close();
			socket.close();
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
		dbManager.connect();

		ArrayList<HashMap<String, Object>> resultParams = dbManager.login(username, password);
		Response res = new Response(Response.LOGIN_APPROVED);
		if (resultParams.size() != 0) {
			res.setParameters(resultParams);
		}
		return res;

	}

	// -------------------------------------------------------------------------------------//


	private Response getRegisterResponse(Request req) {
		String username = req.getParameters().get("username").toString();
		String password = req.getParameters().get("password").toString();
		String email = req.getParameters().get("email").toString();
		dbManager.connect();

		ArrayList<HashMap<String, Object>> resultParams = dbManager.register(username, password, email);
		Response res = new Response(Response.REGISTER_SUCCESSFUL);
		if (resultParams.size() != 0) {
			res.setParameters(resultParams);
		}
		return res;

	}

	// -------------------------------------------------------------------------------------//

	private Response getCategoryResponse(Request req) {

		System.out.println("AIIC");
		dbManager.connect();
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getCategories();
		Response res = new Response(Response.GET_CATEGORIES);
		if (resultParams.size() != 0) {
			res.setParameters(resultParams);
		}
		//System.out.println(resultParams);
		return res;

	}

	// -------------------------------------------------------------------------------------//


	private Response getProductsByCategoryResponse(Request req) {

		int categoryId = Integer.parseInt(req.getParameters().get("categoryId").toString());
		dbManager.connect();
		ArrayList<HashMap<String, Object>> resultParams = dbManager.getProductsByCategory(categoryId);
		Response res = new Response(Response.GET_PRODUCTS);
		if (resultParams.size() != 0) {
			res.setParameters(resultParams);
		}
		return res;

	}


}
