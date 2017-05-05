package examples.contractNet;
 
import jade.content.AgentAction;
 
public class Comprar implements AgentAction {
 
   private Billete ComprarBillete;
 
 
   public Billete getComprarBillete() {
     return ComprarBillete;
   }
 
   public void setComprarBillete(Billete b) {
     ComprarBillete = b;
   }
   //contructor
    public Comprar() {
        
    }
   
 
}
