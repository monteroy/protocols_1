package examples.contactNet;
 
import jade.content.Concept;

public class Billete implements Concept {
   private String billete; 
   
   private int precio = 5;



    public String getBillete() {
        return billete;
    }
    public int getPrecio() {
        return precio;
    }

     
    public void setBillete(String billete) {
        this.billete = billete;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    
    //constructores
     public Billete() {
    }
     
    public Billete(String billeteOrigen, String billeteDestino, int precio ) {
        this.precio = precio;
        
    }
  
  
  
}
