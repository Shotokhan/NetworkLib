package networking;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class takes care of the socket-related part for the server-side.
 * It overrides finalize() method so that the garbage collector closes all the streams.
 * @author marcofelix98
 *
 */
public class ServerManager {
	
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream input = null;
	
	public ServerManager(int port) {
		this.changePort(port);
	}
	
	public void finalize() {
		if(socket == null && input == null && server == null) {
			return;
		}
		try {
			server.close();
			socket.close();
			input.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Make the server listen on a new port, creating a new ServerSocket instance.
	 * @param port the port on which to listen for new connections
	 */
	public void changePort(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Server started on port " + port);
	}
	
	/**
	 * Waits and logs incoming connections, creating a socket using server.accept()
	 * and an input stream related to the socket.
	 */
	public void waitConnection() {
		System.out.println("Server waiting for a client on port " + server.getLocalPort());
		try {
			socket = server.accept();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Client " + socket.getRemoteSocketAddress()
							+ " accepted on port " + server.getLocalPort());
		try {
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public ServerSocket getServer() {
		return server;
	}

	public DataInputStream getInput() {
		return input;
	}
}
