package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class GreetingsClient {

	public static void main(String[] args) {
		String serverName = "79.115.29.173";
		int port = 3000;
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			ObjectInputStream in = new ObjectInputStream(inFromServer);

			GreetingsClient.sendGetProductsByCategoryRequest(in, out);

			try {
				System.out.println("Server says " + in.readObject().toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendLoginRequest(ObjectInputStream in, ObjectOutputStream out) {
		// sending a login request
		System.out.println("logging in ");
		Request req = new Request(Request.LOGIN);
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("username", "asd");
		parameters.put("password", "asd");
		req.setParameters(parameters);


		try {
			out.writeObject(req);
			out.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("Server says " + in.readObject().toString());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendRegisterRequest(ObjectInputStream in, ObjectOutputStream out) {
		// sending a login request
		System.out.println("logging in ");
		Request req = new Request(Request.REGISTER);
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("username", "asd3");
		parameters.put("password", "asd1");
		parameters.put("email", "asd1@asd.com");
		req.setParameters(parameters);


		try {
			out.writeObject(req);
			out.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println("Server says " + in.readObject().toString());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendGetProductsByCategoryRequest(ObjectInputStream in, ObjectOutputStream out) {

		System.out.println("sending categoryId...");
		Request req = new Request(Request.GET_PRODUCTS_BY_CATEGORY);
		HashMap<String, Object> parameters = new HashMap<>();
		parameters.put("categoryId", "4");
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//		try {
		//			//System.out.println("Server says " + in.readObject().toString());
		//
		//		} catch (ClassNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}

	public static void sendGetCategoriesRequest(ObjectInputStream in, ObjectOutputStream out) {

		System.out.println("sending get categories request");
		Request req = new Request(Request.GET_CATEGORIES);
		HashMap<String, Object> parameters = new HashMap<>();
		req.setParameters(parameters);

		try {
			out.writeObject(req);
			out.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//		try {
		//			//System.out.println("Server says " + in.readObject().toString());
		//
		//		} catch (ClassNotFoundException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

	}
}





