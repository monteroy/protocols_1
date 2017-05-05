/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package examples.contactNet;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

/**
   This example shows how to implement the responder role in 
   a FIPA-contract-net interaction protocol. In this case in particular 
   we use a <code>ContractNetResponder</code>  
   to participate into a negotiation where an initiator needs to assign
   a task to an agent among a set of candidates.
   @author Giovanni Caire - TILAB
 */
public class ContractNetResponderAgent extends Agent {
        Billete billete = new Billete();
        Oferta ofertaPrecio = new Oferta();
        Comprar comprar = new Comprar();
        private final Ontology ontologia = ViajesOntology.getInstance();
        private final Codec codec = new SLCodec();
        
        @Override
	protected void setup() {
		System.out.println("Soy el agente "+getLocalName()+" esperando por un mensaje CFP...");
                    MessageTemplate template = MessageTemplate.and(
                    MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                    MessageTemplate.MatchPerformative(ACLMessage.CFP) );
                    MessageTemplate.MatchOntology(ontologia.getName());
                    MessageTemplate.MatchLanguage(codec.getName());
                    
		addBehaviour(new ContractNetResponder(this, template) {
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				System.out.println("Agente "+getLocalName()+": CFP recibo de  "+ cfp.getSender().getName()+".La acción es "+cfp.getContent());
				int proposal = evaluateAction();
				if (proposal <= billete.getPrecio()) {
					// Proporcionamos una propuesta
					System.out.println("Agente "+getLocalName()+": propongo "+proposal);
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(proposal));
                                        
					return propose;
				}
				else {
					// Nos negamos a proporcionar una propuesta
					System.out.println("Agente "+getLocalName()+": Rechazo.Mi precio es mayor " + billete.getPrecio());
					throw new RefuseException("he dicho no antes");
				}
			}

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				System.out.println("Agente "+getLocalName()+": Propuesta aceptada");
				if (performAction()) {
					System.out.println("Agente "+getLocalName()+": Acción realizada con éxito");
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				}
				else {
					System.out.println("Agente "+getLocalName()+": Error en la ejecución de la acción");
					throw new FailureException("error inesperado");
				}	
			}

			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
				System.out.println("El agente "+getLocalName()+": rechaza la propuesta");
			}
		} );
	}

	private int evaluateAction() {
		// evaluación generando un número aleatorio
                ofertaPrecio.setPrecio((int) (Math.random() * 10) );
		return ofertaPrecio.getPrecio();
	}

	private boolean performAction() {
           System.out.println("Billete comprado ");
           return true;
		  
	}
}

