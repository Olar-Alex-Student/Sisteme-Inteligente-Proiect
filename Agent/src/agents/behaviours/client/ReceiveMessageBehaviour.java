package agents.behaviours.client;

import agents.ChatClientAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessageBehaviour extends CyclicBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415611179595805980L;

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			String sender = msg.getSender().getLocalName();
			String content = msg.getContent();

			// Transmite mesajul către GUI-ul destinatarului
			((ChatClientAgent) myAgent).displayReceivedMessage(sender, content);

			System.out.println("Mesaj primit de la " + sender + ": " + content);
		} else {
			block(); // Blochează comportamentul până când sosesc mesaje noi
		}
	}

}
