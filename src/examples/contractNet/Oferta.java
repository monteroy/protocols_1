package examples.contractNet;
 
import jade.content.Predicate;
 
public class Oferta implements Predicate {
 
   private Billete billete;
   private int precio;

   
   public Billete getBillete() {
     return billete;
   }
 
   public void setBillete(Billete b) {
     billete = b;
   }
   
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }
 
}
