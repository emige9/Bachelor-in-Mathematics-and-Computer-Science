import java.util.HashMap;

public class TablaSimbolos {
    
    private static HashMap<String,TIPO> TablaSimbolos;

    static {
        TablaSimbolos = new HashMap<String,TIPO>();
    }

    public static void insertar(String s, TIPO tipo){
        
        TablaSimbolos.put(s,tipo);

    }
    
    public static TIPO buscar(String s){
    
    	return TablaSimbolos.get(s);
    }

    

}
