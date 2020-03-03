package networking;

import java.util.ArrayList;

/**
 * This should be used in the main of the server application by appending to
 * the launchList all the Listeners (ConcreteListener or ConcreteMultiListener)
 * and calling the runListeners() method. If the server admin doesn't want the
 * output to go to stdout, he or she can redirect it changing the value of System.out,
 * for example to redirect it to a log file.
 * @author marcofelix98
 *
 */
public class ServerLauncher {

	private ArrayList<Listener> launchList = new ArrayList<Listener>();
	
	/**
	 * Runs all listeners in parallel and joins them
	 */
	public void runListeners() {
		for(Listener listener : launchList) {
			listener.start();
		}
		for(Listener listener : launchList) {
			try {
				listener.join();
				System.out.println("Listener " + 
						listener.getClass().getSimpleName() + " " + listener.getName() +
						" terminated successfully");
			} catch (InterruptedException e) {
				System.out.println("Listener " + 
			listener.getClass().getSimpleName() + " " + listener.getName() +
			" did not terminate successfully: " + e.getMessage());
			}
		}
	}

	public ArrayList<Listener> getLaunchList() {
		return launchList;
	}

	public void setLaunchList(ArrayList<Listener> launchList) {
		this.launchList = launchList;
	}

}
