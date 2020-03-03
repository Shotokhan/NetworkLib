package networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * For the server-side, if there are different behaviors on connection
 * represented by the first message sent by the client; the handlersList
 * is ordered and has to be filled.
 * @author marcofelix98
 *
 */
public abstract class ConcreteMultiListener extends Listener {

	private ArrayList<ClientHandler> handlersList = new ArrayList<ClientHandler>();
	
	public ConcreteMultiListener(int port) {
		super(port);
	}
	
	/**
	 * This needs the override: given the handlersList and msg sent by the client,
	 * this function should return the index of the array to select which ClientHandler
	 * to execute; note that this occurs after a connection, so the client first makes
	 * a connection, then sends a message: it is the ClientHandler that could handle
	 * the next messages sent by an already connected client.
	 * @param msg the serialized msg sent by the client; the function could deserialize
	 * it to extract informations, for example the msg could be a JSON which maps to
	 * a user-defined class that contains data relevant to the decision clause.
	 * @return the index of the array to select which ClientHandler to execute, or -1
	 * to let the software throw an IndexOutOfBoundsException in the listen method: the
	 * method will catch the exception and log an error message in System.out
	 */
	abstract public int decision(String msg);
	
	/**
	 * Called by the run() method
	 */
	public void listen() {
		ClientHandler clientHandler = null;
		int index = 0;
		while(true) {
			this.getServer().waitConnection();
			try {
				String msg = this.getServer().getInput().readUTF();
				index = decision(msg);
				clientHandler = handlersList.get(index);
				System.out.println("Launching service " + clientHandler.getClass().getSimpleName());
				clientHandler.setSocket(this.getServer().getSocket());
				clientHandler.setInput(this.getServer().getInput()); 
				clientHandler.setOutput(new DataOutputStream(this.getServer().getSocket().getOutputStream()));
				clientHandler.setMsg(msg);
			} catch (IOException | IndexOutOfBoundsException e) {
				System.out.println("A request could not be handled: " + e.getMessage());
				continue;
			}
			clientHandler.start();
			Class<? extends ClientHandler> handler = clientHandler.getClass();
			@SuppressWarnings("rawtypes")
			Class[] arg = new Class[1];
			arg[0] = ClientHandler.class;
			try {
				handlersList.set(index, handler.getDeclaredConstructor(arg).newInstance(clientHandler));
			} catch (Exception e) {
				System.out.println("Can't handle more requests: " + e.getMessage());
				return;
			}
		}
	}

	@Override
	public void run() {
		this.listen();
	}
	
	public ArrayList<ClientHandler> getHandlersList() {
		return handlersList;
	}

	public void setHandlersList(ArrayList<ClientHandler> handlersList) {
		this.handlersList = handlersList;
	}

}
