package agents.behaviours.server;

import jade.core.behaviours.TickerBehaviour;

public class MonitorClientsBehaviour extends TickerBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8597611632228646167L;

	public MonitorClientsBehaviour(jade.core.Agent agent, long period) {
		super(agent, period);
	}

	@Override
	protected void onTick() {
		System.out.println("Monitorizare clienți activi...");
		// Aici poți adăuga logica pentru verificarea clienților activi, ex.:
		// Verificarea mesajelor de tip "heartbeat" de la clienți sau ștergerea
		// clienților inactivi
	}
}
