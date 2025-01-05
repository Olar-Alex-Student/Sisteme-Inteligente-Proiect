package agents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import agents.behaviours.client.ReceiveMessageBehaviour;
import agents.gui.ChatClientGUI;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class ChatClientAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2723424480483167480L;
	private ChatClientGUI gui;
	private List<String> activeUsers;

	@Override
	protected void setup() {
//		// Inițializarea listei de utilizatori activi
//		activeUsers = new ArrayList<>();
//
//		// Obține lista agenților locali
//		try {
//			String[] localAgents = getContainerController().getAgentNames();
//			for (String agentName : localAgents) {
//				if (!agentName.equals(getLocalName())) { // Exclude propriul nume
//					activeUsers.add(agentName);
//				}
//			}
//		} catch (Exception e) {
//			System.err.println("Eroare la obținerea agenților locali: " + e.getMessage());
//		}
//
//		// Crearea GUI-ului și legarea cu agentul
//		gui = new ChatClientGUI(this, activeUsers);
//		gui.setVisible(true);
//
//		System.out.println("ChatClientAgent " + getLocalName() + " este activ.");

//		registerToDF("ChatClient");
//
//		// Obține lista de agenți activi și elimină propriul nume
//		activeUsers = findActiveAgents("ChatClient");
//
//		// Creează GUI-ul
//		gui = new ChatClientGUI(this, activeUsers);
//		gui.setVisible(true);
//
//		System.out.println("ChatClientAgent " + getLocalName() + " este activ.");

		// Înregistrează serviciul la DF
		registerToDF("ChatClient");

		// Obține lista de agenți activi
		activeUsers = findActiveAgents("ChatClient");

		// Creează GUI-ul
		gui = new ChatClientGUI(this, activeUsers);
		gui.setVisible(true);

		// Adaugă comportamentul de primire a mesajelor
		addBehaviour(new ReceiveMessageBehaviour());

		System.out.println("ChatClientAgent " + getLocalName() + " este activ.");
	}

	@Override
	protected void takeDown() {
		// Închidere agent (ex. deregistrare DF, curățenie)
		// DFUtilities.deregister(this); // Pseudo-cod pentru deregistru DF

		System.out.println("ChatClientAgent " + getLocalName() + " este oprit.");
	}

	// Trimiterea unui mesaj către un alt agent
	public void sendMessageTo(String recipient, String content) {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(recipient, AID.ISLOCALNAME));
		message.setContent(content);
		send(message);

		System.out.println("Mesaj trimis către " + recipient + ": " + content);

		// Opțional, actualizează GUI-ul pentru a afișa mesajul trimis
		gui.displayMessage("Tu -> " + recipient + ": " + content);
	}

	// Actualizarea listei de utilizatori activi în GUI
	public void updateActiveUsers(List<String> newUsers) {
		activeUsers.clear();
		activeUsers.addAll(newUsers);
		gui.updateActiveUsers(newUsers);
	}

	// Gestionarea primirii mesajelor (comportament simplu)
	@Override
	protected void beforeMove() {
		addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
			@Override
			public void action() {
				ACLMessage receivedMessage = receive();
				if (receivedMessage != null) {
					String sender = receivedMessage.getSender().getLocalName();
					String content = receivedMessage.getContent();

					// Actualizare GUI pentru a afișa mesajul primit
					gui.displayMessage(sender + " -> Tu: " + content);
				} else {
					block();
				}
			}
		});
	}

	public void displayReceivedMessage(String sender, String content) {
		String formattedMessage = String.format("[%s] %s: %s", LocalDateTime.now().toString(), sender, content);
		gui.displayMessage(formattedMessage);
	}

	private void registerToDF(String serviceType) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID()); // Numele acestui agent

		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType); // Tipul serviciului (ex: "ChatClient")
		sd.setName(getLocalName()); // Numele serviciului
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd); // Înregistrare la DF
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	private List<String> findActiveAgents(String serviceType) {
		List<String> activeAgents = new ArrayList<>();

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType); // Tipul serviciului căutat (ex: "ChatClient")
		template.addServices(sd);

		try {
			DFAgentDescription[] results = DFService.search(this, template);
			for (DFAgentDescription result : results) {
				String agentName = result.getName().getLocalName();
				if (!agentName.equals(getLocalName())) { // Exclude propriul nume
					activeAgents.add(agentName);
				}
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		return activeAgents;
	}
}
