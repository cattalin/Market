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

	private static final String SERVER_NAME = "79.115.29.173";
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

	public void closeConnection() {
		try {
			in.close();
			out.close();
			clientSocket.close();
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

	public Response sendGetCategoriesRequest(HashMap<String, Object> parameters) {

		System.out.println("Getting categories...");
		Request req = new Request(Request.GET_CATEGORIES);
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

}
