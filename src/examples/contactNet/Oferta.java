package examples.contactNet;
 
import jade.content.Predicate;
 
public class Oferta implements Predicate {
 
   private Billete billete;

   
   public Billete getBillete() {
     return billete;
   }
 
   public void setFruta(Billete b) {
     billete = b;
   }
 
}
