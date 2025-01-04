package agents.behaviours;

import java.util.Date;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class SimpleTask extends SimpleBehaviour {

	private Agent localAgent = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1432447024511747370L;

	public SimpleTask(Agent a) {
		this.localAgent = a;
	}

	@Override
	public void action() {
		Date date = new Date();
		System.out.println("Date: " + date.getDay() + " - " + date.getMonth() + " - " + date.getYear());
	}

	@Override
	public boolean done() {
		System.out.println("Job done!");
		localAgent.doDelete();
		return false;
	}

}
