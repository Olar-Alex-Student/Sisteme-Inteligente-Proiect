package agents.behaviours;

import agents.ImprovedPingAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitPingAndReplyBehaviour2 extends CyclicBehaviour {

	private ImprovedPingAgent localAgent = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8477716874340629699L;

	public WaitPingAndReplyBehaviour2(ImprovedPingAgent a) {
		super(a);
		this.localAgent = a;
	}

	@Override
	public void action() {
		System.out.println("... Agent subscription status: " + localAgent.subscriptionStatus);

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
