package agents;

import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class MonitorAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3356082458604959257L;
	// Mapă pentru a stoca ultima activitate a fiecărui client
	private Map<String, Long> clientActivity;
	private static final long TIMEOUT = 30000; // Timp de inactivitate permis (30 secunde)

	@Override
	protected void setup() {
		clientActivity = new HashMap<>();

		System.out.println("MonitorAgent " + getLocalName() + " este activ.");

		// Adaugă un comportament periodic pentru verificarea activității clienților
		addBehaviour(new TickerBehaviour(this, 10000) {
			@Override
			protected void onTick() {
				long currentTime = System.currentTimeMillis();

				// Verifică dacă există clienți inactivi
				clientActivity.entrySet().removeIf(entry -> {
					if (currentTime - entry.getValue() > TIMEOUT) {
						System.out.println(
								"Clientul " + entry.getKey() + " este inactiv și va fi notificat sau deconectat.");
						notifyInactivity(entry.getKey());
						return true;
					}
					return false;
				});
			}
		});
	}

	@Override
	protected void takeDown() {
		System.out.println("MonitorAgent " + getLocalName() + " este oprit.");
	}

	// Actualizează ultima activitate a unui client
	public void updateClientActivity(String clientName) {
		clientActivity.put(clientName, System.currentTimeMillis());
		System.out.println("Activitatea clientului " + clientName + " a fost actualizată.");
	}

	// Notifică un client despre inactivitate (sau implementează altă logică)
	private void notifyInactivity(String clientName) {
		ACLMessage warningMessage = new ACLMessage(ACLMessage.INFORM);
		warningMessage.addReceiver(new jade.core.AID(clientName, jade.core.AID.ISLOCALNAME));
		warningMessage.setContent(
				"Ești inactiv de prea mult timp. Te rugăm să trimiți un mesaj pentru a confirma că ești conectat.");
		send(warningMessage);
	}

}
