package agents.behaviours.client;

import agents.ChatClientAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMessageBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5436385150525509535L;
	private String receiver;
	private String content;

	public SendMessageBehaviour(String receiver, String content) {
		this.receiver = receiver;
		this.content = content;
	}

	@Override
	public void action() {
//		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
//		message.addReceiver(new jade.core.AID(receiver, jade.core.AID.ISLOCALNAME));
//		message.setContent(content);
//		myAgent.send(message);
//		System.out.println("Mesaj trimis către " + receiver + ": " + content);
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
		msg.setContent(content);
		myAgent.send(msg);

		// Afișează mesajul trimis în UI-ul local
		((ChatClientAgent) myAgent).displayReceivedMessage(myAgent.getLocalName(), content);
	}
}
