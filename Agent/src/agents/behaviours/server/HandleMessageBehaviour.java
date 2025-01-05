package agents.behaviours.server;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HandleMessageBehaviour extends CyclicBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9127123492229419849L;

	@Override
	public void action() {
		MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage message = myAgent.receive(template);

		if (message != null) {
			String content = message.getContent();
			String sender = message.getSender().getName();
			System.out.println("Mesaj primit de la " + sender + ": " + content);

			// Logica de rutare: identifică destinatarul și redirecționează mesajul
			String receiverName = extractReceiverFromMessage(content);
			if (receiverName != null) {
				ACLMessage forwardMessage = new ACLMessage(ACLMessage.INFORM);
				forwardMessage.addReceiver(new jade.core.AID(receiverName, jade.core.AID.ISLOCALNAME));
				forwardMessage.setContent(content);
				myAgent.send(forwardMessage);
				System.out.println("Mesaj redirecționat către " + receiverName);
			} else {
				System.out.println("Nu s-a putut identifica destinatarul pentru mesaj: " + content);
			}
		} else {
			block(); // Pune agentul în așteptare dacă nu există mesaje relevante
		}
	}

	private String extractReceiverFromMessage(String content) {
		// Exemplu simplu de extragere a destinatarului din mesaj
		// Ar trebui să adaptezi logica în funcție de structura mesajelor
		if (content.contains("->")) {
			return content.split("->")[1].trim();
		}
		return null;
	}
}
