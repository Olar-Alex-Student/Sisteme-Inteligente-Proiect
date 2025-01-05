package agents.behaviours.server;

import java.util.HashSet;
import java.util.Set;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HandleClientConnectionBehaviour extends CyclicBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = -537420421456051480L;
	private Set<String> activeClients = new HashSet<>();

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
		ACLMessage message = myAgent.receive(template);

		if (message != null) {
			String clientName = message.getSender().getName();
			if (activeClients.add(clientName)) {
				System.out.println("Client conectat: " + clientName);
				// Poți trimite un răspuns de confirmare clientului, dacă e necesar
			} else {
				System.out.println("Clientul este deja conectat: " + clientName);
			}
		} else {
			block(); // Pune agentul în așteptare dacă nu există mesaje relevante
		}
	}
}
