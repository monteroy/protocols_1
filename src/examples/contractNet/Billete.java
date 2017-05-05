package examples.contractNet;
 

import jade.content.Concept;
import java.time.LocalTime;
import java.util.Date;

public class Billete implements Concept {
    private String billete; 
    private String origen; 
    private String destino; 
    private LocalTime fechaHoraSalida; 
    private LocalTime fechaHoraLlegada; 
    private int precio;

    public String getBillete() {
        return billete;
    }

    public String getDestino() {
        return destino;
    }

    public String getOrigen() {
        return origen;
    }
    
    public LocalTime getFechaHoraLlegada() {
        return fechaHoraLlegada;
    }
   
    public LocalTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }
    public int getPrecio() {
        return precio;
    }

    public void setFechaHoraSalida(LocalTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }
    public void setFechaHoraLlegada(LocalTime fechaHoraLlegada) {
        this.fechaHoraLlegada = fechaHoraLlegada;
    }
    public void setBillete(String billete) {
        this.billete = billete;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

   
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    

}
