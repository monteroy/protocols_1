package examples.contactNet;
 
import jade.content.Concept;

public class Billete implements Concept {
 
   private String billete;
   private int precio;
  
 
   public String getBillete() {
     return billete;
   }
 
   public void setNombre(String n) {
     billete = n;
   }
 
   public int getPrecio() {
     return precio;
   }
 
   public void setPrecio(int p) {
     precio = p;
   }
  
}
