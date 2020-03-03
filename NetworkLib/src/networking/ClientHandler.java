package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * For the server-side threads which handle client requests
 * @author marcofelix98
 *
 */
public abstract class ClientHandler extends Thread {
	
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private String msg = null;
	
	public ClientHandler() {
		super();
	}
	
	public ClientHandler(ClientHandler other) {
		this.setSocket(other.getSocket());
		this.setInput(other.getInput());
		this.setOutput(other.getOutput());
		this.setMsg(other.getMsg());
		this.copy(other);
	}
	
	/**
	 * Every class that implements ClientHandler has to override the abstract
	 * 'copy' method, because it is called in the super-constructor with the
	 * 'other' parameter. The copy constructor allows the Listener to instance
	 * new ClientHandlers of the user-defined types alongside with variables that
	 * the user wants to encapsulate inside the ClientHandler, without losing the
	 * context information (such as the reference to a class that contains shared
	 * data structures available to all ClientHandlers running); of course, the
	 * imagination is the limit: the user (the developer)
	 * could define a clientHandler the executes other ClientHandlers defined by
	 * him or her, for example one for the log-in and the other for the session.
	 * @param the ClientHandler whose fields are to be copied in this new instance
	 */
	abstract public void copy(ClientHandler other);
	
	@Override
	abstract public void run();

	public void finalize() {
		if(socket == null || input == null || output == null) {
			return;
		}
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getInput() {
		return input;
	}

	public void setInput(DataInputStream input) {
		this.input = input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
