package networking;

/**
 * It is like a server-side controller class for the ClientHandler
 * @author marcofelix98
 *
 */
public class ConcreteListener extends Listener {

	private ClientHandler clientHandler;
	
	public ConcreteListener() {}
	
	/**
	 * This constructor makes the listener listen on the port specified and
	 * make it execute the specified clientHandler on-demand when there is a new
	 * connection. If there are data structures to share among the ClientHandlers
	 * that are to be executed, it is a good idea to define a class that inherits
	 * from ConcreteListener and to add there volatile/synchronized variables; so
	 * the ClientHandlers defined would need a reference to the ConcreteListener
	 * defined and of course the override of the copy method.
	 * @param port the port on which the server will listen
	 * @param clientHandler the client handler to be executed as new connections arrive
	 */
	public ConcreteListener(int port, ClientHandler clientHandler) {
		super(port);
		this.setClientHandler(clientHandler);
	}
	
	@Override
	public void run() {
		this.listen(clientHandler);
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}

	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}
}
