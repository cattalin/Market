package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarketServer {
	private ServerSocket serverSocket;
	private ArrayList<ClientThread> clientThreads;
	private ExecutorService threadPool;

	public MarketServer(int port) throws IOException {
		try {
			clientThreads = new ArrayList<>();
			threadPool = Executors.newCachedThreadPool();
			serverSocket = new ServerSocket(port);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void start() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

				Socket currentSocket = serverSocket.accept();
				clientThreads.add(new ClientThread(currentSocket));
				threadPool.execute(clientThreads.get(clientThreads.size() - 1));

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		int port = 3000;
		try {
			MarketServer t = new MarketServer(port);
			t.start();

			// DataManager dataManager = DataManager.getInstance();
			// int response = dataManager.requestLogin("asd1", "asd");
			//
			// if (response == DataManager.USER_NOT_FOUND_ERROR) {
			// System.out.println("password or username wrong dude");
			// } else if (response == DataManager.USER_FOUND_LOGIN_APPROVED) {
			// System.out.println("congrats dude");
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
