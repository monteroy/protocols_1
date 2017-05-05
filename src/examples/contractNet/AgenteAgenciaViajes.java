
package examples.contractNet;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.domain.FIPANames;

import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;

/**
   This example shows how to implement the initiator role in 
   a FIPA-contract-net interaction protocol. In this case in particular 
   we use a <code>ContractNetInitiator</code>  
   to assign a dummy task to the agent that provides the best offer
   among a set of agents (whose local
   names must be specified as arguments).
   @author Giovanni Caire - TILAB
 */
public class AgenteAgenciaViajes extends Agent {
    private int nResponders;
    private final Billete billete = new Billete();
    private final Ontology ontologia = ViajesOntology.getInstance();
    private final Codec codec = new SLCodec();  //lenguaje de contenido
  
        @Override
	protected void setup() { 
        getContentManager().registerOntology(ontologia);
       
  	// Leer los nombres de los respondedores como argumentos
  	Object[] args = getArguments();
  	if (args != null && args.length > 0) {
  		nResponders = args.length;
  		System.out.println("Tratando de delegar la acción a uno de cada  "+nResponders+" respondedores.");
  		
  		// Rellenar el mensaje CFP
  		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
  		for (int i = 0; i < args.length; ++i) {//se crea para cada agente un mensaje con su aid
  			msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
                        System.out.println("soy " + args[i] + " estoy en agente inicializador");
  		}
			msg.setOntology(ontologia.getName());
                        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                        msg.setLanguage(codec.getName());
                        msg.getEncoding();
			// recibir una respuesta en 10 segundos
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			msg.setContent(ontologia.getName()+" billete  " + billete.getPrecio() + " precio");
                        
			addBehaviour(new ContractNetInitiator(this, msg) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("Agente "+propose.getSender().getName()+" propone "+ propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("El agente "+refuse.getSender().getName()+" no propone");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println("El mensaje ha fallado");
					}
					else {
						System.out.println("El agente "+failure.getSender().getName()+" ha fallado");
					}
					// Immediate failure --> we will not receive a response from this agent
					nResponders--;
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("Timeout expired: missing "+(nResponders - responses.size())+" responses");
					}
					// Evaluate proposals.
					int bestProposal = billete.getPrecio();
					AID bestProposer = null;
					ACLMessage accept = null;
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
							int proposal = Integer.parseInt(msg.getContent());
							if (proposal < bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}else if (proposal == bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}
						}
					}
					// Accept the proposal of the best proposer
					if (accept != null) {
						System.out.println("La propuesta es buena el precio es "+bestProposal+" puedes realizar la acción "+bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}						
				}
				
                                @Override
				protected void handleInform(ACLMessage inform) {
					System.out.println("El agente "+inform.getSender().getName()+" me informa de que la acción ha sido realizada correctamente. FIN");
				}
			} );
  	}
  	else {
  		System.out.println("Tienes que introducir los respondedores como argumentos");
  	}
  } 
}

