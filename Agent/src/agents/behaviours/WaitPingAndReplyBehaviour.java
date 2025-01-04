package agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitPingAndReplyBehaviour extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7309855990842406563L;

	public WaitPingAndReplyBehaviour(Agent a) {
		super(a);
	}

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();

		if (msg != null) {
			ACLMessage reply = msg.createReply();

			if (msg.getPerformative() == ACLMessage.REQUEST) {
				String content = msg.getContent();
				if ((content != null) && (content.indexOf("ping") != -1)) {
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("pong");
				} else {
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("( UnexpectedContent (" + content + "))");
				}

			} else {
				reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				reply.setContent("( (Unexpected-act " + ACLMessage.getPerformative(msg.getPerformative()) + ") )");
			}
			myAgent.send(reply);
		} else {
			block();
		}
	}

}
