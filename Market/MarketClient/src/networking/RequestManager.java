package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class RequestManager {

	//-------------------------------------------------------------------------------------//
	//Class fields
	//-------------------------------------------------------------------------------------//

	private static final RequestManager requestManager = new RequestManager();

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private RequestManager() {

	}

	public static RequestManager getInstance() {
		return requestManager;
	}

	//-------------------------------------------------------------------------------------//
	//Instance methods
	//-------------------------------------------------------------------------------------//

	public void sendLoginRequest(ObjectInputStream in, ObjectOutputStream out, HashMap<String, Object> parameters) {

		System.out.println("Logging in: " + parameters.get("username"));
		Request req = new Request(Request.LOGIN);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			System.out.println("Server says " + in.readObject().toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//-------------------------------------------------------------------------------------//

	public void sendGetCategoriesRequest(ObjectInputStream in, ObjectOutputStream out, HashMap<String, Object> parameters) {

		System.out.println("Getting categories...");
		Request req = new Request(Request.GET_CATEGORIES);
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();
			System.out.println("Server says " + in.readObject().toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
