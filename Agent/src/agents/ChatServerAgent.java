package agents;

import agents.behaviours.server.HandleClientConnectionBehaviour;
import agents.behaviours.server.HandleMessageBehaviour;
import agents.behaviours.server.MonitorClientsBehaviour;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import utils.ChatLogger;
import utils.Message;

public class ChatServerAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3925689846128948670L;

	@Override
	protected void setup() {
		// Înregistrare la Directory Facilitator (DF) pentru a fi descoperit de clienți
		DFAgentDescription dfDescription = new DFAgentDescription();
		dfDescription.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("ChatServer");
		serviceDescription.setName("CentralChatService");
		dfDescription.addServices(serviceDescription);

		try {
			DFService.register(this, dfDescription);
			System.out.println("ChatServerAgent " + getLocalName() + " s-a înregistrat la DF.");
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// Adăugarea comportamentelor de bază
		ParallelBehaviour serverBehaviours = new ParallelBehaviour();

		// Gestionarea conexiunilor clienților
		serverBehaviours.addSubBehaviour(new HandleClientConnectionBehaviour());

		// Gestionarea mesajelor trimise între clienți
		serverBehaviours.addSubBehaviour(new HandleMessageBehaviour());

		// Monitorizarea periodică a clienților
		serverBehaviours.addSubBehaviour(new MonitorClientsBehaviour(this, 10000));

		addBehaviour(serverBehaviours);
	}

	@Override
	protected void takeDown() {
		// Deregistrare de la Directory Facilitator
		try {
			DFService.deregister(this);
			System.out.println("ChatServerAgent " + getLocalName() + " s-a deregistrat de la DF.");
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		System.out.println("ChatServerAgent " + getLocalName() + " este oprit.");
	}

	// Logarea mesajelor folosind utilitarul ChatLogger
	public void logMessage(Message message) {
		ChatLogger.logMessage(message);
	}
}
