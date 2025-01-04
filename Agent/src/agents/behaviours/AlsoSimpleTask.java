package agents.behaviours;

import java.util.Date;

import jade.core.behaviours.OneShotBehaviour;

public class AlsoSimpleTask extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460670696183039112L;

	@Override
	public void action() {
		Date date = new Date();
		System.out.println("One time date: " + date.getDay() + " - " + date.getMonth() + " - " + date.getYear());
	}

}
