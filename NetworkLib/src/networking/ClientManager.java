package networking;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is for the client-side, taking care of the socket-related part.
 * It overrides finalize() method so that the garbage collector closes all the streams.
 * @author marcofelix98
 *
 */
public class ClientManager {
	
	private Socket socket = null;
	private BufferedReader input = null;
	private DataInputStream remoteInput = null;
	private DataOutputStream out = null;
	
	public ClientManager(String address, int port) {
		this.changeConnection(address, port);
	}
	
	public void finalize() {
		try {
			input.close();
			remoteInput.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates a new socket using the constructor Socket(address, port) and
	 * sets input from System.in (which can be changed), remoteInput from the
	 * socket's input stream and out from the socket's output stream.
	 * @param address the address to connect to
	 * @param port the port to connect to on the specified address
	 */
	public void changeConnection(String address, int port) {
		try {
			socket = new Socket(address, port);
			input = new BufferedReader(new InputStreamReader(System.in));
			remoteInput = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getInput() {
		return input;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public DataInputStream getRemoteInput() {
		return remoteInput;
	}

	public void setInput(BufferedReader input) {
		this.input = input;
	}
	
}
