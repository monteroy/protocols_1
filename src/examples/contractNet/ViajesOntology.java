package examples.contractNet;
 
import jade.content.onto.*;// la ontologia es la instancia de esta clase
import jade.content.schema.*;// instancias de las clases PredicateSchema (implements oferta), AgentActionSchema(implements comprar) y ConceptSchema(implements Billete)
 
public class ViajesOntology extends Ontology {
   // Nombre de la ontología
   public static final String ONTOLOGY_NAME = "ontologia de viajes";
 
  // Vocabulario de la ontología que van a manejar los agentes
  public static final String BILLETE = "billete";
  public static final String BILLETE_PRECIO = "precio";
 
  
  public static final String OFERTA = "Oferta";
  public static final String OFERTA_BILLETE = "billete";

 
  public static final String COMPRAR = "Comprar";
  public static final String COMPRAR_BILLETE = "billete";

 
  // Instancia de la ontología (que será única)
  
  private static final Ontology instancia = new ViajesOntology();
 
  // Método para acceder a la instancia de la ontología
  public static Ontology getInstance() {
     return instancia;
   }
 
   // Constructor privado
      private ViajesOntology() {
     // ViajesOntology extiende la ontología básica
     super(ONTOLOGY_NAME, BasicOntology.getInstance());
 
     try {
       // Añade los elementos
       add(new ConceptSchema(BILLETE), Billete.class);
       add(new PredicateSchema(OFERTA), Oferta.class);
       add(new AgentActionSchema(COMPRAR), Comprar.class);
 
       // Estructura del esquema para el concepto BILLETE
       ConceptSchema cs = (ConceptSchema) getSchema(BILLETE);
       cs.add(BILLETE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
       cs.add(BILLETE_PRECIO, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
   
       
       // Estructura del esquema para el predicado OFERTA
       PredicateSchema ps = (PredicateSchema) getSchema(OFERTA);
       ps.add(OFERTA_BILLETE, (ConceptSchema) getSchema(BILLETE));

       // Estructura del esquema para la acción COMPRAR
       AgentActionSchema as = (AgentActionSchema) getSchema(COMPRAR);
       as.add(COMPRAR_BILLETE, (ConceptSchema) getSchema(BILLETE));
       
     }
	 
     catch (OntologyException oe) {
         System.out.println("Se ha producido un error");
     }
   }
}