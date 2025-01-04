package agents;

import agents.behaviours.AlsoSimpleTask;
import agents.behaviours.WaitPingAndReplyBehaviour;
import agents.behaviours.WaitPingAndReplyBehaviour2;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ImprovedPingAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5822077818888991353L;

	public boolean subscriptionStatus;

	@Override
	protected void setup() {
		System.out.println("Agent Starting...");

		try {
			this.subscriptionStatus = startSubscription();
			if (this.subscriptionStatus) {
				System.out.println("DF subscription activated!");
			} else {
				System.out.println("DF subscription failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			WaitPingAndReplyBehaviour2 waitPingAndReplyBehaviour2 = new WaitPingAndReplyBehaviour2(this);
			addBehaviour(waitPingAndReplyBehaviour2);

			WaitPingAndReplyBehaviour waitPingAndReplyBehaviour = new WaitPingAndReplyBehaviour(this);
			addBehaviour(waitPingAndReplyBehaviour);

			AlsoSimpleTask alsoSimpleTask = new AlsoSimpleTask();
			addBehaviour(alsoSimpleTask);

//			SimpleTask simpleTask = new SimpleTask(this);
//			addBehaviour(simpleTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		this.doDelete();
	}

	private boolean startSubscription() {
		// Registration with the DF
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("ImprovedPingAgent");
		sd.setName(getName());
		sd.setOwnership("OlarAlex");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			return true;
		} catch (FIPAException e) {
			doDelete();
		}

		return false;
	}

	private boolean stopSubscription() {
		try {
			DFService.deregister(this);
			System.out.println("DF Deregister successfully!");
			return true;
		} catch (FIPAException e) {
			System.out.println("DF Deregister failed!");
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected void takeDown() {
		if (this.subscriptionStatus) {
			boolean status = stopSubscription();

			if (status) {
				System.out.println("DF Deregister successfully!");
			} else {
				System.out.println("DF Deregister failed!");
			}
		}

		System.out.println("Agent Closing...");
	}
}
