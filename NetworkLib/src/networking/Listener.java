package networking;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * ConcreteListener and ConcreteMultiListener inherit from this class;
 * you should use them unless you have special purposes.
 * @author marcofelix98
 *
 */
public class Listener extends Thread {
	
	private ServerManager server;
	
	public Listener() {}
	
	public Listener(int port) {
		this.setServer(new ServerManager(port));
	}

	public Listener(ServerManager server) {
		this.setServer(server);
	}
	
	public void listen(ClientHandler clientHandler) {
		while(true) {
			server.waitConnection();
			try {
				clientHandler.setSocket(server.getSocket());
				clientHandler.setInput(server.getInput()); 
				clientHandler.setOutput(new DataOutputStream(server.getSocket().getOutputStream()));
				System.out.println("Launching service " + clientHandler.getClass().getSimpleName());
			} catch (IOException e) {
				System.out.println("A request could not be handled: " + e.getMessage());
				continue;
			}
			clientHandler.start();
			Class<? extends ClientHandler> handler = clientHandler.getClass();
			@SuppressWarnings("rawtypes")
			Class[] arg = new Class[1];
			arg[0] = ClientHandler.class;
			try {
				clientHandler = handler.getDeclaredConstructor(arg).newInstance(clientHandler);
			} catch (Exception e) {
				System.out.println("Can't handle more requests: " + e.getMessage());
				return;
			}
		}
	}
	
	public ServerManager getServer() {
		return server;
	}

	public void setServer(ServerManager server) {
		this.server = server;
	}
}
